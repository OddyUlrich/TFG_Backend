package com.tfgbackend.controller;

import com.tfgbackend.exception.ResourceNotFoundException;
import com.tfgbackend.model.ExerciseBattery;
import com.tfgbackend.model.Tag;
import com.tfgbackend.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
        try {
            if (auth != null && auth.isAuthenticated()) {
                List<Tag> allTags = tagService.findAll();

                return ResponseEntity.status(HttpStatus.OK).body(allTags);
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
