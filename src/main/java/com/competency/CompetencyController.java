package com.competency;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.competency.model.Category;
import com.competency.model.Competency;
import com.competency.model.Counter;
import com.competency.model.Question;
import com.competency.model.Test;
import com.competency.repository.CompetencyRepository;
import com.competency.service.S3Service;
import com.doc.word.entity.User;

@Controller
public class CompetencyController {
	
	final static Logger logger = LoggerFactory.getLogger(CompetencyController.class);
	
	@Autowired
	private CompetencyRepository competencyRepository;
	
	@Autowired
	private S3Service s3Service;
	
	@GetMapping("/competency")
    public String getSurvey(Model model,
    	HttpSession session) throws IOException {
		User user = (User) session.getAttribute("USER");
    	logger.info("{}", user);
    	Test test = competencyRepository.getCompetency(user.getId());
    	test.setUserId(user.getId());
    	model.addAttribute("test", test);
    	model.addAttribute("counter", new Counter());
    	test.getCompetencies().stream().filter(c -> c.isVisualization())
    		.forEach(c -> c.getCategories().stream()
    		.forEach(cat -> cat.getQuestionnaire().stream()
    		.forEach(q -> {
    			q.setImage(s3Service.readFile(q.getExplanation()));
    		})));
    	return "competency";
    }

    @PostMapping("/competency")
    public String submitSurvey(@ModelAttribute("test") Test test,
    	HttpSession session,
    	Model model) throws IOException {
    	logger.info("userId={}, id={}", test.getUserId(), test.getId());
    	List<Question> answers = new ArrayList<>();
    	for (Competency competency : test.getCompetencies()) {
	        logger.info("userId={}, competency.id={}, categories={}", test.getUserId(), 
	        	competency.getId(), competency.getCategories().size());
	    	for (Category category : competency.getCategories()) {
	    		logger.info("category.id={}, questionnaire={}", category.getId(), category.getQuestionnaire().size());
				for (Question question : category.getQuestionnaire()) {
					logger.info("question.itemId={}, question.answer={}", question.getItemId(), question.getAnswer());
					answers.add(question);
				}
	    	}
    	}
    	competencyRepository.saveTest(test, answers);
    	model.addAttribute("message", "You successfully submitted the competencies!");
    	model.addAttribute("scores", competencyRepository.getTestScore(test.getUserId()));
    	model.addAttribute("miScores", competencyRepository.getMultipleIntelligenceScore(test.getUserId()));

        return "success";
    }
    


    @GetMapping("/score")
    public String submitSurvey(Model model,
    	HttpSession session) throws IOException {
    	User user = (User) session.getAttribute("USER");
    	model.addAttribute("message", "You successfully submitted the competencies!");
    	model.addAttribute("scores", competencyRepository.getTestScore(user.getId()));
    	model.addAttribute("miScores", competencyRepository.getMultipleIntelligenceScore(user.getId()));

        return "success";
    }

}