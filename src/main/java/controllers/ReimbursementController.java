package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Reimbursement;
import models.Response;
import models.User;
import services.ReimbursementService;
import services.ReimbursementServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

public class ReimbursementController {
    private static ReimbursementController reimbursementController;
    ReimbursementService reimbursementService;

    private ReimbursementController() { reimbursementService = new ReimbursementServiceImpl(); }

    public static ReimbursementController getInstance() {
        if (reimbursementController == null)
            reimbursementController = new ReimbursementController();

        return reimbursementController;
    }

    public void getAllReimbursements(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        if (req.getParameterMap().containsKey("id")) {
            Integer userId = Integer.parseInt(req.getParameter("id"));

            out.println(new ObjectMapper().writeValueAsString(new Response("Reimbursements retrieved for user",
                    true, reimbursementService.getAllReimbursementsByUserId(userId))));
        } else {
            System.out.println(new ObjectMapper().writeValueAsString(new Response("Reimbursements retrieved",
                    true, reimbursementService.getAllReimbursements())));
            out.println(new ObjectMapper().writeValueAsString(new Response("Reimbursements retrieved",
                    true, reimbursementService.getAllReimbursements())));
        }
    }

    public void createReimbursement(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        Reimbursement reimbursement = new ObjectMapper().readValue(requestBody, Reimbursement.class);

        reimbursementService.createReimbursement(reimbursement);

        out.println(new ObjectMapper().writeValueAsString(new Response("Reimbursement created for user with ID: " + reimbursement.getAuthorId(),
                true, null)));
    }

    public void updateReimbursement(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);

        User resolver = (User) session.getAttribute("userObj");

        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        Reimbursement reimbursement = new ObjectMapper().readValue(requestBody, Reimbursement.class);

        reimbursementService.updateReimbursement(resolver, reimbursement);

        out.println(new ObjectMapper().writeValueAsString(new Response("Reimbursement with ID: " + reimbursement.getReimbId() + " updated",
               true, null)));
    }
}
