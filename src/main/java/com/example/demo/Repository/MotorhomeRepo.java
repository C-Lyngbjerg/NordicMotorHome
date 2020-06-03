package com.example.demo.Repository;

import com.example.demo.Model.Motorhome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class MotorhomeRepo implements RepositoryI { // Lavet af JT & SR
    @Autowired
    JdbcTemplate template;
    // Metode til at finde alle elementer fra motorhome tabellen og retunerer en liste
    public List<Motorhome> fetchAll(){
        String sql = "SELECT motorhome_id, motorhome_reg_number, motorhome_brand, motorhome_model, t.type_description, mh.type_id FROM motorhomes mh JOIN mh_types t ON mh.type_id = t.type_id";
        RowMapper<Motorhome> rowMapper = new BeanPropertyRowMapper<>(Motorhome.class);
        return template.query(sql,rowMapper);
    }
    //tilføjer et element til databasen
    public Object add(Object obj){
        Motorhome mot = (Motorhome) obj;
        String sql = "INSERT INTO motorhomes (motorhome_id, motorhome_reg_number, motorhome_brand, motorhome_room_height, motorhome_model, motorhome_odometer, type_id) VALUES (?,?,?,?,?,?,?)";
        template.update(sql,mot.getMotorhome_id(),mot.getMotorhome_reg_number(),mot.getMotorhome_brand(),mot.getMotorhome_room_height(),mot.getMotorhome_model(),mot.getMotorhome_odometer(),mot.getType_id());
        return null;
    }
    //finder et motorhome ud fra ID
    public Object findById(int id){ // Lavet af SR
        String sql = "SELECT * FROM motorhomes WHERE motorhome_id = ?";
        RowMapper<Motorhome> rowMapper = new BeanPropertyRowMapper<>(Motorhome.class);
        Motorhome motorhome = template.queryForObject(sql,rowMapper,id);
        return motorhome;
    }
    // sletter et motorhome ud fra ID
    public Boolean delete(int id){ // Lavet af SR
        String sql = "DELETE FROM motorhomes WHERE motorhome_id = ?";
        return template.update(sql,id) < 0;
    }
    // opdatere et motorhome i databasen ud fra ID
    public Object update(Object obj){ // Lavet af SR
        Motorhome mot = (Motorhome) obj;
        String sql = "UPDATE motorhomes SET motorhome_id = ?,motorhome_reg_number = ?, motorhome_brand = ?, motorhome_room_height = ?, motorhome_model = ?, motorhome_odometer = ?, type_id = ? WHERE motorhome_id = ?";
        template.update(sql,mot.getMotorhome_id(),mot.getMotorhome_reg_number(),mot.getMotorhome_brand(),mot.getMotorhome_room_height(),mot.getMotorhome_model(),mot.getMotorhome_odometer(),mot.getType_id(), mot.getMotorhome_id());
        return null;
    }
    /*
    Denne metode bruges til at finde motorhomes som ikke er lejet ud mellem de 2 parameteroverført datoer
    Når alle motorhomes er fundet bliver de returneret som en list
     */
    public List<Motorhome> findAvailable(String startDate, String endDate){
        //Dette SQL statement har en subquery som finder motorhome_id for de contracts som har datoer imellem de to datoer. den ydre query returnere så alle de motorhome_id's der ikke er i subquery
        String sql = "SELECT motorhome_id,motorhome_reg_number FROM motorhomes WHERE motorhome_id NOT IN (SELECT motorhome_id FROM contracts WHERE ? BETWEEN contract_start_date AND contract_end_date OR ? BETWEEN contract_start_date AND contract_end_date OR ? >= contract_start_date AND ? <= contract_end_date)";
        RowMapper<Motorhome> rowMapper = new BeanPropertyRowMapper<>(Motorhome.class);
        return template.query(sql,rowMapper,startDate,endDate,startDate,endDate);
    }

    /*
    Denne metode finder alle type_id's og type_descriptions for motorhomes
    Den returnere så en liste med dem efterfølgende
     */
    public List<Motorhome> fetchTypes(){ // Lavet af JT
        String sql = "SELECT type_id, type_description FROM mh_types";
        RowMapper<Motorhome> rowMapper = new BeanPropertyRowMapper<>(Motorhome.class);
        return template.query(sql,rowMapper);
    }
}
