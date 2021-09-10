package services;

import dao.ReimbursementDao;
import models.Reimbursement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class ReimbursementServiceTest {
    @Mock
    private ReimbursementDao reimbursementDaoMock;

    @InjectMocks
    private ReimbursementService reimbursementService = new ReimbursementServiceImpl(reimbursementDaoMock);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnInstanceOfUserServiceImpl() {
        Assert.assertNotNull(reimbursementService);
    }

    @Test
    public void shouldReturnListOfAllReimbursements() {
        List<Reimbursement> reimbursements = Arrays.asList(
                new Reimbursement(new BigDecimal("27.13"), "rental car", 1, 2),
                new Reimbursement(new BigDecimal("16.25"), "hotel room", 1, 3)
        );
        doReturn(reimbursements).when(reimbursementDaoMock).getAllReimbursements();

        List<Reimbursement> results = reimbursementService.getAllReimbursements();

        Assert.assertNotNull(results);
        Assert.assertEquals(2, results.size());

        verify(reimbursementDaoMock, atLeast(1)).getAllReimbursements();
    }

    @Test
    public void shouldReturnReimbursementsByUserId() {
        List<Reimbursement> reimbursements = Arrays.asList(
                new Reimbursement(new BigDecimal("27.13"), "rental car", 1, 2),
                new Reimbursement(new BigDecimal("16.25"), "hotel room", 1, 3)
        );
        doReturn(reimbursements).when(reimbursementDaoMock).getAllReimbursementsByUserId(1);

        List<Reimbursement> results = reimbursementService.getAllReimbursementsByUserId(1);

        Assert.assertNotNull(results);
        Assert.assertEquals(2, results.size());

        verify(reimbursementDaoMock, atLeast(1)).getAllReimbursementsByUserId(1);
    }
}
