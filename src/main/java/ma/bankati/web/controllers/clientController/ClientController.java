package ma.bankati.web.controllers.clientController;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.bankati.dao.clientDao.ClientDao;
import ma.bankati.dao.clientDao.IClientDao;
import ma.bankati.model.users.User;

import java.io.IOException;
import java.util.List;

public class ClientController {

    private final IClientDao clientDao;

    public ClientController() {
        this.clientDao = new ClientDao();
    }

    public void showAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> clients = clientDao.findAll();
        request.setAttribute("clients", clients);
        request.getRequestDispatcher("/clients/home.jsp").forward(request, response);
    }


}
