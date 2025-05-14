package com.example.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.example.model.User;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
    
    public User getUser(int userId) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("getUser")
                .declareParameters(new SqlParameter("in_userId", Types.INTEGER))
                .returningResultSet("result", new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    	User user = new User();
                    	user.setUserId(rs.getInt("userId"));
                    	user.setUserName(rs.getString("userName"));
                    	user.setEmail(rs.getString("email"));
                    	user.setPhoneNum(rs.getString("phoneNum"));
                    	user.setCoverImage(rs.getString("coverImage"));
                    	user.setBiography(rs.getString("biography"));
                    	user.setCreatedAt(rs.getTimestamp("createdAt"));
                        return user;
                    }
                });

        Map<String, Object> params = new HashMap<>();
        params.put("in_userId", userId);

        Map<String, Object> result = jdbcCall.execute(params);
        
        @SuppressWarnings("unchecked")
		List<User> resultList = (List<User>) result.get("result");

        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }
    }
    
    public String getUserName(int userId) {
    	SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
    			.withProcedureName("getUserName")
                .declareParameters(
                        new SqlParameter("in_userId", Types.VARCHAR),
                        new SqlOutParameter("out_userName", Types.INTEGER)
                );

    	Map<String, Object> result = jdbcCall.execute(userId);

        return (String)result.get("out_userName");
    }
}
