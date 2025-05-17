package ma.bankati.web.controllers.deviseController;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.bankati.dao.dataDao.fileDb.DataDao;
import ma.bankati.service.moneyServices.IMoneyService;
import ma.bankati.service.moneyServices.serviceDirham.ServiceDh;
import ma.bankati.service.moneyServices.serviceDollar.ServiceUSDollar;
import ma.bankati.service.moneyServices.servicePound.ServicePound;
import ma.bankati.web.controllers.mainController.HomeController;

import java.io.IOException;

public class DeviseController {
    private IMoneyService service ;
    private IMoneyService serviceDh;
    private DataDao dao = new DataDao();

    public DeviseController(IMoneyService service) {
        this.service = service;
    }


    public void showUsd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service = new ServiceUSDollar(dao);
        serviceDh = new ServiceDh(dao);
        var devise = service.convertData();
        var result = serviceDh.convertData();
        request.setAttribute("result", result);
        request.setAttribute("devise", devise);
        request.getRequestDispatcher("/clients/home.jsp").forward(request, response);
    }

    public void showPound(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service = new ServicePound(dao);
        serviceDh = new ServiceDh(dao);
        var devise = service.convertData();
        var result = serviceDh.convertData();
        request.setAttribute("result", result);
        request.setAttribute("devise", devise);
        request.getRequestDispatcher("/clients/home.jsp").forward(request, response);
    }
    public void showMad(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        serviceDh = new ServiceDh(dao);
        var result = serviceDh.convertData();
        request.setAttribute("result", result);
        request.setAttribute("devise", result);
        request.getRequestDispatcher("/clients/home.jsp").forward(request, response);
    }
}