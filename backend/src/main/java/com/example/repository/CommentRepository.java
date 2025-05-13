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

import com.example.model.Comment;
@Repository
public class CommentRepository {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public int addComment(Comment comment) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("addComment")
                .declareParameters(
                        new SqlParameter("in_userId", Types.INTEGER),
                        new SqlParameter("in_postId", Types.INTEGER),
                        new SqlParameter("in_content", Types.VARCHAR),
                        new SqlOutParameter("out_commentId", Types.INTEGER)
                );

        Map<String, Object> params = new HashMap<>();
        params.put("in_userId", comment.getUserId());
        params.put("in_postId", comment.getPostId());
        params.put("in_content", comment.getContent());
        
        Map<String, Object> result = jdbcCall.execute(params);
        
        return (int)result.get("out_commentId");
    }
	
	// 列出所有發文
    public List<Comment> getComments(int postId) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("getComments")
                .declareParameters(new SqlParameter("in_postId", Types.INTEGER))
                .returningResultSet("result", new RowMapper<Comment>() {
                    @Override
                    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
                    	Comment comment = new Comment();
                    	comment.setCommentId(rs.getInt("commentId"));
                    	comment.setUserId(rs.getInt("userId"));
                    	comment.setPostId(rs.getInt("postId"));
                    	comment.setContent(rs.getString("content"));
                    	comment.setCreatedAt(rs.getTimestamp("createdAt"));
                        return comment;
                    }
                });
;

        Map<String, Object> params = new HashMap<>();
        params.put("in_postId", postId);

        Map<String, Object> result = jdbcCall.execute(params);
        @SuppressWarnings("unchecked")
		List<Comment> comments = (List<Comment>) result.get("result");
        return comments;
    }
}
