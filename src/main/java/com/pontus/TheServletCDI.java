package com.pontus;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: pontus
 * Date: 11/5/12
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */
@WebServlet(name = "TheServlet", urlPatterns = "/servlet")
public class TheServletCDI extends HttpServlet {
    @Inject
    private TheServletHandler theServletHandler;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        theServletHandler.service(req, resp);
    }
}
