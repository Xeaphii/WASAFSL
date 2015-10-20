package com.example.survey;

import android.os.Parcel;
import android.os.Parcelable;

public class Parcelablecomplain implements Parcelable {
	private Complain complain;
	public byte[] image;

	public Complain getComplain() {
		return complain;
	}

	public Parcelablecomplain(Complain _complain) {
		super();
		this.complain = _complain;
	}

	private Parcelablecomplain(Parcel in) {
		complain = new Complain();
		complain._id = in.readInt();
		complain._type = in.readString();
		complain._sub_type = in.readString();
		complain._lat = in.readString();
		complain._lng = in.readString();
		complain._date = in.readString();
		image = new byte[in.readInt()];
		in.readByteArray(image);
		complain._image = image;
	}

	/*
	 * you can use hashCode() here.
	 */
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(complain._id);
		parcel.writeString(complain._type);
		parcel.writeString(complain._sub_type);
		parcel.writeString(complain._lat);
		parcel.writeString(complain._lng);
		parcel.writeString(complain._date);
		parcel.writeInt(complain._image.length);
		parcel.writeByteArray(complain._image);
	}

	/*
	 * Parcelable interface must also have a static field called CREATOR, which
	 * is an object implementing the Parcelable.Creator interface. Used to
	 * un-marshal or de-serialize object from Parcel.
	 */
	public static final Parcelable.Creator<Parcelablecomplain> CREATOR = new Parcelable.Creator<Parcelablecomplain>() {
		public Parcelablecomplain createFromParcel(Parcel in) {
			return new Parcelablecomplain(in);
		}

		public Parcelablecomplain[] newArray(int size) {
			return new Parcelablecomplain[size];
		}
	};
}
