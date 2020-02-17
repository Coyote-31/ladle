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
      <li class="nav-item"><a class="nav-link" href="Inscription">Inscription</a></li>
      <li class="nav-item"><a class="nav-link" href="Connexion">Connexion</a></li>
      <li class="nav-item"><a class="nav-link" href="#">À propos</a></li>
    </ul>

    <%-- Button trigger modal --%>
    <button type="button" class="btn btn-primary" data-toggle="modal"
      data-target="#loggin_ModalCenter">Connexion</button>
  </div>

</nav>

<%-- Modal connexion --%>
<div class="modal fade" id="loggin_ModalCenter" tabindex="-1" role="dialog"
  aria-labelledby="loggin_ModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="loggin_ModalLongTitle">Modal title</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
          aria-hidden="true">&times;</span></button>
      </div>
      <div class="modal-body">

        <form>
          <div class="form-row">

            <%-- Input Email --%>
            <div class="col-12 col-sm">
              <div class="form-group">
                <label for="loggin_InputEmail">Email</label>
                <input type="email" class="form-control form-control-sm" id="loggin_InputEmail"
                  placeholder="Entrez votre mail">
              </div>
            </div>

            <%-- Input Password --%>
            <div class="col-12 col-sm">
              <div class="form-group">
                <label for="loggin_InputPassword">Password</label>
                <input type="password" class="form-control form-control-sm"
                  id="loggin_InputPassword" placeholder="Mot de passe...">
              </div>
            </div>

          </div>
          <div class="form-row">

            <%-- Input checkBox --%>
            <div class="col">
              <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" id="loggin_CheckStayConnected">
                <label class="form-check-label" for="loggin_CheckStayConnected">Rester
                  connecté</label>
              </div>
            </div>

            <%-- Bouton Envoyé --%>
            <div class="col">
              <button type="submit" class="btn btn-primary">Envoyer</button>
            </div>

          </div>
        </form>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>
