package com.example.service;




import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.PasswordUtil;
import com.example.model.User;
import com.example.repository.UserRepository;

@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;
	private static final Map<String, Integer> sessionStore = new HashMap<>();
	
	@Transactional
    public void registerUser(User user) {
		user.setPass(PasswordUtil.hashPassword(user.getPass()));
        userRepository.addUser(user); 
    }
	
	@Transactional
    public boolean login(String phoneNum, String password) {
		String correctP = userRepository.getPassByPhoneNum(phoneNum);
		if(correctP != null) {
			return PasswordUtil.checkPassword(password, correctP);
		}else {
			return false;
		}
		
    }
	
	@Transactional
    public String getSessionId(String phoneNum) {
		int userId = 0;
		userId = userRepository.getUserIdByPhoneNum(phoneNum);
		String sessionId = UUID.randomUUID().toString();
		sessionStore.put(sessionId, userId);
		return sessionId;
    }
	
	@Transactional
    public static int getUserIdBySessionID(String sessionId) {
		int userId = 0;
		userId = sessionStore.get(sessionId);
		return userId;
    }
}