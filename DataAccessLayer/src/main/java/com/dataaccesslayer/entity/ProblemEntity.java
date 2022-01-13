package com.dataaccesslayer.entity;

public class ProblemEntity {
    private Integer id = null;
    private final String title;
    private final String summary;
    private final String configuration;
    private final String expectedBehavior;
    private final String actualBehavior;
    private final UserEntity userEntity;
    private final DeploymentEntity deploymentEntity;

    public ProblemEntity(final String title, final String summary, final String configuration, final String expectedBehavior,
                         final String actualBehavior, final UserEntity userEntity, DeploymentEntity deploymentEntity) {
        this.title = title;
        this.summary = summary;
        this.configuration = configuration;
        this.expectedBehavior = expectedBehavior;
        this.actualBehavior = actualBehavior;
        this.userEntity = userEntity;
        this.deploymentEntity = deploymentEntity;
    }

    public ProblemEntity(final int id, final String title, final String summary, final String configuration, final String expectedBehavior,
                         final String actualBehavior, final UserEntity userEntity, DeploymentEntity deploymentEntity) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.configuration = configuration;
        this.expectedBehavior = expectedBehavior;
        this.actualBehavior = actualBehavior;
        this.userEntity = userEntity;
        this.deploymentEntity = deploymentEntity;
    }

    public ProblemEntity(Integer id) {
        this.id = id;
        this.title = null;
        this.summary = null;
        this.configuration = null;
        this.expectedBehavior = null;
        this.actualBehavior = null;
        this.userEntity = null;
        this.deploymentEntity = null;
    }

    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public String getSummary() { return summary; }
    public String getConfiguration() { return configuration; }
    public String getExpectedBehavior() { return expectedBehavior; }
    public String getActualBehavior() { return actualBehavior; }
    public UserEntity getUserEntity() { return userEntity; }
    public DeploymentEntity getDeploymentEntity() { return deploymentEntity; }

    @Override
    public String toString() {
        return "ProblemEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", configuration='" + configuration + '\'' +
                ", expectedBehavior='" + expectedBehavior + '\'' +
                ", actualBehavior='" + actualBehavior + '\'' +
                ", userEntity=" + userEntity +
                ", deploymentEntity=" + deploymentEntity +
                '}';
    }
}
