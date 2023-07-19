package com.example.SpringBoot.controller;

import com.example.SpringBoot.Service.NotesService;
import com.example.SpringBoot.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

    @Autowired
    NotesService notesService;

    @GetMapping
    public List<Note> get(){
        return notesService.showNotes();
    }


    @GetMapping(value = "/titleContains")
    public List<Note> getByTitleContaining(@RequestParam String term){
        return notesService.findByTitleContaining(term);
    }

    @PostMapping()
    public String post(@RequestBody Note note){
        System.out.println(note);
        notesService.addNote(note);
        return "In post";
    }

    @DeleteMapping
    public void delete(@RequestParam Long id){
        notesService.deleteNote(id);
    }

    @PutMapping
    public void updateNote(@RequestParam Long id,@RequestBody Note note){
        notesService.updateNote(note,id);
    }

}