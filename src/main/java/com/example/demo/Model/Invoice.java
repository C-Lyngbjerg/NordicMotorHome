package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Invoice {
    @Id
    private int invoice_id;
    private double invoice_total_price;
    private int invoice_distance_driven;
    private int contract_id;

    public Invoice() {
    }
    public Invoice(int invoice_id, int contract_id, double invoice_total_price,int invoice_distance_driven){
        this.invoice_id = invoice_id;
        this.contract_id = contract_id;
        this.invoice_total_price = invoice_total_price;
        this.invoice_distance_driven = invoice_distance_driven;
    }

    public int getInvoice_id() {
        return invoice_id;
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
}