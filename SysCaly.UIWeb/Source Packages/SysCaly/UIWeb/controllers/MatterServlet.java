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
import syscaly.dal.MatterDAL;
import syscaly.el.Matter;
import SysCaly.UIWeb.utils.*;






/**
 *
 * @author Fsociety   
 */
@WebServlet(name = "MatterServlet", urlPatterns = {"/Matter"})
public class MatterServlet extends HttpServlet {

    
    private Matter obtenerMatter(HttpServletRequest request){
       String accion = Utility.getParameter(request,"accion","Index");
       Matter matter = new Matter();
       if(accion.equals("create")==false){
       matter.setIdMatter(Integer.parseInt(Utility.getParameter(request,"Id", "0")));
       
       }
        matter.setNameMatter(Utility.getParameter(request, "nombre", ""));
                
        return matter;
    }
    

    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Matter matter = new Matter(); 
            matter.setNameTeacher(""); 
            ArrayList<Matter> matters = MatterDAL.buscar(matter); 
            request.setAttribute("matters", matters); 
            request.setAttribute("NameTeacher", matter.getNameTeacher());
            request.getRequestDispatcher("Views/Matter/index.jsp").forward(request, response); 
        } catch (Exception ex) {
            Utility.enviarError(ex.getMessage(), request, response); 
        }
    }
    
    
      private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Matter matter = obtenerMatter(request); 
            ArrayList<Matter> matters = MatterDAL.buscar(matter); 
            request.setAttribute("matters", matters); 
            request.setAttribute("NameTeacher", matter.getNameTeacher());
            request.getRequestDispatcher("Views/Matter/index.jsp").forward(request, response);
        } catch (Exception ex) {
            // Enviar al jsp de error si hay un Exception 
            Utility.enviarError(ex.getMessage(), request, response);
        }
    }
    
     private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Matter/create.jsp").forward(request, response);
    }
     
     
     private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Matter matter = obtenerMatter(request); 
            int result = MatterDAL.crear(matter);
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
            Matter matter = obtenerMatter(request); 
            Matter matter_result = MatterDAL.obtenerPorId(matter);
            if (matter_result.getIdMatter()> 0) { // Si el Id es mayor a cero.
                request.setAttribute("matter", matter_result);
            } else {
                Utility.enviarError("El Id:" + matter.getIdMatter()+ " no existe en la tabla de Matter", request, response);
            }
        } catch (Exception ex) {
            Utility.enviarError(ex.getMessage(), request, response);
        }
    }
       
       
       
      private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Matter/edit.jsp").forward(request, response);
    } 
       
        private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Matter matter = obtenerMatter(request); 
            int result = MatterDAL.modificar(matter);
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
        request.getRequestDispatcher("Views/Matter/details.jsp").forward(request, response);
    }
         
         
         
         
         
          private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Matter/delete.jsp").forward(request, response);
    }

   
    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Matter matter = obtenerMatter(request); 
            int result = MatterDAL.eliminar(matter);
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
