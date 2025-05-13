package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Post;
import com.example.repository.PostRepository;

@Service
public class PostService {
	@Autowired
    private PostRepository postRepository;
	
	@Transactional
    public void addPost(Post post) {
		postRepository.addPost(post); 
    }
	
	@Transactional
    public List<Post> getPosts(int userId) {
		return postRepository.getPosts(userId); 
    }
	
	@Transactional
    public void deletePost(int postId) {
		postRepository.deletePost(postId); 
    }
	
	@Transactional
    public void updatePost(Post post) {
		postRepository.updatePost(post); 
    }
	
	@Transactional
    public List<Post> getOthersPosts(int userId) {
		return postRepository.getOthersPosts(userId); 
    }
	
	@Transactional
    public Post getPost(int postId) {
		return postRepository.getPost(postId); 
    }
}
