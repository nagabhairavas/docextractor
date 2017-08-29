package com.doc.word.extractor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doc.word.entity.User;
import com.doc.word.repository.UserRepository;

@Service
public class UserService {
	final static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	public User save(User user) {
		return userRepository.save(user);
	}
	
	public User findUser(Integer id, String password) {
		return userRepository.findByIdAndPassword(id, password);
	}
}
