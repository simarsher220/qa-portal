package org.codejudge.sb.controller;

import org.codejudge.sb.entity.Question;
import org.codejudge.sb.error.exception.GenericException;
import org.codejudge.sb.serivce.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/post")
    @ResponseBody
    public ResponseEntity<Question> postQuestion(@RequestBody Question question) throws GenericException {
        Question.validate(question);
        return new ResponseEntity<>(questionService.postQuestion(question), HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    @ResponseBody
    public ResponseEntity<Question> editQuestion(@RequestBody Question question) throws GenericException {
        Question.validate(question);
        return new ResponseEntity<>(questionService.editQuestion(question), HttpStatus.ACCEPTED);
    }

    @GetMapping("/tag")
    @ResponseBody
    public ResponseEntity<List<Question>> getQuestionsByTag(@RequestParam("name") String tagName) throws GenericException {
        if (StringUtils.isEmpty(tagName)) {
            throw new GenericException("Invalid tag name!!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(questionService.getQuestionsByTagName(tagName), HttpStatus.OK);
    }

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<Question> getQuestionById(@RequestParam("id") Integer id) throws GenericException {
        if (id == null) {
            throw new GenericException("Id not found!!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(questionService.getById(id), HttpStatus.OK);
    }

}
