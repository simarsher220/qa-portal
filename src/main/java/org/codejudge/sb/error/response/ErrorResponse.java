package org.codejudge.sb.error.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {

    private String status;
    private String explanation;

    public ErrorResponse(String explanation) {
        this.status = "failure";
        this.explanation = explanation;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("reason")
    public String getExplanation() {
        return explanation;
    }
}
