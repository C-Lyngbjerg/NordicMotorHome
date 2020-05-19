package com.example.demo.Model;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Contract {
    @Id
    private int contract_id;
    private double contract_rent_price;
    private String contract_start_date;
    private String contract_end_date;
    private int contract_odometer_start;
    private boolean contract_extra_bike_rack;
    private boolean contract_extra_child_seat;
    private boolean contract_extra_bed_sheets;
    private boolean contract_extra_picnic_table;
    private boolean contract_extra_chairs;
    private int customer_id;
    private String motorhome_reg_number;
    private int dateDiff;

    public Contract() {
    }

    public Contract(int contract_id, double contract_rent_price, String contract_start_date, String contract_end_date, int contract_odometer_start, boolean contract_extra_bike_rack, boolean contract_extra_child_seat, boolean contract_extra_bed_sheets, boolean contract_extra_picnic_table, boolean contract_extra_chairs, int customer_id, String motorhome_reg_number) {
        this.contract_id = contract_id;
        this.contract_rent_price = contract_rent_price;
        this.contract_start_date = contract_start_date;
        this.contract_end_date = contract_end_date;
        this.contract_odometer_start = contract_odometer_start;
        this.contract_extra_bike_rack = contract_extra_bike_rack;
        this.contract_extra_child_seat = contract_extra_child_seat;
        this.contract_extra_bed_sheets = contract_extra_bed_sheets;
        this.contract_extra_picnic_table = contract_extra_picnic_table;
        this.contract_extra_chairs = contract_extra_chairs;
        this.customer_id = customer_id;
        this.motorhome_reg_number = motorhome_reg_number;
    }

    public int getDateDiff() {
        return dateDiff;
    }

    public void setDateDiff(int dateDiff) {
        this.dateDiff = dateDiff;
    }

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }

    public double getContract_rent_price() {
        return contract_rent_price;
    }

    public void setContract_rent_price(double contract_rent_price) {
        this.contract_rent_price = contract_rent_price;
    }

    public String getContract_start_date() {
        return contract_start_date;
    }

    public void setContract_start_date(String contract_start_date) {
        this.contract_start_date = contract_start_date;
    }

    public String getContract_end_date() {
        return contract_end_date;
    }

    public void setContract_end_date(String contract_end_date) {
        this.contract_end_date = contract_end_date;
    }

    public int getContract_odometer_start() {
        return contract_odometer_start;
    }

    public void setContract_odometer_start(int contract_odometer_start) {
        this.contract_odometer_start = contract_odometer_start;
    }

    public boolean isContract_extra_bike_rack() {
        return contract_extra_bike_rack;
    }

    public void setContract_extra_bike_rack(boolean contract_extra_bike_rack) {
        this.contract_extra_bike_rack = contract_extra_bike_rack;
    }

    public boolean isContract_extra_child_seat() {
        return contract_extra_child_seat;
    }

    public void setContract_extra_child_seat(boolean contract_extra_child_seat) {
        this.contract_extra_child_seat = contract_extra_child_seat;
    }

    public boolean isContract_extra_bed_sheets() {
        return contract_extra_bed_sheets;
    }

    public void setContract_extra_bed_sheets(boolean contract_extra_bed_sheets) {
        this.contract_extra_bed_sheets = contract_extra_bed_sheets;
    }

    public boolean isContract_extra_picnic_table() {
        return contract_extra_picnic_table;
    }

    public void setContract_extra_picnic_table(boolean contract_extra_picnic_table) {
        this.contract_extra_picnic_table = contract_extra_picnic_table;
    }

    public boolean isContract_extra_chairs() {
        return contract_extra_chairs;
    }

    public void setContract_extra_chairs(boolean contract_extra_chairs) {
        this.contract_extra_chairs = contract_extra_chairs;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getMotorhome_reg_number() {
        return motorhome_reg_number;
    }

    public void setMotorhome_reg_number(String motorhome_reg_number) {
        this.motorhome_reg_number = motorhome_reg_number;
    }
    public void calculatePrice(List<Double> dateAndPrice){
        double totalPrice = seasonCheck(dateAndPrice);
        if(contract_extra_bike_rack){
            totalPrice += 200;
        }
        if (contract_extra_bed_sheets){
            totalPrice += 150;
        }
        if (contract_extra_child_seat){
            totalPrice += 200;
        }
        if (contract_extra_picnic_table){
            totalPrice += 100;
        }
        if (contract_extra_chairs){
            totalPrice += 200;
        }
        this.contract_rent_price = totalPrice;
    }
    public double seasonCheck(List<Double> dateAndPrice){
        double totalPrice = 0;
        LocalDate startDate = LocalDate.parse(contract_start_date);
        for(int i = 0; i < dateAndPrice.get(1) + 1; i++){
            if(startDate.getMonthValue() == 6 || startDate.getMonthValue() == 7 || startDate.getMonthValue() == 8){
                totalPrice += dateAndPrice.get(0) * 1.6;

            }else if(startDate.getMonthValue() == 3 || startDate.getMonthValue() == 4 || startDate.getMonthValue() == 5 || startDate.getMonthValue() == 9 || startDate.getMonthValue() == 10){
                totalPrice += dateAndPrice.get(0) * 1.3;
            }else{
                totalPrice += dateAndPrice.get(0);
            }
            startDate = startDate.plusDays(1);
        }
        return totalPrice;
    }
}
