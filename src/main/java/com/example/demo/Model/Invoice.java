package com.example.demo.Model;

import com.example.demo.Service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Invoice {

    @Id
    private int invoice_id;
    private double invoice_total_price;
    private int invoice_distance_driven;
    private int invoice_odometer_end;
    private int contract_id;
    private boolean invoice_fuel_gage;


    public Invoice() {
    }
    public Invoice(int invoice_id, int contract_id, double invoice_total_price,int invoice_distance_driven,int invoice_odometer_end, boolean invoice_fuel_gage){
        this.invoice_id = invoice_id;
        this.contract_id = contract_id;
        this.invoice_total_price = invoice_total_price;
        this.invoice_distance_driven = invoice_distance_driven;
        this.invoice_odometer_end = invoice_odometer_end;
        this.invoice_fuel_gage = invoice_fuel_gage;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public int getInvoice_odometer_end() {
        return invoice_odometer_end;
    }

    public void setInvoice_odometer_end(int invoice_odometer_end) {
        this.invoice_odometer_end = invoice_odometer_end;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }

    public double getInvoice_total_price() {
        invoice_total_price = 0.0;
        if(isInvoice_fuel_gage()){
            invoice_total_price += 70.0;
        }else{
            invoice_total_price = 0.0;
        }
        int distance_per_day = invoice_distance_driven/ 1; //1 = placeholder for datediff
        if(distance_per_day > 400){
            invoice_total_price += (double)(distance_per_day-400)*1;
        }
        return invoice_total_price;
    }

    public void setInvoice_total_price(double invoice_total_price) {
        this.invoice_total_price = invoice_total_price;
    }

    public int getInvoice_distance_driven() {
        return invoice_distance_driven;
    }

    public void setInvoice_distance_driven(int invoice_distance_driven) {
        this.invoice_distance_driven = invoice_distance_driven;
    }

    public boolean isInvoice_fuel_gage() { return true; }

    public void setInvoice_fuel_gage(boolean invoice_fuel_gage) { this.invoice_fuel_gage = invoice_fuel_gage; }
}