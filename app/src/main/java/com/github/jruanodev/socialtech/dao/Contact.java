package com.github.jruanodev.socialtech.dao;

public class Contact {
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
}
