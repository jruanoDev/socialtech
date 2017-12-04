package com.github.jruanodev.socialtech.dao;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

@SuppressLint("ParcelCreator")
public class Contact implements Parcelable{
    private String name;
    private String phone;
    private String email;
    private int age;
    private String sex;
    private String formation;

    public Contact(String name, String phone, String email, int age, String sex, String formation) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.sex = sex;
        this.formation = formation;
    }

    public HashMap<String, String> toMap() {
        HashMap<String, String> contactData = new HashMap<>();

        contactData.put("name", name);
        contactData.put("phone", phone);
        contactData.put("email", email);
        contactData.put("age", Integer.toString(age));
        contactData.put("sex", sex);
        contactData.put("formation", formation);

        return contactData;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getFormation() {
        return formation;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", formation='" + formation + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] {
                this.name,
                this.phone,
                this.email,
                Integer.toString(this.age),
                this.sex,
                this.formation
        });
    }
}
