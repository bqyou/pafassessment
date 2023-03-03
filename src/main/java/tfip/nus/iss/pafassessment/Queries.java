package tfip.nus.iss.pafassessment;

public class Queries {

    public static final String MYSQL_SELECT_NAME_ACCOUNTIDS = """
            SELECT name, account_id FROM railway.accounts;
                """;

    public static final String MYSQL_SELECT_BY_ACCOUNTID = """
            SELECT * FROM railway.accounts WHERE account_id = ?
            """;

    public static final String MYSQL_SELECT_BAL_BY_ACCOUNTID = """
            SELECT balance FROM railway.accounts WHERE account_id = ?
            """;

    public static final String MYSQL_UPDATE_BAL_BY_ACCOUNTID = """
            UPDATE railway.accounts set balance = ? WHERE account_id = ?;
                """;

}
