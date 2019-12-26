package org.codejudge.sb.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(doNotUseGetters = true, callSuper = true)
public class SuccessResponse {

    private String status;

    public SuccessResponse() {
        status = "success";
    }

}
