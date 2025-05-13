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
                .withProcedureName("register")
                .declareParameters(
                        new SqlParameter("in_userName", Types.VARCHAR),
                        new SqlParameter("in_email", Types.VARCHAR),
                        new SqlParameter("in_phoneNum", Types.VARCHAR),
                        new SqlParameter("in_pass", Types.VARCHAR),
                        new SqlParameter("in_coverImage", Types.VARCHAR),
                        new SqlParameter("in_biography", Types.LONGVARCHAR)
                );

        Map<String, Object> params = new HashMap<>();
        params.put("in_userName", user.getUserName());
        params.put("in_email", user.getEmail());
        params.put("in_phoneNum", user.getPhoneNum());
        params.put("in_pass", user.getPass());
        params.put("in_coverImage", user.getCoverImage());
        params.put("in_biography", user.getBiography());

        jdbcCall.execute(params);
    }

    
    //抓取正確密碼
    public String getPassByPhoneNum(String phoneNum) {
    	SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
    			.withProcedureName("login")
                .declareParameters(
                        new SqlParameter("in_phoneNum", Types.VARCHAR),
                        new SqlOutParameter("out_pass", Types.VARCHAR)
                );

    	Map<String, Object> result = jdbcCall.execute(phoneNum);

        return (String)result.get("out_pass");
    }
    
    //登入成功者獲取userId
    public int getUserIdByPhoneNum(String phoneNum) {
    	SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
    			.withProcedureName("getUserId")
                .declareParameters(
                        new SqlParameter("in_phoneNum", Types.VARCHAR),
                        new SqlOutParameter("out_userId", Types.INTEGER)
                );

    	Map<String, Object> result = jdbcCall.execute(phoneNum);

        return (int)result.get("out_userId");
    }
    
}
