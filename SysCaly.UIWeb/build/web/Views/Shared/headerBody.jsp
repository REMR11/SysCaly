<%-- 
    Document   : headerBody
    Created on : 19 ago 2022, 16:13:15
    Author     : Fsociety
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="SysCaly.UIWeb.utils.*"%>
<nav>
    <div class="nav-wrapper blue">
        <a href="Home" class="brand-logo">SysCaly</a>
        <a href="#" data-target="mobile-demo" class="sidenav-trigger"><i class="material-icons">menu</i></a>       
        <ul class="right hide-on-med-and-down">  
            <% if (SessionUser.isAuth(request)) {  %>
            <li><a href="Home">Inicio</a></li>
            <li><a href="UserSys">Usuario</a></li>
            <li><a href="Rol">Rol</a></li>
            <li><a href="UserSys?accion=cambiarpass">Cambiar password</a></li>
            <li><a href="UserSys?accion=login">Cerrar sesi�n</a></li>
                <%}%>   
        </ul>
    </div>
</nav>
<ul class="sidenav" id="mobile-demo">
    <% if (SessionUser.isAuth(request)) {  %>
    <li><a href="Home">Inicio</a></li>
    <li><a href="UserSys">Usuario</a></li>
    <li><a href="Rol">Rol</a></li>
    <li><a href="UserSys?accion=cambiarpass">Cambiar password</a></li>
    <li><a href="UserSys?accion=login">Cerrar sesi�n</a></li>
        <%}%>
</ul>


