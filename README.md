# Bubble Level(Getting Device Orientation)
1. This is an app which gives you the orientation of a device.
2. Sensor Used - Orientation Sensor(TYPE_ORIENTATION)
3. Database used- SQLite

## Brief Description
- Gives orientation of the device using orientation sensor.
- Getting roll and pitch of the device.
Roll is rotation of the device around front to back axis.(Vertical orientation)
Pitch is rotation around side to side axis.(Horizontal orientation)
- Using pitch an roll value as translateX and translateY values and animating using valueAnimator.
- Have used SQLite database by room library for storing the last 500 orientation values(pitch and roll values).
- These orientations values are displayed using ListView
- Maximum and minimum of the last 500 orientations is found out using Math.max and Math.min function and displayed on the top of the page using TextView.
- Database used is SQLite because we have to store data i.e the last 500 orientations value locally.


## App apk
-(click here)[https://drive.google.com/file/d/18uusUcCfBmaLTART_f0Wzoqv_DROPYdj/view?usp=sharing]






