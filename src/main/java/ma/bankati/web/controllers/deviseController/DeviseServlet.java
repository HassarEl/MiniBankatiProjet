package ma.bankati.web.controllers.deviseController;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.bankati.dao.creditDao.CreditDao;
import ma.bankati.dao.dataDao.fileDb.DataDao;
import ma.bankati.service.moneyServices.IMoneyService;
import ma.bankati.service.moneyServices.serviceDirham.ServiceDh;
import ma.bankati.service.moneyServices.serviceDollar.ServiceUSDollar;
import ma.bankati.service.moneyServices.servicePound.ServicePound;

import java.io.IOException;

@WebServlet(value = {"/client/devise/*"}, loadOnStartup = 9)
public class DeviseServlet extends HttpServlet {

    private IMoneyService service;
    private DeviseController deviseController;
    private CreditDao dao;

    @Override
    public void init() throws ServletException {
        service = (IMoneyService) getServletContext().getAttribute("moneyService");
        deviseController = new DeviseController(service);
        dao = new CreditDao();
        System.out.println("DeviseServlet init");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if(path.equals("/usd")) {
            deviseController.showUsd(request, response);
        }else if(path.equals("/mad")){
            deviseController.showMad(request, response);
        }else if(path.equals("/pound")){
            deviseController.showPound(request, response);
        }else{
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
