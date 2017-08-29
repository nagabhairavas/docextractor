package com.doc.word.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3)
			throws Exception {
		log.debug("Request Completed!");
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView model)
			throws Exception {
		log.debug("Method executed");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		log.debug("Before process request");
		String uri = request.getRequestURI();
        if (!uri.endsWith("/") && !uri.endsWith("/login") && !uri.endsWith("/register")) {
            if (request.getSession().getAttribute("USER") == null) {
                response.sendRedirect("/");
                return false;
            }
        }
        return true;
	}
}