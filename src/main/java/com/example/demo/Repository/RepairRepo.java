package com.example.demo.Repository;


import com.example.demo.Model.Repair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepairRepo implements RepositoryI {

    @Autowired
    JdbcTemplate template;

    public List<Repair> fetchAll(){
        String sql = "SELECT repair_id, repair_description, repair_date, r.motorhome_id FROM repairs r JOIN motorhomes m ON r.motorhome_id = m.motorhome_id";
        RowMapper<Repair> rowMapper = new BeanPropertyRowMapper<>(Repair.class);
        return template.query(sql,rowMapper);
    }

    public Repair add(Object obj){
        Repair repair = (Repair) obj;
        String sql = "INSERT INTO repairs (repair_id, repair_description,repair_date,motorhome_id) VALUES (?,?,?,?)";
        template.update(sql,repair.getRepair_id(),repair.getRepair_description(),repair.getRepair_date(),repair.getMotorhome_id());
        return null;
    }

    public Repair findById(int id){
        String sql = "SELECT * FROM repairs WHERE repair_id = ?";
        RowMapper<Repair> rowMapper = new BeanPropertyRowMapper<>(Repair.class);
        Repair repair = template.queryForObject(sql,rowMapper,id);
        return repair;
    }

    public Boolean delete(int id){
        String sql = "DELETE FROM repairs WHERE repair_id = ?";
        return template.update(sql,id) < 0;
    }

    public Repair update(int id, Object obj){
        Repair repair = (Repair) obj;
        String sql = "UPDATE repairs SET repair_id = ?, repair_description = ?, repair_date = ?, motorhome_id = ? WHERE repair_id = ?";
        template.update(sql,repair.getRepair_id(),repair.getRepair_description(),repair.getRepair_date(),repair.getMotorhome_id(),repair.getRepair_id());
        return null;
    }
}
