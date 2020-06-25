package com.practice.all.demo.service;

import com.practice.all.demo.Entity.Student;
import org.elasticsearch.index.query.QueryBuilder;
import java.util.List;

public interface  UserService {
    Student findById(String studentId);

    void deleteById(String studentId);

    List<Student> search(QueryBuilder qb1);

    List<Student> findAll();
}
