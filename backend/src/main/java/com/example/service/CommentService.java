package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Comment;
import com.example.repository.CommentRepository;
@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository;
	
	@Transactional
    public void addComment(Comment comment) {
		commentRepository.addComment(comment); 
    }
	
	@Transactional
    public List<Comment> getComments(int postId) {
		return commentRepository.getComments(postId); 
    }
}
