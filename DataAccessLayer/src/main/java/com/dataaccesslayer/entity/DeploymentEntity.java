package com.dataaccesslayer.entity;

public class DeploymentEntity {
    private Integer id = null;
    private final String domain;
    private final String contact;
    private final String desc;

    public DeploymentEntity(final String domain, final String contact, final String desc) {
        this.domain = domain;
        this.contact = contact;
        this.desc = desc;
    }

    public DeploymentEntity(final int id, final String domain, final String contact, final String desc) {
        this.id = id;
        this.domain = domain;
        this.contact = contact;
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public String getDomain() {
        return domain;
    }

    public String getContact() {
        return contact;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "DeploymentEntity{" +
                "id=" + id +
                ", domain='" + domain + '\'' +
                ", contact='" + contact + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
