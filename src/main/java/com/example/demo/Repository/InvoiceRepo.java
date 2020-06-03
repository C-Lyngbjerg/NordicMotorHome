package com.example.demo.Repository;

import com.example.demo.Model.Contract;
import com.example.demo.Model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class InvoiceRepo implements RepositoryI{ // Lavet af WO & CB

    @Autowired
    JdbcTemplate template;
    /*  fetchAll() henter data fra table invoices i databasen, via et (DML) SQL statement, der sammen med en rowMapper
    bliver lavet til et SQL query, ved hjælp af 'template' laver en Invoice instance, bestående af data
    fra databasen.
    */
    public List<Invoice> fetchAll(){ // Lavet af WO
        String sql = "SELECT invoice_id, invoice_total_price, i.invoice_odometer_end - con.contract_odometer_start AS invoice_distance_driven,(SELECT DATEDIFF(contract_end_date,contract_start_date) FROM contracts WHERE contracts.contract_id = i.contract_id) AS invoice_rent_days,i.invoice_odometer_end,i.invoice_fuel_gage,i.contract_id FROM invoices i JOIN contracts con ON i.contract_id = con.contract_id";
        RowMapper<Invoice> rowMapper = new BeanPropertyRowMapper<>(Invoice.class);
        return template.query(sql, rowMapper);
    }
    // Muliggør at man via klienten, kan skabe en ny Invoice, der bliver tilføjet til databasen, ud fra et (DML) SQL query
    public Invoice add(Object object){ // Lavet af WO
        String sql = "INSERT INTO invoices (invoice_id,invoice_total_price,invoice_distance_driven,invoice_odometer_end,contract_id, invoice_rent_days, invoice_fuel_gage) VALUES (?,?,?,?,?,?,?)";
        Invoice invoice = (Invoice) object;
        template.update(sql,invoice.getInvoice_id(),invoice.getInvoice_total_price(),invoice.getInvoice_distance_driven(),invoice.getInvoice_odometer_end(),invoice.getContract_id(),invoice.getInvoice_rent_days(),invoice.isInvoice_fuel_gage());
        return null;
    }
    //Søger databasen efter et specifikt datasæt, der i dette tilfælde er en specifik invoice, via dens primary key (invoice_id)
    public Invoice findById(int id){ // Lavet af CB
        String sql = "SELECT invoice_id, invoice_total_price, i.invoice_odometer_end - con.contract_odometer_start AS invoice_distance_driven,(SELECT DATEDIFF(contract_end_date,contract_start_date) FROM contracts WHERE contracts.contract_id = i.contract_id) AS invoice_rent_days,i.invoice_odometer_end,i.invoice_fuel_gage,i.contract_id FROM invoices i JOIN contracts con ON i.contract_id = con.contract_id WHERE i.invoice_id = ?";
        RowMapper<Invoice> rowMapper = new BeanPropertyRowMapper<>(Invoice.class);
        Invoice invoice = template.queryForObject(sql, rowMapper,id);
        return invoice;
    }
    // Fjerner (DELETE) et datasæt i databasens 'invoices' table, der bliver fundet via dens primary key (invoice_id)
    public Boolean delete(int id){ // Lavet af WO & CB
        String sql = "DELETE FROM invoices WHERE invoice_id = ?";
        return template.update(sql, id) < 0;
    }
    /*Opdaterer (UPDATE) et datasæt i databasen 'invoices' table, fundet via dens primary key (invoice_id)
      Den bruger en UPDATE og sætter hver attribut i et datasæts til en ny værdi (eller den nuværende,
      hvis ingen ændringer bliver lavet til den givende attribut i klienten).
     */
    public Invoice update(Object object){ // Lavet af WO & CB
        Invoice invoice = (Invoice) object;
        String sql = "UPDATE invoices SET invoice_id = ?, invoice_total_price = ?, invoice_distance_driven = ?,invoice_odometer_end = ?,contract_id = ?, invoice_fuel_gage = ?, invoice_rent_days = ? WHERE invoice_id = ?";
        template.update(sql, invoice.getInvoice_id(),invoice.getInvoice_total_price(),invoice.getInvoice_distance_driven(),invoice.getInvoice_odometer_end(),invoice.getContract_id(), invoice.isInvoice_fuel_gage(), invoice.getInvoice_rent_days(),invoice.getInvoice_id());
        return null;
    }
    public Boolean checkContractId(int contract_id){ // Lavet af CB
        String sql = "SELECT contract_id FROM contracts WHERE contract_id = ?";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        /*if((template.queryForObject(sql, rowMapper, contract_id)) == null){
            return false;
        }*/
        try{
            template.queryForObject(sql, rowMapper, contract_id);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }
}
