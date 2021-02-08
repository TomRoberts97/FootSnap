# FootSnap
FootSnap.
An Android application created for my final year university project.
Purpose of application is to take pictures of diabetic feet.
To track the progress of diabetic foot ulcers.

Firstly a user takes a photo of one of their feet.
Using the New Photo page.

This image is saved to the phone using the Users details.
Over time more images will be taken of the users foot.

When a user goes to take a another photo a ghost image is placed over the camera.
A ghost image is an outline of the users foot.
So all images can be lined up accurately.

The ghost image is created using the first image taken by the user.
Ghost image is created using a canny edge detection algorithm (OpenCV).

Having accurately lined up images allows for checks to be done, too see if the foot is growing in any way.
Growth could indicate a developing foot ulcer.
Early detection of foot ulcers is very important, if they are left to grow then huge complications can occur.

Main Features
- Login page
- Menu page -> used for naviagtion around application
- Take new photo page -> used to take a users first foot image.
- Take another photo page -> used to take all other images of user foot. 
