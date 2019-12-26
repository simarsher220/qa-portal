package org.codejudge.sb.serivce;

import lombok.extern.slf4j.Slf4j;
import org.codejudge.sb.dao.QuestionTagRepository;
import org.codejudge.sb.entity.Question;
import org.codejudge.sb.entity.QuestionTag;
import org.codejudge.sb.entity.Tag;
import org.codejudge.sb.error.exception.GenericException;
import org.codejudge.sb.model.QuestionTagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class QuestionTagService {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TagService tagService;

    @Autowired
    private QuestionTagRepository questionTagRepo;


    public void addQuestionTags(List<QuestionTagDto> questionTagDtos) throws GenericException {
        for (QuestionTagDto questionTagDto : questionTagDtos) {
            Question question = questionService.getById(questionTagDto.getQuesId());
            Tag tag = tagService.getTagById(questionTagDto.getTagId());
            QuestionTag questionTag = new QuestionTag(question, tag);
            questionTagRepo.saveAndFlush(questionTag);
            question.getTags().add(questionTag);
            tag.getQuestions().add(questionTag);
            questionService.saveAndFlush(question);
            tagService.saveAndFlush(tag);
        }
    }
}
