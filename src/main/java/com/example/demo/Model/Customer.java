package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer {
    @Id
    private int customer_id;
    private String customer_first_name;
    private String customer_last_name;
    private String customer_address;
    private String customer_drivers_license;
    private String customer_license_type;
    private String customer_phone;
    private String customer_nationality;
    private String customer_city;
    private int customer_zip_code;

    public Customer(){}

    public Customer(int customer_id, String customer_first_name, String customer_last_name, String customer_address, String customer_drivers_license,
                    String customer_license_type, String customer_phone, String customer_city, String customer_nationality, int customer_zip_code) {
        this.customer_id = customer_id;
        this.customer_first_name = customer_first_name;
        this.customer_last_name = customer_last_name;
        this.customer_address = customer_address;
        this.customer_drivers_license = customer_drivers_license;
        this.customer_license_type = customer_license_type;
        this.customer_phone = customer_phone;
        this.customer_nationality = customer_nationality;
        this.customer_city = customer_city;
        this.customer_zip_code = customer_zip_code;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_first_name() {
        return customer_first_name;
    }

    public void setCustomer_first_name(String customer_first_name) {
        this.customer_first_name = customer_first_name;
    }

    public String getCustomer_last_name() {
        return customer_last_name;
    }

    public void setCustomer_last_name(String customer_last_name) {
        this.customer_last_name = customer_last_name;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_drivers_license() {
        return customer_drivers_license;
    }

    public void setCustomer_drivers_license(String customer_drivers_license) {
        this.customer_drivers_license = customer_drivers_license;
    }

    public String getCustomer_license_type() {
        return customer_license_type;
    }

    public void setCustomer_license_type(String customer_license_type) {
        this.customer_license_type = customer_license_type;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getCustomer_city() {
        return customer_city;
    }

    public void setCustomer_city(String customer_city) {
        this.customer_city = customer_city;
    }

    public String getCustomer_nationality() {
        return customer_nationality;
    }

    public void setCustomer_nationality(String customer_country) {
        this.customer_nationality = customer_nationality;
    }

    public int getCustomer_zip_code() {
        return customer_zip_code;
    }

    public void setCustomer_zip_code(int customer_zip_code) {
        this.customer_zip_code = customer_zip_code;
    }
}
