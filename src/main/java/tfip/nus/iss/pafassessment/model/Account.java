package tfip.nus.iss.pafassessment.model;

import java.math.BigDecimal;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Account {

    private String accountId;
    private String name;
    private BigDecimal balance;
    private String accountString;

    public String getAccountString() {
        return accountString;
    }

    public void setAccountString(String accountString) {
        this.accountString = accountString;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public static Account toAccount(SqlRowSet rs) {
        Account acc = new Account();
        acc.setAccountId(rs.getString("account_id"));
        acc.setName(rs.getString("name"));
        acc.setAccountString(toNameAccountID(acc));
        return acc;
    }

    public static String toNameAccountID(Account acc) {
        return "%s (%s)".formatted(acc.getName(), acc.getAccountId());
    }
}
