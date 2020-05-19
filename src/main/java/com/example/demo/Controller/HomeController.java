package com.example.demo.Controller;

import com.example.demo.Model.Customer;
import com.example.demo.Model.Invoice;
import com.example.demo.Model.Motorhome;
import com.example.demo.Service.CustomerService;
import com.example.demo.Service.InvoiceService;
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
    InvoiceService invoiceService;

    @GetMapping("/")
    public String index() {
        return "home/index";
    }

    @GetMapping("/customerTable")
    public String customerTable(Model model) {
        List<Customer> customerList = customerService.fetchAll();
        model.addAttribute("customers", customerList);
        return "home/customerTable";
    }

    @GetMapping("/createCustomer")
    public String createCustomer() {
        return "home/createCustomer";
    }

    @PostMapping("/createCustomer")
    public String createCustomer(@ModelAttribute Customer customer) {
        customerService.addCustomer(customer);
        return "redirect:/customerTable";
    }


    @GetMapping("/motorhomeTable")
    public String motorhomeTable(Model model) {
        List<Motorhome> motorhomeList = motorhomeService.fetchall();
        model.addAttribute("motorhomes", motorhomeList);
        return "home/motorhomeTable";
    }

    @GetMapping("/createMotorhome")
    public String createMotorhome() {
        return "home/createMotorhome";
    }

    @PostMapping("/createMotorhome")
    public String createMotorhome(@ModelAttribute Motorhome motorhome) {
        motorhomeService.addMotorhome(motorhome);
        return "redirect:/motorhomeTable";
    }

    //Create invoice table i html filen 'invoiceTable'
    @GetMapping("/invoiceTable")
    public String invoiceTable(Model model) {
        List<Invoice> invoiceList = invoiceService.fetchAll();
        model.addAttribute("invoices", invoiceList);
        return "home/invoiceTable";
    }

    //Returnere fra et givent punkt til invoieTable side
    @PostMapping("/invoiceTable")
    public String invoiceTable() {
        return "redirect:/";
    }

    // Går til createInvoice siden, hvor man kan lave en ny invoice
    @GetMapping("/createInvoice")
    public String createInvoice() {
        return "home/createInvoice";
    }

//    returnerer fra /createInvoice siden og creater den nye invoice, med de informationer der er tastet ind
    @PostMapping("/createInvoice")
    public String createInvoice(@ModelAttribute Invoice invoice) {
        invoiceService.addInvoice(invoice);
        return "redirect:/invoiceTable";
    }
}