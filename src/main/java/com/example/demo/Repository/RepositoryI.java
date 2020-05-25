package com.example.demo.Repository;

import java.sql.SQLException;
import java.util.List;

public interface RepositoryI {

    public List fetchAll();

    public Object add(Object obj);

    public Boolean delete(int id);

    public Object findById(int id);

    public Object update(Object obj);
}
