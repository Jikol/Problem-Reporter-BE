package com.domainlayer.dto.problem;

import com.domainlayer.dto.SuperProblemDTO;

import java.util.List;

public class ProblemDTO extends SuperProblemDTO {
    public ProblemDTO(String title, String summary, String configuration, String expectedBehavior, String actualBehavior, List<String> attachments) {
        super(title, summary, configuration, expectedBehavior, actualBehavior, attachments);
    }

    public String getTitle() {
        return title;
    }
    public String getSummary() {
        return summary;
    }
    public String getConfiguration() {
        return configuration;
    }
    public String getExpectedBehavior() {
        return expectedBehavior;
    }
    public String getActualBehavior() {
        return actualBehavior;
    }
    public List getAttachments() {
        return attachments;
    }

    @Override
    public String toString() {
        return "ProblemDTO{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", configuration='" + configuration + '\'' +
                ", expectedBehavior='" + expectedBehavior + '\'' +
                ", actualBehavior='" + actualBehavior + '\'' +
                ", attachments=" + attachments +
                '}';
    }
}
