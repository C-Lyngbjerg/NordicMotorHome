package com.example.demo.Repository;

import com.example.demo.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CustomerRepo implements RepositoryI{ // Lavet af WO & CB
    @Autowired
    JdbcTemplate template;

    /* Denne fetchAll() tager et resultset fra databasen, ud fra SELECT statementet, der bliver sendt som et query. Rowmapper tager det resultset der bliver lavet
       og fylder det op med objekter af Customer klassen, der bliver puttet i en List<> */
    public List<Customer> fetchAll(){
        String sql = "SELECT customer_id, customer_first_name, customer_last_name, customer_address, customer_drivers_license,customer_license_type, customer_phone,customer_nationality,zip_code AS customer_zip_code FROM customers";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql, rowMapper);
    }
    // Skaber en ny customer, ud fra et 'INSERT' DML query til databasen.
    public Customer add(Object obj){
        //Caster objekt til customer (nødvendigt på grund af interfacebrug)
        Customer cus = (Customer) obj;
        /*Try catch, der bliver brugt til at bestemme, hvorvidt der også skal laves et nyt datasæt i zips table i databasen
          eller om den skal bruge et der allerede eksistere i databasen.*/
        try {
            //Bruger zips data, der allerede eksistere i zips table og laver derfor bare en ny customer.
            String sql2 = "INSERT INTO customers (customer_id,customer_first_name,customer_last_name,customer_address,customer_drivers_license,customer_license_type, customer_phone,customer_nationality,zip_code) VALUES (?,?,?,?,?,?,?,?,?)";
            template.update(sql2, cus.getCustomer_id(), cus.getCustomer_first_name(), cus.getCustomer_last_name(), cus.getCustomer_address(), cus.getCustomer_drivers_license(), cus.getCustomer_license_type(), cus.getCustomer_phone(), cus.getCustomer_nationality(), cus.getCustomer_zip_code());
        }catch (Exception exception){
            //Bruger zips data, der endnu ikke findes. Disse data skal derfor først laves og indsættes i zips table, hvorefter det kan bruges i customer table
            String sql1 = "INSERT INTO zips (zip_code,zip_city) VALUES (?,?)";
            template.update(sql1,cus.getCustomer_zip_code(),cus.getCustomer_city());
            String sql2 = "INSERT INTO customers (customer_id,customer_first_name,customer_last_name,customer_address,customer_drivers_license,customer_license_type, customer_phone,customer_nationality,zip_code) VALUES (?,?,?,?,?,?,?,?,?)";
            template.update(sql2, cus.getCustomer_id(), cus.getCustomer_first_name(), cus.getCustomer_last_name(), cus.getCustomer_address(), cus.getCustomer_drivers_license(), cus.getCustomer_license_type(), cus.getCustomer_phone(), cus.getCustomer_nationality(), cus.getCustomer_zip_code());
        }
        return null;
    }
    // Finder et datasæt af customers fra databasen, og returnere det givende resultset eller NULL. Resultsettet bliver dertil lavet til et Customer objekt
    public Customer findById(int id){
        String sql = "SELECT customer_id, customer_first_name, customer_last_name, customer_address, customer_drivers_license,customer_license_type, customer_phone,customer_nationality,c.zip_code AS customer_zip_code,z.zip_city AS customer_city FROM customers c JOIN zips z ON c.zip_code = z.zip_code WHERE customer_id = ?";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        Customer cus =template.queryForObject(sql, rowMapper,id);
        return cus;
    }
    //Sletter kunde, samt kontrakt og invoice denne kunde er en del af.
    public Boolean delete(int id){
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        return template.update(sql, id) < 0;
    }
    /* Opdatere en Customer. Hvis der bliver ændret info til zips, checker den om den allerede findes i databasen.
       hvis den findes, opdateres den bare via et UPDATE query. Hvis den ikke allerede eksistere, laves der først et nyt, for ikke at få en SQL exception. */
    public Customer update(Object obj){ // Lavet af WO
        Customer cus = (Customer) obj;
        try {
            String sql1 = "UPDATE zips SET zip_code = ?, zip_city = ? WHERE zip_code = ?";
            template.update(sql1, cus.getCustomer_zip_code(), cus.getCustomer_city(),cus.getCustomer_zip_code());
            String sql2 = "UPDATE customers SET customer_id = ?, customer_first_name = ?, customer_last_name = ?,customer_address = ?, customer_drivers_license = ?, customer_license_type = ?,customer_phone = ?, customer_nationality = ?, zip_code = ? WHERE customer_id = ?";
            template.update(sql2, cus.getCustomer_id(), cus.getCustomer_first_name(), cus.getCustomer_last_name(), cus.getCustomer_address(), cus.getCustomer_drivers_license(), cus.getCustomer_license_type(), cus.getCustomer_phone(), cus.getCustomer_nationality(), cus.getCustomer_zip_code(), cus.getCustomer_id());
        }catch(Exception e){
            String sql1 = "INSERT INTO zips (zip_code,zip_city) VALUES (?,?)";
            template.update(sql1, cus.getCustomer_zip_code(), cus.getCustomer_city());
            String sql2 = "UPDATE customers SET customer_id = ?, customer_first_name = ?, customer_last_name = ?,customer_address = ?, customer_drivers_license = ?, customer_license_type = ?,customer_phone = ?, customer_nationality = ?, zip_code = ? WHERE customer_id = ?";
            template.update(sql2, cus.getCustomer_id(), cus.getCustomer_first_name(), cus.getCustomer_last_name(), cus.getCustomer_address(), cus.getCustomer_drivers_license(), cus.getCustomer_license_type(), cus.getCustomer_phone(), cus.getCustomer_nationality(), cus.getCustomer_zip_code(), cus.getCustomer_id());
       }
        return null;
    }
}
