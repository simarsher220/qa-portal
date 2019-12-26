package org.codejudge.sb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.codejudge.sb.error.exception.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString(doNotUseGetters = true, callSuper = true)
public class QuestionTagDto {

    private Integer quesId;
    private Integer tagId;

    public static void validate(List<QuestionTagDto> questionTagDtos, Integer quesId) throws GenericException {
        if (CollectionUtils.isEmpty(questionTagDtos)) {
            throw new GenericException("Question Tags list cannot be empty!!", HttpStatus.BAD_REQUEST);
        }
        for (QuestionTagDto questionTagDto : questionTagDtos) {
            if (questionTagDto == null) {
                throw new GenericException("Question Tag cannot be empty!!", HttpStatus.BAD_REQUEST);
            }
            questionTagDto.validate(quesId);
        }
    }

    private void validate(Integer quesId) throws GenericException {
        if (this.quesId == null) {
            throw new GenericException("Question Tag quesId cannot be empty!!", HttpStatus.BAD_REQUEST);
        }
        if (this.tagId == null) {
            throw new GenericException("Question Tag quesId cannot be empty!!", HttpStatus.BAD_REQUEST);
        }
        if (!this.quesId.equals(quesId)) {
            throw new GenericException("Question Tag quesId isn't same as parameter!!", HttpStatus.BAD_REQUEST);
        }
    }
}
