package frontcontroller;

import controllers.ReimbursementController;
import controllers.UserController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="dispatcher", urlPatterns = "/api/*")
public class Dispatcher extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String URI = req.getRequestURI();

        switch (URI) {
            case "/api/login":
                if (req.getMethod().equals("POST"))
                    UserController.getInstance().login(req, resp);
                break;
            case "/api/logout":
                if (req.getMethod().equals("GET"))
                    UserController.getInstance().logout(req, resp);
                break;
            case "/api/reimbursements":
                switch (req.getMethod()) {
                    case "GET":
                        ReimbursementController.getInstance().getAllReimbursements(req, resp);
                        break;
                    case "POST":
                        ReimbursementController.getInstance().createReimbursement(req, resp);
                        break;
                    case "PATCH":
                        ReimbursementController.getInstance().updateReimbursement(req, resp);
                        break;
                }
                break;
            case "/api/users":
                if (req.getMethod().equals("GET"))
                    UserController.getInstance().getUsers(req, resp);
                break;
            case "/api/check-session":
                if (req.getMethod().equals("GET"))
                    UserController.getInstance().checkSession(req, resp);
        }
    }
}
