package org.codejudge.sb.serivce;

import lombok.extern.slf4j.Slf4j;
import org.codejudge.sb.dao.QuestionRepository;
import org.codejudge.sb.dao.QuestionTagRepository;
import org.codejudge.sb.entity.Question;
import org.codejudge.sb.entity.QuestionTag;
import org.codejudge.sb.entity.Tag;
import org.codejudge.sb.error.exception.GenericException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class QuestionService {

    @Autowired
    private QuestionRepository quesRepo;

    @Autowired
    private TagService tagService;

    @Autowired
    private QuestionTagRepository quesTagRepo;

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public Question postQuestion(Question question) {
        question.setUpvotes(0);
        question.setViews(0);
        question = quesRepo.saveAndFlush(question);
        return question;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public Question editQuestion(Question question) throws GenericException {
        Integer id = question.getId();
        if (id == null || getById(id) == null) {
            throw new GenericException("No Question found!!", HttpStatus.NOT_FOUND);
        }
        Question questionFromTable = getById(id);
        Question questionToBeUpserted = id != null ? questionFromTable : question;
        questionToBeUpserted.setTitle(question.getTitle());
        questionToBeUpserted.setDescription(question.getDescription());
        questionToBeUpserted.setUpvotes(question.getUpvotes() == null ? questionToBeUpserted.getUpvotes() : question.getUpvotes());
        questionToBeUpserted.setViews(question.getViews() == null ? questionToBeUpserted.getViews() : question.getViews());
        question = quesRepo.saveAndFlush(questionToBeUpserted);
        return question;
    }

    public Question getById(Integer id) throws GenericException {
        Question question = null;
        question = quesRepo.getById(id);
        if (question == null) {
            throw new GenericException("No Question found!!", HttpStatus.NOT_FOUND);
        }
        return question;
    }

    public void deleteQuestionById(Integer id) throws GenericException {
        Question question = getById(id);
        quesRepo.delete(question);
    }

    public List<Question> getQuestionsByTagName(String tagName) throws GenericException {
        Tag tag = tagService.getByTagName(tagName);
        List<Question> totalQuestions = quesRepo.findAll();
        log.info("Printing all question ids...");
        for (Question question: totalQuestions) {
            log.info("" + question.getId());
        }
        List<Tag> tags = tagService.getTags();
        log.info("Printing all tags...");
        for (Tag loopTag: tags) {
            log.info("id : " + loopTag.getId());
            log.info("name : " + loopTag.getTagName());
        }
        log.info("Printing all questionTags...");
        List<QuestionTag> questionTags = quesTagRepo.findAll();
        for (QuestionTag questionTag: questionTags) {
            log.info("question id for questionTag is: " + questionTag.getKey().getQuesId());
            log.info("tag id for questionTag is: " + questionTag.getKey().getTagId());
        }
        log.info("tag details after get from tagService in questionService are:- ");
        log.info("tag id : " + tag.getId());
        log.info("tag name : " + tag.getTagName());
        log.info("total tagged questions : " + tag.getQuestionTagSet().size());
        Set<QuestionTag> questionSet = tag.getQuestionTagSet();
        if (CollectionUtils.isEmpty(questionSet)) {
            throw new GenericException("No Questions found for the tag!!", HttpStatus.NOT_FOUND);
        }
        return questionSet.stream().map(QuestionTag::getQuestion).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public Question saveAndFlush(Question question) {
        return quesRepo.saveAndFlush(question);
    }

}
