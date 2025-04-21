import cv2
from matplotlib import pyplot as plt
import numpy as np
import imutils
import easyocr 
import sys

# Read the image path from command line arguments
if len(sys.argv) != 2:
    print("Usage: python number_plate_detection.py <image_path>")
    sys.exit(1)

image_path = sys.argv[1]

# Step 1: Load and preprocess the image
img = cv2.imread(image_path)
if img is None:
    print("Error: Unable to load image. Check the file path.")
    sys.exit(1)

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

    # Step 5: OCR for text detection
    reader = easyocr.Reader(['en'])
    result = reader.readtext(cropped_image)  # OCR only on cropped image (number plate region)

    # Extract detected text
    if result:
        detected_plate = " ".join([res[1] for res in result])  # Combine all detected text parts
        print(detected_plate)
    else:
        print("No plate detected.")
else:
    print("No plate detected.")
