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
    // Fetch all, tager alle invoice data fra DB, laver et invoice objekt, hvor den
    //tager data fra invoice_odometer_end og trækker det fra contract_odometer_start, og sætter det til at være invoice_distance_driven.
    public List<Invoice> fetchAll(){
        String sql = "SELECT invoice_id, invoice_total_price, i.invoice_odometer_end - con.contract_odometer_start AS invoice_distance_driven,i.invoice_odometer_end,i.contract_id FROM invoices i JOIN contracts con ON i.contract_id = con.contract_id ";
        RowMapper<Invoice> rowMapper = new BeanPropertyRowMapper<>(Invoice.class);
        return template.query(sql, rowMapper);
    }

    public Invoice addInvoice(Invoice invoice){
        String sql = "INSERT INTO invoices (invoice_id,invoice_total_price,invoice_distance_driven,invoice_odometer_end,contract_id) VALUES (?,?,?,?,?)";
        template.update(sql,invoice.getInvoice_id(),invoice.getInvoice_total_price(),invoice.getInvoice_distance_driven(),invoice.getInvoice_odometer_end(),invoice.getContract_id());
        return null;
    }
    public Invoice findInvoiceById(int id){
        String sql = "SELECT * FROM invoices WHERE invoice_id = ?";
        RowMapper<Invoice> rowMapper = new BeanPropertyRowMapper<>(Invoice.class);
        Invoice invoice = template.queryForObject(sql, rowMapper,id);
        return invoice;
    }
    public Boolean deleteInvoice(int id){
        String sql = "DELETE FROM invoices WHERE invoice_id = ?";
        return template.update(sql, id) < 0;
    }
    public Invoice updateInvoice(int id, Invoice invoice){
        String sql = "UPDATE invoices SET invoice_id = ?, invoice_total_price = ?, invoice_distance_driven = ?,invoice_odometer_end = ?,contract_id = ? WHERE invoice_id = ?";
        template.update(sql, invoice.getInvoice_id(),invoice.getInvoice_total_price(),invoice.getInvoice_distance_driven(),invoice.getInvoice_odometer_end(),invoice.getContract_id(),invoice.getInvoice_id());
        return null;
    }
}
