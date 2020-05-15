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
    public Customer addCustomer(Customer cus){
        return repo.addCustomer(cus);
    }
    public Customer findCustomerById(int id){
        return repo.findCustomerById(id);
    }
    public Boolean deleteCustomer(int id){
        return repo.deleteCustomer(id);
    }
    public Customer updateCustomer(int id,Customer cus){
        return repo.updateCustomer(id,cus);
    }

}
