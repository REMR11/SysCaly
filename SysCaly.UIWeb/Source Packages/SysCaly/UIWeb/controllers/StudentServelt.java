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
import SysCaly.UIWeb.utils.*;
import syscaly.dal.StudentDAL;
import syscaly.el.Student;


/**
 *
 * @author Fsociety
 */
@WebServlet(name = "StudentServelt", urlPatterns = {"/StudentServelt"})
public class StudentServelt extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
  
  private Student obtenerStudent(HttpServletRequest request){
       String accion = Utility.getParameter(request,"accion","Index");
       Student student = new Student();
       if(accion.equals("create")==false){
       student.setIdStudent(Integer.parseInt(Utility.getParameter(request,"Id", "0")));
       
       }
        student.setNameStudent(Utility.getParameter(request, "nombre", ""));
        
        if(accion.equals("Index")){
        student.setTop_aux(Integer.parseInt(Utility.getParameter(request, "top_aux", "10")));
       student.setTop_aux(student.getTop_aux() == 0 ? Integer.MAX_VALUE : student.getTop_aux());
         }
        
        return student;
    }
     private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Student student = new Student(); 
            student.setTop_aux(10); 
            ArrayList<Student> Students = StudentDAL.Buscar(student); 
            request.setAttribute("Students", Students); 
            request.setAttribute("top_aux", student.getTop_aux());
            request.getRequestDispatcher("Views/student/index.jsp").forward(request, response); 
        } catch (Exception ex) {
            Utility.enviarError(ex.getMessage(), request, response); 
        }
    }
    
    
      private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Student student = obtenerStudent(request); 
            ArrayList<Student> students = StudentDAL.Buscar(student); 
            request.setAttribute("roles", students); 
            request.setAttribute("top_aux", student.getTop_aux());
            request.getRequestDispatcher("Views/Rol/index.jsp").forward(request, response); // Direccionar al jsp index de Rol
        } catch (Exception ex) {
            // Enviar al jsp de error si hay un Exception 
            Utility.enviarError(ex.getMessage(), request, response);
        }
      }
      
       private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Student/create.jsp").forward(request, response);
    }
    
       
       
       private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Student student = obtenerStudent(request); 
            int result = StudentDAL.crear(student);
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
            Student student = obtenerStudent(request); 
            Student Student_result = StudentDAL.ObtenerPorId(student);
            if (Student_result.getIdStudent()> 0) { 
                request.setAttribute("rol", Student_result);
            } else {
                Utility.enviarError("El Id:" + student.getIdStudent()+ " no existe en la tabla de Rol", request, response);
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
            Student student = obtenerStudent(request); 
            int result = StudentDAL.modificar(student);
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
        // Direccionar al jsp delete de Rol.
        request.getRequestDispatcher("Views/Rol/delete.jsp").forward(request, response);
    }

   
    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Student student = obtenerStudent(request); 
            int result = StudentDAL.Eliminar(student);
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
