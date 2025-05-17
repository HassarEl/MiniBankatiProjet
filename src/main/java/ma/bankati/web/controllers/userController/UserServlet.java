package ma.bankati.web.controllers.userController;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ma.bankati.web.controllers.profileController.ProfileController;

import java.io.IOException;
import java.net.URISyntaxException;

@WebServlet(value = "/users/*" , loadOnStartup = 4)
public class UserServlet extends HttpServlet {

    private UserController userController;
    private ProfileController profileController;

    @Override
    public void init() throws ServletException {
        System.out.println("UserController créé et initialisé");
            userController = new UserController();
            profileController = new ProfileController();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null || "/".equals(path)) {
            userController.showAll(req, resp);
        } else if (path.equals("/edit")) {
            userController.editForm(req, resp);
        } else if (path.equals("/delete")) {
            userController.delete(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path.equals("/save")) {
            userController.saveOrUpdate(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void destroy() {
        System.out.println("UserController détruit");
    }
}
