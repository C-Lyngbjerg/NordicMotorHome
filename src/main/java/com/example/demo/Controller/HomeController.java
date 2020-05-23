package com.example.demo.Controller;

import com.example.demo.Model.*;
import com.example.demo.Service.*;
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
    @Autowired
    RepairService repairService;


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
    //
    @PostMapping("/customerTable")
    public String returnFromTable(){
        return "redirect:/";
    }
    @GetMapping("/createCustomer")
    public String createCustomer() {
        return "home/createCustomer";
    }
    //
    @PostMapping("/createCustomer")
    public String createCustomer(@ModelAttribute Customer customer) {
        customerService.add(customer);
        return "redirect:/customerTable";
    }
    //Går til side der viser fulde information om en given repair, ud fra den repair man trykker 'view' på i 'repairTable'
    @GetMapping("/viewCustomer/{customer_id}") //TODO lortet gider ikke at vise city, nationality og zip code
    public String viewCustomer(@PathVariable("customer_id") int id, Model model){
        model.addAttribute("customer",customerService.findById(id));
        return "home/viewCustomer";
    }
    //Returnere til 'repairTable' ved at trykke 'return' i view siden.
    @PostMapping("/viewCustomer")
    public String viewCustomer(){
        return "redirect:/customerTable";
    }

    //Fjerne customer instance, fra siden og sletter det i DB via et DML DELETE statement.
    @GetMapping("/deleteCustomer/{customer_id}")
    public String deleteCustomer(@PathVariable("customer_id") int id){
        boolean deleted = customerService.delete(id);
        return "redirect:/customerTable";
    }

    //Tager til side, der giver input muligheder for at opdatere en given repair, ud fra den repair man trykker 'update' på i 'customerTable'
    @GetMapping("/updateCustomer/{customer_id}")
    public String updateCustomer(@PathVariable("customer_id") int id,Model model){
        model.addAttribute("customer",customerService.findById(id));
        return "home/updateCustomer";
    }
    //Sender de opdaterede information til database via et DML statement, ud fra den givende input
    @PostMapping("/updateCustomer")
    public String updateRepair(@ModelAttribute Customer customer){
        customerService.update(customer.getCustomer_id(), customer);
        return "redirect:/customerTable";
    }
    /*
    *
    *   Motorhome del
    *
    */
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

    /*
    * Invoice del
    */

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
    // returnerer fra /createInvoice siden og creater den nye invoice data, med de informationer der er tastet ind
    // Dette bliver gjort ved hjælp af @ModelAttribute der derefter tilføje data til databasen, via add() i invoiceRepo klasse.
    @PostMapping("/createInvoice")
    public String createInvoice(@ModelAttribute Invoice invoice) {
        invoiceService.addInvoice(invoice);
        return "redirect:/invoiceTable";
    }

    /*
     * Contract del
     */

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
    /*
     * Repair del
     */

    //står for at lave og vise de tilgængelige repair objekter, til html side 'repairTable'
    @GetMapping("/repairTable")
    public String createRepair(Model model){
        List<Repair> repairList = repairService.fetchAll();
        model.addAttribute("repairs", repairList);
        return "home/repairTable";
    }
    //Returnere fra et givent punkt i repair del, ved tryk på en return knap
    @PostMapping("/repairTable")
    public String returnFromRepair(){
        return "redirect:/";
    }

    //Tager dig til createRepair html side, så man kan indsætte data
    @GetMapping("/createRepair")
    public String createRepair() {
        return "home/createRepair";
    }
    //Står for at lave et nyt repair objekt ud fra indsat data, ved tryk på
    @PostMapping("/createRepair")
    public String createRepair(@ModelAttribute Repair repair){
        repairService.addRepair(repair);
        return "redirect:/repairTable";
    }

    //Tager til side, der giver input muligheder for at opdatere en given repair, ud fra den repair man trykker 'update' på i 'repairTable'
    @GetMapping("/updateRepair/{repair_id}")
    public String updateRepair(@PathVariable("repair_id") int id,Model model){
        model.addAttribute("repairs",repairService.findById(id));
        return "home/updateRepair";
    }
    //Sender de opdaterede information til database via et DML statement, ud fra den givende input
    @PostMapping("/updateRepair")
    public String updateRepair(@ModelAttribute Repair repair){
        repairService.update(repair.getRepair_id(), repair);
        return "redirect:/repairTable";
    }

    //Går til side der viser fulde information om en given repair, ud fra den repair man trykker 'view' på i 'repairTable'
    @GetMapping("/viewRepair/{repair_id}")
    public String viewRepair(@PathVariable("repair_id") int id, Model model){
        model.addAttribute("repair",repairService.findById(id));
        return "home/viewRepair";
    }
    //Returnere til 'repairTable' ved at trykke 'return' i view siden.
    @PostMapping("/viewRepair")
    public String viewRepair(){
        return "redirect:/repairTable";
    }

    //Fjerne repair instance, fra siden og sletter det i DB via et DML DELETE statement.
    @GetMapping("/deleteRepair/{repair_id}")
    public String deleteRepair(@PathVariable("repair_id") int id){
        boolean deleted = repairService.delete(id);
        return "redirect:/repairTable";
    }

    /*
    *
    *   Customer del
    *
    */


//    //Står for at lave et nyt repair objekt ud fra indsat data, ved tryk på
//    @PostMapping("/createRepair")
//    public String createRepair(@ModelAttribute Repair repair){
//        repairService.addRepair(repair);
//        return "redirect:/repairTable";
//    }
//

//



}
