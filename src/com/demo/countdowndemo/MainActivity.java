package com.demo.countdowndemo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MainActivity extends Activity {

	private static final int CREATE_NEW_EVENT_ACTIVITY = 1;

	private int year;
	private int month;
	private int day;
	private int hour;
	private int min;
	private Date dateFull;
	private Uri selectedImage;
	private String title;
	public LinkedList<UserEvent> myData = new LinkedList<UserEvent>();
	public ListView listView;
	public CustomAdapter adapter;

	public class UserEvent {
		private int cyear;
		private int cmonth;
		private int cday;
		private int chour;
		private int cmin;
		private Uri cselectedImage;
		private String ctitle;
		private Date cDate;

		public UserEvent(int year, int month, int day, int hour, int min,
				Uri image, String title, Date fullDate) {
			this.cyear = year;
			this.cmonth = month;
			this.cday = day;
			this.chour = hour;
			this.cmin = min;
			this.cselectedImage = image;
			this.ctitle = title;
			this.cDate = fullDate;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.eventsListView);
		adapter = new CustomAdapter(this, myData);
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// this method is used to start to create a new CreateNewEventactivity
	public void createNewEvent(View v) {
		Intent intent = new Intent(this, CreateNewEventActivity.class);
		startActivityForResult(intent, CREATE_NEW_EVENT_ACTIVITY);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

			if (resultCode == RESULT_OK) {
				title = data.getStringExtra("title");
				year = data.getIntExtra("year", 1970);
				month = data.getIntExtra("month", 1);
				day = data.getIntExtra("day", 1);
				hour = data.getIntExtra("hour", 12);
				min = data.getIntExtra("minutes", 0);
				selectedImage = data.getParcelableExtra("selectedImage");
				dateFull = (Date) data.getSerializableExtra("dateFull");

				// Log.d("MainActivity", "Return input: " + year + " " + month +
				// " " + day + " " + hour + " " + min + " " +
				// selectedImage.getPath().toString() + " " + title);

				UserEvent event = new UserEvent(year, month, day, hour, min,
						selectedImage, title, dateFull);
				myData.addFirst(event);

				adapter.notifyDataSetChanged();
			}
			if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "No data returned", Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	public class CustomAdapter extends ArrayAdapter<UserEvent> {
		Context mContext;
		LinkedList<UserEvent> data;

		public class ViewHolder {
			public TextView txtEvent;
			public TextView txtDayLeft;
			public TextView txtDay;
			public ImageView imgIcon;
		}

		public CustomAdapter(Context context, LinkedList<UserEvent> data) {
			super(context, R.layout.item_view, data);
			this.mContext = context;
			this.data = data;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;

			if (rowView == null) {
				LayoutInflater inflater = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				rowView = inflater.inflate(R.layout.item_view, parent, false);
				ViewHolder holder = new ViewHolder();
				holder.imgIcon = (ImageView) rowView
						.findViewById(R.id.item_icon);
				holder.txtEvent = (TextView) rowView
						.findViewById(R.id.item_txtEvent);
				holder.txtDayLeft = (TextView) rowView
						.findViewById(R.id.item_txtDayLeft);
				holder.txtDay = (TextView) rowView
						.findViewById(R.id.item_txtDay);
				rowView.setTag(holder);
			}
			//for background alternative with 3 colors rows
			for (int i=0; i<=position; i++)
			{
				
				
				if (position == i) {
					rowView.setBackgroundColor(Color.parseColor("#75D7A3"));//green
				}
				i++;
				if(position == i){
					rowView.setBackgroundColor(Color.parseColor("#C375EB"));//purple
				}
				i++;
				if(position == i) {
					rowView.setBackgroundColor(Color.parseColor("#F6A33B"));//orange
				}
				
			}
			
			//this is to alternate 2 colors of the rows 
//			if (position % 2 == 1) {
//				rowView.setBackgroundColor(Color.parseColor("#75D7A3"));//2nd row color
//			}else {
//				rowView.setBackgroundColor(Color.parseColor("#F6A33B"));//1st row color
//			}
//          #C375EB purple
			ViewHolder holder = (ViewHolder) rowView.getTag();

			holder.txtEvent.setText(data.get(position).ctitle);
			//set future date to current date to start with 
			Date futureDate = new Date();
			if (data.get(position).cDate != null) {
				//if user select a date we set the future date to that
				futureDate = data.get(position).cDate;
			}
			//it format the date object to a human readable format
			String ft = new SimpleDateFormat("EEEE, MMM-dd-yyyy")
					.format(futureDate);

			holder.txtDay.setText(ft);

			//checks to see if the user selected a valid image, if yes we use it, if not will display the default image
			if (data.get(position).cselectedImage != null
					&& !data.get(position).cselectedImage.getPath().isEmpty()
					&& data.get(position).cselectedImage != Uri.EMPTY) {
				Picasso.with(mContext).load(data.get(position).cselectedImage)
						.skipMemoryCache().into(holder.imgIcon);
			} else {
				Picasso.with(mContext).load(android.R.drawable.ic_menu_today)
						.skipMemoryCache().into(holder.imgIcon);
			}
			
			//it calculates the time difference between future date and current date
			Date currentDate = new Date();

			long daysLeft = futureDate.getTime() - currentDate.getTime();
			holder.txtDayLeft.setText((daysLeft / (1000 * 60 * 60 * 24))
					+ " day(s) left.");

			//on click start new intent and transports the info needed to display in the new activity
			final String pTitle = data.get(position).ctitle;
			final Uri pImageUri = data.get(position).cselectedImage;
			final long pDaysLeft = daysLeft;
			rowView.setClickable(true);
			rowView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(),
							ViewEventActivity.class);
					intent.putExtra("title", pTitle);
					intent.putExtra("image", pImageUri);
					intent.putExtra(
							"daysLeft",
							((pDaysLeft / (1000 * 60 * 60 * 24)) + " day(s) left."));

					startActivity(intent);
				}
			});

			return rowView;
		}
	}
}