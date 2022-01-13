package com.dataaccesslayer.entity;

public class AttachmentEntity {
    private Integer id = null;
    private final String data;
    private final ProblemEntity problemEntity;

    public AttachmentEntity(Integer id, String data, ProblemEntity problemEntity) {
        this.id = id;
        this.data = data;
        this.problemEntity = problemEntity;
    }

    public AttachmentEntity(String data, ProblemEntity problemEntity) {
        this.data = data;
        this.problemEntity = problemEntity;
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
