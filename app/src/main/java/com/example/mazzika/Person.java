package com.example.mazzika;

public abstract class Person {
    private Name name;
    private String country, phone, profileImageUrl;

    public Person(String phone) {
        this.phone = phone;
    }

    public Person() {
        name = new Name("", "", "");
        country = "";
        phone = "";
        profileImageUrl = "";
    }

    public Person(Name name, String country, String phone, String profileImageUrl) {
        this.name = name;
        this.country = country;
        this.phone = phone;
        this.profileImageUrl = profileImageUrl;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    public Name getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }
}
