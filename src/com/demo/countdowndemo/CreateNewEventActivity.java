package com.demo.countdowndemo;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class CreateNewEventActivity extends Activity {
	//Declare the buttons
	Button btn_pickDate;
	Button btn_pickTime;
	Button btn_pickPhoto;
	Button btn_submit;
	EditText edt_title;
	Date dateFull;

	private int year;
	private int month;
	private int day;
	private int hour;
	private int min;
	private Uri selectedImage;

	private static final int DATE_DIALOG_ID = 998;
	private static final int TIME_DIALOG_ID = 999;
	private static final int PICK_PHOTO = 997;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR_OF_DAY);
		min = c.get(Calendar.MINUTE);

		edt_title = (EditText) findViewById(R.id.editText1);

		btn_pickDate = (Button) findViewById(R.id.btn_datePicker);
		btn_pickDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID);
			}
		});

		btn_pickTime = (Button) findViewById(R.id.btn_timePiker);
		btn_pickTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog(TIME_DIALOG_ID);
			}
		});

		btn_pickPhoto = (Button) findViewById(R.id.btn_photoPiker);
		btn_pickPhoto.setOnClickListener(new OnClickListener() {
			//open the gallery and picks a image
			@Override
			public void onClick(View arg0) {
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, PICK_PHOTO);
			}
		});

		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(new OnClickListener() {
			//Gather all the info the user puts in and send them back to the main activity
			@Override
			public void onClick(View v) {
				Intent returnIntent = new Intent();
				returnIntent.putExtra("title", edt_title.getText().toString());
				returnIntent.putExtra("year", year);
				returnIntent.putExtra("month", month);
				returnIntent.putExtra("day", day);
				returnIntent.putExtra("hour", hour);
				returnIntent.putExtra("minutes", min);
				returnIntent.putExtra("selectedImage",
						(selectedImage != null) ? selectedImage : Uri.EMPTY);
				returnIntent.putExtra("dateFull", dateFull);

				setResult(RESULT_OK, returnIntent);
				finish();
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, timePickerListener, hour, min,
					true);
		}

		return null;
	}

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			hour = hourOfDay;
			min = minute;

			// time display

			TextView makeText = (TextView) findViewById(R.id.textView3);
			makeText.setText(hour + ":" + min);
		}
	};

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			dateFull = new Date(view.getYear() - 1900, view.getMonth(),
					view.getDayOfMonth());

			// date display:
			TextView makeText = (TextView) findViewById(R.id.textView2);
			makeText.setText((month + 1) + "/" + day + "/" + year);

		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

		switch (requestCode) {
		case PICK_PHOTO:
			if (resultCode == RESULT_OK) {
				selectedImage = imageReturnedIntent.getData();
			}
		}
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.create_new, menu);
//		return true;
//	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case android.R.id.home:
//			// This ID represents the Home or Up button. In the case of this
//			// activity, the Up button is shown. Use NavUtils to allow users
//			// to navigate up one level in the application structure. For
//			// more details, see the Navigation pattern on Android Design:
//			//
//			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
//			//
//			NavUtils.navigateUpFromSameTask(this);
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}