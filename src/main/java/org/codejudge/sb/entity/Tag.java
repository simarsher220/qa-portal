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

@NoArgsConstructor
@Getter
@Setter
@ToString(doNotUseGetters = true, callSuper = true)
@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "tagName")
    private String tagName;

    @Type(type = "text")
    @Column(name = "tagDescription")
    private String tagDescription;

    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private Set<QuestionTag> questions = new HashSet<>();

    public static void validate(Tag tag) throws GenericException {
        if (tag == null) {
            throw new GenericException("Tag cannot be null!!", HttpStatus.BAD_REQUEST);
        }
        tag.validate();
    }

    private void validate() throws GenericException {
        validateTagName();
        validateTagDescription();
    }

    private void validateTagName() throws GenericException {
        if (StringUtils.isEmpty(tagName)) {
            throw new GenericException("Tag name cannot be null!!", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateTagDescription() throws GenericException {
        if (StringUtils.isEmpty(tagDescription)) {
            throw new GenericException("Tag description cannot be null!!", HttpStatus.BAD_REQUEST);
        }
    }
}
