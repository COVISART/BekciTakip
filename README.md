Bekçi Takip Sistemi  [![Download](https://api.bintray.com/packages/dlazaro66/maven/QRCodeReaderView/images/download.svg) ](https://bintray.com/dlazaro66/maven/QRCodeReaderView/_latestVersion) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-QRCodeReaderView-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1891) [![Build Status](https://travis-ci.org/dlazaro66/QRCodeReaderView.svg?branch=master)](https://travis-ci.org/dlazaro66/QRCodeReaderView)
===

### Disclaimer: This project is used to track guard using QR code ###

#### Modification of ZXING Barcode Scanner project for easy Android QR-Code detection and AR purposes. ####

This project implements an Android view which show camera and notify when there's a QR code inside the preview.

Some Classes of camera controls and autofocus are taken and slightly modified from Barcode Scanner Android App.

You can also use this for Augmented Reality purposes, as you get QR control points coordinates when decoding.
### Dhis project use javax mail to send mail house owner, mail detected on QR code  ###
Usage
-----

- Add a "QRCodeReaderView" in the layout editor like you actually do with a button for example.
- In your onCreate method, you can find the view as usual, using findViewById() function.
- Create an Activity which implements `onQRCodeReadListener`, and let implements required methods or set a `onQRCodeReadListener` to the QRCodeReaderView object
- Make sure you have camera permissions in order to use the library. (https://developer.android.com/training/permissions/requesting.html)

```xml

 <com.dlazaro66.qrcodereaderview.QRCodeReaderView
        android:id="@+id/qrdecoderview"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

```

- Start & Stop camera preview in onPause() and onResume() overriden methods.
- You can place widgets or views over QRDecoderView.
 
```java
public class DecoderActivity extends Activity implements OnQRCodeReadListener {

    private TextView resultTextView;
	private QRCodeReaderView qrCodeReaderView;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder);
        
        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setOnQRCodeReadListener(this);

    	  // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(2000L);

        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true);

        // Use this function to set front camera preview
        qrCodeReaderView.setFrontCamera();

        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();
    }

    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed in View
	@Override
	public void onQRCodeRead(String text, PointF[] points) {
		resultTextView.setText(text);
	}
    
	@Override
	protected void onResume() {
		super.onResume();
		qrCodeReaderView.startCamera();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		qrCodeReaderView.stopCamera();
	}
}
```

