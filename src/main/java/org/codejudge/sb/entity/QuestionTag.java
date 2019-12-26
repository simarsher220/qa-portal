package org.codejudge.sb.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@ToString(doNotUseGetters = true, callSuper = true)
@Entity
@Table(name = "question_tag")
public class QuestionTag {

    @EmbeddedId
    private QuestionTagKey key;

    @ManyToOne
    @MapsId("quesId")
    @JoinColumn(name = "ques_id", referencedColumnName = "id")
    private Question question;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    private Tag tag;

    public QuestionTag(Question question, Tag tag) {
        key = new QuestionTagKey(question.getId(), tag.getId());
        this.question = question;
        this.tag = tag;

        question.getTags().add(this);
        tag.getQuestions().add(this);
    }
}
