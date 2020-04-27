package com.gerontechies.semonaid.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ServiceItem {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "service_name")
    public String service_name;

    @ColumnInfo(name = "what")
    public String what;

    @ColumnInfo(name = "who")
    public String who;

    @ColumnInfo(name = "address_1")
    public String address_1;

    @ColumnInfo(name = "address_2")
    public String address_2;

    @ColumnInfo(name = "suburb")
    public String suburb;

    @ColumnInfo(name = "phone_number")
    public String phone_number;

    @ColumnInfo(name = "free_line")
    public String free_line;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "website")
    public String website;

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

    @ColumnInfo(name = "public_holodays")
    public String public_holodays;

    @ColumnInfo(name = "cost")
    public String cost;

    @ColumnInfo(name = "tram_routes")
    public String tram_routes;

    @ColumnInfo(name = "nearest_train")
    public String nearest_train;

    @ColumnInfo(name = "category_1")
    public String category_1;

    @ColumnInfo(name = "category_2")
    public String category_2;

    @ColumnInfo(name = "category_4")
    public String category_4;

    @ColumnInfo(name = "category_3")
    public String category_3;


    @ColumnInfo(name = "latitude")
    public double latitude;

    @ColumnInfo(name = "longitude")
    public double longitude;

    @ColumnInfo(name = "geocoded_location")
    public String geocoded_location;

    public ServiceItem(){}

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

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getFree_line() {
        return free_line;
    }

    public void setFree_line(String free_line) {
        this.free_line = free_line;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public String getPublic_holodays() {
        return public_holodays;
    }

    public void setPublic_holodays(String public_holodays) {
        this.public_holodays = public_holodays;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTram_routes() {
        return tram_routes;
    }

    public void setTram_routes(String tram_routes) {
        this.tram_routes = tram_routes;
    }

    public String getNearest_train() {
        return nearest_train;
    }

    public void setNearest_train(String nearest_train) {
        this.nearest_train = nearest_train;
    }

    public String getCategory_1() {
        return category_1;
    }

    public void setCategory_1(String category_1) {
        this.category_1 = category_1;
    }

    public String getCategory_2() {
        return category_2;
    }

    public void setCategory_2(String category_2) {
        this.category_2 = category_2;
    }

    public String getCategory_4() {
        return category_4;
    }

    public void setCategory_4(String category_4) {
        this.category_4 = category_4;
    }

    public String getCategory_3() {
        return category_3;
    }

    public void setCategory_3(String category_3) {
        this.category_3 = category_3;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getGeocoded_location() {
        return geocoded_location;
    }

    public void setGeocoded_location(String geocoded_location) {
        this.geocoded_location = geocoded_location;
    }
}
