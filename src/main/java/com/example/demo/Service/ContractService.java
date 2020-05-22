package com.example.demo.Service;

import com.example.demo.Model.Contract;
import com.example.demo.Repository.ContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractService {
    @Autowired
    ContractRepo repo;

    public List<Contract> fetchAll(){
        return repo.fetchAll();
    }
    public Contract addContract(Contract con){
        return repo.addContract(con);
    }
    public Contract findContractById(int id){
        return repo.findContractById(id);
    }
    public Boolean deleteCustomer(int id){
        return repo.deleteContract(id);
    }
    public Contract updateContract(int id,Contract con){
        return repo.updateContract(id,con);
    }
    public int calculateRentPeriodPrice(Contract con){
        return repo.calculateRentPeriodPrice(con);
    }
}
