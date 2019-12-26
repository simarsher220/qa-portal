package org.codejudge.sb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.codejudge.sb.error.exception.GenericException;
import org.hibernate.annotations.Type;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@Entity
@Table(name = "question")
public class Question {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Type(type = "text")
    @Column(name = "title")
    private String title;

    @Type(type = "text")
    @Column(name = "description")
    private String description;

    @Column(name = "upvotes")
    private Integer upvotes;

    @Column(name = "views")
    private Integer views;

    @OneToMany(mappedBy = "tag")
    @JsonIgnore
    private Set<QuestionTag> tags = new HashSet<>();

    public static void validate(Question question) throws GenericException {
        if (null == question) {
            throw new GenericException("Question cannot be empty!!", HttpStatus.BAD_REQUEST);
        }
        question.validate();
    }

    private void validate() throws GenericException {
        validateTitle();
        validateDescription();
    }

    private void validateTitle() throws GenericException {
        if (StringUtils.isEmpty(title)) {
            throw new GenericException("Question title cannot be empty!!", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateDescription() throws GenericException {
        if (StringUtils.isEmpty(description)) {
            throw new GenericException("Question description cannot be empty!!", HttpStatus.BAD_REQUEST);
        }
    }
}
