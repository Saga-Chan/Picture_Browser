import argparse as ap
import cv2
import numpy as np
import os
from sklearn.externals import joblib
from scipy.cluster.vq import *

from sklearn import preprocessing
import math

# Get the training classes names and store them in a list
train_path = "/home/decanter/PycharmProjects/Picture_Browser/ServerREST/traitement/retriever/dataset-retr/train"

training_names = os.listdir(train_path)

numWords = 1000

# Get all the path to the images and save them in a list
# image_paths and the corresponding label in image_paths
image_paths = []
for training_name in training_names:
    image_path = os.path.join(train_path, training_name)
    image_paths += [image_path]

# Create feature extraction and keypoint detector objects
# fea_det = cv2.FeatureDetector_create("SIFT")
# des_ext = cv2.DescriptorExtractor_create("SIFT")

# List where all the descriptors are stored
des_list = []
sift = cv2.xfeatures2d.SIFT_create()

for i, image_path in enumerate(image_paths):
    im = cv2.imread(image_path)
    print("Extract SIFT of %s image, %d of %d images" % (training_names[i], i, len(image_paths)))
    gray = cv2.cvtColor(im, cv2.COLOR_BGR2GRAY)
    kp, des = sift.detectAndCompute(gray, None)
    des_list.append((image_path, des))

# Stack all the descriptors vertically in a numpy array
# downsampling = 1
# descriptors = des_list[0][1][::downsampling,:]
# for image_path, descriptor in des_list[1:]:
#    descriptors = np.vstack((descriptors, descriptor[::downsampling,:]))

# Stack all the descriptors vertically in a numpy array
descriptors = des_list[0][1]
for image_path, descriptor in des_list[1:]:
    descriptors = np.vstack((descriptors, descriptor))

# Perform k-means clustering

voc, variance = kmeans(descriptors, numWords, 1)

# Calculate the histogram of features
im_features = np.zeros((len(image_paths), numWords), "float32")
for i in range(len(image_paths)):
    words, distance = vq(des_list[i][1], voc)
    for w in words:
        im_features[i][w] += 1

# Perform Tf-Idf vectorization
nbr_occ = np.sum((im_features > 0) * 1, axis=0)
idf = np.array(np.log((1.0 * len(image_paths) + 1) / (1.0 * nbr_occ + 1)), 'float32')

# Perform L2 normalization
im_features = im_features * idf
im_features = preprocessing.normalize(im_features, norm='l2')

joblib.dump((im_features, image_paths, idf, numWords, voc), "bof_retr.pkl", compress=3)