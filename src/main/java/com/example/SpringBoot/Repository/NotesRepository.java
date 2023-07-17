package com.example.SpringBoot.Repository;

import com.example.SpringBoot.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface NotesRepository extends JpaRepository<Note,Long> {

}
