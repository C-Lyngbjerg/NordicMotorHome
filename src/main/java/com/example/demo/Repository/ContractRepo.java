package com.example.demo.Repository;

import com.example.demo.Model.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ContractRepo{ // JT & SR
    /* Alle SQL queries bliver excecuted med 'template', hvor objecterne bliver skabt, ved hjælp af rowMapper og 'Beans'
       'template' er en instance af JdbcTemplate klassen, der hører under Spring Framework.
       'bean' er et objekt der bliver initialiseret af Spring Framework, hvor objektets type bliver skrevet i dens < > syntax
     */
    @Autowired
    JdbcTemplate template;

    // Vores fetchAll metode henter alle kontrakterne og mapper et database resultset i en List bestående af Contract objekter.
    public List<Contract> fetchAll(){ // Lavet af JT & SR
        String sql = "SELECT * FROM contracts c JOIN motorhomes m ON c.motorhome_id = m.motorhome_id ORDER BY contract_id";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        return template.query(sql, rowMapper);
    }
    // Vores addContract metode indsætter data vi får fra inputs ind i dette prepared statement og opretter en kontrakt med disse informationer i databsen.
    public Object add(Object obj){ // Lavet af JT & SR
        Contract con = (Contract) obj;
        String sql = "INSERT INTO contracts (contract_id,contract_rent_price,contract_start_date,contract_end_date,contract_odometer_start,contract_extra_bike_rack,contract_extra_bed_sheets,contract_extra_child_seat,contract_extra_picnic_table,contract_extra_chairs,contract_pick_up_distance,contract_drop_off_distance,customer_id,motorhome_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        template.update(sql,con.getContract_id(),con.getContract_rent_price(),con.getContract_start_date(),con.getContract_end_date(),con.getContract_odometer_start(),con.isContract_extra_bike_rack(),con.isContract_extra_bed_sheets(),con.isContract_extra_child_seat(),con.isContract_extra_picnic_table(),con.isContract_extra_chairs(),con.getContract_pick_up_distance(),con.getContract_drop_off_distance(),con.getCustomer_id(),con.getMotorhome_id());
        return null;
    }
    // Denne metode vælger alle fra contracts hvor id'et matcher det parameteroverførte id.
    // så mapper Rowmapperen informationerne fra kontrakten og der oprettes et contract object som returneres.
    public Object findById(int id){ //Lavet af JT
        String sql = "SELECT * FROM contracts c JOIN motorhomes m ON c.motorhome_id = m.motorhome_id WHERE contract_id = ?";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        Contract con =template.queryForObject(sql,rowMapper,id);
        return con;
    }


    /*Denne metode bruger prepared statement til at slette en kontrakt med det parameteroverføte id.
     SQL statementet findet matchet med id'et og sletter.*/

    public Boolean delete(int id){ // Lavet af JT
        String sql = "DELETE FROM contracts WHERE contract_id = ?";
        return template.update(sql, id) < 0;
    }
  
    /*Denne metode vælge via prepared sql statement en kontrakt med det parameteroverførte id og giver mulighed for at ændre i informationer.
    Den displayer alle informationerne og gemmer efterfølgende det der står, ændret eller uændret.*/

    public Object update(Object obj){ // Lavet af JT
        Contract con = (Contract) obj;
        String sql = "UPDATE contracts SET contract_rent_price = ?,contract_start_date = ?,contract_end_date = ?,contract_odometer_start = ?,contract_extra_bike_rack = ?,contract_extra_bed_sheets = ?,contract_extra_child_seat = ?,contract_extra_picnic_table = ?,contract_extra_chairs = ?,customer_id = ?,motorhome_id = ? WHERE contract_id = ?";
        template.update(sql,con.getContract_rent_price(),con.getContract_start_date(),con.getContract_end_date(),con.getContract_odometer_start(),con.isContract_extra_bike_rack(),con.isContract_extra_bed_sheets(),con.isContract_extra_child_seat(),con.isContract_extra_picnic_table(),con.isContract_extra_chairs(),con.getCustomer_id(),con.getMotorhome_id(), con.getContract_id());
        return null;
    }

    /* Denne metode tager den parameteroverførte kontrakt og søger efter price_per_day i mh_types tabellen hvor type_id matcher med type_id for den autocamper det er brugt i kontrakten.
     Så laver den et list object priceList som indeholder den pris der bliver hentet hjem vi statementet og templaten.
     Derefter lavet den et statement som tager to datoer og regner distancen mellem disse.
     Den laver næst et List object dateDiff som tager fat i de to datoer, start og end fra Contract objectet.
     Så laver den et tredje list object dateDiffAndDays som er en ArrayList der indeholder double værdier.
    De to værdier der er blevet sat ind i det to lists bliver added til den sidste liste og den liste bliver returneret og brugt i homecontrolleren.*/

    public List<Double> calculateRentPeriodAndPrice(Contract con){ // Lavet af JT og SR
        String sql1 = "SELECT type_price_per_day FROM mh_types WHERE type_id IN (SELECT type_id FROM motorhomes m WHERE m.motorhome_id = ?)";
        Double price = template.queryForObject(sql1, Double.class, con.getMotorhome_id());
        String sql = "SELECT DATEDIFF(?,?) AS dateDiff";
        Double dateDiff = template.queryForObject(sql, Double.class, con.getContract_end_date(), con.getContract_start_date());
        List<Double> priceAndDateDiff = new ArrayList<Double>();
        priceAndDateDiff.add(price);
        priceAndDateDiff.add(dateDiff);
        return priceAndDateDiff;
    }

    /*
    Denne metode bruges til at udregne prisen for en kontrakt hvis den bliver annulleret
    Den finder den udvalgte kontrakt og udregner hvor mange dage der er indtil kontraktens start dato.
    Derefter nedsætter den prisen med den procentdel den skal
    */
    public Contract cancelContract(int id){ // Lavet af JT
        String sql = "SELECT * FROM contracts WHERE contract_id = ?";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        Contract con = template.queryForObject(sql,rowMapper,id);
        String sql1 = "SELECT DATEDIFF(?,CURDATE()) FROM contracts WHERE contract_id = ?"; // Her udregner den dagene mellem dagens dato og starten på kontrakten
        Double daysToStart = template.queryForObject(sql1, Double.class, con.getContract_start_date(),con.getContract_id());
        // De forskellige if statement til at omregne prisen med den procent del der passer.
        if(daysToStart >= 50){
            if((con.getContract_rent_price() * 0.2) < 200){
                con.setContract_rent_price(200);
            }else {
                con.setContract_rent_price(con.getContract_rent_price() * 0.2);
            }
        }else if(daysToStart >= 15 && daysToStart <= 49){
            con.setContract_rent_price(con.getContract_rent_price()*0.5);
        }else if(daysToStart < 15 && daysToStart > 0){
            con.setContract_rent_price(con.getContract_rent_price()*0.8);
        }else{
            con.setContract_rent_price(con.getContract_rent_price()*0.95);
        }
        //Ændringen bliver ikke opdateret til databasen endnu. den retuneres bare
        return con;
    }
}
