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

    public List<Contract> fetchAll(){ // Lavet JT og SR
        return repo.fetchAll();
    }
  
    public Object add(Object obj){ //Lavet af JT og SR
        return repo.add(obj);
    }
  
    public Object findById(int id){ // lavet af JT
        return repo.findById(id);
    }
  
    public Boolean delete(int id){ // Lavet af JT
        return repo.delete(id);
    }

    public Object update(Object obj){ // Lavet af JT og SR
        return repo.update(obj);
    }
  
    public List<Double> calculateRentPeriodAndPrice(Contract con){ // Lavet af JT og SR
        return repo.calculateRentPeriodAndPrice(con);
    }
  
    public Contract cancelContract(int id){ // Lavet af JT
        return repo.cancelContract(id);
    }
  
}
