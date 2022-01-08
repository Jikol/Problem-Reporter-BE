package com.domainlayer.dto;

public class SuperProblemDTO {
    protected final String title;
    protected final String summary;
    protected final String configuration;
    protected final String expectedBehavior;
    protected final String actualBehavior;
    protected final String context;

    public SuperProblemDTO(String title, String summary, String configuration, String expectedBehavior, String actualBehavior, String context) {
        this.title = title;
        this.summary = summary;
        this.configuration = configuration;
        this.expectedBehavior = expectedBehavior;
        this.actualBehavior = actualBehavior;
        this.context = context;
    }
}
