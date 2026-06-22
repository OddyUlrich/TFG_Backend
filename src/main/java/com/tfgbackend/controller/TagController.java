package com.tfgbackend.controller;

import com.tfgbackend.model.Tag;
import com.tfgbackend.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.security.core.Authentication;


@RestController

@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tag>> getAllTags(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            List<Tag> allTags = tagService.findAll();

            return ResponseEntity.status(HttpStatus.OK).body(allTags);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tag> saveBattery(@RequestBody Tag data, Authentication auth) {

        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Tag newTag = tagService.createTag(data.name());

        return ResponseEntity.status(HttpStatus.CREATED).body(newTag);

    }
}
