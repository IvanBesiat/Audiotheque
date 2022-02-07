package com.ipiecoles.audiotheque.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(max = 120)
    private String name;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("artist")
    private Set<Album> albums = new HashSet<>();

    //getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public Set<Album> getAlbums() {return albums;}

    //setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAlbums(HashSet<Album> albums) { this.albums = albums; }

    //constructeurs
    public Artist(){

    }

    public Artist(String nom,HashSet albums){
        this.name = name;
        this.albums = albums;
    }

    //methodes

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", albums=" + albums +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist)) return false;
        Artist artist = (Artist) o;
        return getId().equals(artist.getId()) && getName().equals(artist.getName()) && albums.equals(artist.albums);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
