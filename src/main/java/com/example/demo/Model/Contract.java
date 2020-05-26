package com.example.demo.Model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Contract {
    public interface dateValidation{};
    @Id
    @NotNull
    @Min(1)
    private int contract_id;
    @NotNull
    private double contract_rent_price;
    @NotNull(groups= {dateValidation.class})
    @Size(min=10,max=10,groups= {dateValidation.class})
    private String contract_start_date;
    @NotNull(groups= {dateValidation.class})
    @Size(min=10,max=10,groups= {dateValidation.class})
    private String contract_end_date;
    @NotNull
    @Positive
    private int contract_odometer_start;
    @NotNull
    @Size(min=5,max=15)
    private String motorhome_reg_number;
    @NotNull
    private boolean contract_extra_bike_rack;
    @NotNull
    private boolean contract_extra_child_seat;
    @NotNull
    private boolean contract_extra_bed_sheets;
    @NotNull
    private boolean contract_extra_picnic_table;
    @NotNull
    private boolean contract_extra_chairs;
    @NotNull
    @Positive
    private int customer_id;
    @NotNull
    @Positive
    private int motorhome_id;
    @NotNull
    @Positive
    private int dateDiff;
    @NotNull
    private double contract_pick_up_distance;
    @NotNull
    private double contract_drop_off_distance;

    public Contract() {
    }

    public Contract(int contract_id, double contract_rent_price, String contract_start_date, String contract_end_date, int contract_odometer_start, boolean contract_extra_bike_rack, boolean contract_extra_child_seat, boolean contract_extra_bed_sheets, boolean contract_extra_picnic_table, boolean contract_extra_chairs, int customer_id, int motorhome_id) {
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
        this.motorhome_id = motorhome_id;
    }

    public String getMotorhome_reg_number() {
        return motorhome_reg_number;
    }

    public void setMotorhome_reg_number(String motorhome_reg_number) {
        this.motorhome_reg_number = motorhome_reg_number;
    }

    public double getContract_pick_up_distance() {
        return contract_pick_up_distance;
    }

    public void setContract_pick_up_distance(double contract_pick_up_distance) {
        this.contract_pick_up_distance = contract_pick_up_distance;
    }

    public double getContract_drop_off_distance() {
        return contract_drop_off_distance;
    }

    public void setContract_drop_off_distance(double contract_drop_off_distance) {
        this.contract_drop_off_distance = contract_drop_off_distance;
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

    public int getMotorhome_id() {
        return motorhome_id;
    }

    public void setMotorhome_id(int motorhome_reg_number) {
        this.motorhome_id = motorhome_reg_number;
    }

    /*
    * I metoden calculatePrice findes prisen for hele lejeperioden
    * Dette inkluderer hvilke extra ting kunden vælger at leje, så som camping bord og stole
     */
    public void calculatePrice(List<Double> dateAndPrice){
        double totalPrice = seasonCheck(dateAndPrice);
        //en masse if-statements som tilføjer prisen for alt extra indhold som bliver lejet.
        if(contract_extra_bike_rack != false){
            totalPrice += 200;
        }
        if (contract_extra_bed_sheets != false){
            totalPrice += 150;
        }
        if (contract_extra_child_seat != false){
            totalPrice += 200;
        }
        if (contract_extra_picnic_table != false){
            totalPrice += 100;
        }
        if (contract_extra_chairs != false){
            totalPrice += 200;
        }
        totalPrice += (this.contract_pick_up_distance + this.contract_drop_off_distance) * 0.7;
        this.contract_rent_price = totalPrice;//sætter kontraktens pris til den ændelige total pris
    }

    /*
    * seasonCheck metoden udregner den samlede pris for udlejningsperioden i de enkelte dages sæsonperiode
    * Den parametre overførte liste dateAndPrice indenholder 2 variabler: index plads 0 har den daglige pris og index plads 1 indenholder det samlede antal dage udlejningen varer.
    * */
    public double seasonCheck(List<Double> dateAndPrice){
        double totalPrice = 0;
        LocalDate startDate = LocalDate.parse(contract_start_date); //contractens String værdi bliver lavet om til en LocalDate objekt. Det gør at vi kan tjekke datoen lettere
        for(int i = 0; i < dateAndPrice.get(1) + 1; i++){  //vi tilføjer 1 til den samlede mængde af dage så den sidste dag også bliver regnet med.
            //I if-else statementerne herunder tjekkes datoen for de enkelte dage hvor udlejning stræker sig og prisen bliver så forhøjet.
            //Vi har valgt at højsæsonen strækker sig over de store sommermånederne 6-8
            if(startDate.getMonthValue() == 6 || startDate.getMonthValue() == 7 || startDate.getMonthValue() == 8){
                totalPrice += dateAndPrice.get(0) * 1.6;
            //Midtsæsonen har vi valgt at lægge i alle efterårs og forårs månederne ud over november
            }else if(startDate.getMonthValue() == 3 || startDate.getMonthValue() == 4 || startDate.getMonthValue() == 5 || startDate.getMonthValue() == 9 || startDate.getMonthValue() == 10){
                totalPrice += dateAndPrice.get(0) * 1.3;
            }else{
                totalPrice += dateAndPrice.get(0);
            }
            startDate = startDate.plusDays(1);//Her lægger vi en dag til datoen
        }
        return totalPrice;
    }
}
