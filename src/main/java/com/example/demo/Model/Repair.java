package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Repair {
    @Id
    private int repair_id;
    private String repair_description;
    private String repair_date;
    private int motorhome_id;

    public Repair() {
    }

    public Repair(int repair_id, String repair_description, String repair_date, int motorhome_id) {
        this.repair_id = repair_id;
        this.repair_description = repair_description;
        this.repair_date = repair_date;
        this.motorhome_id = motorhome_id;
    }

    public int getRepair_id() {
        return repair_id;
    }

    public void setRepair_id(int repair_id) {
        this.repair_id = repair_id;
    }

    public String getRepair_description() {
        return repair_description;
    }

    public void setRepair_description(String repair_description) {
        this.repair_description = repair_description;
    }

    public String getRepair_date() {
        return repair_date;
    }

    public void setRepair_date(String repair_date) {
        this.repair_date = repair_date;
    }

    public int getMotorhome_id() {
        return motorhome_id;
    }

    public void setMotorhome_id(int motorhome_id) {
        this.motorhome_id = motorhome_id;
    }
}
