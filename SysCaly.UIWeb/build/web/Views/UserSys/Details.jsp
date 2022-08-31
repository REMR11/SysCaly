<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="syscaly.el.UserSys"%>
<% UserSys usuario = (UserSys) request.getAttribute("usuario");%>
<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Detalle de Usuario</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Detalle de Usuario</h5>
             <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" value="<%=usuario.getNameUser()%>" disabled>
                        <label for="txtNombre">Nombre</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtApellido" type="text" value="<%=usuario.getLastName()%>" disabled>
                        <label for="txtApellido">Apellido</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input  id="txtLogin" type="text" value="<%=usuario.getLogin()%>" disabled>
                        <label for="txtLogin">Login</label>
                    </div>                     
                    <div class="input-field col l4 s12">   
                        <select id="slEstatus" name="estatus" disabled>
                            <option value="0" <%=(usuario.getStatusUser()== 10) ? "selected" : ""%>>SELECCIONAR</option>
                            <option value="<%=UserSys.StatusUser.ACTIVO%>"  <%=(usuario.getStatusUser() == UserSys.StatusUser.ACTIVO) ? "selected" : ""%>>ACTIVO</option>
                            <option value="<%=UserSys.StatusUser.INACTIVO%>"  <%=(usuario.getStatusUser() == UserSys.StatusUser.INACTIVO) ? "selected" : ""%>>INACTIVO</option>
                        </select>       
                        <label for="slEstatus">Estatus</label>                       
                    </div>
                    <div class="input-field col l4 s12">
                        <input id="txtRol" type="text" value="<%=usuario.getRol().getNameRol()%>" disabled>
                        <label for="txtRol">Rol</label>
                    </div> 
                </div>

                <div class="row">
                    <div class="col l12 s12">
                         <a href="Usuario?accion=edit&id=<%=usuario.getIdUser()%>" class="waves-effect waves-light btn blue"><i class="material-icons right">edit</i>Ir modificar</a>            
                        <a href="Usuario" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />         
    </body>
</html>