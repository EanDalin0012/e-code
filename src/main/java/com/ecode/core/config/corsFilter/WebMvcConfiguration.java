package com.ecode.core.config.corsFilter;

import com.ecode.core.constant.KeyCode;
import com.ecode.core.encryption.EASEncrpter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("deprecation")
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Component
    @Order(Ordered.HIGHEST_PRECEDENCE)
    class CorsFilter implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }

        @Override
        public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
                throws IOException, ServletException {
            HttpServletResponse response = (HttpServletResponse) res;
            HttpServletRequest request = (HttpServletRequest) req;

            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            response.setHeader("Access-Control-Max-Age", "3600");
            try {
                response.setHeader("Key-Code", EASEncrpter.decodeKey(KeyCode.keyCode));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if ("*/*".equals(request.getHeader("Accept"))) {
                response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            } else {
                response.setHeader("Access-Control-Allow-Origin", "*");

            }

            if (!"OPTIONS".equals(request.getMethod())) {
                chain.doFilter(req, res);
            } else {
            }
        }

        @Override
        public void destroy() {
        }
    }
}
