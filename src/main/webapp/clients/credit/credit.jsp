<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="ma.bankati.model.data.MoneyData" pageEncoding="UTF-8" %>
<%@page import="ma.bankati.model.users.User" %>
<%@ page import="ma.bankati.model.Crediit.Credit" %>
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
    Credit credit = (Credit) request.getAttribute("credit");
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
                <c:choose>
                    <c:when test="${connectedUser.role == 'ADMIN'}">
                        <li class="nav-item">
                            <a class="nav-link text-primary fw-bold" href="<%= ctx %>/users">
                                <i class="bi bi-people-fill me-1"></i> Utilisateurs
                            </a>
                        </li>
                    </c:when>
                </c:choose>
                <li class="nav-item">
                    <a class="nav-link text-primary fw-bold" href="<%= ctx %>/admin/credit/">
                        <i class="bi bi-coin"></i> Crèdits
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
                <!--<li><span class="dropdown-item-text text-muted small">Session ouverte</span></li>-->
                <li><hr class="dropdown-divider"></li>
                <c:choose>
                    <c:when test="${connectedUser.role eq 'CLIENT'}">
                        <li>
                            <a class="dropdown-item text-primary logout-btn fw-bold" href="<%= ctx %>/profile">
                                <i class="bi bi-box-arrow-right me-1"></i> <b>Profile</b>
                            </a>
                        </li>
                    </c:when>
                </c:choose>

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
<div class="container w-75 mt-5 mb-5 bg-white p-4 rounded-3 shadow-sm border border-light">
    <h4 class="text-center text-primary mb-4">Desmmandes de Crèdits</h4>

    <table class="table table-hover table-bordered text-center">
        <thead class="table-light blue">
        <tr>
            <th class="text-center">ID Crèdit</th>
            <th class="text-center">Montant </th>
            <th class="text-center">Nbr de Mois</th>
            <th class="text-center">Status</th>
            <th class="text-center">Motif</th>
            <th class="text-center">Actions</th>
        </tr>
        </thead>
        <tbody class="bold">

        <c:forEach items="${credits}" var="credit">
            <tr>
                <td>${credit.id}</td>
                <td>${credit.mtCredit}</td>
                <td>${credit.nbrMois}</td>
                <td>${credit.motif}</td>
                <td>
                    <c:choose>
                        <c:when test="${credit.status=='ENCOURS'}">
                            <span class="badge bg-warning">${credit.status}</span>
                        </c:when>
                        <c:when test="${credit.status=='ACCEPT'}">
                            <span class="badge bg-success">${credit.status}</span>
                        </c:when>
                        <c:when test="${credit.status=='REFUSE'}">
                            <span class="badge bg-danger">${credit.status}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge bg-secondary">${credit.status}</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${not empty credit and not empty connectedUser and credit.status eq 'ENCOURS' and connectedUser.role eq 'CLIENT'}">
                            <a href="${pageContext.request.contextPath}/client/credit/delete?id=${credit.id}" onclick="return confirm('Confirm delete?')" class="btn btn-outline-danger btn-sm">
                                <i class="bi bi-trash-fill"></i>
                            </a>
                        </c:when>
                        <c:when test="${connectedUser.role=='ADMIN'}">
                            <a href="${pageContext.request.contextPath}/client/credit/acc?id=${credit.id}" onclick="return confirm('Confirm acceptation?')" class="btn btn-outline-success btn-sm">
                                Accepter
                            </a>
                            <a href="${pageContext.request.contextPath}/client/credit/ref?id=${credit.id}" onclick="return confirm('Confirm Refus?')" class="btn btn-outline-danger btn-sm">
                                Refuser
                            </a>
                        </c:when>
                    </c:choose>

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- ✅ FORMULAIRE DE DEMANDE CREDIT -->
    <c:choose>
        <c:when test="${connectedUser.role eq 'CLIENT'}">
            <div class="border border-primary rounded-4 p-4 mt-5 mb-5 shadow-sm bg-white w-75 mx-auto">

                <h5 class="text-center bold blue">
                    <c:choose>
                        <c:when test="${not empty credits}"></c:when>
                        <c:otherwise>Nouvelle Demmande De Crèdit</c:otherwise>
                    </c:choose>
                </h5>

                <!-- ✅ Bouton visible uniquement si on est en modification -->
                <c:if test="${not empty credits}">
                    <div class="text-center mb-3">
                        <a href="${pageContext.request.contextPath}/client/credit/" class="btn btn-outline-primary btn-sm fw-bold">
                            <i class="bi bi-person-plus-fill me-1"></i> Ajouter un nouveau Crèdit
                        </a>
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/client/credit/save" method="post" class="mt-3">
                    <input type="hidden" name="id" value="${credit.id}"/>

                    <!-- Prénom -->
                    <div class="mb-3">
                        <div class="input-group align-items-center">
				<span class="input-group-text bg-white">
					<i class="bi bi-person-badge text-primary" style="font-size: 1.2rem; margin-right: 6px;"></i>
				</span>
                            <input type="text" class="form-control text-dark bold" name="mtCredit" placeholder="Montant De Crèdit" value="${ credit.mtCredit}"/>
                        </div>
                    </div>

                    <!-- Nom -->
                    <div class="mb-3">
                        <div class="input-group align-items-center">
				<span class="input-group-text bg-white">
					<i class="bi bi-person text-primary" style="font-size: 1.2rem; margin-right: 6px;"></i>
				</span>
                            <input type="text" class="form-control text-dark bold" name="nbrMois" placeholder="Nombre de mois" value="${credit.nbrMois}"/>
                        </div>
                    </div>

                    <!-- Nom d'utilisateur -->
                    <div class="mb-3">
                        <div class="input-group align-items-center">
				<span class="input-group-text bg-white">
					<i class="bi bi-person-circle text-primary" style="font-size: 1.2rem; margin-right: 6px;"></i>
				</span>
                            <input type="text" class="form-control text-dark bold" name="motif" placeholder="Motif De Crèdit" value="${credit.motif}"/>
                        </div>
                    </div>


                    <!-- Bouton Enregistrer -->
                    <div class="text-center">
                        <button type="submit" class="btn btn-outline-success font-weight-bold">
                            <i class="bi bi-save me-1"></i> Demmandé
                        </button>
                    </div>
                </form>
            </div>
        </c:when>
    </c:choose>

</div>
<!-- ✅ FOOTER FIXÉ EN BAS -->
<nav class="navbar footer-navbar fixed-bottom bg-white shadow-sm">
    <div class="container d-flex justify-content-between align-items-center w-100">
        <span class="text-muted small"><b class="blue"><i class="bi bi-house-door me-1"></i> Bankati 2025 </b>– © Tous droits réservés</span>
    </div>
</nav>

</body>
</html>
