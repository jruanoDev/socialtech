package com.github.jruanodev.socialtech.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class Business implements Parcelable {
    private String name;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String country;
    private String info;

    public Business(String name, String email, String phone, String address, String city,
                    String country, String info) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.city = city;
        this.country = country;
        this.info = info;
    }

    protected Business(Parcel in) {
        name = in.readString();
        phone = in.readString();
        email = in.readString();
        address = in.readString();
        city = in.readString();
        country = in.readString();
        info = in.readString();
    }

    public static final Creator<Business> CREATOR = new Creator<Business>() {
        @Override
        public Business createFromParcel(Parcel in) {
            return new Business(in);
        }

        @Override
        public Business[] newArray(int size) {
            return new Business[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return "Business{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

    public HashMap<String, String> toMap() {
        HashMap<String, String> businessMap = new HashMap<>();

        businessMap.put("name", name);
        businessMap.put("phone", phone);
        businessMap.put("email", email);
        businessMap.put("address", address);
        businessMap.put("city", city);
        businessMap.put("country", country);
        businessMap.put("info", info);

        return businessMap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(address);
        dest.writeString(city);
        dest.writeString(country);
        dest.writeString(info);
    }
}
