<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav id="header-navbar" class="navbar navbar-expand-lg navbar-dark mb-3 sticky-top">

  <%-- Logo + LADLE title --%>
  <a class="navbar-brand" href="./"> <img src="images/ladle_logo_sm.png" width="80" height="52"
    class="d-inline-block" alt="" />Les Amis De L'Escalade
  </a>

  <%-- Btn d'extension de la barre de nav --%>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor02"
    aria-controls="navbarColor02" aria-expanded="false" aria-label="Toggle navigation"><span
    class="navbar-toggler-icon"></span></button>

  <%-- Conteneur pour l'extension de la barre de nav --%>
  <div class="collapse navbar-collapse" id="navbarColor02">
    <ul class="navbar-nav mr-auto pt-2 pt-lg-0">
      <li class="nav-item active"><a class="nav-link" href="./">Accueil</a></li>
      <li class="nav-item"><a class="nav-link" href="./recherche-site-secteur">Site &amp; Secteur</a></li>
      <c:if test="${isLoginValid}">
        <li class="nav-item"><a class="nav-link" href="./recherche-topo">Topo</a></li>
        <li class="nav-item"><a class="nav-link" href="./ajout-site">Ajouter un Site</a></li>
      </c:if>
    </ul>

    <c:if test="${!isLoginValid}">
    
      <%-- Button trigger modal --%>
      <button type="button" class="btn btn-primary" data-toggle="modal"
        data-target="#login_ModalCenter">Connexion</button>
      
      <%-- Button 'Inscription' --%>
      <button type="button" class="btn btn-primary" 
        onclick="window.location.href = 'inscription'" >Inscription</button>
        
    </c:if>
    
    <c:if test="${isLoginValid}">
    
      <%-- Button 'Mon Compte' --%>
      <button type="button" class="btn btn-primary" onclick="location.href='mon-compte'">Mon Compte</button>
      
      <%-- Button 'Deconnexion' --%>
      <button type="button" class="btn btn-primary" 
        onclick="window.location.href = 'deconnexion'" >Deconnexion</button>
    
    </c:if>
  </div>

</nav>

<%-- Modal connexion --%>
<div class="modal fade" id="login_ModalCenter" tabindex="-1" role="dialog"
  aria-labelledby="login_ModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="login_ModalLongTitle">Connexion</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
          aria-hidden="true">&times;</span></button>
      </div>
      <div class="modal-body">

        <form method="post" action="connexion">
        <fieldset>
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
                <input type="checkbox" checked name="login_CheckStayConnected" id="login_CheckStayConnected"
                 class="form-check-input" value="true">
                <label class="form-check-label" for="login_CheckStayConnected"
                >Rester connecté</label>
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
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
