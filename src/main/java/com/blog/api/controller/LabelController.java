package com.blog.api.controller;

import com.blog.api.repository.model.Label;
import com.blog.api.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelRepository labelRepository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Label createLabel(@RequestBody Label newLabel) {
        return labelRepository.save(newLabel);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<Label> getById(@PathVariable("id") long id) {
        return labelRepository.findById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Label> getAll() {
        return labelRepository.findAll();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public void updateLabel(@RequestBody Label newLabel, @PathVariable Long id) {
        labelRepository.findById(id).map(label -> {
            label.setLabel(newLabel.getLabel());
            return labelRepository.save(label);
        }).orElseGet(() -> {
            newLabel.setId(id);
            return labelRepository.save(newLabel);
        });
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteLabel(@PathVariable Long id) {
        labelRepository.deleteById(id);
    }
}