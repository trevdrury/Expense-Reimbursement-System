package services;

import models.Reimbursement;
import models.User;

import java.util.List;

public interface ReimbursementService {
    List<Reimbursement> getAllReimbursements();
    List<Reimbursement> getAllReimbursementsByUserId(Integer userId);
    void createReimbursement(Reimbursement reimbursement);
    void updateReimbursement(User resolver, Reimbursement reimbursement);
}
