package com.blog.api.repository.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "label")
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * 标签的内容
     */
    private String label;

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
