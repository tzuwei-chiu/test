package com.example.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.Post;
import com.example.service.PostService;
import com.example.service.UserService;

@RestController
@RequestMapping("/api/post")
public class PostController {
	@Autowired
	PostService	postService;
	
	@PostMapping("/newPost")
	public ResponseEntity<String> newPost(@RequestHeader String sessionId, @RequestPart("post") Post post,
	        @RequestPart(value = "image", required = false) MultipartFile imageFile) {
	    int userId = UserService.getUserIdBySessionID(sessionId);
	    post.setUserId(userId);
	    try {
	        if (imageFile != null && !imageFile.isEmpty()) {
	            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
	            String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";


	            File uploadPath = new File(uploadDir);
	            if (!uploadPath.exists()) {
	                uploadPath.mkdirs();
	            }
	            File dest = new File(uploadDir + File.separator + fileName);

	            dest.getParentFile().mkdirs();
	            imageFile.transferTo(dest);
	            post.setImage("/uploads/" + fileName);
	        }
	        postService.addPost(post);
	        return ResponseEntity.status(HttpStatus.CREATED).body("User posted successfully!");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
	    }
	}


	
	@GetMapping("/myPosts")
    public ResponseEntity<List<Post>> getPosts(@RequestHeader String sessionId) {
		int userId = UserService.getUserIdBySessionID(sessionId);
        if(userId!=0) {
	        try {
	        	List<Post> posts = postService.getPosts(userId);
	            return ResponseEntity.ok(posts);
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
        }else {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
	
	@DeleteMapping("/deletePost/{postId}")
    public ResponseEntity<String> deletePost(@RequestHeader String sessionId, @PathVariable int postId) {
		int userId = UserService.getUserIdBySessionID(sessionId);
		if(userId!=0) {
			try {
	        	postService.deletePost(postId);
	        	return ResponseEntity.ok("post deleted.");
	        } catch (Exception e) {
	        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
	        }
		}else {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
	
	@PutMapping("/updatePost/{postId}")
	public ResponseEntity<String> updatePost(@RequestHeader String sessionId, @PathVariable int postId, @RequestPart("post") Post post,
	        @RequestPart(value = "image", required = false) MultipartFile imageFile) {
		int userId = UserService.getUserIdBySessionID(sessionId);
		post.setUserId(userId);
		post.setPostId(postId);
		Post existingPost = postService.getPost(postId);
	    try {
	        if (imageFile != null && !imageFile.isEmpty()) {
	            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
	            String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";


	            File uploadPath = new File(uploadDir);
	            if (!uploadPath.exists()) {
	                uploadPath.mkdirs();
	            }
	            File dest = new File(uploadDir + File.separator + fileName);

	            dest.getParentFile().mkdirs();
	            imageFile.transferTo(dest);
	            post.setImage("/uploads/" + fileName);
	        }else {
	        	post.setImage(existingPost.getImage());
	        }
	        postService.updatePost(post);
	        return ResponseEntity.status(HttpStatus.CREATED).body("User posted successfully!");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
	    }
	}

	
	@GetMapping("/othersPosts")
    public ResponseEntity<List<Post>> othersPosts(@RequestHeader String sessionId) {
		int userId = UserService.getUserIdBySessionID(sessionId);
        try {
        	List<Post> posts = postService.getOthersPosts(userId);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
	
	@GetMapping("/getPost/{postId}")
    public ResponseEntity<Post> getPost(@RequestHeader String sessionId, @PathVariable int postId) {
		int userId = UserService.getUserIdBySessionID(sessionId);
        if(userId!=0) {
        	try {
            	Post post = postService.getPost(postId);
                return ResponseEntity.ok(post);
            } catch (Exception e) {
            	e.printStackTrace();
            	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }else {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
