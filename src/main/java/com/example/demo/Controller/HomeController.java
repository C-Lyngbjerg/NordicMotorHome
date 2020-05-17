package com.example.demo.Controller;

import com.example.demo.Model.Customer;
import com.example.demo.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/")
    public String index(){
        return "home/index";
    }

    @GetMapping("/customerTable")
    public String customerTable(Model model){
        List<Customer> customerList = customerService.fetchAll();
        model.addAttribute("customers",customerList);
        return "home/customerTable";
    }
    @GetMapping("/createCustomer")
    public String create(){
        return "home/createCustomer";
    }
    @PostMapping("/createCustomer")
    public String createCustomer(@ModelAttribute Customer customer){
        customerService.addCustomer(customer);
        return "redirect:/customerTable";
    }
}
