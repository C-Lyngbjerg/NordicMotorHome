package com.example.demo;

import com.example.demo.Model.Contract;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void dateTest(){

    }

    @Test
    public void cancelContractTest(){
        Contract con = new Contract();
        con.setContract_rent_price(15592.12345);
        System.out.println(con.getContract_rent_price());
        Double daysToStart = 5.0;
        if(daysToStart >= 50){
            if(con.getContract_rent_price() < 200){
                con.setContract_rent_price(200);
            }else {
                con.setContract_rent_price(con.getContract_rent_price() * 0.2);
            }
        }else if(daysToStart >= 15 && daysToStart <= 49){
            con.setContract_rent_price(con.getContract_rent_price()*0.5);
        }else if(daysToStart < 15 && daysToStart > 0){
            con.setContract_rent_price(con.getContract_rent_price()*0.8);
        }else{
            con.setContract_rent_price(con.getContract_rent_price()*0.95);
        }
        System.out.println(con.getContract_rent_price());
    }

    @Test
    public void objectCastingTest(){
        Object con = new Object();
    }

}
