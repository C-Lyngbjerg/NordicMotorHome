package com.example.demo.Service;

import com.example.demo.Model.Customer;
import com.example.demo.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo repo;

    public List<Customer> fetchAll(){
        return repo.fetchAll();
    }
    public Customer add(Customer cus){
        return repo.add(cus);
    }
    public Customer findById(int id){
        return repo.findById(id);
    }
    public Boolean delete(int id){
        return repo.delete(id);
    }
    public Customer update(int id,Customer cus){
        return repo.update(id,cus);
    }

}
