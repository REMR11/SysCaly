/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SysCaly.UIWeb.utils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;  
import syscaly.el.UserSys;

/**
 *
 * @author Fsociety
 */
public class SessionUser {
     public static void autenticarUser(HttpServletRequest request, UserSys pUsuario) {
        HttpSession session = (HttpSession) request.getSession(); // obtener las variables de session del request
        session.setMaxInactiveInterval(3600); // la variables de session se eliminara una hora despues de inactividad de un usuario
        // session.setAttribute es para crear una variable de session y asignar un valor
        session.setAttribute("auth", true); // asignar el valor de true a la variable de session auth 
        session.setAttribute("user", pUsuario.getLogin()); // asignar el login del usuario a la variable de session user 
        session.setAttribute("rol", pUsuario.getRol().getNameRol()); // asignar el rol del usuario a la variable de session rol 
    }

    /**
     * En este método vamos obtener el login del usuario que inicio session
     *
     * @param request en este parámetro vamos a recibir el request de un servlet
     * o jsp
     * @return boolean true si el usuario ha iniciado session, false si no ha
     * iniciado session
     */
    public static boolean isAuth(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        // utilizar el operador ternario para verificar si la variable de session auth es diferente a null, 
        // si es diferente a null se convierte el valor de la variable de session auth en boolean, si es null se le asigna
        // a la variable auth el valor false. 
        // session.getAttribute es para obtener el valor de una variable de session, si no existe devuelve null por defecto.
        // Aprender sobre el operador ternario en java http://lineadecodigo.com/java/el-operador-ternario-en-java/
        boolean auth = session.getAttribute("auth") != null ? (boolean) session.getAttribute("auth") : false;
        return auth;
    }

    /**
     * En este método vamos obtener el login del usuario que inicio session.
     *
     * @param request en este parámetro vamos a recibir el request de un servlet
     * o jsp
     * @return String el login del usuario
     */
    public static String getUser(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        String user = "";
        if (SessionUser.isAuth(request)) { // verificar si el usuario ha iniciado session. 
            // obtener el valor de la variable de session user, en el caso que la variable de session sea diferente a null, utilizando 
            // operador ternario
            user = session.getAttribute("user") != null ? (String) session.getAttribute("user") : "";
        }
        return user;
    }

    /**
     * En este método vamos obtener el rol del usuario que inicio session
     *
     * @param request en este parámetro vamos a recibir el request de un servlet
     * o jsp
     * @return String el rol del usuario
     */
    public static String getRol(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        String user = "";
        if (SessionUser.isAuth(request)) { // verificar si el usuario ha iniciado session 
            // obtener el valor de la variable de session rol, en el caso que la variable de session sea diferente a null, utilizando 
            // operador ternario
            user = session.getAttribute("rol") != null ? (String) session.getAttribute("rol") : "";
        }
        return user;
    }
   
     public static void authorize(HttpServletRequest request, HttpServletResponse response, IAuthorize pIAuthorize) throws ServletException, IOException {
        if (SessionUser.isAuth(request)) { // verificar si el usuario ha iniciado session 
            pIAuthorize.authorize(); // ejecutar la expresión Lambda, si el usuario ha sido autorizado en el sistema
        } else {
            response.sendRedirect("Usuario?accion=login"); // direccionar al servlet de Usuario accion login, en el caso que no tenga permiso el usuario
        }
    }
     
     public static void cerrarSession(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        session.invalidate(); // eliminanos cualquier variable de session, que tenga creada un usuario que inicio session en el sistema.
    }
}
