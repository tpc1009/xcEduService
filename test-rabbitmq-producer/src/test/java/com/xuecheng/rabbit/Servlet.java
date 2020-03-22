package com.xuecheng.rabbit;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import com.sun.net.httpserver.HttpServer;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

/**
 * @author tpc
 * @date 2020/3/21 18:21
 */
public class Servlet {

    protected void doPost(MockHttpServletRequest request, MockHttpServletResponse response) throws Exception {

        User user = new User();
        user.setUsername("huahua");
        user.setPassworld("tpc");

        //https://localhost:8080/test1/servlet?username=huahua&pwd=hehe
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");

        if(user.getUsername() == username && username.equals(user.getUsername()) && pwd.equals(user.getPassworld())){
            //重定向
            response.sendRedirect("main.jsp");
        }else {
            //转发
            //request.getRequestDispatcher("ces.html").forward(request,response);
        }
    }
}
