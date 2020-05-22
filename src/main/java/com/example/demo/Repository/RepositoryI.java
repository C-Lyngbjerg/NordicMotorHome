package com.example.demo.Repository;

import java.util.List;

public interface RepositoryI {

    public List fetchAll();

    public Object add(Object obj);

    public Boolean delete(int id);

    public Object findById(int id);

}
