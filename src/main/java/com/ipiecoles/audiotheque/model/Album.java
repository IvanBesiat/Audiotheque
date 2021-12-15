package com.ipiecoles.audiotheque.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Album {

    //attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    @Size(max = 160)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    @JsonIgnoreProperties("albums")
    private Artist artist;

    //getters
    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public Artist getArtist() {return artist;}

    //setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setArtist(Artist artist) {this.artist = artist;}

    //constructeurs
    public Album(){
    }

    public Album(String title, Artist artist) {
        this.title = title;
        this.artist = artist;
    }

    //methodes

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist=" + artist +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album)) return false;
        Album album = (Album) o;
        return getId().equals(album.getId()) && title.equals(album.title) && getArtist().equals(album.getArtist());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), title, getArtist());
    }
}
