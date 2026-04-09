package org.example.interceptors;

import org.example.pojo.Result;
import org.example.utils.JwtUtil;
import org.example.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        String token = request.getHeader("Authorization");
        //验证token
        try {
            //从redis中获取相同的token
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get(token);
            if (redisToken==null){
                //token已经失效了
                throw new RuntimeException();
            }
            Map<String, Object> claims = JwtUtil.parseToken(token);

            // 提取用户角色
            List<String> roles = (List<String>) claims.get("roles");
            if (roles == null || roles.isEmpty()) {
                setUnauthorizedResponse(response, "用户角色信息缺失，无法访问");
                return false;
            }

            // 获取当前请求 URI
            String requestURI = request.getRequestURI();

            // 检查权限
            if (!hasPermission(requestURI, roles)) {
                setUnauthorizedResponse(response, "权限不足，无法访问");
                return false;
            }

            //把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(claims);
            //放行
            return true;
        } catch (Exception e) {
            //http响应状态码为401
            response.setStatus(401);
            //不放行
            return false;
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }
    /**
     * 检查用户是否有权限访问当前 URI
     *
     * @param uri   当前请求的 URI
     * @param roles 用户角色列表
     * @return 是否有权限
     */
    private boolean hasPermission(String uri, List<String> roles) {
        // 权限控制逻辑
        if (uri.startsWith("/admin")) {
            // 管理员权限
            return roles.contains("admin") || roles.contains("super_admin");
        } else if (uri.startsWith("/student")) {
            // 学生权限
            return roles.contains("student");
        } else if (uri.startsWith("/super-admin")) {
            // 超级管理员权限
            return roles.contains("super_admin");
        }
        // 默认放行其他路径
        return true;
    }

    /**
     * 设置 401 未授权响应
     *
     * @param response HttpServletResponse 对象
     * @param message  错误提示信息
     */
    private void setUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 状态码
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":401,\"message\":\"" + message + "\"}");
    }
}
