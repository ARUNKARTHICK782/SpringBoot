package com.example.SpringBoot.Service;

import com.example.SpringBoot.Repository.NotesRepository;
import com.example.SpringBoot.Entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

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
//            notesRepository.updateNote(note.getTitle(),note.getDescription(),id);
        Note noteToBeUpdated =   notesRepository.findById(id).orElse(null);
        noteToBeUpdated.setTitle(note.getTitle());
        noteToBeUpdated.setDescription(note.getDescription());
        notesRepository.save(noteToBeUpdated);
    }

    public List<Note> findByTitleContaining(String term){
        return notesRepository.findByTitleContaining(term);
    }

}
