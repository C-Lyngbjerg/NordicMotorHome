package com.example.demo.Repository;

import com.example.demo.Model.Contract;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Motorhome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static javax.swing.UIManager.get;

@Repository
public class ContractRepo {

    @Autowired
    JdbcTemplate template;

    public List<Contract> fetchAll(){
        String sql = "SELECT * FROM contracts";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        return template.query(sql, rowMapper);
    }
    public Contract addContract(Contract con){
        String sql = "INSERT INTO contracts (contract_id,contract_rent_price,contract_start_date,contract_end_date,contract_odometer_start,contract_extra_bike_rack,contract_extra_bed_sheets,contract_extra_child_seat,contract_extra_picnic_table,contract_extra_chairs,customer_id,motorhome_reg_number) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        template.update(sql,con.getContract_id(),con.getContract_rent_price(),con.getContract_start_date(),con.getContract_end_date(),con.getContract_odometer_start(),con.isContract_extra_bike_rack(),con.isContract_extra_bed_sheets(),con.isContract_extra_child_seat(),con.isContract_extra_picnic_table(),con.isContract_extra_chairs(),con.getCustomer_id(),con.getMotorhome_reg_number());
        return null;
    }
    public Contract findContractById(int id){
        String sql = "SELECT * FROM contracts WHERE contract_id = ?";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        Contract con =template.queryForObject(sql, rowMapper,id);
        return con;
    }
    public Boolean deleteContract(int id){
        String sql = "DELETE FROM contracts WHERE contract_id = ?";
        return template.update(sql, id) < 0;
    }
    public Contract updateContract(int id, Contract con){
        String sql = "UPDATE contracts SET contract_id = ?,contract_rent_price = ?,contract_start_date = ?,contract_end_date = ?,contract_odometer_start = ?,contract_extra_bike_rack = ?,contract_extra_bed_sheets = ?,contract_extra_child_seat = ?,contract_extra_picnic_table = ?,contract_extra_chairs = ?,customer_id = ?,motorhome_reg_number = ?) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        template.update(sql, con.getContract_id(),con.getContract_rent_price(),con.getContract_start_date(),con.getContract_end_date(),con.getContract_odometer_start(),con.isContract_extra_bike_rack(),con.isContract_extra_bed_sheets(),con.isContract_extra_child_seat(),con.isContract_extra_picnic_table(),con.isContract_extra_chairs(),con.getCustomer_id(),con.getMotorhome_reg_number());
        return null;
    }
    public double calculateRentPeriodPrice(Contract con){
        String sql1 = "SELECT type_price_per_day FROM mh_types WHERE type_id IN (SELECT type_id FROM motorhomes m WHERE m.motorhome_reg_number = ?)";
        List<Double> priceList = template.queryForList(sql1, Double.class, con.getMotorhome_reg_number());
        String sql = "SELECT DATEDIFF(?,?) AS dateDiff";
        List<Double> dateDiff = template.queryForList(sql, Double.class, con.getContract_end_date(), con.getContract_start_date());
        return seasonCheck(con,priceList,dateDiff);
    }
    public double seasonCheck(Contract con, List<Double> priceList, List<Double> dateDiff){
        double totalPrice = 0;
        LocalDate startDate = LocalDate.parse(con.getContract_start_date());
        for(int i = 0; i < dateDiff.get(0) + 1; i++){
            if(startDate.getMonthValue() == 6 || startDate.getMonthValue() == 7 || startDate.getMonthValue() == 8){
                totalPrice += priceList.get(0) * 1.6;

            }else if(startDate.getMonthValue() == 3 || startDate.getMonthValue() == 4 || startDate.getMonthValue() == 5 || startDate.getMonthValue() == 9 || startDate.getMonthValue() == 10){
                totalPrice += priceList.get(0) * 1.3;
            }else{
                totalPrice += priceList.get(0);
            }
            startDate = startDate.plusDays(1);
        }
        return totalPrice;
    }
}
