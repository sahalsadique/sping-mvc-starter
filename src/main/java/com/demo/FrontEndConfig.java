package com.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.demo.model.User;

@Configuration
public class FrontEndConfig extends WebMvcConfigurerAdapter {

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HandlerInterceptor() {

			@Override
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
					throws Exception {
				HttpSession httpSession = request.getSession();
				String requestURI = request.getRequestURI();
				 if (!requestURI.contains("/login") && !requestURI.contains("/signUp") && !requestURI.contains("/error")) {
                    User user = (User) httpSession.getAttribute("authUser");
					if (user == null) {						
						System.out.println("filtered: URL redirected to login");
						response.sendRedirect("login");
						return false;
					}
				}
				return true;
			}

			@Override
			public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
					ModelAndView modelAndView) throws Exception {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
					Exception ex) throws Exception {
				// TODO Auto-generated method stub

			}
		});
	}
}
