package com.demo.controller;

import java.util.Date;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.dao.UserRepository;
import com.demo.model.User;

@Controller     
public class MainController {
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/addUser") 
	public @ResponseBody String addNewUser (@ModelAttribute User user) {
		Date sysDate = new Date(System.currentTimeMillis());
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		user.setCreatedAt(sysDate);
		user.setUpdatedAt(sysDate);
		userRepository.save(user);
		return "Saved";
	}
	
}
