package ma.bankati.web.controllers.profileController;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ma.bankati.dao.dbDao.userDao.UserDao;
import ma.bankati.dao.userDao.IUserDao;
// import ma.bankati.dao.userDao.UserDao;
import ma.bankati.model.users.ERole;
import ma.bankati.model.users.User;

import java.io.IOException;

public class ProfileController {

    private final IUserDao userDao;
    public ProfileController(){
        this.userDao = new UserDao();
    }

    public void showProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // User client = userDao.findAll();
        HttpSession session  = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("connectedUser") : null;
        User client = userDao.findById(user.getId());
        request.setAttribute("client", client);
        request.getRequestDispatcher("/clients/profile.jsp").forward(request, response);
    }


    public void Update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("id");
        Long id = (idStr == null || idStr.isEmpty()) ? null : Long.parseLong(idStr);

        User user = User.builder()
                .id(id)
                .firstName(request.getParameter("firstName"))
                .lastName(request.getParameter("lastName"))
                .username(request.getParameter("username"))
                .password(request.getParameter("password"))
                .role(ERole.valueOf(request.getParameter("role")))
                        .build();

        userDao.update(user);

        request.setAttribute("client", user);
        response.sendRedirect(request.getContextPath() + "/logout");
    }


}
