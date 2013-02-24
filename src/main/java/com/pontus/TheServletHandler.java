package com.pontus;

import javax.annotation.ManagedBean;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * Created with IntelliJ IDEA.
 * User: pontus
 * Date: 10/23/12
 * Time: 1:05 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean("theServletHandler")
public class TheServletHandler {

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal != null) {
            response.getOutputStream().print("<div>" + userPrincipal.getName() + "</div>");
        }
    }


    //@Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service(request, response);
    }
}
