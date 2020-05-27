package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Invoice {

    @Id
    @NotNull
    private int invoice_id;
    @NotNull
    @Min(0)
    private double invoice_total_price;
    @NotNull
    @Min(0)
    private int invoice_distance_driven;
    @NotNull
    @Min(0)
    private int invoice_odometer_end;
    @NotNull
    @Min(0)
    private int invoice_rent_days;
    @NotNull
    @Min(1)
    private int contract_id;
    @NotNull
    private boolean invoice_fuel_gage;

//TODO Kig lige op på hvordan Spring frameworket bruger constructors
// Indeholder en tom og en "fuld" constructor, da Spring anvender den tomme

    public Invoice() {
    }
    public Invoice(int invoice_id, int contract_id, double invoice_total_price,int invoice_distance_driven,int invoice_odometer_end, int invoice_rent_days, boolean invoice_fuel_gage){
        this.invoice_id = invoice_id;
        this.contract_id = contract_id;
        this.invoice_distance_driven = invoice_distance_driven;
        this.invoice_odometer_end = invoice_odometer_end;
        this.invoice_fuel_gage = invoice_fuel_gage;
        this.invoice_rent_days = invoice_rent_days;
        this.invoice_total_price = invoice_total_price;
    }

    public int getInvoice_id() {
        return invoice_id;
    }
    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }
    public int getInvoice_odometer_end() {
        return invoice_odometer_end;
    }

    public void setInvoice_odometer_end(int invoice_odometer_end) {
        this.invoice_odometer_end = invoice_odometer_end;
    }

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }
/*
 * Udregner den totale pris kunden skal betale ved aflevering af autocamper.
 * Dette er ekstra omkostninger, der indeholder:
 *      Ekstra omkostninger ved kørsel over 400 km om dagen i gennemsnit
 *      Ekstra omkostninger ved en gas tank under 50%
 * Disse bliver udregnet og sat til objektets element "invoice_total_price"
 * Da spring bruger getters, til at lave object via rowMapper, bliver dette lavet ved initialisation af objektet.
 */

    public double getInvoice_total_price() {
        invoice_total_price = 0.0;
        //Hvis gas tank er under 50% (sat som en boolean til at være true) vil der blive lagt 70 til total price
        if(isInvoice_fuel_gage()){
            invoice_total_price += 70.0;
        }else{
            invoice_total_price = 0.0;
        }
        //Antal km kørt divideret med antal leje dage giver den daglige distance kørt i gennemsnit
        double distance_per_day = (double) invoice_distance_driven / (double)invoice_rent_days;
        //Hvis gennemsnit er over 400, betyder det at der vil være omkostninger der skal omregnes,
        //Hvis det er under 400 i gennemsnit, vil der ikke være grund til at lave noget computing og ignorere det
        if(distance_per_day > 400){
            invoice_total_price += (double)(distance_per_day-400)*invoice_rent_days;
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

    public boolean isInvoice_fuel_gage() { return invoice_fuel_gage; }

    public void setInvoice_fuel_gage(boolean invoice_fuel_gage) { this.invoice_fuel_gage = invoice_fuel_gage; }

    public int getInvoice_rent_days() {
        return invoice_rent_days;
    }

    public void setInvoice_rent_days(int invoice_rent_days) {
        this.invoice_rent_days = invoice_rent_days;
    }


}