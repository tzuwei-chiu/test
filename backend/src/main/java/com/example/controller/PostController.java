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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.Post;
import com.example.service.PostService;
import com.example.service.UserService;

import jakarta.servlet.http.HttpSession;

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
	                uploadPath.mkdirs(); // 建立 uploads 資料夾
	            }
	            File dest = new File(uploadDir + File.separator + fileName);

	            dest.getParentFile().mkdirs(); // 確保目錄存在
	            imageFile.transferTo(dest);

	            // Log the image file path to make sure it's being saved correctly
	            System.out.println("Image saved to: " + dest.getAbsolutePath());

	            // 儲存圖片路徑（或 URL）到 Post
	            post.setImage("/uploads/" + fileName); // 或者加上 domain path
	        }
	        postService.addPost(post);
	        return ResponseEntity.status(HttpStatus.CREATED).body("User posted successfully!");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
	    }
	}


	
	@GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts(HttpSession session) {
		int userId = (int) session.getAttribute("userId");
        try {
        	List<Post> posts = postService.getPosts(userId);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
	
	@DeleteMapping("/deletePost/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable int postId) {
        try {
        	postService.deletePost(postId);
        	return ResponseEntity.ok("post deleted.");
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
	
	@PutMapping("/updatePost")
    public ResponseEntity<String> updatePost(@RequestBody Post post) {
        try {
        	postService.updatePost(post);
        	return ResponseEntity.ok("post updated.");
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
	
	@GetMapping("/othersPosts")
    public ResponseEntity<List<Post>> othersPosts(@RequestHeader String sessionId, HttpSession session) {
		int userId = UserService.getUserIdBySessionID(sessionId);
        try {
        	List<Post> posts = postService.getOthersPosts(userId);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
	
	@GetMapping("/getpost/{postId}")
    public ResponseEntity<Post> getPost(@RequestHeader String sessionId, @PathVariable int postId) {
		int userId = UserService.getUserIdBySessionID(sessionId);
        try {
        	Post post = postService.getPost(userId);
            return ResponseEntity.ok(post);
        } catch (Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
