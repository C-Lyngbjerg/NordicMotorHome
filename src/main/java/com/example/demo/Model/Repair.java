package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
public class Repair { // WO
    @Id
    private int repair_id;
    @NotNull
    @Size(min=10,max=50,message="Please make an short and precise description (10-50 characters)")
    private String repair_description;
    @NotNull
    @Size(min=10,max=10,message="Must be in dateformat: YYYY-MM-DD")
    private String repair_date;
    @NotNull
    @Positive
    private int motorhome_id;

    public Repair() {// WO
    }

    public Repair(int repair_id, String repair_description, String repair_date, int motorhome_id) { // WO
        this.repair_id = repair_id;
        this.repair_description = repair_description;
        this.repair_date = repair_date;
        this.motorhome_id = motorhome_id;
    }

    public int getRepair_id() {
        return repair_id;
    }// WO

    public void setRepair_id(int repair_id) {
        this.repair_id = repair_id;
    }// WO

    public String getRepair_description() {
        return repair_description;
    }// WO

    public void setRepair_description(String repair_description) {
        this.repair_description = repair_description;
    }// WO

    public String getRepair_date() {
        return repair_date;
    }// WO

    public void setRepair_date(String repair_date) {
        this.repair_date = repair_date;
    }// WO

    public int getMotorhome_id() {
        return motorhome_id;
    }// WO

    public void setMotorhome_id(int motorhome_id) {
        this.motorhome_id = motorhome_id;
    }// WO
}
