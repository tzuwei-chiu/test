package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Comment;
import com.example.service.CommentService;
import com.example.service.UserService;


@RestController
@RequestMapping("/api/comment")
public class CommentController {
	@Autowired
    private CommentService commentService;
	
    @PostMapping("/newComment")
    public ResponseEntity<String> newComment(@RequestHeader String sessionId, @RequestBody Comment comment) {
    	int userId = UserService.getUserIdBySessionID(sessionId);
    	comment.setUserId(userId);
        try {
        	commentService.addComment(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body("commented successfully!");
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<Comment>> getPosts(@RequestHeader String sessionId, @PathVariable int postId) {
    	UserService.getUserIdBySessionID(sessionId);
		
        try {
        	List<Comment> comments = commentService.getComments(postId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
