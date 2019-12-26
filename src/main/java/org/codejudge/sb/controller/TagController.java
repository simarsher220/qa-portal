package org.codejudge.sb.controller;

import io.swagger.annotations.ApiOperation;
import org.codejudge.sb.entity.Tag;
import org.codejudge.sb.error.exception.GenericException;
import org.codejudge.sb.serivce.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping("/add")
    @ApiOperation("Add a new Tag to the portal")
    public ResponseEntity<Tag> addTag(@RequestBody Tag tag) throws GenericException {
        Tag.validate(tag);
        return new ResponseEntity<>(tagService.addTag(tag), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @ApiOperation("Get all tags for the portal")
    public ResponseEntity<List<Tag>> getTags() throws GenericException {
        return new ResponseEntity<>(tagService.getTags(), HttpStatus.OK);
    }

    @GetMapping("")
    @ApiOperation("Get tag by id")
    public ResponseEntity<Tag> getTagById(@RequestParam("id") Integer id) throws GenericException {
        return new ResponseEntity<>(tagService.getTagById(id), HttpStatus.OK);
    }

    @GetMapping("/")
    @ApiOperation("Get tag by name")
    public ResponseEntity<Tag> getTagByName(@RequestParam("name") String tagName) throws GenericException {
        return new ResponseEntity<>(tagService.getByTagName(tagName), HttpStatus.OK);
    }
}
