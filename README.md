# Result Visualizer
<p align="center">
  <img src="https://github.com/sahajb/Result-Visualizer/blob/master/Result%20Visualizer/app/src/main/res/drawable/icon_launcher.9.png" width="450" height="450"/>
</p>

An Android Application that generates and displays a comprehensive report on every semester’s result after accepting a DTU Roll. No . 

* The Application uses data gathered from DTU PDF's using various Data Cleaning Techniques to form a JSON file forming the source of the information that is obtained from an online datastore in the Application

* Using systemized Visualization and Analysis on the obtained data Statistics and Summary of each student were generated

* The Application renders Interactive Graphs ( GPA , Credits , Rank ) during runtime with the extended option of being able to save to a device as JPEG Image 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See Deployment for notes on how to deploy the project on a live system.

This application uses the Gradle build system. To build this project, use the `gradlew build` command or use `Import Project` in Android Studio.

### Requirements:

* Android Studio

* Gradle

* Android SDK (21 or higher)

* Refer [build.gradle - App Level](https://github.com/sahajb/Result-Visualizer/blob/master/Result%20Visualizer/app/build.gradle) and [build.gradle - Project Level](https://github.com/sahajb/Result-Visualizer/blob/master/Result%20Visualizer/build.gradle) and [gradle-wrapper.properties](https://github.com/sahajb/Result-Visualizer/blob/master/Result%20Visualizer/gradle/wrapper/gradle-wrapper.properties) for further details of the versions used to avoid any incompatibility issues

## Deployment

Run using :-

* Android device ( Phones , Tablets ) 

* Android Virtual Device ( AVD )

which contains SDK *21 or higher*

## Built With

* [AndroidStudio](https://developer.android.com/studio/) - The Android IDE used

* [Gradle](https://gradle.org/) - Build system

## Authors

* **Sahaj Bhalla** - [sahajb](https://github.com/sahajb)

## License

This project is licensed under the MIT License - see the [LICENSE.md](https://github.com/sahajb/Result-Visualizer/blob/master/LICENSE) file for details

## Additional Notes

* The source of the PDF's can be found here [DTU Results](http://exam.dtu.ac.in/result.htm)

## Acknowledgments

* [StackOverflow](https://stackoverflow.com/)

* [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)

* [FixAppBarLayoutBehavior](https://gist.github.com/chrisbanes/8391b5adb9ee42180893300850ed02f2)