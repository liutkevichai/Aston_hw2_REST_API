package ru.lai.hw2_rest.controllers;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "apiControllerServlet ", value = "/")
public class ApiControllerServlet  extends HttpServlet {
    private String message;

    public void init() {
        message = """
                <html>
                    <body>
                        <a href="doctors">/doctors</a>
                        <br/>
                        <a href="offices">/offices</a>
                        <br/>
                        <a href="patients">/patients</a>
                        <br/>
                        <a href="appointments">/appointments</a>
                    </body>
                </html>""";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println(message);
    }

    public void destroy() {
    }
}