<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="syscaly.el.Rol"%>
<%@page import="syscaly.dal.RolDAL"%>
<%@page import="java.util.ArrayList"%>
<% ArrayList<Rol> roles = RolDAL.obtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slRol" name="idRol">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Rol rol : roles) {%>
    <option <%=(id == rol.getIdRol()) ? "selected" : ""%>  value="<%=rol.getIdRol()%>"><%= rol.getNameRol()%></option>
    <%}%>
</select>
<label for="idRol">Rol</label>