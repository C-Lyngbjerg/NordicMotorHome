package com.example.demo.Repository;

import com.example.demo.Model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvoiceRepo {

    @Autowired
    JdbcTemplate template;

    public List<Invoice> fetchAll(){
        String sql = "SELECT * FROM invoices";
        RowMapper<Invoice> rowMapper = new BeanPropertyRowMapper<>(Invoice.class);
        return template.query(sql, rowMapper);
    }
    public Invoice addInvoice(Invoice invoice){
        String sql = "INSERT INTO invoices (invoice_id,invoice_total_price,invoice_distance_driven,contract_id) VALUES (?,?,?,?)";
        template.update(sql,invoice.getInvoice_id(),invoice.getInvoice_total_price(),invoice.getInvoice_distance_driven(),invoice.getContract_id());
        return null;
    }
    public Invoice findInvoiceById(int id){
        String sql = "SELECT * FROM invoices WHERE invoice_id = ?";
        RowMapper<Invoice> rowMapper = new BeanPropertyRowMapper<>(Invoice.class);
        Invoice invoice = template.queryForObject(sql, rowMapper,id);
        return invoice;
    }
    public int findContractById(Invoice invoice){ //Skal nok hedder calculateDistanceDriven eller noget i den stil
        String sql = "SELECT inv.invoice_odometer_end - con.contract_odometer_start AS total_driven FROM contracts con JOIN invoices inv ON con.contract_id = ?";
        return template.update(sql, invoice.getContract_id());
    }
    public Boolean deleteInvoice(int id){
        String sql = "DELETE FROM invoices WHERE invoice_id = ?";
        return template.update(sql, id) < 0;
    }
    public Invoice updateInvoice(int id, Invoice invoice){
        String sql = "UPDATE invoices SET invoice_id = ?, invoice_total_price = ?, invoice_distance_driven = ?,contract_id = ? WHERE invoice_id = ?";
        template.update(sql, invoice.getInvoice_id(),invoice.getInvoice_total_price(),invoice.getInvoice_distance_driven(),invoice.getContract_id(),invoice.getInvoice_id());
        return null;
    }
}
