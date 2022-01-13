package com.dataaccesslayer.entity;

public class ProblemEntity {
    private Integer id;
    private String title;
    private String summary;
    private String configuration;
    private String expectedBehavior;
    private String actualBehavior;
    private UserEntity userEntity;
    private DeploymentEntity deploymentEntity;

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

    public ProblemEntity(Integer id, String title, String summary, String configuration, String expectedBehavior, String actualBehavior) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.configuration = configuration;
        this.expectedBehavior = expectedBehavior;
        this.actualBehavior = actualBehavior;
    }

    public ProblemEntity(Integer id) {
        this.id = id;
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
