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
    public Customer create(Customer cus){
        return repo.create(cus);
    }
    public Customer findCustomerById(int id){
        return repo.findById(id);
    }
    public Boolean deleteCustomer(int id){
        return repo.delete(id);
    }
    public Customer updateCustomer(int id,Customer cus){
        return repo.update(id,cus);
    }

}
