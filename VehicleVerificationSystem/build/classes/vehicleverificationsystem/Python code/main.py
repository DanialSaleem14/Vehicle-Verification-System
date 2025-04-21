import cv2 from matplotlib 
import pyplot as plt
import numpy as np
import imutils
import easyocr

# Step 1: Load and preprocess the image
img = cv2.imread('image1.jpg')
gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

# Step 2: Noise reduction and edge detection
bfilter = cv2.bilateralFilter(gray, 11, 17, 17)  # Noise reduction
edged = cv2.Canny(bfilter, 30, 200)  # Edge detection

# Step 3: Contour detection
keypoints = cv2.findContours(edged.copy(), cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
contours = imutils.grab_contours(keypoints)
contours = sorted(contours, key=cv2.contourArea, reverse=True)[:10]
location = None
for contour in contours:
    approx = cv2.approxPolyDP(contour, 10, True)
    if len(approx) == 4:  # We need a quadrilateral (4 points)
        location = approx
        break

# Step 4: Mask and extract number plate region
if location is not None:
    mask = np.zeros(gray.shape, np.uint8)
    new_image = cv2.drawContours(mask, [location], 0, 255, -1)
    new_image = cv2.bitwise_and(img, img, mask=mask)

    # Get the bounding box of the detected region (number plate)
    (x, y, w, h) = cv2.boundingRect(location)
    cropped_image = gray[y:y+h, x:x+w]  # Crop the number plate region

    # Display the cropped number plate
    plt.imshow(cv2.cvtColor(cropped_image, cv2.COLOR_BGR2RGB))
    plt.show()

    # Step 5: OCR for text detection
    reader = easyocr.Reader(['en'])
    result = reader.readtext(cropped_image)  # OCR only on cropped image (number plate region)

    # Step 6: Format results from OCR
    detections = [
        (detection[0], detection[1], detection[2]) for detection in result
    ]

    # Display detected text and confidence scores
    for detection in detections:
        print(f"Detected Text: {detection[1]}, Confidence: {detection[2]:.2f}")

    # Step 7: Draw the detected text on the original image
    for detection in result:
        box = detection[0]  # Bounding box points
        text = detection[1]  # Detected text
        confidence = detection[2]  # Confidence score

        # Map box coordinates back to the original image
        top_left = (int(box[0][0]) + x, int(box[0][1]) + y)  # Offset by crop coordinates
        bottom_right = (int(box[2][0]) + x, int(box[2][1]) + y)

        # Draw the bounding box on the original image
        cv2.rectangle(img, top_left, bottom_right, (0, 255, 0), 3)

        # Put detected text above the bounding box
        text_position = (top_left[0], top_left[1] - 10)  # Slightly above the box
        cv2.putText(img, text, text_position, fontFace=cv2.FONT_HERSHEY_SIMPLEX,
                    fontScale=0.9, color=(0, 255, 0), thickness=2, lineType=cv2.LINE_AA)

    # Display the final result
    plt.imshow(cv2.cvtColor(img, cv2.COLOR_BGR2RGB))
    plt.show()

else:
    print("No valid contour found for number plate.")
