package com.example.novel_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
@RequestMapping("/test")
public class DatabaseTestController {

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/db-connection")
    public String testDatabaseConnection() {
        try (Connection connection = dataSource.getConnection()) {
            return "Kết nối database thành công! Database: " 
                   + connection.getMetaData().getDatabaseProductName()
                   + " Version: " 
                   + connection.getMetaData().getDatabaseProductVersion();
        } catch (SQLException e) {
            return "Lỗi kết nối database: " + e.getMessage();
        }
    }

    @GetMapping("/db-query")
    public String testDatabaseQuery() {
        try {
            String result = jdbcTemplate.queryForObject("SELECT 1", String.class);
            return "Query test thành công: " + result;
        } catch (Exception e) {
            return "Lỗi khi thực hiện query: " + e.getMessage();
        }
    }
}