<%-- 
    Document   : error
    Created on : 19 ago 2022, 16:12:39
    Author     : Fsociety
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <jsp:include page="/Views/Shared/title.jsp" />
        <title>Error de la aplicación</title>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
      <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container"> 
            <div class="row">
                <div class="col l12 s12">
                    <h4>Succedio el siguiente error en la aplicación</h4> 
                    <span style="color: red"><%= request.getAttribute("error") %></span> 
                </div>
            </div>            
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />      
    </body>
</html>
