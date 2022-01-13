package com.dataaccesslayer.entity;

public class AttachmentEntity {
    private Integer id;
    private String data;
    private ProblemEntity problemEntity;

    public AttachmentEntity(Integer id, String data, ProblemEntity problemEntity) {
        this.id = id;
        this.data = data;
        this.problemEntity = problemEntity;
    }

    public AttachmentEntity(String data, ProblemEntity problemEntity) {
        this.data = data;
        this.problemEntity = problemEntity;
    }

    public AttachmentEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    public String getData() {
        return data;
    }
    public ProblemEntity getProblemEntity() {
        return problemEntity;
    }

    @Override
    public String toString() {
        return "AttachmentEntity{" +
                "id=" + id +
                ", data='" + data + '\'' +
                ", problemEntity=" + problemEntity +
                '}';
    }
}
