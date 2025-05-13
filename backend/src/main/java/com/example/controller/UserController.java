package com.example.controller;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.User;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    

    // 註冊
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
        @RequestParam("user") String userJson, 
        @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            // 解析用戶資料
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(userJson, User.class);

            // 處理圖片
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
                user.setCoverImage("/uploads/" + fileName);
            }

            // 註冊用戶
            userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    
    // 登入
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, Object> params, HttpSession session) {
    	String phoneNum = (String) params.get("phoneNum");
		String pass = (String) params.get("pass");
		Map<String, String> response = new HashMap<>();
		try {
	        if (userService.login(phoneNum, pass)) {
	            String sessionId = userService.getSessionId(phoneNum);
	            response.put("sessionId", sessionId);
	            return ResponseEntity.ok(response);
	        } else {
	            response.put("error", "Invalid credentials.");
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	        }
	    } catch (Exception e) {
	        response.put("error", "Error: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
    }
    
    @GetMapping("/personalInfo")
    public ResponseEntity<User> getPost(@RequestHeader String sessionId) {
		int userId = UserService.getUserIdBySessionID(sessionId);
        if(userId!=0) {
        	try {
            	User user = userService.getUser(userId);
            	user.setPass("");
                return ResponseEntity.ok(user);
            } catch (Exception e) {
            	e.printStackTrace();
            	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }else {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    
}