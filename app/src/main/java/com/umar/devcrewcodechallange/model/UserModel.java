package com.umar.devcrewcodechallange.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by UmarQasim on 9/20/2016.
 */
public class UserModel implements Parcelable{

    private String firstName;
    private String lastName;

    public UserModel(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserModel(Parcel in){
        String[] data = new String[2];

        in.readStringArray(data);
        this.firstName = data[0];
        this.lastName = data[1];
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] {this.firstName,
                this.lastName});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
