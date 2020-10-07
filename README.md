# TriangularShadeImageView
A simple image view which draws opaque triangular shades on top of image view in animated fashion.


## Demo
<p float="left">
  <img src="demo/gif_triangular.gif" width="250" />
</p>


## Install
```gradle
dependencies {
  implementation 'com.viceboy.library:triangularshadeimageview:1.0.1'
}
```


## Usage

### XML

```xml

 <?xml version="1.0" encoding="utf-8"?>
 <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <com.viceboy.widget.TriangularShadeImageView
        android:id="@+id/tvShade"
        android:layout_width="match_parent"
        android:layout_height="260dp"
        android:src="@drawable/img_nature_blue"
        app:firstTriangleAnimDelay="80"
        app:firstTriangleAnimDuration="200"
        app:firstTriangleDrawDir="TOP_LEFT"
        app:firstTriangleSize="100dp"
        app:layout_constraintTop_toTopOf="parent"
        app:secondTriangleAnimDelay="50"
        app:secondTriangleAnimDuration="200"
        app:secondTriangleDrawDir="BOTTOM_RIGHT"
        app:secondTriangleSize="150dp"
        app:startPtForAnimFirstTriangle="80"
        app:startPtForAnimSecondTriangle="-100"
        app:startPtForAnimThirdTriangle="100"
        app:thirdTriangleAnimDelay="60"
        app:thirdTriangleAnimDuration="200"
        app:thirdTriangleDrawDir="BOTTOM_LEFT"
        app:thirdTriangleSize="250dp" />

 </androidx.constraintlayout.widget.ConstraintLayout>

```


### Kotlin

### Basic Usage
```kotlin

    class MainActivity : Activity() {
        override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          requestWindowFeature(Window.FEATURE_NO_TITLE)
          setContentView(R.layout.activity_main)
        }

        override fun onWindowFocusChanged(hasFocus: Boolean) {
           tvShade.shadeColor = ContextCompat.getColor(this,android.R.color.holo_orange_light)
           tvShade.postDelayed( {
              tvShade.animateDraw()
           },40)
           super.onWindowFocusChanged(hasFocus)
        }
}
```

### Attributes

* **shadeColor**, triangular shade color to be overcast on image view.
* **firstTriangleSize**, the length of first triangle.
* **secondTriangleSize**, the length of second triangle.
* **thirdTriangleSize**, the length of third triangle.
* **firstTriangleDrawDir**, layout direction for first triangle.
* **secondTriangleDrawDir**, layout direction for second triangle.
* **thirdTriangleDrawDir**, layout direction for third triangle.
* **firstTriangleAnimDelay**, start delay for drawing animation of first triangle.
* **secondTriangleAnimDelay**, start delay for drawing animation of second triangle.
* **thirdTriangleAnimDelay**, start delay for drawing animation of third triangle.
* **firstTriangleAnimDuration**, animation duration to draw first triangle from startPtForAnimFirstTriangle.
* **secondTriangleAnimDuration**, animation duration to draw first triangle from startPtForAnimSecondTriangle.
* **thirdTriangleAnimDuration**, animation duration to draw first triangle from thirdPtForAnimFirstTriangle.
* **startPtForAnimFirstTriangle**, starting point for animation to draw first triangle.
* **startPtForAnimSecondTriangle**, starting point for animation to draw second triangle.
* **startPtForAnimThirdTriangle**, starting point for animation to draw third triangle.


## License

```
   Copyright 2020 Sumit Jha

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

