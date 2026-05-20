package com.tfgbackend.service;

import com.tfgbackend.model.Tag;
import com.tfgbackend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tr;

    @Autowired
    public TagService(TagRepository tr){
        this.tr = tr;
    }

    public List<Tag> findAll(){
        return this.tr.findAll();
    }

    public Tag findByNAme(String tagName){
        return tr.findByName(tagName);
    }

    public void saveTag (Tag tag){tr.save(tag);}
}
