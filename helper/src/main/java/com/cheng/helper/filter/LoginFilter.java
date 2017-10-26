package com.cheng.helper.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.cheng.helper.config.Context;
import com.cheng.helper.dto.UserDTO;

public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		String url = httpServletRequest.getRequestURI();
		HttpSession session = httpServletRequest.getSession();
		UserDTO userDTO = (UserDTO) session.getAttribute("userDTO");
		if(userDTO!=null&&StringUtils.startsWith(url, "/login")){
			httpServletResponse.sendRedirect("/index");
		}
		else if (StringUtils.startsWith(url, "/login")
				|| StringUtils.startsWith(url, "/register") || StringUtils.startsWith(url, "/static")) {
			chain.doFilter(request, response);
		} else {
			// 添加个人信息
			
			if (null == userDTO) {
				httpServletResponse.sendRedirect("/login");
			} else {
				Context.setUser(userDTO);
				chain.doFilter(request, response);
				Context.remove();
			}

		}
	}

	@Override
	public void destroy() {
	}

}
