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
import org.springframework.web.bind.annotation.PathVariable;
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
        customerService.create(customer);
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

    //Metoden til at lave et contract objekt, udregne den samlede pris og tilføje det til databasen
    @PostMapping("/createContract")
    public String createContract(@ModelAttribute Contract contract){
        List<Double> datesAndPrice = contractService.calculateRentPeriodAndPrice(contract); // metoden retunere en list som indenholder den daglige pris for lejet og samlet antaldage lejeperioden er på
        contract.calculatePrice(datesAndPrice);// Listen der blev inisaliseret før bliver parameter overført til at kunne udregne den totale pris for udlejningsperioden
        contractService.add(contract);//contracten bliver tilføjet til databasen
        return "redirect:/contractTable";
    }

    @GetMapping("/viewOneContract/{contract_id}")
    public String viewOneContract(@PathVariable("contract_id") int contract_id, Model model){
        model.addAttribute(contractService.findById(contract_id));
        return "home/viewOneContract";
    }

    @GetMapping("/cancelContract/{contract_id}")
    public String cancelContract(@PathVariable("contract_id") int contract_id, Model model){
        model.addAttribute("contract",contractService.cancelContract(contract_id));
        return "home/cancelledContract";

    }
}
