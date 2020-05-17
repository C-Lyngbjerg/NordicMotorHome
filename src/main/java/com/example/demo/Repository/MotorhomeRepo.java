package com.example.demo.Repository;

import com.example.demo.Model.Motorhome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MotorhomeRepo {
    @Autowired
    JdbcTemplate template;

    public List<Motorhome> fetchAll(){
        String sql = "SELECT motorhome_reg_number, motorhome_brand, motorhome_model, t.type_description, mh.type_id FROM motorhomes mh JOIN mh_types t ON mh.type_id = t.type_id";
        RowMapper<Motorhome> rowMapper = new BeanPropertyRowMapper<>(Motorhome.class);
        return template.query(sql,rowMapper);
    }

    public Motorhome addMotorhome(Motorhome motorhome){
        String sql = "INSERT INTO motorhomes (motorhome_reg_number, motorhome_brand, motorhome_room_height, motorhome_model, motorhome_odometer, type_id) VALUES (?,?,?,?,?,?)";
        template.update(sql,motorhome.getMotorhome_reg_number(),motorhome.getMotorhome_brand(),motorhome.getMotorhome_room_heigth(),motorhome.getMotorhome_model(),motorhome.getMotorhome_odometer(),motorhome.getType_id());
        return null;
    }

    public Motorhome findMotorhomeByRegNumber(String regNumber){
        String sql = "SELECT * FROM motorhomes WHERE motorhome_reg_number = ?";
        RowMapper<Motorhome> rowMapper = new BeanPropertyRowMapper<>(Motorhome.class);
        Motorhome motorhome = template.queryForObject(sql,rowMapper);
        return motorhome;
    }

    public Boolean deleteMotorhome(String regNumber){
        String sql = "DELETE FROM motorhomes WHERE motorhome_reg_number = ?";
        return template.update(sql,regNumber) < 0;
    }

    public Motorhome updateMotorhome(String regNumber, Motorhome motorhome){
        String sql = "UPDATE motorhomes SET motorhome_reg_number = ?, motorhome_brand = ?, motorhome_room_height = ?, motorhome_model = ?, motorhome_odometer = ?, type_id = ? WHERE motorhome_reg_number = ?";
        template.update(sql,motorhome.getMotorhome_reg_number(),motorhome.getMotorhome_brand(),motorhome.getMotorhome_room_heigth(),motorhome.getMotorhome_model(),motorhome.getMotorhome_odometer(),motorhome.getType_id(), motorhome.getMotorhome_reg_number());
        return null;
    }
}
