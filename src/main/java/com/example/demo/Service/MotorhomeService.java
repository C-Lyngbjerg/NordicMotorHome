package com.example.demo.Service;

import com.example.demo.Model.Motorhome;
import com.example.demo.Repository.MotorhomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotorhomeService { // WO, JT & CB
    @Autowired
    MotorhomeRepo motorhomeRepo;

    public List<Motorhome> fetchall(){
        return motorhomeRepo.fetchAll();
    }

    public Object add(Motorhome motorhome){
        return motorhomeRepo.add(motorhome);
    }

    public Object findById(int id){
        return motorhomeRepo.findById(id);
    }

    public Boolean delete(int id){
        return motorhomeRepo.delete(id);
    }

    public Object update(Motorhome motorhome){
        return motorhomeRepo.update(motorhome);
    }

    public List<Motorhome> findAvailable(String startDate, String endDate){
        return motorhomeRepo.findAvailable(startDate,endDate);
    }
}
