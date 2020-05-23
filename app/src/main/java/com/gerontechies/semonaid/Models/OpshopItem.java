package com.gerontechies.semonaid.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OpshopItem {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "opshop_name")
    public String opshop_name;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "street")
    public String street;

    @ColumnInfo(name = "suburb")
    public String suburb;

    @ColumnInfo(name = "monday")
    public String monday;

    @ColumnInfo(name = "tuesday")
    public String tuesday;

    @ColumnInfo(name = "wednesday")
    public String wednesday;

    @ColumnInfo(name = "thursday")
    public String thursday;

    @ColumnInfo(name = "friday")
    public String friday;


    @ColumnInfo(name = "saturday")
    public String saturday;

    @ColumnInfo(name = "sunday")
    public String sunday;

    @ColumnInfo(name = "lat")
    public double lat;

    @ColumnInfo(name = "lng")
    public double lng;

    @ColumnInfo(name = "geocoded_location")
    public String geocoded_location;

    public OpshopItem(){}

//    public ServiceItem(int id, String service_name, String what, String who, String address_1, String address_2, String suburb, String phone_number, String free_line, String email, String website, String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String sunday, String public_holodays, String cost, String tram_routes,  String nearest_train, String category_1, String category_2, String category_4, String category_3, double latitude, double longitude, String geocoded_location) {
//        this.id = id;
//        this.service_name = service_name;
//        this.what = what;
//        this.who = who;
//        this.address_1 = address_1;
//        this.address_2 = address_2;
//        this.suburb = suburb;
//        this.phone_number = phone_number;
//        this.free_line = free_line;
//        this.email = email;
//        this.website = website;
//        this.monday = monday;
//        this.tuesday = tuesday;
//        this.wednesday = wednesday;
//        this.thursday = thursday;
//        this.friday = friday;
//        this.saturday = saturday;
//        this.sunday = sunday;
//        this.public_holodays = public_holodays;
//        this.cost = cost;
//        this.tram_routes = tram_routes;
//        this.nearest_train = nearest_train;
//        this.category_1 = category_1;
//        this.category_2 = category_2;
//        this.category_4 = category_4;
//        this.category_3 = category_3;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.geocoded_location = geocoded_location;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return opshop_name;
    }

    public void setName(String name) {
        this.opshop_name = name;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getGeocoded_location() {
        return geocoded_location;
    }

    public void setGeocoded_location(String geocoded_location) {
        this.geocoded_location = geocoded_location;
    }
}
