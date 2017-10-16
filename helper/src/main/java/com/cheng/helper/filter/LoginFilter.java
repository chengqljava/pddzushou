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



public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
            filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String url = httpServletRequest.getRequestURI();
        HttpSession session = httpServletRequest.getSession();
        //添加个人信息
      //  Object memberIdObj = session.getAttribute("memberId");

//        if (null == memberIdObj) {
//            if (StringUtils.startsWith(url, "/member/message")) {
//                if (Context.getMobileBrowserFlag()) {
//                    httpServletResponse.sendRedirect("/toLogin");
//                } else {
//                    httpServletResponse.sendRedirect("/index");
//                }
//            }
//        } else {
//            if (memberIdObj != null) {
//                httpServletRequest.setAttribute("member", SpringContextHolder
//                    .getBean(MemberService.class).get(String.valueOf(memberIdObj)));
//            }
//            Context.setMemberId(String.valueOf(memberIdObj));
//        }


        chain.doFilter(request, response);
       // Context.removeMemberId();

    }

    @Override
    public void destroy() {
    }

}
