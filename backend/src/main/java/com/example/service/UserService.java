package com.example.service;


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
	
	
	@Transactional
    public void registerUser(User user) {
		user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
        userRepository.addUser(user); 
    }
	
	@Transactional
    public boolean login(String email, String password) {
		String correctP = userRepository.getPasswordByEmail(email);
		if(correctP != null) {
			return PasswordUtil.checkPassword(password, correctP);
		}else {
			return false;
		}
		
    }
}