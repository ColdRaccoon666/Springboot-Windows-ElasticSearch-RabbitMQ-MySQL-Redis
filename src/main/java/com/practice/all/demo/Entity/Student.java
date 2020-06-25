package com.practice.all.demo.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "testtwo", type = "student")   //索引的名字  类型
public class Student implements Serializable {

    @Id
    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "  学生信息： " +
                "学生id： '" + id + '\'' +
                ", 学生姓名： '" + name+'\n';
    }
}
