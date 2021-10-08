<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Connexion</title>
<link rel="icon" href="images/favicon/favicon.ico">
<%@ include file="/WEB-INF/parts/meta.jsp"%>
</head>
<body>
  <%@ include file="/WEB-INF/parts/header.jsp"%>
  
  <div class="container ladle-bg-main">

    <%-- Message de redirection depuis une page sécurisée --%>    
    <c:if test="${fromSecurePage}">
      <p class="text-danger">
        Vous tentez d'accèder à une page sécurisée qui nécessite d'être connecté :
      </p>
    </c:if>
  
    <%-- Erreur de login invalid --%>
    
    <c:if test="${errorLoginInvalid}">
      <p class="text-danger">
        Erreur ! Echec de connexion. Le pseudo/mail ou le mot de passe est invalide.
      </p>
    </c:if>
    
    <%-- Formulaire de connexion --%>
    <form method="post" action="connexion">
    
      <h1 class="mb-0">Connexion :</h1>
      <hr>
      
        <div class="form-row">
  
          <%-- Input Pseudo / Email --%>
          <div class="col-12 col-sm">
            <div class="form-group">
              <label for="login_InputPseudoEmail">Pseudo / Email</label>
              <input type="text" name="login_InputPseudoEmail" id="login_InputPseudoEmail" 
              class="form-control form-control-sm" placeholder="Entrez votre pseudo ou mail"
              value="${lastLoginPseudoMail}">
            </div>
          </div>
  
          <%-- Input Password --%>
          <div class="col-12 col-sm">
            <div class="form-group">
              <label for="login_InputPassword">Password</label> 
              <input type="password" name="login_InputPassword" id="login_InputPassword" 
              class="form-control form-control-sm" placeholder="Mot de passe...">
            </div>
          </div>
  
        </div>
        <div class="form-row">
  
          <%-- Input checkBox --%>
          <div class="col-12 col-sm-6">
            <div class="form-group form-check mb-0">
              <input type="checkbox" checked name="login_CheckStayConnected" 
              id="login_CheckStayConnected" class="form-check-input" value="true"> 
              <label class="form-check-label" for="login_CheckStayConnected">Rester connecté</label>
            </div>
          </div>

        </div>
        
        <hr>
        <%-- Bouton Envoyé --%>
        <div class="d-flex justify-content-center">
          <input type="submit" class="btn btn-primary ml-0" value="Envoyer">
        </div>

    </form>

  </div>

  <%@ include file="/WEB-INF/parts/footer.jsp"%>

</body>
</html>
