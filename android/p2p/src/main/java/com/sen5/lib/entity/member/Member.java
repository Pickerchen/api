package com.sen5.lib.entity.member;

import android.os.Parcel;
import android.os.Parcelable;

public class Member implements Parcelable{

	/** Icon的图标路径 */
	private String iconPath;
	private String identity_id;
	private String identity_name;
	private String phone;
	
	public Member() {

	}

	protected Member(Parcel in) {
		identity_id = in.readString();
		identity_name = in.readString();
		iconPath = in.readString();
		phone = in.readString();
	}

	public static final Creator<Member> CREATOR = new Creator<Member>() {
		@Override
		public Member createFromParcel(Parcel in) {
			return new Member(in);
		}

		@Override
		public Member[] newArray(int size) {
			return new Member[size];
		}
	};

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getIdentity_id() {
		return identity_id;
	}


	public void setIdentity_id(String identity_id) {
		this.identity_id = identity_id;
	}

	public String getIdentity_name() {
		return identity_name;
	}

	public void setIdentity_name(String identity_name) {
		this.identity_name = identity_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(identity_id);
		dest.writeString(identity_name);
		dest.writeString(iconPath);
		dest.writeString(phone);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Member){
			if(((Member)obj).getIdentity_id().equals(identity_id)){
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Member{" +
				"identity_id='" + identity_id + '\'' +
				", identity_name='" + identity_name + '\'' +
				",iconPath='" + iconPath + '\'' +
				", phone='" + phone + '\'' +
				'}';
	}
}



