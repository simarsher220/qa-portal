package org.codejudge.sb.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "question_tag")
public class QuestionTag {

    @EmbeddedId
    private QuestionTagKey key;

    @ManyToOne
    @MapsId("ques_id")
    @JoinColumn(name = "ques_id")
    private Question question;

    @ManyToOne
    @MapsId("tag_id")
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public QuestionTag(Question question, Tag tag) {
        key = new QuestionTagKey(question.getId(), tag.getId());
        this.question = question;
        this.tag = tag;

        question.getQuestionTagSet().add(this);
        tag.getQuestionTagSet().add(this);
    }
}
