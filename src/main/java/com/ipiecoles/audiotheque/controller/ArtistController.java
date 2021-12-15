package com.ipiecoles.audiotheque.controller;

import com.ipiecoles.audiotheque.model.Artist;
import com.ipiecoles.audiotheque.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/artists")
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Optional<Artist> getArtistById(@PathVariable Long id){
        if (artistRepository.findById(id).isEmpty()){
            throw new EntityNotFoundException("L'artiste d'identifiant " + id + " n'a pas été trouvé.");
        }
        return artistRepository.findById(id);
    }
}
