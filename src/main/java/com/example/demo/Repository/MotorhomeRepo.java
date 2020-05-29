package com.example.demo.Repository;

import com.example.demo.Model.Motorhome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MotorhomeRepo implements RepositoryI {
    @Autowired
    JdbcTemplate template;

    public List<Motorhome> fetchAll(){
        String sql = "SELECT motorhome_id, motorhome_reg_number, motorhome_brand, motorhome_model, t.type_description, mh.type_id FROM motorhomes mh JOIN mh_types t ON mh.type_id = t.type_id";
        RowMapper<Motorhome> rowMapper = new BeanPropertyRowMapper<>(Motorhome.class);
        return template.query(sql,rowMapper);
    }

    public Object add(Object obj){
        Motorhome mot = (Motorhome) obj;
        String sql = "INSERT INTO motorhomes (motorhome_id, motorhome_reg_number, motorhome_brand, motorhome_room_height, motorhome_model, motorhome_odometer, type_id) VALUES (?,?,?,?,?,?,?)";
        template.update(sql,mot.getMotorhome_id(), mot.getMotorhome_reg_number(),mot.getMotorhome_brand(),mot.getMotorhome_room_height(),mot.getMotorhome_model(),mot.getMotorhome_odometer(),mot.getType_id());
        return null;
    }

    public Object findById(int id){
        String sql = "SELECT * FROM motorhomes WHERE motorhome_id = ?";
        RowMapper<Motorhome> rowMapper = new BeanPropertyRowMapper<>(Motorhome.class);
        Motorhome motorhome = template.queryForObject(sql,rowMapper,id);
        return motorhome;
    }

    public Boolean delete(int id){
        String sql = "DELETE FROM motorhomes WHERE motorhome_id = ?";
        //String sql2 = "DELETE FROM contracts WHERE motorhome_id = ?";
        //String sql3 = "DELETE FROM invoices WHERE contract_id IN (SELECT contract_id FROM contracts WHERE motorhome_id =?";
        //template.update(sql3, id);
        //template.update(sql2, id);
        return template.update(sql,id) < 0;
    }

    public Object update(Object obj){
        Motorhome mot = (Motorhome) obj;
        String sql = "UPDATE motorhomes SET motorhome_id = ?,motorhome_reg_number = ?, motorhome_brand = ?, motorhome_room_height = ?, motorhome_model = ?, motorhome_odometer = ?, type_id = ? WHERE motorhome_id = ?";
        template.update(sql,mot.getMotorhome_id(),mot.getMotorhome_reg_number(),mot.getMotorhome_brand(),mot.getMotorhome_room_height(),mot.getMotorhome_model(),mot.getMotorhome_odometer(),mot.getType_id(), mot.getMotorhome_id());
        return null;
    }

    public List<Motorhome> findAvailable(String startDate, String endDate){
        String sql = "SELECT motorhome_id,motorhome_reg_number FROM motorhomes WHERE motorhome_id NOT IN (SELECT motorhome_id FROM contracts WHERE ? BETWEEN contract_start_date AND contract_end_date OR ? BETWEEN contract_start_date AND contract_end_date OR ? >= contract_start_date AND ? <= contract_end_date)";
        RowMapper<Motorhome> rowMapper = new BeanPropertyRowMapper<>(Motorhome.class);
        return template.query(sql,rowMapper,startDate,endDate,startDate,endDate);
    }
}
