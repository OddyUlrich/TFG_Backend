package com.tfgbackend.service;

import com.tfgbackend.model.Tag;
import com.tfgbackend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tr;

    @Autowired
    public TagService(TagRepository tr){
        this.tr = tr;
    }

    public Tag createTag(String name){

        Tag tagInDatabase = findByName(name);

        if (tagInDatabase != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Tag newTag = new Tag(name);
        return saveTag(newTag);
    }

    public Tag saveTag (Tag tag){return tr.save(tag);}

    public List<Tag> findAll(){
        return this.tr.findAll();
    }

    public Tag findByName(String tagName){
        return tr.findByName(tagName);
    }

    public List<Tag> findByNameIn (List<String> tagNames){
        return tr.findByNameIn(tagNames);
    }
}
