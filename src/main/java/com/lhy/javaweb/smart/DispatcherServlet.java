package com.lhy.javaweb.smart;

import com.lhy.javaweb.smart.bean.Data;
import com.lhy.javaweb.smart.bean.Handler;
import com.lhy.javaweb.smart.bean.Param;
import com.lhy.javaweb.smart.bean.View;
import com.lhy.javaweb.smart.helper.BeanHelper;
import com.lhy.javaweb.smart.helper.ConfigHelper;
import com.lhy.javaweb.smart.helper.ControllerHelper;
import com.lhy.javaweb.smart.utils.ArrayUtil;
import com.lhy.javaweb.smart.utils.CodeUtil;
import com.lhy.javaweb.smart.utils.JsonUtil;
import com.lhy.javaweb.smart.utils.ReflectionUtil;
import com.lhy.javaweb.smart.utils.StreamUtil;
import com.lhy.javaweb.smart.utils.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuhaiyan
 * @date 2019-12-31 17:24
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {


    @Override
    public void init(ServletConfig config) throws ServletException {
        // 初始化相关helper
        HelperLoader.init();
        // 获取ServletContext对象（用于注册servlet）
        ServletContext servletContext = config.getServletContext();
        // 注册处理jsp的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        // 注册处理静态资源的默认servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppViewPath() + "*");
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // 获取请求方法与请求路径
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        // 获取action处理器
        Handler handler= ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {
            // 获取controller类及其bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            // 创建请求参数对象
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> paramNames = req.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            String body = CodeUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
            if (StringUtil.isNotEmpty(body)) {
                String[] params = StringUtil.splitString(body, "&");
                if (ArrayUtil.isNotEmpty(params)) {
                    for(String param : params) {
                        String[] array = StringUtil.splitString(param, "=");
                        if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }
            Param param = new Param(paramMap);
            // 调用action方法
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            // 处理action方法返回值
            if (result instanceof View) {
                // 返回jsp页面
                View view = (View) result;
                String path = view.getPath();
                if (StringUtil.isNotEmpty(path)) {
                    if (path.startsWith("/")) {
                        res.sendRedirect(req.getContextPath() + path);
                    }else {
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String, Object> entry : model.entrySet()) {
                            req.setAttribute(entry.getKey(), entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, res);
                    }
                }
            }else if (result instanceof Data) {
                // 返回json数据
                Data data = (Data) result;
                Object model = data.getModel();
                if (model != null) {
                    res.setContentType("application/json");
                    res.setCharacterEncoding("UTF-8");
                    PrintWriter writer = res.getWriter();
                    String json = JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }

            }

        }

    }
}
