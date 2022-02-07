package com.ipiecoles.audiotheque.controller;

import com.ipiecoles.audiotheque.exception.ArtistException;
import com.ipiecoles.audiotheque.model.Artist;
import com.ipiecoles.audiotheque.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
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
    public Optional<Artist> getArtistById(@PathVariable Long id) {
        if (artistRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("L'artiste d'identifiant " + id + " n'a pas été trouvé.");
        }
        return artistRepository.findById(id);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            params = "name"
    )
    public Page<Artist> getArtistsByNom(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "name") String sortProperty,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection,
            @RequestParam String name) throws ArtistException {
        name = "%" + name + "%";
        Page<Artist> search = artistRepository.findByName(name, PageRequest.of(page, size, sortDirection, sortProperty));
        if (search != null) {
            return search;
        }
        throw new EntityNotFoundException("Aucun artist ne se nomme ainsi ou ne contient cette suite de caractère.");
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Page<Artist> getAllArtistSorted(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "name") String sortProperty,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection
    ) {
        //sortProperty n'est pas un attribut d'Employé => 400 BAD REQUEST
        List<String> properties = Arrays.asList("id", "name");
        if (!properties.contains(sortProperty))
            throw new IllegalArgumentException("La propriété de tri " + sortProperty + "est incorrecte.");
        //Valeurs négatives pour page et size => 400 BAD REQUEST
        if (page < 0 || size <= 0)
            throw new IllegalArgumentException("Les arguments page et size doivent être positif.");
        //contraindre size <= 50 => 400 BAD REQUEST
        if (size > 50)
            throw new IllegalArgumentException("L'argument size doit être inférieur ou égale à 50.");
        //page et size cohérents par rapport au nombre de lignes de la table => 400 BAD REQUEST
        if (page * size > artistRepository.count())
            throw new IllegalArgumentException("Les arguments page et size doivent représenter des valeurs existantes.");
        return artistRepository.findAll(PageRequest.of(page, size, sortDirection, sortProperty));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Artist insertArtist(@RequestBody Artist artist) {

        return artistRepository.save(artist);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{Id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Artist updateArtist(@RequestBody Artist artist){
        return artistRepository.save(artist);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{id}"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable Long id){

        artistRepository.deleteById(id);
    }
}