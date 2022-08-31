<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Crear Rol</title>

    </head>
    <body>
<<<<<<< HEAD
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Crear Rol</h5>
            <form action="Rol" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">                
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="nombre" required class="validate" maxlength="30">
=======
        <jsp:include page="/Views/Shared/headerBody.jsp"/>
        <main class="container"/>
        <h5>Crear Rol<h5/>
            <form action="Rol" method="post">
                
                <imput type="hiden" name="accion"  value="<%=request.getAttribute("accion")%>">
                    <div class="row">
                        
                        <div class="imput-field col 14 s12">
                            <input  id="txtNombre" type="text" name="nombre" required class="validate" maxlength="30">
>>>>>>> fcf0b754d2da2ad1b5cb9001aecd1731b81cdb0d
                        <label for="txtNombre">Nombre</label>
                    </div>                                       
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Rol" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
<<<<<<< HEAD
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />      
=======
                
                <form/>
                
                <main/>
                <jsp:include page="/Views/Shared/footerBody.jsp"/>
>>>>>>> fcf0b754d2da2ad1b5cb9001aecd1731b81cdb0d
    </body>
</html>