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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class QuestionTagService {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TagService tagService;

    @Autowired
    private QuestionTagRepository questionTagRepo;

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void addQuestionTags(List<QuestionTagDto> questionTagDtos) throws GenericException {
        for (QuestionTagDto questionTagDto : questionTagDtos) {
            Question question = questionService.getById(questionTagDto.getQuesId());
            log.info("question details are:- ");
            log.info("question id : " + question.getId());
            Tag tag = tagService.getTagById(questionTagDto.getTagId());
            log.info("tag details are:- ");
            log.info("tag id : " + tag.getId());
            log.info("tag name : " + tag.getTagName());
            QuestionTag questionTag = new QuestionTag(question, tag);
            questionTag = questionTagRepo.saveAndFlush(questionTag);
            questionService.saveAndFlush(question);
            tag = tagService.saveAndFlush(tag);
            tag = tagService.getByTagName(tag.getTagName());
            log.info("tag details after save and flush are:- ");
            log.info("tag id : " + tag.getId());
            log.info("tag name : " + tag.getTagName());
            log.info("total tagged questions : " + tag.getQuestionTagSet().size());
            question = questionService.getById(question.getId());
            log.info("question details after save and flush are:- ");
            log.info("question id : " + question.getId());
            log.info("question total tags : " + question.getQuestionTagSet().size());
        }
        log.info("tags and question are getting saved!!!");
    }
}
