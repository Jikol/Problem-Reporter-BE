package com.dataaccesslayer.entity;

public class ProblemEntity {
    private Integer id = null;
    private final String title;
    private final String summary;
    private final String configuration;
    private final String expectedBehavior;
    private final String actualBehavior;
    private final String context;
    private final UserEntity userEntity;

    public ProblemEntity(final String title, final String summary, final String configuration, final String expectedBehavior,
                         final String actualBehavior, final String context, final UserEntity userEntity) {
        this.title = title;
        this.summary = summary;
        this.configuration = configuration;
        this.expectedBehavior = expectedBehavior;
        this.actualBehavior = actualBehavior;
        this.context = context;
        this.userEntity = userEntity;
    }

    public ProblemEntity(final int id, final String title, final String summary, final String configuration, final String expectedBehavior,
                         final String actualBehavior, final String context, final UserEntity userEntity) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.configuration = configuration;
        this.expectedBehavior = expectedBehavior;
        this.actualBehavior = actualBehavior;
        this.context = context;
        this.userEntity = userEntity;
    }

    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public String getSummary() { return summary; }
    public String getConfiguration() { return configuration; }
    public String getExpectedBehavior() { return expectedBehavior; }
    public String getActualBehavior() { return actualBehavior; }
    public String getContext() { return context; }
    public UserEntity getUserEntity() { return userEntity; }
}
