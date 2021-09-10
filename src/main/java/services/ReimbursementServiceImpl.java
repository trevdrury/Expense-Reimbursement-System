package services;

import dao.ReimbursementDao;
import dao.ReimbursementDaoImpl;
import models.Reimbursement;
import models.User;

import java.util.List;

public class ReimbursementServiceImpl implements ReimbursementService {
    ReimbursementDao reimbursementDao;

    public ReimbursementServiceImpl(ReimbursementDao reimbursementDaoMock) {
        this.reimbursementDao = reimbursementDaoMock;
    }

    public ReimbursementServiceImpl() { reimbursementDao = ReimbursementDaoImpl.getInstance(); }

    @Override
    public List<Reimbursement> getAllReimbursements() {
        return reimbursementDao.getAllReimbursements();
    }

    @Override
    public List<Reimbursement> getAllReimbursementsByUserId(Integer userId) {
        return reimbursementDao.getAllReimbursementsByUserId(userId);
    }

    @Override
    public void createReimbursement(Reimbursement reimbursement) {
        reimbursementDao.createReimbursement(reimbursement);
    }

    @Override
    public void updateReimbursement(User resolver, Reimbursement reimbursement) {
        reimbursementDao.updateReimbursement(resolver, reimbursement);
    }
}
