package com.example.demo.Controller;

import com.example.demo.Model.Contract;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Motorhome;
import com.example.demo.Service.ContractService;
import com.example.demo.Service.CustomerService;
import com.example.demo.Service.MotorhomeService;
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
    @Autowired
    MotorhomeService motorhomeService;
    @Autowired
    ContractService contractService;

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
    public String createCustomer(){
        return "home/createCustomer";
    }
    @PostMapping("/createCustomer")
    public String createCustomer(@ModelAttribute Customer customer){
        customerService.addCustomer(customer);
        return "redirect:/customerTable";
    }

    @GetMapping("/motorhomeTable")
    public String motorhomeTable(Model model){
        List<Motorhome> motorhomeList = motorhomeService.fetchall();
        model.addAttribute("motorhomes",motorhomeList);
        return "home/motorhomeTable";
    }
    @GetMapping("/createMotorhome")
    public String createMotorhome(){
        return "home/createMotorhome";
    }
    @PostMapping("/createMotorhome")
    public String createMotorhome(@ModelAttribute Motorhome motorhome){
        motorhomeService.addMotorhome(motorhome);
        return "redirect:/motorhomeTable";
    }

    @GetMapping("/contractTable")
    public String contractTable(Model model){
        List<Contract> contractList = contractService.fetchAll();
        model.addAttribute("contracts", contractList);
        return "home/contractTable";
    }
    @GetMapping("/createContract")
    public String createContract() {
        return "home/createContract";
    }
    @PostMapping("/createContract")
    public String createContract(@ModelAttribute Contract contract){
        int rentPeriodPrice = contractService.calculateRentPeriodPrice(contract);
        contract.calculatePrice(rentPeriodPrice);
        contractService.addContract(contract);
        return "redirect:/contractTable";
    }
}
