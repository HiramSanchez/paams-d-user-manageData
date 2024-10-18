package com.paa.dms.user.manage.data.config;
import org.springframework.stereotype.Component;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class HeaderFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uid = httpRequest.getHeader("uid");
        if (uid == null || uid.isEmpty()) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.setContentType("application/json");
            String timestamp = new Date().toString();
            String details = ((HttpServletRequest) request).getServletPath();
            httpResponse.getWriter().write("{\"timestamp\": \"" +timestamp+ "\"," +
                    "\"statusCode\":400," +
                    "\"message\":\"Missing uid header\"," +
                    "\"details\": \"uri="+ details +"\"}");
            return;
        }

        chain.doFilter(request, response);
    }
}
