package org.codejudge.sb.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
public class QuestionTagKey implements Serializable {

    @Column(name = "ques_id")
    private Integer quesId;

    @Column(name = "tag_id")
    private Integer tagId;

    public QuestionTagKey(Integer quesId, Integer tagId) {
        this.quesId = quesId;
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        QuestionTagKey that = (QuestionTagKey) o;
        return Objects.equals(quesId, that.quesId) &&
                Objects.equals(tagId, that.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quesId, tagId);
    }

}
