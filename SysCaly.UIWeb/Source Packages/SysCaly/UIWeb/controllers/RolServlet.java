/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SysCaly.UIWeb.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import java.util.ArrayList;
import syscaly.dal.RolDAL;
import syscaly.el.Rol;
import SysCaly.UIWeb.utils.*;






/**
 *
 * @author Fsociety
 */
@WebServlet(name = "RolServlet", urlPatterns = {"/Rol"})
public class RolServlet extends HttpServlet {

    
    private Rol obtenerRol(HttpServletRequest request){
       String accion = Utility.getParameter(request,"accion","Index");
       Rol rol = new Rol();
       if(accion.equals("create")==false){
       rol.setIdRol(Integer.parseInt(Utility.getParameter(request,"Id", "0")));
       
       }
        rol.setNameRol(Utility.getParameter(request, "nombre", ""));
        
        if(accion.equals("Index")){
        rol.setTop_aux(Integer.parseInt(Utility.getParameter(request, "top_aux", "10")));
       rol.setTop_aux(rol.getTop_aux() == 0 ? Integer.MAX_VALUE : rol.getTop_aux());
         }
        
        return rol;
    }
    

    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Rol rol = new Rol(); 
            rol.setTop_aux(10); 
            ArrayList<Rol> roles = RolDAL.buscar(rol); 
            request.setAttribute("roles", roles); 
            request.setAttribute("top_aux", rol.getTop_aux());
            request.getRequestDispatcher("Views/Rol/index.jsp").forward(request, response); 
        } catch (Exception ex) {
            Utility.enviarError(ex.getMessage(), request, response); 
        }
    }
    
    
      private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Rol rol = obtenerRol(request); 
            ArrayList<Rol> roles = RolDAL.buscar(rol); 
            request.setAttribute("roles", roles); 
            request.setAttribute("top_aux", rol.getTop_aux());
            request.getRequestDispatcher("Views/Rol/index.jsp").forward(request, response); // Direccionar al jsp index de Rol
        } catch (Exception ex) {
            // Enviar al jsp de error si hay un Exception 
            Utility.enviarError(ex.getMessage(), request, response);
        }
    }
    
     private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Rol/create.jsp").forward(request, response);
    }
     
     
     private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Rol rol = obtenerRol(request); 
            int result = RolDAL.crear(rol);
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
            Rol rol = obtenerRol(request); 
            Rol rol_result = RolDAL.obtenerPorId(rol);
            if (rol_result.getIdRol()> 0) { // Si el Id es mayor a cero.
                request.setAttribute("rol", rol_result);
            } else {
                Utility.enviarError("El Id:" + rol.getIdRol() + " no existe en la tabla de Rol", request, response);
            }
        } catch (Exception ex) {
            Utility.enviarError(ex.getMessage(), request, response);
        }
    }
       
       
       
      private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Rol/edit.jsp").forward(request, response);
    } 
       
        private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Rol rol = obtenerRol(request); 
            int result = RolDAL.modificar(rol);
            if (result != 0) { 
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response); // Ir al metodo doGetRequestIndex para que nos direcciones al jsp index.
            } else {
                Utility.enviarError("No se logro actualizar el registro", request, response);
            }
        } catch (Exception ex) {
            Utility.enviarError(ex.getMessage(), request, response);
        }
    }
       
         private void doGetRequestDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Rol/details.jsp").forward(request, response);
    }
         
         
         
         
         
          private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Rol/delete.jsp").forward(request, response);
    }

   
    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Rol rol = obtenerRol(request); 
            int result = RolDAL.eliminar(rol);
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
         
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    
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
