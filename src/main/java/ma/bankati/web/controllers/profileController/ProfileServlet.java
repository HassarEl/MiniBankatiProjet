package ma.bankati.web.controllers.profileController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.bankati.web.controllers.clientController.ClientController;
import ma.bankati.web.controllers.userController.UserController;

import java.io.IOException;

@WebServlet(value = "/profile/*", loadOnStartup = 6)
public class ProfileServlet extends HttpServlet {

    private ProfileController profileController;
    private ClientController clientController;
    private UserController userController;

    @Override
    public void init() throws ServletException {
        System.out.println("ProfileServlet init");
        profileController = new ProfileController();
        clientController = new ClientController();
        userController = new UserController();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        // System.out.println(path);
        if(path == null || "/".equals(path)) {
            profileController.showProfile(request, response);
        }else{
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path.equals("/save")) {
            profileController.Update(request, response);
            System.out.println("send to save profile controller");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
