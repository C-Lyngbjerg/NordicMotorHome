package com.example.demo.Service;

import com.example.demo.Model.Repair;
import com.example.demo.Repository.RepairRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairService {// WO
    @Autowired
    RepairRepo repairRepo;

    public List<Repair> fetchAll(){
        return repairRepo.fetchAll();
    }

    public Repair add(Repair repair){
        return repairRepo.add(repair);
    }

    public Repair findById(int id){
        return repairRepo.findById(id);
    }

    public Boolean delete(int id){
        return repairRepo.delete(id);
    }

    public Repair update(Repair repair){
        return repairRepo.update(repair);
    }
}
