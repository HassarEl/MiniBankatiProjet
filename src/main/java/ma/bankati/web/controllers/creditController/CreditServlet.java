package ma.bankati.web.controllers.creditController;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.bankati.dao.creditDao.CreditDao;
import ma.bankati.model.Crediit.Credit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = {"/client/credit/*", "/admin/credit/*"}, loadOnStartup = 7)
public class CreditServlet extends HttpServlet {

    private CreditDao creditDao;
    private CreditController creditController;

    @Override
    public void init() throws ServletException {
        creditDao = new CreditDao();
        creditController = new CreditController();
        System.out.println("Init Servlet CreditServlet");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getPathInfo();
        System.out.println("path : " + path);
        if(path == null || "/".equals(path)) {
            List<Credit> credits = new ArrayList<>();
            credits = creditDao.findAll();
            request.setAttribute("credits", credits);
            request.getRequestDispatcher("/clients/credit/credit.jsp").forward(request, response);
        } else if (path.equals("/acc")) {
            creditController.accept(request, response);
        } else if (path.equals("/ref")) {
            creditController.refuse(request, response);
        } else if (path.equals("/delete")) {
            creditController.delete(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if(path.equals("/save")) {
            creditController.saveOrUpdate(request, response);
        }else if (path.equals("/edit")) {
            creditController.editForm(request, response);
        }else{
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
