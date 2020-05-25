package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
public class Motorhome {
    @Id
    private int motorhome_id;
    @NotNull
    @Size(min=1,max=15)
    private String motorhome_reg_number;
    @NotNull
    @Size(min=1,max=15)
    private String motorhome_brand;
    @NotNull
    @Min(100)
    private int motorhome_room_height;
    @NotNull
    @Size(min=1,max=15)
    private String motorhome_model;
    @NotNull
    @Min(0)
    private int motorhome_odometer;
    @NotNull
    @Positive
    private int type_id;
    @NotNull
    @Size(min=1,max=50)
    private String type_description;
    @NotNull
    @Positive
    private int type_price_per_day;
    @NotNull
    @Positive
    private int type_bed_count;
    @NotNull
    @Positive
    private int type_seat_count;

    public Motorhome(){

    }

    public Motorhome(int motorhome_id, String motorhome_reg_number, String motorhome_brand, int motorhome_room_height, String motorhome_model, int motorhome_odometer, int type_id, int type_price_per_day, int type_bed_count, int type_seat_count) {
        this.motorhome_id = motorhome_id;
        this.motorhome_reg_number = motorhome_reg_number;
        this.motorhome_brand = motorhome_brand;
        this.motorhome_room_height = motorhome_room_height;
        this.motorhome_model = motorhome_model;
        this.motorhome_odometer = motorhome_odometer;
        this.type_id = type_id;
        this.type_price_per_day = type_price_per_day;
        this.type_bed_count = type_bed_count;
        this.type_seat_count = type_seat_count;
    }

    public int getMotorhome_id() {
        return motorhome_id;
    }

    public void setMotorhome_id(int motorhome_id) {
        this.motorhome_id = motorhome_id;
    }

    public String getType_description() {
        return type_description;
    }

    public void setType_description(String type_description) {
        this.type_description = type_description;
    }

    public String getMotorhome_reg_number() {
        return motorhome_reg_number;
    }

    public void setMotorhome_reg_number(String motorhome_reg_number) {
        this.motorhome_reg_number = motorhome_reg_number;
    }

    public String getMotorhome_brand() {
        return motorhome_brand;
    }

    public void setMotorhome_brand(String motorhome_brand) {
        this.motorhome_brand = motorhome_brand;
    }

    public int getMotorhome_room_height() {
        return motorhome_room_height;
    }

    public void setMotorhome_room_height(int motorhome_room_heigth) {
        this.motorhome_room_height = motorhome_room_heigth;
    }

    public String getMotorhome_model() {
        return motorhome_model;
    }

    public void setMotorhome_model(String motorhome_model) {
        this.motorhome_model = motorhome_model;
    }

    public int getMotorhome_odometer() {
        return motorhome_odometer;
    }

    public void setMotorhome_odometer(int motorhome_odometer) {
        this.motorhome_odometer = motorhome_odometer;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getType_price_per_day() {
        return type_price_per_day;
    }

    public void setType_price_per_day(int type_price_per_day) {
        this.type_price_per_day = type_price_per_day;
    }

    public int getType_bed_count() {
        return type_bed_count;
    }

    public void setType_bed_count(int type_bed_count) {
        this.type_bed_count = type_bed_count;
    }

    public int getType_seat_count() {
        return type_seat_count;
    }

    public void setType_seat_count(int type_seat_count) {
        this.type_seat_count = type_seat_count;
    }
}
