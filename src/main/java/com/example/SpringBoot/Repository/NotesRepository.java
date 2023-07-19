package com.example.SpringBoot.Repository;

import com.example.SpringBoot.entity.Note;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@org.springframework.stereotype.Repository
public interface NotesRepository extends JpaRepository<Note,Long> {
    public List<Note> findByTitleContaining(String searchTerm);

//    @Modifying
//    @Transactional
//    @Query("update Notes note set note.title = ?1,note.description = ?2 where note.id = ?3")
//    public default void updateNote(String title,String description,Long id){
//        System.out.println(title+description+id);
//        System.out.println("In updateNote");
//    }

}
