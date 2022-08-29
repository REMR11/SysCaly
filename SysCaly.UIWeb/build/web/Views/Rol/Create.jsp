<%-- 
    Document   : Create
    Created on : 14 ago 2022, 16:58:15
    Author     : Fsociety
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp"/>
        <main class="container"/>
        <h5>Crear Rol<h5/>
            <form action="Rol" method="post">
                
                <imput type="hiden" name="accion"  value="<%=request.getAttribute("accion")%>">
                    <div class="row">
                        
                        <div class="imput-field col 14 s12">
                            <input  id="txtNombre" type="text" name="nombre" required class="validate" maxlength="30">
                        <label for="txtNombre">Nombre</label>
                    </div>                                       
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Rol" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                
                <form/>
                
                <main/>
                <jsp:include page="/Views/Shared/footerBody.jsp"/>
    </body>
</html>
