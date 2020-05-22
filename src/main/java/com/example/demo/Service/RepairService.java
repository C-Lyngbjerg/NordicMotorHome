package com.example.demo.Service;

import com.example.demo.Model.Repair;
import com.example.demo.Repository.RepairRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairService {
    @Autowired
    RepairRepo repairRepo;

    public List<Repair> fetchAll(){
        return repairRepo.fetchAll();
    }

    public Repair addRepair(Repair repair){
        return repairRepo.addRepair(repair);
    }

    public Repair findRepairById(int id){
        return repairRepo.findRepairById(id);
    }

    public Boolean deleteRepair(int id){
        return repairRepo.deleteRepair(id);
    }

    public Repair updateRepair(int id, Repair repair){
        return repairRepo.updateRepair(id,repair);
    }
}
