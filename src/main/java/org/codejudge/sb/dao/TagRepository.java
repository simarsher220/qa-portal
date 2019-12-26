package org.codejudge.sb.dao;

import org.codejudge.sb.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query(value = "select * from tag where tag_name = :tagName", nativeQuery = true)
    Tag findByTagName(@Param("tagName") String name);

    @Query(value = "select * from tag where id = :id", nativeQuery = true)
    Tag getById(Integer id);
}
