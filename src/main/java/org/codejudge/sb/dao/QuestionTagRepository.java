package org.codejudge.sb.dao;

import org.codejudge.sb.entity.QuestionTag;
import org.codejudge.sb.entity.QuestionTagKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionTagRepository extends JpaRepository<QuestionTag, QuestionTagKey> {
}
