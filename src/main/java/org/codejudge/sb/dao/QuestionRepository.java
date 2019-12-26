package org.codejudge.sb.dao;

import org.codejudge.sb.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query(value = "select * from question where id = :id", nativeQuery = true)
    Question getById(Integer id);
}
