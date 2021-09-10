package models;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Reimbursement {
    Integer reimbId;
    BigDecimal amount;
    Timestamp timeSubmitted;
    Timestamp timeResolved;
    String description;
    String firstName;
    String lastName;
    Integer authorId;
    Integer resolverId;
    Integer statusId;
    Integer typeId;

    public Reimbursement() {}

    public Reimbursement(BigDecimal amount, String description, Integer authorId, Integer typeId) {
        this.amount = amount;
        this.description = description;
        this.authorId = authorId;
        this.typeId = typeId;
    }

    public Reimbursement(Integer reimbId, Integer statusId) {
        this.reimbId = reimbId;
        this.statusId = statusId;
    }

    public Reimbursement(BigDecimal amount, Timestamp timeSubmitted, String description,
                         Integer authorId, Integer statusId, Integer typeId,
                         String firstName, String lastName) {
        this.amount = amount;
        this.timeSubmitted = timeSubmitted;
        this.description = description;
        this.authorId = authorId;
        this.statusId = statusId;
        this.typeId = typeId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Reimbursement(Integer reimbId, BigDecimal amount, Timestamp timeSubmitted, Timestamp timeResolved,
                         String description, Integer authorId, Integer resolverId,
                         Integer statusId, Integer typeId, String firstName, String lastName) {
        this.reimbId = reimbId;
        this.amount = amount;
        this.timeSubmitted = timeSubmitted;
        this.timeResolved = timeResolved;
        this.description = description;
        this.authorId = authorId;
        this.resolverId = resolverId;
        this.statusId = statusId;
        this.typeId = typeId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getReimbId() {
        return reimbId;
    }

    public void setReimbId(Integer reimbId) {
        this.reimbId = reimbId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getTimeSubmitted() {
        return timeSubmitted;
    }

    public void setTimeSubmitted(Timestamp timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
    }

    public Timestamp getTimeResolved() {
        return timeResolved;
    }

    public void setTimeResolved(Timestamp timeResolved) {
        this.timeResolved = timeResolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getResolverId() {
        return resolverId;
    }

    public void setResolverId(Integer resolverId) {
        this.resolverId = resolverId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbId=" + reimbId +
                ", amount=" + amount +
                ", timeSubmitted=" + timeSubmitted +
                ", timeResolved=" + timeResolved +
                ", description='" + description + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", authorId=" + authorId +
                ", resolverId=" + resolverId +
                ", statusId=" + statusId +
                ", typeId=" + typeId +
                '}';
    }
}