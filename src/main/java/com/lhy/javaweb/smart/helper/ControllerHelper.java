package com.lhy.javaweb.smart.helper;

import com.lhy.javaweb.smart.annotation.Action;
import com.lhy.javaweb.smart.bean.Handler;
import com.lhy.javaweb.smart.bean.Request;
import com.lhy.javaweb.smart.utils.ArrayUtil;
import com.lhy.javaweb.smart.utils.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 * @author liuhaiyan
 * @date 2019-12-31 16:19
 */
public final class ControllerHelper {

    /**
     * 用于存放请求与处理器的映射关系=
     * */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        // 获取所有的controleer类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(controllerClassSet)) {
            for (Class<?> controllerCLass : controllerClassSet) {
                Method[] methods = controllerCLass.getDeclaredMethods();
                if (ArrayUtil.isNotEmpty(methods)) {
                    // 遍历这些controller中的方法
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Action.class)) {
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            // 验证url映射规则
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] array = mapping.split(":");
                                if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                                    // 获取请求方法与请求路径
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerCLass, method);
                                    // 初始化actionMap
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
