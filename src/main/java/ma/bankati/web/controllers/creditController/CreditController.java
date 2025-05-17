package ma.bankati.web.controllers.creditController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.bankati.dao.creditDao.CreditDao;
import ma.bankati.dao.userDao.IUserDao;
import ma.bankati.dao.userDao.UserDao;
import ma.bankati.model.Crediit.Credit;
import ma.bankati.model.Crediit.Status;
import ma.bankati.model.users.ERole;
import ma.bankati.model.users.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CreditController {

    private final IUserDao userDao;
    public CreditDao creditDao;

    public CreditController(){
        this.userDao = new UserDao();
    }

    public void showAll(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Credit> credits = new ArrayList<>();
        request.setAttribute("credits", credits);
        request.getRequestDispatcher("/clients/credit.jsp").forward(request, response);
    }

    /*
    public void save(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        Credit credit = new Credit();
        credit.setMtCredit(Double.parseDouble(request.getParameter("mtCredit")));
        credit.setMotif(request.getParameter("motif"));
        credit.setNbrMois(Long.parseLong(request.getParameter("nbrMois")));
        credit.setStatus(Status.ENCOURS);
        //creditDao = new CreditDao();
        creditDao.save(credit);

        response.sendRedirect(request.getContextPath() + "/client/credit");
    }
    */


    // Save or Edit Methode
    public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("id");
        Long id = (idStr == null || idStr.isEmpty()) ? null : Long.parseLong(idStr);
        creditDao = new CreditDao();

        Credit credit = Credit.builder()
                .id(id)
                .mtCredit(Double.parseDouble(request.getParameter("mtCredit")))
                .nbrMois(Long.valueOf(request.getParameter("nbrMois")))
                .motif(request.getParameter("motif"))
                .status(Status.ENCOURS)
                .build();

        if (id == null) {
            creditDao.save(credit);
        } else {
            creditDao.update(credit);
        }
        response.sendRedirect(request.getContextPath() + "/client/credit");
    }

    // Methode De Modification Form
    public void editForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long id = Long.parseLong(request.getParameter("id"));
        creditDao = new CreditDao();
        Credit credit = creditDao.findById(id);
        request.setAttribute("credit", credit);
        showAll(request, response);
    }

    // Methode Delete
    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        creditDao = new CreditDao();
        creditDao.deleteById(id);
        System.out.println("CreditController Delete id : " + id + ".");
        response.sendRedirect(request.getContextPath() + "/client/credit");
    }

    // Methode Update Status Accept
    public void accept(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        creditDao = new CreditDao();
        Credit credit = creditDao.findById(id);
        credit.setStatus(Status.ACCEPT);
        creditDao.update(credit);
        response.sendRedirect(request.getContextPath() + "/client/credit");
    }

    public void refuse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        creditDao = new CreditDao();
        Credit credit = creditDao.findById(id);
        credit.setStatus(Status.REFUSE);
        creditDao.update(credit);
        response.sendRedirect(request.getContextPath() + "/client/credit");
    }

}
