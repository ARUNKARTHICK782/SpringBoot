package com.example.SpringBoot.Service;

import com.example.SpringBoot.Repository.NotesRepository;
import com.example.SpringBoot.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class NotesService {
    @Autowired
    NotesRepository notesRepository;

    public void addNote(Note note){
        notesRepository.save(note);
    }

    public void deleteNote(Long id){
        notesRepository.deleteById(id);
    }


    public List<Note> showNotes(){
        return notesRepository.findAll();
    }


    public void updateNote(Note note,Long id){
        Note noteToBeUpdated =   notesRepository.findById(id).orElse(null);
        noteToBeUpdated.setTitle(note.getTitle());
        noteToBeUpdated.setDescription(note.getDescription());
        notesRepository.save(noteToBeUpdated);
    }

}
