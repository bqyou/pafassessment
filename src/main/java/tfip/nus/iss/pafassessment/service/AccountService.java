package tfip.nus.iss.pafassessment.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfip.nus.iss.pafassessment.model.Account;
import tfip.nus.iss.pafassessment.repo.AccountsRepository;

@Service
public class AccountService {

    @Autowired
    private AccountsRepository accRepo;

    public List<Account> listAccounts() {
        return accRepo.listAccounts();
    }

    public Account accExist(String accId) {
        return accRepo.accountExist(accId);
    }

    public BigDecimal findBalById(String accId) {
        return accRepo.findBalById(accId);
    }

}
