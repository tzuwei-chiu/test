package com.example.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;


import com.example.model.Post;
@Repository
public class PostRepository {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
    public int addPost(Post post) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("addPost")
                .declareParameters(
                        new SqlParameter("in_userId", Types.INTEGER),
                        new SqlParameter("in_content", Types.VARCHAR),
                        new SqlParameter("in_image", Types.VARCHAR),
                        new SqlOutParameter("out_postId", Types.INTEGER)
                );

        Map<String, Object> params = new HashMap<>();
        params.put("in_userId", post.getUserId());
        params.put("in_content", post.getContent());
        params.put("in_image", post.getImage());
        
        Map<String, Object> result = jdbcCall.execute(params);
        
        return (int)result.get("out_postId");
    }
    
    public List<Post> getPosts(int userId) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("getPosts")
                .declareParameters(new SqlParameter("in_userId", Types.INTEGER))
                .returningResultSet("result", new RowMapper<Post>() {
                    @Override
                    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Post post = new Post();
                        post.setPostId(rs.getInt("postId"));
                        post.setUserId(rs.getInt("userId"));
                        post.setUserName(rs.getString("userName"));
                        post.setContent(rs.getString("content"));
                        post.setImage(rs.getString("image"));
                        post.setCreatedAt(rs.getTimestamp("createdAt"));
                        return post;
                    }
                });
;

        Map<String, Object> params = new HashMap<>();
        params.put("in_userId", userId);

        Map<String, Object> result = jdbcCall.execute(params);
        @SuppressWarnings("unchecked")
		List<Post> posts = (List<Post>) result.get("result");
        return posts;
    }
    
    public void deletePost(int postId) {
    	System.out.println(postId);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("deletePost")
                .declareParameters(new SqlParameter("in_postId", Types.INTEGER));
;

        Map<String, Object> params = new HashMap<>();
        params.put("in_postId", postId);

        jdbcCall.execute(params);
    }
    
    public void updatePost(Post post) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("updatePost")
                .declareParameters(
                        new SqlParameter("in_postId", Types.INTEGER),
                        new SqlParameter("in_content", Types.VARCHAR),
                        new SqlParameter("in_image", Types.VARCHAR)
                );

        Map<String, Object> params = new HashMap<>();
        params.put("in_postId", post.getPostId());
        params.put("in_content", post.getContent());
        params.put("in_image", post.getImage());
        
        jdbcCall.execute(params);
    }
    
    public List<Post> getOthersPosts(int userId) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("getOthersPosts")
                .declareParameters(new SqlParameter("in_userId", Types.INTEGER))
                .returningResultSet("result", new RowMapper<Post>() {
                    @Override
                    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Post post = new Post();
                        post.setPostId(rs.getInt("postId"));
                        post.setUserId(rs.getInt("userId"));
                        post.setUserName(rs.getString("userName"));
                        post.setContent(rs.getString("content"));
                        post.setImage(rs.getString("image"));
                        post.setCreatedAt(rs.getTimestamp("createdAt"));
                        return post;
                    }
                });
;

        Map<String, Object> params = new HashMap<>();
        params.put("in_userId", userId);

        Map<String, Object> result = jdbcCall.execute(params);
        @SuppressWarnings("unchecked")
		List<Post> posts = (List<Post>) result.get("result");
        return posts;
    }
    
    public Post getPost(int postId) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("getPost")
                .declareParameters(new SqlParameter("in_postId", Types.INTEGER))
                .returningResultSet("result", new RowMapper<Post>() {
                    @Override
                    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Post post = new Post();
                        post.setPostId(rs.getInt("postId"));
                        post.setUserId(rs.getInt("userId"));
                        post.setUserName(rs.getString("userName"));
                        post.setContent(rs.getString("content"));
                        post.setImage(rs.getString("image"));
                        post.setCreatedAt(rs.getTimestamp("createdAt"));
                        return post;
                    }
                });

        Map<String, Object> params = new HashMap<>();
        params.put("in_postId", postId);

        Map<String, Object> result = jdbcCall.execute(params);
        
        @SuppressWarnings("unchecked")
		List<Post> resultList = (List<Post>) result.get("result");

        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

}
