package com.example.demo.Repository;

import com.example.demo.Model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class InvoiceRepo implements RepositoryI{
/* Alle SQL queries bliver excecuted med 'template', hvor objecterne bliver skabt, ved hjælp af rowMapper og 'Beans'
   'template' er en instance af JdbcTemplate klassen, der hører under Spring Framework.
   'bean' er et objekt der bliver initialiseret af Spring Framework, hvor objektets type bliver skrevet i dens < > syntax
 */
    @Autowired
    JdbcTemplate template;
/*  fetchAll() henter data fra table invoices i databasen, via et (DML) SQL statement, der sammen med en rowMapper
    bliver lavet til et SQL query, ved hjælp af 'template' laver en Invoice instance, bestående af data
    fra databasen.
 */
    public List<Invoice> fetchAll(){
        String sql = "SELECT invoice_id, invoice_total_price, i.invoice_odometer_end - con.contract_odometer_start AS invoice_distance_driven,(SELECT DATEDIFF(contract_end_date,contract_start_date) FROM contracts WHERE contracts.contract_id = i.contract_id) AS invoice_rent_days,i.invoice_odometer_end,i.invoice_fuel_gage,i.contract_id FROM invoices i JOIN contracts con ON i.contract_id = con.contract_id";
        RowMapper<Invoice> rowMapper = new BeanPropertyRowMapper<>(Invoice.class);
        return template.query(sql, rowMapper);
    }
    //Muliggør at man via klienten, kan skabe en ny Invoice, der bliver tilføjet til databasen, ud fra et (DML) SQL query
    public Invoice add(Object object){
        String sql = "INSERT INTO invoices (invoice_id,invoice_total_price,invoice_distance_driven,invoice_odometer_end,contract_id, invoice_rent_days, invoice_fuel_gage) VALUES (?,?,?,?,?,?,?)";
        Invoice invoice = (Invoice) object;
        template.update(sql,invoice.getInvoice_id(),invoice.getInvoice_total_price(),invoice.getInvoice_distance_driven(),invoice.getInvoice_odometer_end(),invoice.getContract_id(), invoice.isInvoice_fuel_gage(), invoice.getInvoice_rent_days());
        return null;
    }
    //Søger databasen efter et specifikt datasæt, der i dette tilfælde er en specifik invoice, via dens primary key (invoice_id)
    public Invoice findById(int id){
        String sql = "SELECT invoice_id, invoice_total_price, i.invoice_odometer_end - con.contract_odometer_start AS invoice_distance_driven,(SELECT DATEDIFF(contract_end_date,contract_start_date) FROM contracts WHERE contracts.contract_id = i.contract_id) AS invoice_rent_days,i.invoice_odometer_end,i.invoice_fuel_gage,i.contract_id FROM invoices i JOIN contracts con ON i.contract_id = con.contract_id WHERE i.invoice_id = ?";
        RowMapper<Invoice> rowMapper = new BeanPropertyRowMapper<>(Invoice.class);
        Invoice invoice = template.queryForObject(sql, rowMapper,id);
        return invoice;
    }
    // Fjerner (DELETE) et datasæt i databasens 'invoices' table, der bliver fundet via dens primary key (invoice_id)
    public Boolean delete(int id){
        String sql = "DELETE FROM invoices WHERE invoice_id = ?";
        return template.update(sql, id) < 0;
    }
    /*Opdaterer (UPDATE) et datasæt i databasen 'invoices' table, fundet via dens primary key (invoice_id)
      Den bruger en UPDATE og sætter hver attribut i et datasæts til en ny værdi (eller den nuværende,
      hvis ingen ændringer bliver lavet til den givende attribut i klienten).
     */
    public Invoice updateInvoice(int id, Invoice invoice){
        String sql = "UPDATE invoices SET invoice_id = ?, invoice_total_price = ?, invoice_distance_driven = ?,invoice_odometer_end = ?,contract_id = ?, invoice_fuel_gage = ?, invoice_rent_days = ? WHERE invoice_id = ?";
        template.update(sql, invoice.getInvoice_id(),invoice.getInvoice_total_price(),invoice.getInvoice_distance_driven(),invoice.getInvoice_odometer_end(),invoice.getContract_id(), invoice.isInvoice_fuel_gage(), invoice.getInvoice_rent_days(),invoice.getInvoice_id());
        return null;
    }
}
