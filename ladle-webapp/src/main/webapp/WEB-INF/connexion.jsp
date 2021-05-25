<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Connexion</title>
<link rel="icon" href="images/favicon/favicon.ico">
<%@ include file="/WEB-INF/parts/meta.jsp"%>
</head>
<body class="pb-3">
  <%@ include file="/WEB-INF/parts/header.jsp"%>
  
  <div class="container ladle-bg-main">
  
    <%-- Erreur de login invalid --%>
    
    <c:if test="${errorLoginInvalid}">
      <p>Erreur ! Echec de connexion. Le pseudo/mail ou le mot de passe est invalide.</p>
    </c:if>
    
    <%-- Formulaire de connexion --%>
    <form method="post" action="connexion">
      <fieldset>
      <legend>Connexion :</legend>
      
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
          <div class="col">
            <div class="form-group form-check">
              <input type="checkbox" checked name="login_CheckStayConnected" 
              id="login_CheckStayConnected" class="form-check-input" value="true"> 
              <label class="form-check-label" for="login_CheckStayConnected">Rester connecté</label>
            </div>
          </div>
  
          <%-- Bouton Envoyé --%>
          <div class="col">
            <input type="submit" class="btn btn-primary" value="Envoyer">
          </div>
  
        </div>
      </fieldset>
    </form>

  </div>

  <%@ include file="/WEB-INF/parts/footer.jsp"%>

</body>
</html>
