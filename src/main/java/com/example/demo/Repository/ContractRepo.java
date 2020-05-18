package com.example.demo.Repository;

import com.example.demo.Model.Contract;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Motorhome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
    public int calculateRentPeriodPrice(Contract con){
        String sql1 = "SELECT type_price_per_day FROM mh_types WHERE type_id IN (SELECT type_id FROM motorhomes m WHERE m.motorhome_reg_number = ?)";
        RowMapper<Motorhome> row = new BeanPropertyRowMapper<>(Motorhome.class);
        Motorhome motorhome = template.queryForObject(sql1, row, con.getMotorhome_reg_number());
        String sql = "SELECT DATEDIFF(?,?) AS dateDiff";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        Contract con1 = template.queryForObject(sql, rowMapper, con.getContract_end_date(), con.getContract_start_date());
        return con1.getDateDiff() * motorhome.getType_price_per_day();
    }
}
