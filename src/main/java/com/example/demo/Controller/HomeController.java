package com.example.demo.Controller;

import com.example.demo.Model.*;
import com.example.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.util.List;
//Spring frameworks MVC
@Controller
public class HomeController implements WebMvcConfigurer {
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

    //TODO kig på at indbygge knapper til valg af zipkode og andre ting du ved bro

    //TODO check up på hvad denne gør
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
    }
    @GetMapping("/")
    public String index() {
        return "home/index";
    }
    //Laver et table af customer objekterne fra databasen der bliver vist til brugeren af systemet
    @GetMapping("/customerTable")
    public String customerTable(Model model) {
        List<Customer> customerList = customerService.fetchAll();
        model.addAttribute("customers", customerList);
        return "home/customerTable";
    }
    //returnere til customerTable.html, når man trykker på den tilknyttede 'return' knap
    @PostMapping("/customerTable")
    public String returnFromTable(){
        return "redirect:/";
    }
    // Directer bruger til createCustomer.html, hvor man kan inputte data til at lave en customer
    // Customer objekt bliver taget med her, for at blive brugt under Springs indbygget inputvalidering.
    @GetMapping("/createCustomer")
    public String createCustomer(Customer customer) {
        return "home/createCustomer";
    }
    //Laver et customer objekt ud fra indtastet information i createCustomer.html
    //Bruger et 'BindingResult' objekt, der indeholder nødvendige behaviour og state for brug af Springs Validator.
    //@Valid er en notation der bliver brugt til at sige til programmet at den skal opfylde validation notationen i customer klassen.
    @PostMapping("/createCustomer")
    public String createCustomer(@ModelAttribute @Valid Customer customer, BindingResult bindingResult) {
        //hvis der er en error, så gå tilbage til createCustomer.html for at prøve igen, med beskrivende error messages
        if(bindingResult.hasErrors()){
            return "home/createCustomer";
        }
        customerService.add(customer);
        return "redirect:/customerTable";
    }
    //Går til side der viser fulde information om en given repair, ud fra den repair man trykker 'view' på i 'repairTable'
    @GetMapping("/viewCustomer/{customer_id}")
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
    public String updateRepair(@ModelAttribute @Valid Customer customer, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "home/updateCustomer";
        }
        customerService.update(customer);
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
    @PostMapping("/motorhomeTable")
    public String motorhomeTable() {
        return "redirect:/";
    }

    @GetMapping("/createMotorhome")
    public String createMotorhome(Motorhome motorhome) {
        return "home/createMotorhome";
    }

    @PostMapping("/createMotorhome")
    public String createMotorhome(@ModelAttribute @Valid Motorhome motorhome,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "home/createMotorhome";
        }
        motorhomeService.add(motorhome);
        return "redirect:/motorhomeTable";
    }

    @GetMapping("/viewOneMotorhome/{motorhome_id}")
    public String viewOneMotorhome(@PathVariable("motorhome_id") int motorhome_id, Model model){
        model.addAttribute(motorhomeService.findById(motorhome_id));
        return "home/viewOneMotorhome";
    }
    @PostMapping("/viewOneMotorhome")
    public String viewOneMotorhome() {
        return "redirect:/motorhomeTable";
    }

    @GetMapping("/updateMotorhome/{motorhome_id}")
    public String updateMotorhome(@PathVariable("motorhome_id") int motorhome_id, Model model){
        model.addAttribute(motorhomeService.findById(motorhome_id));
        return "home/updateMotorhome";
    }
    @PostMapping("/updateMotorhome")
    public String updateMotorhome(@ModelAttribute Motorhome motorhome){
        motorhomeService.update(motorhome);
        return "redirect:/motorhomeTable";
    }
    @GetMapping("/deleteMotorhome/{motorhome_id}")
    public String delete(@PathVariable("motorhome_id") int motorhome_id){
        boolean deleted = motorhomeService.delete(motorhome_id);
        if(deleted){
            return "redirect:/";
        }
        else{
            return "redirect:/";
        }
    }

    /*
    * Invoice del
    */

    // Create invoice table i html filen 'invoiceTable'
    @GetMapping("/invoiceTable")
    public String invoiceTable(Model model) {
        List<Invoice> invoiceList = invoiceService.fetchAll();
        model.addAttribute("invoices", invoiceList);
        return "home/invoiceTable";
    }
  
    // Returnerer fra et givent punkt til invoieTable side
    @PostMapping("/invoiceTable")
    public String invoiceTable() {
        return "redirect:/";
    }

    // Går til createInvoice siden, hvor man kan lave en ny invoice
    @GetMapping("/createInvoice")
    public String createInvoice(Model model) {
        createCon(model);
        return "home/createInvoice";
    }
    public String createCon(Model model){
        List<Contract> contractList = contractService.fetchAll();
        model.addAttribute("contracts", contractList);
        return "home/createInvoice";
    }
    // returnerer fra /createInvoice siden og creater den nye invoice data, med de informationer der er tastet ind
    // Dette bliver gjort ved hjælp af @ModelAttribute der derefter tilføje data til databasen, via add() i invoiceRepo klasse.

    @PostMapping("/createInvoice")
    public String createInvoice(@ModelAttribute Invoice invoice) {
        invoiceService.add(invoice);
        return "redirect:/invoiceTable";
    }

    @PostMapping("/viewInvoice")
    public String viewInvoice(){
        return "redirect:/invoiceTable";
    }

    //Show info about the chosen invoice on a new site called "viewInvoice"
    @GetMapping("/viewInvoice/{invoice_id}")
    public String viewInvoice(@PathVariable("invoice_id") int id, Model model){
        model.addAttribute("invoice", invoiceService.findById(id));
        return "home/viewInvoice";
    }

    @GetMapping("/deleteInvoice/{invoice_id}")
    public String deleteInvoice(@PathVariable("invoice_id") int id){
        invoiceService.delete(id);
        return "redirect:/invoiceTable";
    }

    @GetMapping("/updateInvoice/{invoice_id}")
    public String updateInvoice(@PathVariable("invoice_id") int id, Model model){
        model.addAttribute("invoice",invoiceService.findById(id));
        return "home/updateInvoice";
    }

    @PostMapping("/updateInvoice")
    public String updateInvoice(@ModelAttribute Invoice invoice){
        invoiceService.update(invoice);
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


    @GetMapping("/selectRentDays")
    public String selectRentDays(){
        return "home/selectRentDays";
    }
  
    @PostMapping("/contractTable")
    public String contractTable() {
        return "redirect:/";
    }

    @GetMapping("/createContract")
    public String createContract(@ModelAttribute Contract contract, Model motorhomeModel, Model contractModel) {
        List<Motorhome> motorhomeList = motorhomeService.findAvailable(contract.getContract_start_date(),contract.getContract_end_date());
        motorhomeModel.addAttribute("motorhomeList", motorhomeList);
        contractModel.addAttribute("contract", contract);
        return "home/createContract";
    }

    //Metoden til at lave et contract objekt, udregne den samlede pris og tilføje det til databasen
    @PostMapping("/finalizeContract")
    public String finalizeContract(@ModelAttribute Contract contract){
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
    @PostMapping("/viewOneContract")
    public String viewOneContract() {
        return "redirect:/contractTable";
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
        // TODO invoice skal ændres/laves her.
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

    /* *********** *
     * Repair del  *
     ************* */

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
    public String createRepair(Repair repair) {
        return "home/createRepair";
    }
    //Står for at lave et nyt repair objekt ud fra indsat data, ved tryk på
    @PostMapping("/createRepair")
    public String createRepair(@ModelAttribute @Valid Repair repair,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "home/createRepair";
        }
        repairService.add(repair);
        return "redirect:/repairTable";
    }

    //Tager til side, der giver input muligheder for at opdatere en given repair, ud fra den repair man trykker 'update' på i 'repairTable'
    @GetMapping("/updateRepair/{repair_id}")
    public String updateRepair(@PathVariable("repair_id") int id,Model model){
        model.addAttribute("repair",repairService.findById(id));
        return "home/updateRepair";
    }
    //Sender de opdaterede information til database via et DML statement, ud fra den givende input
    @PostMapping("/updateRepair")
    public String updateRepair(@ModelAttribute @Valid Repair repair,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "home/updateRepair";
        }
        repairService.update(repair);
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
