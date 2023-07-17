package com.example.SpringBoot.notes;

import com.example.SpringBoot.Service.NotesService;
import com.example.SpringBoot.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
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
