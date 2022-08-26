/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SysCaly.UIWeb.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import java.util.ArrayList;
import syscaly.dal.UserSysDal;
import syscaly.el.UserSys;
import SysCaly.UIWeb.utils.*;
/**
 *
 * @author Fsociety
 */
@WebServlet(name = "UserSysServlet", urlPatterns = {"/UserSys"})
public class UserSysServlet extends HttpServlet {

  
   private UserSys obtenerUsuario(HttpServletRequest request){
       String accion = Utility.getParameter(request,"accion","Index");
       UserSys user = new UserSys();
       if(accion.equals("create")==false){
       user.setIdRol(Integer.parseInt(Utility.getParameter(request,"Id", "0")));
       
       }
        user.setNameUser(Utility.getParameter(request, "nombre", ""));
        
        if(accion.equals("Index")){
        user.setTop_aux(Integer.parseInt(Utility.getParameter(request, "top_aux", "10")));
       user.setTop_aux(user.getTop_aux() == 0 ? Integer.MAX_VALUE : user.getTop_aux());
         }
        
        return user;
    }
    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserSys user = new UserSys(); 
            user.setTop_aux(10); 
            ArrayList<UserSys> Users = UserSysDal.buscar(user); 
            request.setAttribute("roles", Users); 
            request.setAttribute("top_aux", user.getTop_aux());
            request.getRequestDispatcher("Views/Rol/index.jsp").forward(request, response); 
        } catch (Exception ex) {
            Utility.enviarError(ex.getMessage(), request, response); 
        }
    }
    
    
      private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserSys user = obtenerUsuario(request); 
            ArrayList<UserSys> Users = UserSysDal.buscar(user); 
            request.setAttribute("Users", Users); 
            request.setAttribute("top_aux", user.getTop_aux());
            request.getRequestDispatcher("Views/UserSys/index.jsp").forward(request, response); // Direccionar al jsp index de Rol
        } catch (Exception ex) {
            // Enviar al jsp de error si hay un Exception 
            Utility.enviarError(ex.getMessage(), request, response);
        }
    }
   
       private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/UserSys/create.jsp").forward(request, response);
    }
     
     
     private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserSys user = obtenerUsuario(request); 
            int result = UserSysDal.crear(user);
            if (result != 0) { 
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response); 
            } else {
                Utility.enviarError("No se pudo completar el registro", request, response);
            }
        } catch (Exception ex) {
            Utility.enviarError(ex.getMessage(), request, response);
        }

    }
      private void requestObtenerPorId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserSys user = obtenerUsuario(request); 
            UserSys User_result = UserSysDal.obtenerPorId(user);
            if (User_result.getIdRol()> 0) { // Si el Id es mayor a cero.
                request.setAttribute("rol", User_result);
            } else {
                Utility.enviarError("El Id:" + user.getIdRol() + " no existe en la tabla de UserSys", request, response);
            }
        } catch (Exception ex) {
            Utility.enviarError(ex.getMessage(), request, response);
        }
    }

        private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/UserSys/edit.jsp").forward(request, response);
    } 
        
        private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserSys user = obtenerUsuario(request); 
            int result = UserSysDal.modificar(user);
            if (result != 0) { 
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response); 
            } else {
                Utility.enviarError("No se logro actualizar el registro", request, response);
            }
        } catch (Exception ex) {
            Utility.enviarError(ex.getMessage(), request, response);
        }
    }
        
          private void doGetRequestDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/UserSys/details.jsp").forward(request, response);
    }
          
          
          
          private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/UserSys/delete.jsp").forward(request, response);
    }
          
           private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserSys user = obtenerUsuario(request); 
            int result = UserSysDal.eliminar(user);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utility.enviarError("No se logro eliminar el registro", request, response);
            }
        } catch (Exception ex) {
            Utility.enviarError(ex.getMessage(), request, response);
        }
    }
           
           
            protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // processRequest(request, response);
       SessionUser.authorize(request, response, () -> { 
           
            String accion = Utility.getParameter(request, "accion", "index");
            switch (accion) {
                case "index":
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response); 
                    break;
                case "create":
                    request.setAttribute("accion", accion);
                    doGetRequestCreate(request, response); 
                    break;
                case "edit":
                    request.setAttribute("accion", accion);
                    doGetRequestEdit(request, response);
                    break;
                case "delete":
                    request.setAttribute("accion", accion);
                    doGetRequestDelete(request, response); 
                    break;
                case "details":
                    request.setAttribute("accion", accion);
                    doGetRequestDetails(request, response); 
                    break;
                default:
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response); 
            }
        });
       
    }
}
