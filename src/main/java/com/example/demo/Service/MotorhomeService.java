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

    public Motorhome findMotorhomeById(int id){
        return motorhomeRepo.findMotorhomeById(id);
    }

    public Boolean deleteMotorhome(int id){
        return motorhomeRepo.deleteMotorhome(id);
    }

    public Motorhome updateMotorhome(int id, Motorhome motorhome){
        return motorhomeRepo.updateMotorhome(id,motorhome);
    }
}
