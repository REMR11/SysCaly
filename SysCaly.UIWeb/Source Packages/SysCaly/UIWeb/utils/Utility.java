/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SysCaly.UIWeb.utils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Fsociety
 */
public class Utility {
     public static String getParameter(HttpServletRequest request, String pKey, String pDefault) {
        String result = "";
        // request.getParameter es para obtener el valor de un parámetro que se envio desde un formulario o url del navegador web 
        String value = request.getParameter(pKey); // obtener el valor del parámetro a partir del nombre que se envio pKey 
        if (value != null && value.trim().length() > 0) { // verificar que valor del parámetro sea un valor correcto  
            result = value; // asigar el valor del parámetro a la variable de result
        } else {
            result = pDefault; // en el caso que el valor del parámetro sea un valor incorrecto devolver el valor por defecto 
        }
        return result; // retornar el valor del parámetro o valor por defecto
    }
     
      public static void enviarError(String pError, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // request.setAttribute es para crear un atributo y asignarle un valor el cual puede ser recibido en un jsp
        request.setAttribute("error", pError); // crear el atributo error  y asignarle el valor de la variable pError
        // request.getRequestDispatcher nos permite direccionar a un jsp desde un servlet 
        request.getRequestDispatcher("Views/Shared/error.jsp").forward(request, response); // direccionar al jsp error 
    }
      
       public static String obtenerRuta(HttpServletRequest request, String pStrRuta) {
        // request.getContextPath() devuelve el nombre de la aplicación (o directorio raíz del proyecto) en Java EE.
        return request.getContextPath() + pStrRuta; // concatenar la ruta raiz de la aplicacion, mas la ruta del archivo css, js o imagen 
    }
}
