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
import syscaly.dal.QualificationDAL;
import syscaly.el.Qualification;
import SysCaly.UIWeb.utils.*;
/**
 *
 * @author Fsociety
 */
@WebServlet(name = "QualificationServlet", urlPatterns = {"/QualificationServlet"})
public class QualificationServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
 

    
    private Qualification obtenerQualification(HttpServletRequest request){
    String accion = Utility.getParameter(request, "accion", "Index");
    Qualification qual = new Qualification();
    
    return qual;
    }
    
    
    private void doGetRequestIndex (HttpServletRequest request , HttpServletResponse response )
            throws ServletException , IOException{
      
        try{
        Qualification qual = new Qualification();
        ArrayList<Qualification> Quals = QualificationDAL.buscar(qual);
        request.setAttribute("Qualification", Quals);
        request.getRequestDispatcher("Views/Qualification/Index.jsp").forward(request, response);
        
        
                
        }   catch(Exception ex){
     
        Utility.enviarError(ex.getMessage(), request, response);
        }
        
   
    }
    
    
    
    private void doPostRequestIndex(HttpServletRequest request , HttpServletResponse response) throws ServletException , IOException{
     
        
    try {
        Qualification qual = obtenerQualification(request);
        ArrayList<Qualification> quals =  QualificationDAL.buscar(qual);
      request.setAttribute("roles", quals);
            request.getRequestDispatcher("Views/Rol/index.jsp").forward(request, response); // Direccionar al jsp index de Rol
        } catch (Exception ex) {
            // Enviar al jsp de error si hay un Exception 
            Utility.enviarError(ex.getMessage(), request, response);
        }
    
    
    }
    
    
     private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Qualification/create.jsp").forward(request, response);
    }
     
     
     private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Qualification qual = obtenerQualification(request); 
            int result = QualificationDAL.crear(qual);
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
            Qualification qual = obtenerQualification(request); 
            Qualification qual_result = QualificationDAL.obtenerPorId(qual);
            if (qual_result.getIdQualification()> 0) { // Si el Id es mayor a cero.
                request.setAttribute("Qualification", qual_result);
            } else {
                Utility.enviarError("El Id:" + qual.getIdQualification()+ " no existe en la tabla de Rol", request, response);
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
            Qualification qual = obtenerQualification(request); 
            int result = QualificationDAL.modificar(qual);
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
        request.getRequestDispatcher("Views/Qualification/details.jsp").forward(request, response);
    }
         
         
         
         
         
          private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Qualification/delete.jsp").forward(request, response);
    }

   
    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Qualification qual = obtenerQualification(request); 
            int result = QualificationDAL.eliminar(qual);
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
    
    
    
    

