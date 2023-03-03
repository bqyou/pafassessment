package tfip.nus.iss.pafassessment.model;

import java.util.Date;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Transfer {

    private String transactionId;

    private String fromAccId;

    private String toAccId;

    private Double transferAmount;

    private String comment;

    public String getComment() {
        return comment;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFromAccId() {
        return fromAccId;
    }

    public void setFromAccId(String fromAccId) {
        this.fromAccId = fromAccId;
    }

    public String getToAccId() {
        return toAccId;
    }

    public void setToAccId(String toAccId) {
        this.toAccId = toAccId;
    }

    public Double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public static JsonObject toJson(Transfer t) {
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("transactionId", t.getTransactionId())
                .add("date", new Date().toString())
                .add("from_account", t.getFromAccId())
                .add("to_account", t.getToAccId())
                .add("amount", t.getTransferAmount());

        return builder.build();
    }

}
