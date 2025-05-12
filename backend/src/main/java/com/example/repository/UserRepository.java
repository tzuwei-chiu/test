package com.example.repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.example.model.User;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 註冊
    public void addUser(User user) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("RegisterUser")
                .declareParameters(
                        new SqlParameter("p_userName", Types.VARCHAR),
                        new SqlParameter("p_email", Types.VARCHAR),
                        new SqlParameter("p_password", Types.VARCHAR),
                        new SqlParameter("p_coverImage", Types.VARCHAR),
                        new SqlParameter("p_biography", Types.LONGVARCHAR)
                );

        Map<String, Object> params = new HashMap<>();
        params.put("p_userName", user.getUserName());
        params.put("p_email", user.getEmail());
        params.put("p_password", user.getPassword());
        params.put("p_coverImage", user.getCoverImage());
        params.put("p_biography", user.getBiography());

        jdbcCall.execute(params);
    }

    
    //登入
    public String getPasswordByEmail(String email) {
    	SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
    			.withProcedureName("login")
                .declareParameters(
                        new SqlParameter("in_email", Types.VARCHAR),
                        new SqlOutParameter("out_password", Types.VARCHAR)
                );

    	Map<String, Object> result = jdbcCall.execute(email);

        return (String)result.get("out_password");
    }
}
