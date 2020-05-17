package com.example.demo.Repository;

import com.example.demo.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class    CustomerRepo {
    @Autowired
    JdbcTemplate template;

    public List<Customer> fetchAll(){
        String sql = "SELECT * FROM customers";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql, rowMapper);
    }
    public Customer addCustomer(Customer cus){
        String sql = "INSERT INTO customers (customer_id,customer_first_name,customer_last_name,customer_address,customer_drivers_license,customer_license_type, customer_phone,customer_city,customer_country,customer_zip_code) VALUES (?,?,?,?,?,?,?,?,?,?)";
        template.update(sql,cus.getCustomer_id(),cus.getCustomer_first_name(),cus.getCustomer_last_name(),cus.getCustomer_address(),cus.getCustomer_drivers_license(),cus.getCustomer_license_type(),cus.getCustomer_phone(),cus.getCustomer_city(),cus.getCustomer_country(),cus.getCustomer_zip_code());
        return null;
    }
    public Customer findCustomerById(int id){
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        Customer cus =template.queryForObject(sql, rowMapper,id);
        return cus;
    }
    public Boolean deleteCustomer(int id){
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        return template.update(sql, id) < 0;
    }
    public Customer updateCustomer(int id, Customer cus){
        String sql = "UPDATE customers SET customer_id = ?, customer_first_name = ?, customer_last_name = ?,customer_address = ?, customer_drivers_license = ?, customer_license_type = ?,customer_phone = ?, customer_city = ?, customer_county = ?, customer_zip_code = ? WHERE customer_id = ?";
        template.update(sql, cus.getCustomer_id(),cus.getCustomer_first_name(),cus.getCustomer_last_name(),cus.getCustomer_address(),cus.getCustomer_drivers_license(),cus.getCustomer_license_type(),cus.getCustomer_phone(),cus.getCustomer_city(),cus.getCustomer_country(),cus.getCustomer_zip_code(), cus.getCustomer_id());
        return null;
    }
}
