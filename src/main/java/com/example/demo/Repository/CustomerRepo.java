package com.example.demo.Repository;

import com.example.demo.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CustomerRepo implements RepositoryI{
    @Autowired
    JdbcTemplate template;

    public List<Customer> fetchAll(){
        String sql = "SELECT customer_id, customer_first_name, customer_last_name, customer_address, customer_drivers_license,customer_license_type, customer_phone,customer_nationality,zip_code AS customer_zip_code FROM customers";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql, rowMapper);
    }
    public Customer add(Object obj){
        Customer cus = (Customer) obj;
//        String sql1 = "INSERT INTO zips (zip_code,zip_city) VALUES (?,?)";
        String sql2 = "INSERT INTO customers (customer_id,customer_first_name,customer_last_name,customer_address,customer_drivers_license,customer_license_type, customer_phone,customer_nationality,zip_code) VALUES (?,?,?,?,?,?,?,?,?)";
//        template.update(sql1,cus.getCustomer_zip_code(),cus.getCustomer_city());
        template.update(sql2,cus.getCustomer_id(),cus.getCustomer_first_name(),cus.getCustomer_last_name(),cus.getCustomer_address(),cus.getCustomer_drivers_license(),cus.getCustomer_license_type(),cus.getCustomer_phone(),cus.getCustomer_nationality(),cus.getCustomer_zip_code());
        return null;
    }

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

    public Customer update(int id, Object obj){
        Customer cus = (Customer) obj;
        String sql1 = "UPDATE zips SET zip_code = ?, zip_city = ? WHERE zip_code = ?";
        String sql2 = "UPDATE customers SET customer_id = ?, customer_first_name = ?, customer_last_name = ?,customer_address = ?, customer_drivers_license = ?, customer_license_type = ?,customer_phone = ?, customer_nationality = ?, zip_code = ? WHERE customer_id = ?";
        template.update(sql1,cus.getCustomer_zip_code(),cus.getCustomer_city(),cus.getCustomer_zip_code());
        template.update(sql2, cus.getCustomer_id(),cus.getCustomer_first_name(),cus.getCustomer_last_name(),cus.getCustomer_address(),cus.getCustomer_drivers_license(),cus.getCustomer_license_type(),cus.getCustomer_phone(),cus.getCustomer_nationality(),cus.getCustomer_zip_code(), cus.getCustomer_id());
        return null;
    }
}
