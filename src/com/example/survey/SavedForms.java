package com.example.survey;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;

public class SavedForms extends Activity implements OnItemClickListener {
	ConnectionDetector cd;
	InputStream is;
	boolean isInternetPresent = false;
	Updator updates;

	ArrayList<Complain> imageArry = new ArrayList<Complain>();
	ContactImageAdapter adapter;
	GPSTracker gps;
	String CiDateTime;
	String[] options = { "Delete", "Details" };

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		imageArry.clear();
		DataBaseHandler db = new DataBaseHandler(this);

		List<Complain> complain = db.getAllComplains();

		for (Complain cn : complain) {
			// // add contacts data in arrayList
			imageArry.add(cn);
			//
		}
		adapter = new ContactImageAdapter(this, R.layout.screen_list, imageArry);
		ListView dataList = (ListView) findViewById(R.id.list);
		dataList.setAdapter(adapter);
		registerForContextMenu(dataList);
		dataList.setOnItemClickListener(this);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.saved_forms_layout);
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		final Action otherAction = new IntentAction(this, new Intent(this,
				SelectedItemActivity.class), R.drawable.select);
		actionBar.addAction(otherAction);

		actionBar.setTitle("			Saved Records");

		actionBar.setHomeLogo(R.drawable.save_icon);
		actionBar.setDisplayHomeAsUpEnabled(true);
		cd = new ConnectionDetector(getApplicationContext());
		DataBaseHandler db = new DataBaseHandler(this);

		List<Complain> complain = db.getAllComplains();

		for (Complain cn : complain) {
			// // add contacts data in arrayList
			imageArry.add(cn);
			//
		}
		adapter = new ContactImageAdapter(this, R.layout.screen_list, imageArry);
		ListView dataList = (ListView) findViewById(R.id.list);
		dataList.setAdapter(adapter);
		dataList.setOnItemClickListener(this);

		intialize();
	}

	private void intialize() {
		// TODO Auto-generated method stub
		gps = new GPSTracker(SavedForms.this);

	}

	public class ContactImageAdapter extends ArrayAdapter<Complain> {
		Context context;
		int layoutResourceId;
		ArrayList<Complain> data = new ArrayList<Complain>();

		public ContactImageAdapter(Context context, int layoutResourceId,
				ArrayList<Complain> data) {
			super(context, layoutResourceId, data);
			this.layoutResourceId = layoutResourceId;
			this.context = context;
			this.data = data;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			ImageHolder holder = null;
			if (convertView == null) {
				LayoutInflater inflater = ((Activity) context)
						.getLayoutInflater();
				row = inflater.inflate(layoutResourceId, parent, false);
				holder = new ImageHolder();
				holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);
				holder.sub_type = (TextView) row.findViewById(R.id.txtTitle1);
				holder.imgIcon = (ImageView) row.findViewById(R.id.imgIcon);
				row.setTag(holder);
			} else {
				holder = (ImageHolder) row.getTag();
			}
			Complain picture = data.get(position);
			String type = "";
			if (picture._type.equals("1")) {
				type = "Sewerage";
			} else if (picture._type.equals("2")) {
				type = "Water Problem";

			} else if (picture._type.equals("3")) {
				type = "Drainage Channels";

			} else if (picture._type.equals("4")) {
				type = "Road Side Drains";

			} else if (picture._type.equals("5")) {
				type = "WWM";

			} else {
				type = picture._type;
			}
			String subtype = "";

			// subtype mux
			if (picture._sub_type.equals("1")) {
				subtype = "Sewer Overflow / Blockage of Sewer";
			} else if (picture._sub_type.equals("2")) {
				subtype = "Missing Manhole Cover";
			} else if (picture._sub_type.equals("3")) {
				subtype = "Broken Manhole Cover / Slab";
			} else if (picture._sub_type.equals("4")) {
				subtype = "Damaged / Settled Sewer Line";
			} else if (picture._sub_type.equals("5")) {
				subtype = "Others";
			} else if (picture._sub_type.equals("6")) {
				subtype = "Water Leakage";
			} else if (picture._sub_type.equals("7")) {
				subtype = "Contamination";
			} else if (picture._sub_type.equals("8")) {
				subtype = "Brusting of pipe";
			} else if (picture._sub_type.equals("9")) {
				subtype = "Water Shortage / low pressure";
			} else if (picture._sub_type.equals("10")) {
				subtype = "Others";
			} else if (picture._sub_type.equals("11")) {
				subtype = "Others";
			} else if (picture._sub_type.equals("12")) {
				subtype = "Breaching of Channel";
			} else if (picture._sub_type.equals("13")) {
				subtype = "Damaged / Broken Old Wall / Slab";
			} else if (picture._sub_type.equals("14")) {
				subtype = "Overflow";
			} else if (picture._sub_type.equals("15")) {
				subtype = "Removal of solid waste / Garbage / Silt";
			} else if (picture._sub_type.equals("16")) {
				subtype = "Blockage of Covered / Opened portion";
			} else if (picture._sub_type.equals("17")) {
				subtype = "Others";
			} else if (picture._sub_type.equals("18")) {
				subtype = "Broken Slab";
			} else if (picture._sub_type.equals("19")) {
				subtype = "Removal of Solid Waste / Garbage";
			} else if (picture._sub_type.equals("20")) {
				subtype = "Overflow";
			} else if (picture._sub_type.equals("21")) {
				subtype = "Connected wiht WASA / or Not";
			} else if (picture._sub_type.equals("22")) {
				subtype = "Blockage of Covered / Open poriton";
			} else if (picture._sub_type.equals("23")) {
				subtype = "Others";
			} else if (picture._sub_type.equals("24")) {
				subtype = "Power Failure";
			} else if (picture._sub_type.equals("25")) {
				subtype = "Internal Fault/Screening chamber problem";
			} else if (picture._sub_type.equals("26")) {
				subtype = "Internal Fault/Mechanical";
			} else if (picture._sub_type.equals("27")) {
				subtype = "Internal Fault/Electrical";
			} else if (picture._sub_type.equals("28")) {
				subtype = "Others";
			} else {
				subtype = picture._sub_type;
			}
			holder.txtTitle.setText(type);
			holder.sub_type.setText(subtype);
			// convert byte to bitmap take from contact class
			byte[] outImage = picture._image;
			ByteArrayInputStream imageStream = new ByteArrayInputStream(
					outImage);
			Bitmap theImage = BitmapFactory.decodeStream(imageStream);
			holder.imgIcon.setImageBitmap(theImage);
			final int temp = position;
			return row;
		}

		class ImageHolder {
			ImageView imgIcon;
			TextView txtTitle;
			TextView sub_type;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if (cd.isConnectingToInternet()) {
			Complain complain = new Complain(imageArry.get(arg2)._id,
					imageArry.get(arg2)._type, imageArry.get(arg2)._sub_type,
					imageArry.get(arg2)._lat, imageArry.get(arg2)._lng, "", "",
					imageArry.get(arg2)._date, imageArry.get(arg2)._role,
					imageArry.get(arg2)._image);
			Intent intent = new Intent(getApplicationContext(),
					FinalComplainView.class);
			Parcelablecomplain parcelableComplain = new Parcelablecomplain(
					complain);
			intent.putExtra("complain", parcelableComplain);
			startActivity(intent);
		} else {
			Toast.makeText(getApplicationContext(),
					"Sorry no internet present", Toast.LENGTH_SHORT).show();
		}
	}

	public static Intent createIntent(Context context) {
		Intent i = new Intent(context, SavedForms.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return i;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.list) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle(imageArry.get(info.position)._type);
			String[] menuItems = options;
			for (int i = 0; i < menuItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int menuItemIndex = item.getItemId();
		String[] menuItems = options;
		String menuItemName = menuItems[menuItemIndex];
		final Complain listItemComplain = imageArry.get(info.position);
		if (menuItemName == options[0]) {
			// do delete

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);

			// set title
			alertDialogBuilder.setTitle("Delete Selected Items");

			// set dialog message
			alertDialogBuilder
					.setMessage("Are you sure you want to delete these items?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

									DataBaseHandler db = new DataBaseHandler(
											getBaseContext());
									db.deleteComplain(new Complain(
											listItemComplain._id,
											listItemComplain._type,
											listItemComplain._sub_type,
											listItemComplain._lat,
											listItemComplain._lng, "", "",
											listItemComplain._date, "",
											listItemComplain.getImage()));

									SavedForms.this.onResume();

									Toast.makeText(getBaseContext(),
											"Record Deleted...",
											Toast.LENGTH_SHORT).show();
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									dialog.cancel();
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();

		} else if (menuItemName == options[1]) {
			// do details

			Dialog dialog = new Dialog(this, android.R.style.Theme_Dialog);
			dialog.setContentView(R.layout.custom_info_dialog);
			dialog.getWindow().getAttributes().width = LayoutParams.WRAP_CONTENT;
			dialog.getWindow().getAttributes().height = LayoutParams.WRAP_CONTENT;
			dialog.setTitle("complain Detail");
			TextView type = (TextView) dialog.findViewById(R.id.TVTypeC);
			type.setText(listItemComplain._type);
			TextView stype = (TextView) dialog.findViewById(R.id.TVSubType);
			stype.setText(listItemComplain._sub_type);
			TextView time = (TextView) dialog.findViewById(R.id.TVDate);
			time.setText(listItemComplain._date);

			dialog.show();
		}

		return true;
	}
}
