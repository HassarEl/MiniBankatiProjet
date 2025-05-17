package ma.bankati.web.controllers.clientController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/client/*", loadOnStartup = 5)
public class ClientServlet extends HttpServlet {
    private ClientController clientController;

    @Override
    public void init() throws ServletException {
        System.out.println("ClientController créé et initialisé");
        clientController = new ClientController();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getContextPath();
        if(path == null || "/".equals(path)) {
            clientController.showAll(request, response);
        } else if (path.equals("/client")) {
            clientController.showAll(request, response);
        }else{
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    @Override
    public void destroy() {
        System.out.println("ClientController détruit");
    }
}
