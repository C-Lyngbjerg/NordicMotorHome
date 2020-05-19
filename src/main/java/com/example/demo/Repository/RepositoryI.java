package com.example.demo.Repository;



import java.util.List;

public interface RepositoryI {

    public List fetchAll();

    public Object create(Object o);


    public Object findById();


    public Boolean delete();


    public Object update();
}
