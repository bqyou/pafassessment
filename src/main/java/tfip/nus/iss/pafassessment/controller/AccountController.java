package tfip.nus.iss.pafassessment.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import tfip.nus.iss.pafassessment.TransactionException;
import tfip.nus.iss.pafassessment.model.Account;
import tfip.nus.iss.pafassessment.model.Transfer;
import tfip.nus.iss.pafassessment.service.AccountService;
import tfip.nus.iss.pafassessment.service.FundsTransferService;
import tfip.nus.iss.pafassessment.service.LogAuditService;

@Controller
@RequestMapping
public class AccountController {

    @Autowired
    private AccountService accSvc;

    @Autowired
    private FundsTransferService fundsTransSvc;

    @Autowired
    private LogAuditService logAuditSvc;

    @GetMapping(path = { "/", "/index" })
    public String landingPage(Model model) {
        List<Account> accounts = accSvc.listAccounts();
        Transfer t = new Transfer();
        model.addAttribute("transfer", t);
        model.addAttribute("listofaccounts", accounts);
        model.addAttribute("message", "Welcome");
        return "index";
    }

    @PostMapping(path = "/transfer")
    public String transfer(@RequestBody MultiValueMap<String, String> form, Model model) throws TransactionException {
        Transfer t = new Transfer();
        t.setFromAccId(form.getFirst("fromAccId"));
        t.setToAccId(form.getFirst("toAccId"));
        t.setTransferAmount(Double.parseDouble(form.getFirst("transferAmount")));
        t.setComment(form.getFirst("comments"));
        System.out.println("the acc id is " + accSvc.accExist(t.getToAccId()).getAccountId());
        if (accSvc.accExist(t.getFromAccId()).getAccountId() == null) {
            model.addAttribute("message", "Account Id %s does not exist".formatted(t.getFromAccId()));
            model.addAttribute("transfer", t);
            List<Account> accounts = accSvc.listAccounts();
            model.addAttribute("listofaccounts", accounts);
            return "index";
        }
        if (accSvc.accExist(t.getToAccId()).getAccountId() == null) {
            model.addAttribute("message", "Account Id %s does not exist".formatted(t.getToAccId()));
            model.addAttribute("transfer", t);
            List<Account> accounts = accSvc.listAccounts();
            model.addAttribute("listofaccounts", accounts);
            return "index";
        }
        if (t.getTransferAmount() <= 0) {
            model.addAttribute("message", "Amount to transfer cannot be 0 or negative");
            model.addAttribute("transfer", t);
            List<Account> accounts = accSvc.listAccounts();
            model.addAttribute("listofaccounts", accounts);
            return "index";
        }
        if (t.getTransferAmount() < 10) {
            model.addAttribute("message", "Minimum amount to transfer is $10");
            model.addAttribute("transfer", t);
            List<Account> accounts = accSvc.listAccounts();
            model.addAttribute("listofaccounts", accounts);
            return "index";
        }
        Double fromAccBal = accSvc.findBalById(t.getFromAccId()).doubleValue();
        if (t.getTransferAmount() > fromAccBal) {
            model.addAttribute("message", "Insufficient balance to process transfer");
            model.addAttribute("transfer", t);
            List<Account> accounts = accSvc.listAccounts();
            model.addAttribute("listofaccounts", accounts);
            return "index";
        }
        if (t.getFromAccId().equals(t.getToAccId())) {
            model.addAttribute("message", "From account and To account cannot be the same");
            model.addAttribute("transfer", t);
            List<Account> accounts = accSvc.listAccounts();
            model.addAttribute("listofaccounts", accounts);
            return "index";
        }
        Boolean transfer = false;
        transfer = fundsTransSvc.transferFund(t.getFromAccId(), t.getToAccId(),
                BigDecimal.valueOf(t.getTransferAmount()));
        if (!transfer) {
            model.addAttribute("message", "Error in transaction");
            model.addAttribute("transfer", t);
            List<Account> accounts = accSvc.listAccounts();
            model.addAttribute("listofaccounts", accounts);
            return "index";
        }
        t.setTransactionId(UUID.randomUUID().toString().substring(0, 8));
        // logAuditSvc.logTransaction(t);
        model.addAttribute("transfer", t);
        model.addAttribute("fromAccString", accSvc.accExist(t.getFromAccId()).getAccountString());
        model.addAttribute("toAccString", accSvc.accExist(t.getToAccId()).getAccountString());
        return "success";
    }
}
