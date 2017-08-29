package com.doc.word.extractor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.competency.repository.CompetencyRepository;
import com.doc.word.entity.User;
import com.doc.word.extractor.service.MyWordExtractor;
import com.doc.word.extractor.service.UserService;

import java.io.IOException;

import javax.servlet.http.HttpSession;

@Controller
public class FileUploadController {

	final static Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@Autowired
	private MyWordExtractor myWordExtractor;
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private CompetencyRepository competencyRepository;

    @GetMapping("/")
    public String index(Model model, HttpSession session) throws IOException {
    	model.addAttribute("title", "Upload your document");
    	return "index";
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
    	HttpSession session,
    	Model model) throws IOException {

        User user = (User) session.getAttribute("USER");
    	int transactionId = competencyRepository.getTransactionId();
    	logger.info("userId={}, transactionId={}, File Name : {}", 
    		user.getId(), transactionId, file.getOriginalFilename());
    	myWordExtractor.extract(user.getId(), transactionId, 
    		file.getOriginalFilename(), file.getInputStream());
    	model.addAttribute("title", "Document upload result");
        model.addAttribute("message",
                "Congratulations! Your resume was uploaded successfully " + file.getOriginalFilename() + "!");
        //model.addAttribute("results", competencyRepository.getDocResult(user.getId(), transactionId));
        return "result";
    }

    @PostMapping("/login")
    public String login(@RequestParam("userId") Integer userId,
    	@RequestParam("password") String password,
    	HttpSession session) throws IOException {
    	logger.info("userId={},password={}", userId, password);
    	User user = userService.findUser(userId, password);
    	logger.info("{}",user);
    	session.setAttribute("USER", user);
    	user.setPassword(null);
        return "index";
    }

    @GetMapping("/register")
    public String newUser(Model model) throws IOException {
    	model.addAttribute("title", "Register New User");
    	model.addAttribute("user", new User());
    	return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user,
    	HttpSession session) throws IOException {
    	userService.save(user);
    	session.setAttribute("USER", user);
    	user.setPassword(null);
    	return "redirect:/";
    }

    @GetMapping("/logout")
    public String newUser(HttpSession session) throws IOException {
    	session.removeAttribute("USER");
    	return "redirect:/";
    }

}