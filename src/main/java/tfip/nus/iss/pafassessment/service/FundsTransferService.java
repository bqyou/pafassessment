package tfip.nus.iss.pafassessment.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tfip.nus.iss.pafassessment.TransactionException;
import tfip.nus.iss.pafassessment.repo.AccountsRepository;

@Service
public class FundsTransferService {

    @Autowired
    private AccountsRepository accRepo;

    @Transactional(rollbackFor = TransactionException.class)
    public Boolean transferFund(String fromAccId, String toAccId, BigDecimal transferAmt) throws TransactionException {
        BigDecimal fromAccIdBeforeTransfer = accRepo.findBalById(fromAccId);
        BigDecimal toAccIdBeforeTransfer = accRepo.findBalById(toAccId);
        BigDecimal fromAccIdAfterTransfer = fromAccIdBeforeTransfer.subtract(transferAmt);
        if (fromAccIdAfterTransfer.doubleValue() < 0) {
            throw new TransactionException("Insufficient balance to transfer");
        }
        BigDecimal toAccIdAfterTransfer = toAccIdBeforeTransfer.add(transferAmt);
        accRepo.updateBalById(fromAccId, fromAccIdAfterTransfer);
        accRepo.updateBalById(toAccId, toAccIdAfterTransfer);

        return true;
    }

}
