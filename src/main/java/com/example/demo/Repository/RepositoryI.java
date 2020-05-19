package com.example.demo.Repository;


import com.example.demo.Model.Customer;
import sun.jvm.hotspot.asm.Operand;

import java.util.List;

public interface RepositoryI {

    public List fetchAll();

    public Object create(Object o);


    public Object findById();


    public Boolean delete();


    public Object update();
}
