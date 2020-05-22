package com.example.demo.Service;

import com.example.demo.Model.Invoice;
import com.example.demo.Repository.InvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    InvoiceRepo repo;

    public List<Invoice> fetchAll(){
        return repo.fetchAll();
    }
    public Invoice addInvoice(Invoice invoice){
        return repo.addInvoice(invoice);
    }
    public Invoice findInvoiceById(int id){
        return repo.findInvoiceById(id);
    }
    public Boolean deleteInvoice(int id){
        return repo.deleteInvoice(id);
    }
    public Invoice updateInvoice(int id, Invoice invoice){
        return repo.updateInvoice(id,invoice);
    }
}
