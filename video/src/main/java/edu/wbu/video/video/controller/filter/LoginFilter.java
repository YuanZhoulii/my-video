package edu.wbu.video.video.controller.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author yuanzhouli
 * @date 2020/12/10 - 23:22
 */
//@Component
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        filterChain.doFilter(servletRequest,servletResponse);
    }
}
