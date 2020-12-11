<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Mon Compte</title>
<%@ include file="/WEB-INF/parts/meta.jsp"%>
</head>
<body class="pb-3">
  <%@ include file="/WEB-INF/parts/header.jsp"%>

  <div class="container ladle-bg-main">

    <h1>Mon Compte :</h1>

    <c:if test="${not empty utilisateur}">
      <table class="table">
        <caption>Récapitulatif du compte</caption>
        <tr>
          <th scope="row">utilisateurID</th>
          <td>${utilisateur.utilisateurID}</td>
        </tr>
        <tr>
          <th scope="row">ville</th>
          <td>${utilisateur.ville.nom}</td>
        </tr>
        <tr>
          <th scope="row">pseudo</th>
          <td>${utilisateur.pseudo}</td>
        </tr>
        <tr>
          <th scope="row">genre</th>
          <td>${utilisateur.genre}</td>
        </tr>
        <tr>
          <th scope="row">nom</th>
          <td>${utilisateur.nom}</td>
        </tr>
        <tr>
          <th scope="row">prenom</th>
          <td>${utilisateur.prenom}</td>
        </tr>
        <tr>
          <th scope="row">email</th>
          <td>${utilisateur.email}</td>
        </tr>
        <tr>
          <th scope="row">role</th>
          <td>${utilisateur.role}</td>
        </tr>
        <tr>
          <th scope="row">dateCompte</th>
          <td>${utilisateur.dateCompte}</td>
        </tr>
        <tr>
          <th scope="row">emailSHA</th>
          <td>${utilisateur.emailSHA}</td>
        </tr>
      </table>
    </c:if>
  </div>

  <%@ include file="/WEB-INF/parts/footer.jsp"%>

</body>
</html>
