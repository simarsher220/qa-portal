package org.codejudge.sb.controller;

import org.codejudge.sb.error.exception.GenericException;
import org.codejudge.sb.model.QuestionTagDto;
import org.codejudge.sb.model.SuccessResponse;
import org.codejudge.sb.serivce.QuestionTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question-tag")
public class QuestionTagController {

    @Autowired
    private QuestionTagService questionTagService;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<SuccessResponse> addQuestionTags(@RequestBody List<QuestionTagDto> questionTagDtos, @RequestParam Integer quesId) throws GenericException {
        QuestionTagDto.validate(questionTagDtos, quesId);
        questionTagService.addQuestionTags(questionTagDtos);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }
}
