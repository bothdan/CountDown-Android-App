package com.demo.countdowndemo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewEventActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_event);

		//Obtain the width and height of the screen
		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth(); // deprecated
		int height = display.getHeight(); // deprecated

		//it collects the information from the originated activity list
		Bundle bundle = getIntent().getExtras();
		String title = bundle.getString("title");
		Uri image = bundle.getParcelable("image");
		String daysLeft = bundle.getString("daysLeft");
		//display the title
		TextView txtTitle = (TextView) findViewById(R.id.title);
		txtTitle.setText(title);
		//display days left
		TextView txtDaysLeft = (TextView) findViewById(R.id.item_txtDayLeft_detail);
		txtDaysLeft.setText(daysLeft);
		//display the image
		ImageView imageView = (ImageView) findViewById(R.id.imageView_image);
		if (image != null && !image.getPath().isEmpty() && image != Uri.EMPTY) {
			Picasso.with(getApplicationContext()).load(image).skipMemoryCache()
					.resize(width, height / 2).centerCrop().into(imageView);
		}
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.view_event, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case android.R.id.home:
	// // This ID represents the Home or Up button. In the case of this
	// // activity, the Up button is shown. Use NavUtils to allow users
	// // to navigate up one level in the application structure. For
	// // more details, see the Navigation pattern on Android Design:
	// //
	// //
	// http://developer.android.com/design/patterns/navigation.html#up-vs-back
	// //
	// NavUtils.navigateUpFromSameTask(this);
	// return true;
	// }
	// return super.onOptionsItemSelected(item);
	// }
}