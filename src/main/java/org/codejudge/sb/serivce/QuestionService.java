package org.codejudge.sb.serivce;

import lombok.extern.slf4j.Slf4j;
import org.codejudge.sb.dao.QuestionRepository;
import org.codejudge.sb.entity.Question;
import org.codejudge.sb.entity.QuestionTag;
import org.codejudge.sb.entity.Tag;
import org.codejudge.sb.error.exception.GenericException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository quesRepo;

    @Autowired
    private TagService tagService;

    public Question postQuestion(Question question) {
        question.setUpvotes(0);
        question.setViews(0);
        question = quesRepo.saveAndFlush(question);
        return question;
    }

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
        Set<QuestionTag> questionSet = tag.getQuestions();
        if (CollectionUtils.isEmpty(questionSet)) {
            throw new GenericException("No Questions found for the tag!!", HttpStatus.NOT_FOUND);
        }
        return questionSet.stream().map(QuestionTag::getQuestion).collect(Collectors.toList());
    }

    public Question saveAndFlush(Question question) {
        return quesRepo.saveAndFlush(question);
    }

}
