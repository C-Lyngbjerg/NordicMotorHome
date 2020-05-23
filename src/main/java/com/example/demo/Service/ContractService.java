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
    public Object add(Object obj){
        return repo.add(obj);
    }
    public Object findById(int id){
        return repo.findById(id);
    }
    public Boolean delete(int id){
        return repo.delete(id);
    }
    public Contract updateContract(int id,Contract con){
        return repo.update(id,con);
    }
    public List<Double> calculateRentPeriodAndPrice(Contract con){
        return repo.calculateRentPeriodAndPrice(con);
    }
    public Contract cancelContract(int id){
        return repo.cancelContract(id);
    }
}
