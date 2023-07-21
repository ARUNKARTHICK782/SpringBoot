package com.example.SpringBoot.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "Notes")
public class Note {
    @Id
    @SequenceGenerator(
            name = "noteIdSequence",
            allocationSize = 1,
            sequenceName = "noteIdSequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "noteIdSequence"
    )
    private int id;
    private String title;
    private String description;


    public Note(){

    }

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    @Override
    public String toString() {
        return "Note{" +
                " title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
