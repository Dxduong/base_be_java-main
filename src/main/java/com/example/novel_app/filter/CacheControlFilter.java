package com.example.novel_app.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

@WebFilter("/*")  // Lọc tất cả các request
public class CacheControlFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Thực hiện các thao tác khởi tạo nếu cần
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            // Thêm header Cache-Control cho tất cả các phản hồi HTTP
            httpResponse.setHeader("Cache-Control", "public, max-age=3600");  // Cache trong 1 giờ

            // Tiếp tục chuỗi xử lý
            chain.doFilter(request, response);
        } else {
            // Nếu request hoặc response không phải là HttpServletRequest/HttpServletResponse, tiếp tục mà không thêm cache
            chain.doFilter(request, response);
        }
    }



    @Override
    public void destroy() {
        // Dọn dẹp tài nguyên nếu cần
    }
}