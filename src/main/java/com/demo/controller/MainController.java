package com.demo.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.dao.SimpleInterestRepository;
import com.demo.dao.UserRepository;
import com.demo.model.SimpleInterest;
import com.demo.model.User;
import com.mysql.jdbc.StringUtils;

@Controller     
public class MainController {
	
	@Autowired
	private UserRepository userDao;
	@Autowired
	private SimpleInterestRepository simpleInterestDao;
	
	@PostMapping("/addUser") 
	public @ResponseBody String addNewUser (@ModelAttribute User user) {
		Date sysDate = new Date(System.currentTimeMillis());
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		user.setCreatedAt(sysDate);
		user.setUpdatedAt(sysDate);
		userDao.save(user);
		return "Saved";
	}
	
    @GetMapping("/signUp")
    public String signUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signUp";
    }
	
    @PostMapping("/signUp")
    public String signUpSubmit(Model model, @ModelAttribute User user, HttpServletRequest request) {
    	
    	if(user != null && !StringUtils.isNullOrEmpty(user.getEmail())){
    		User existingUser = userDao.findByEmail(user.getEmail());
    		if(existingUser == null){
    			Date sysDate = new Date(System.currentTimeMillis());
        		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        		user.setCreatedAt(sysDate);
        		user.setUpdatedAt(sysDate);
        		userDao.save(user);
            	return "login";
    		}
    	}
    	model.addAttribute("status", "error");
    	return "signUp";
    	
    }
	
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
    
    @PostMapping("/login")
	public String loginSubmit(@ModelAttribute User user, Model model,
			HttpServletRequest request, HttpServletResponse httpResponse, RedirectAttributes attributes,
			HttpSession httpSession) throws IOException {
    	
    	Boolean isAuthenticated = false;
    	User loginUser = userDao.findByEmail(user.getEmail());
    	if(loginUser != null){
    		isAuthenticated = BCrypt.checkpw(user.getPassword(), loginUser.getPassword());
    	}
    	
    	if(isAuthenticated){
    		httpSession.setAttribute("authUser", loginUser);
    		return "redirect:/home";
    	}else{
    		model.addAttribute("status", "failed");
    		return "login";
    	}
    }
    
	@GetMapping("/logout")
    public String logout(HttpSession httpSession)
    {
		httpSession.invalidate();
    	return "redirect:/login";
    }
    
    @GetMapping("/home")
    public String home(Model model, HttpSession httpSession) {
    	User user = (User) httpSession.getAttribute("authUser");
    	if(user != null){
    		SimpleInterest simpleInterest = new SimpleInterest();
        	simpleInterest.setUserId(user.getId());
            model.addAttribute("simpleInterest", simpleInterest);
            
            List<SimpleInterest> interests =  simpleInterestDao.findByUserIdOrderByIdDesc(user.getId());
            model.addAttribute("interests", interests);
            return "home";
    	}else{
    		return "login";
    	}
    }
    
    @PostMapping("/calculateSI")
    public String calculateSI(Model model, @ModelAttribute SimpleInterest simpleInterest, HttpServletRequest request) {
		Date sysDate = new Date(System.currentTimeMillis());
		
		simpleInterest.setInterest((simpleInterest.getPrincipal() * simpleInterest.getRate() * simpleInterest.getTime()));
		simpleInterest.setCreatedAt(sysDate);
		simpleInterest.setUpdatedAt(sysDate);
		simpleInterestDao.save(simpleInterest);
    	return "redirect:/home";
    }
}
