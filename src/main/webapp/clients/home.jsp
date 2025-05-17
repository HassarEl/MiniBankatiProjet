<%@page import="ma.bankati.model.data.MoneyData" pageEncoding="UTF-8" %>
<%@page import="ma.bankati.model.users.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%
    var ctx = request.getContextPath();
%>
<html>
<head>
    <title>Accueil</title>
    <link rel="stylesheet" href="<%= ctx %>/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%= ctx %>/assets/css/bootstrap-icons.css">
    <link rel="stylesheet" href="<%= ctx %>/assets/css/style.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<%
    var result  = (MoneyData) request.getAttribute("result");
    var connectedUser    = (User) session.getAttribute("connectedUser");
    var appName = (String) application.getAttribute("AppName");
%>
<body class="Optima bgBlue">


<!-- ✅ NAVBAR -->
<nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm">
    <div class="container-fluid">
        <!-- Logo & Brand -->
        <a class="navbar-brand d-flex align-items-center" href="<%= ctx %>/home">
            <img src="<%= ctx %>/assets/img/logoBlue.png" alt="Logo" width="40" height="40" class="d-inline-block align-text-top me-2">
            <strong class="blue ml-1"><%=application.getAttribute("AppName")%></strong>
        </a>

        <!-- Menu de navigation -->
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link text-primary fw-bold" href="<%= ctx %>/home">
                        <i class="bi bi-house-door me-1"></i> Accueil
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-primary fw-bold" href="<%= ctx %>/client/credit/">
                        <i class="bi bi-coin"></i> Crédits
                    </a>
                </li>

            </ul>
        </div>

        <!-- Infos session avec sous-menu -->
        <div class="dropdown d-flex align-items-center">
            <a class="btn btn-sm btn-light border dropdown-toggle text-success fw-bold"
               href="#" role="button" id="dropdownSessionMenu" data-bs-toggle="dropdown" aria-expanded="false">
                <i class="bi bi-person-circle me-1"></i> <b><%= connectedUser.getRole() %></b> : <i><%= connectedUser.getFirstName() + " " + connectedUser.getLastName() %></i>
            </a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownSessionMenu">
                <li><hr class="dropdown-divider"></li>
                <li>
                    <a class="dropdown-item text-primary logout-btn fw-bold" href="<%= ctx %>/profile">
                        <i class="bi bi-box-arrow-right me-1"></i> <b>Profile</b>
                    </a>
                </li>
                <li>
                    <a class="dropdown-item text-danger logout-btn fw-bold" href="<%= ctx %>/logout">
                        <i class="bi bi-box-arrow-right me-1"></i> <b>Déconnexion</b>
                    </a>
                </li>
            </ul>
        </div>

    </div>
</nav>


<!-- ✅ CONTENU PRINCIPAL -->
<div class="container w-50 bg-white mt-5 border border-light rounded-circle mb-5">
    <div class="card-body text-center">
        <h4>Demmande de crédit</h4>
        <h5 class="mt-4 mb-3">
            Bienvenue à votre compte <span class="text-primary font-weight-bold"><%= appName %></span>
        </h5>

        <h5 class="mt-4 mb-3"> Mr. <%=connectedUser.getFirstName()%>  <%=connectedUser.getLastName()%></h5>

        <ul class="list-unstyled">
            <li class="text-primary h5">
                Solde de votre compte :
                <span class="text-danger font-weight-bold"> <%= result %></span>
            </li>
        </ul>

        <!--
        <div>
            <h5 class="text-primary h5">Changement de devise :</h5>
            <form class="d-flex justify-content-center" action="${pageContext.request.contextPath}/client/devise" method="post">
                <select name="devis" class="form-select">
                    <option selected>Choisire La Devise</option>
                    <option value="usd">USD $</option>
                    <option value="mad">MAD Dh</option>
                    <option value="pound">Pound £</option>
                </select>
                <input type="submit" class="btn btn-primary" value="Changer">
            </form>
        </div>
         -->
        <div class="mt-5 d-flex justify-content-center align-items-center">
            <h5 class="text-primary">Changement de devise :</h5>
            <ul class="nav nav-tabs text-align-center">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false">Choix Devis</a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/client/devise/usd">USD $</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/client/devise/mad">MAD dh</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/client/devise/pound">Pound £</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <c:choose>
            <c:when test="${not empty devise}">
                <h5 class="text-primary h5"> Montant : ${devise}</h5>
            </c:when>
        </c:choose>

    </div>
</div>

<!-- ✅ FOOTER FIXÉ EN BAS -->
<nav class="navbar footer-navbar fixed-bottom bg-white shadow-sm">
    <div class="container d-flex justify-content-between align-items-center w-100">
        <span class="text-muted small"><b class="blue"><i class="bi bi-house-door me-1"></i> Bankati 2025 </b>– © Tous droits réservés</span>
    </div>
</nav>

</body>
</html>
