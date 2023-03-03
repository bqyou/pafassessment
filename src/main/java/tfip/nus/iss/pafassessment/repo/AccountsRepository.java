package tfip.nus.iss.pafassessment.repo;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import tfip.nus.iss.pafassessment.model.Account;

import static tfip.nus.iss.pafassessment.Queries.*;

@Repository
public class AccountsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Account> listAccounts() {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(MYSQL_SELECT_NAME_ACCOUNTIDS);
        List<Account> accounts = new LinkedList<Account>();
        while (rs.next()) {
            Account a = Account.toAccount(rs);
            accounts.add(a);
        }
        return accounts;
    }

    public Account accountExist(String accountId) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(MYSQL_SELECT_BY_ACCOUNTID, accountId);
        Account a = new Account();
        rs.next();
        if (rs.getRow() == 0) {
            return a;
        }
        a = Account.toAccount(rs);
        return a;
    }

    public BigDecimal findBalById(String accountId) {
        Account a = jdbcTemplate.queryForObject(MYSQL_SELECT_BY_ACCOUNTID,
                BeanPropertyRowMapper.newInstance(Account.class), accountId);
        return a.getBalance();
    }

    public void updateBalById(String accountId, BigDecimal balance) {
        jdbcTemplate.update(MYSQL_UPDATE_BAL_BY_ACCOUNTID, balance, accountId);
    }

}
