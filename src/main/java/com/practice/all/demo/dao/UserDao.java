package com.practice.all.demo.dao;

import com.practice.all.demo.Entity.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface  UserDao extends ElasticsearchRepository<Student, String> {
}
