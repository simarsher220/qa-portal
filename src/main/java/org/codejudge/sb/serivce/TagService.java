package org.codejudge.sb.serivce;

import lombok.extern.slf4j.Slf4j;
import org.codejudge.sb.dao.TagRepository;
import org.codejudge.sb.entity.Tag;
import org.codejudge.sb.error.exception.GenericException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public Tag addTag(Tag tag) throws GenericException {
        Tag tagFromTable = null;
        String tagName = tag.getTagName();
        tagFromTable = tagRepository.findByTagName(tagName.toLowerCase());
        if (tagFromTable != null) {
            throw new GenericException("Tag already exists!!", HttpStatus.BAD_REQUEST);
        }
        tag.setTagName(tag.getTagName().toLowerCase());
        tag = tagRepository.saveAndFlush(tag);
        return tag;
    }

    public List<Tag> getTags() throws GenericException {
        List<Tag> tags;
        tags = tagRepository.findAll();
        if (CollectionUtils.isEmpty(tags)) {
            throw new GenericException("No Tags found for this site!!", HttpStatus.NOT_FOUND);
        }
        return tags;
    }

    public Tag getByTagName(String name) throws GenericException {
        Tag tag = tagRepository.findByTagName(name.toLowerCase());
        log.info("tag details after get from tagRepository are:- ");
        log.info("tag id : " + tag.getId());
        log.info("tag name : " + tag.getTagName());
        log.info("total tagged questions : " + tag.getQuestionTagSet().size());
        if (tag == null) {
            throw new GenericException("No Tags Found!!", HttpStatus.NOT_FOUND);
        }
        return tag;
    }

    public Tag getTagById(Integer id) throws GenericException {
        Tag tag = tagRepository.getById(id);
        if (tag == null) {
            throw new GenericException("No Tags Found!!", HttpStatus.NOT_FOUND);
        }
        return tag;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public Tag saveAndFlush(Tag tag) {
        return tagRepository.saveAndFlush(tag);
    }
}
