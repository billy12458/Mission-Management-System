package com.cabbage.mms.utils;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("all")
public class AlertUtils {

    // well,this method is of no use
    public static void alertJS(HttpServletResponse response, String message)
            throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print("<script type='text/javascript'>alert('" + message + "!');</script>");
    }

}
