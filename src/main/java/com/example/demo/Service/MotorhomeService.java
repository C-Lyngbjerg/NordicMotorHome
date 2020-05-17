package com.example.demo.Service;

import com.example.demo.Model.Motorhome;
import com.example.demo.Repository.MotorhomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotorhomeService {
    @Autowired
    MotorhomeRepo motorhomeRepo;

    public List<Motorhome> fetchall(){
        return motorhomeRepo.fetchAll();
    }

    public Motorhome addMotorhome(Motorhome motorhome){
        return motorhomeRepo.addMotorhome(motorhome);
    }

    public Motorhome findMotorhomeByRegNumber(String regNumber){
        return motorhomeRepo.findMotorhomeByRegNumber(regNumber);
    }

    public Boolean deleteMotorhome(String regNumber){
        return motorhomeRepo.deleteMotorhome(regNumber);
    }

    public Motorhome updateMotorhome(String regNumber, Motorhome motorhome){
        return motorhomeRepo.updateMotorhome(regNumber,motorhome);
    }
}
