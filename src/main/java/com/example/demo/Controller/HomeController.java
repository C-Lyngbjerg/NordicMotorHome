package com.example.demo.Controller;

import com.example.demo.Model.Contract;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Invoice;
import com.example.demo.Model.Motorhome;
import com.example.demo.Service.ContractService;
import com.example.demo.Service.CustomerService;
import com.example.demo.Service.InvoiceService;
import com.example.demo.Service.MotorhomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
//Spring frameworks MVC
@Controller
public class HomeController {
    @Autowired
    CustomerService customerService;
    @Autowired
    MotorhomeService motorhomeService;
    @Autowired
    InvoiceService invoiceService;
    @Autowired
    ContractService contractService;


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
        customerService.add(customer);
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

    //TODO
    //TODO Lav udregning af ekstra omkostninger i forbindelse med 400 km om dagen i gennemsnit.
    //TODO Lav udregning af ekstra omkostninger i forbindelse med hvorvidt tank er under 50%

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

//    returnerer fra /createInvoice siden og creater den nye invoice data, med de informationer der er tastet ind
    // Dette bliver gjort ved hjælp af @ModelAttribute der derefter tilføje data til databasen, via add() i invoiceRepo klasse.
    @PostMapping("/createInvoice")
    public String createInvoice(@ModelAttribute Invoice invoice) {
        invoiceService.addInvoice(invoice);
        return "redirect:/invoiceTable";
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

    //Denne metode bruges hvis man vælger at annullere en kontrakt.
    @GetMapping("/cancelContract/{contract_id}")
    public String cancelContract(@PathVariable("contract_id") int contract_id, Model model){
        //Tilføjer den contract som har fået ændret sin pris efter annullering
        model.addAttribute("contract",contractService.cancelContract(contract_id));
        return "home/cancelledContract";
    }

    /*
        Hvis det bliver annulleringen bliver bekræftet opdateres databasen med denne metode
        Der bliver parameter overført to ting til metoden. kontraktens id og kontraktens nye pris
        Først finder den konktrakten fra databasen ud fra id'et og laver et nyt kontrakt objekt
        så bruges der en set funktion til at ændre kontraktens pris til det der bliver parameteroverført
        objektet bliver så brugt til at opdatere databasen ligesom der bliver gjort i updatedContract metoden nedenfor
    */
    @GetMapping("/cancelContract/confirmCancellation/{id}/{price}")
    public String confirmCancellation(@PathVariable int id, @PathVariable double price){
        Contract con = (Contract) contractService.findById(id);
        con.setContract_rent_price(price);
        contractService.update(con);
        //invoice skal ændres/laves her.
        return "redirect:/contractTable";
    }

    @GetMapping("/updateContract/{contract_id}")
    public String updateContract(@PathVariable("contract_id") int contract_id, Model model){
        model.addAttribute("contract", contractService.findById(contract_id));
        return "home/updateContract";
    }

    @PostMapping("/updatedContract")
    public String updatedContract(@ModelAttribute Contract contract){
        List<Double> priceAndDateDiff = contractService.calculateRentPeriodAndPrice(contract);
        contract.calculatePrice(priceAndDateDiff);
        contractService.update(contract);
        return "redirect:/contractTable";
    }

    @GetMapping("/deleteContract/{contract_id}")
    public String deleteContract(@PathVariable("contract_id") int contract_id){
        boolean deleted = contractService.delete(contract_id);
        if(deleted){
            return "redirect:/contractTable";
        }
        else{
            return "redirect:/contractTable";
        }
    }
}
//én lille piskommentar