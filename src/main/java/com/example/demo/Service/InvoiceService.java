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
    public Invoice add(Invoice invoice){
        return repo.add(invoice);
    }
    public Invoice findById(int id){
        return repo.findById(id);
    }
    public Boolean delete(int id){
        return repo.delete(id);
    }
    public Invoice updateInvoice(int id, Invoice invoice){
        return repo.updateInvoice(id,invoice);
    }
}
