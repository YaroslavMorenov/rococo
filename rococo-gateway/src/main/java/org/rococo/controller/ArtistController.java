package org.rococo.controller;

import org.rococo.model.ArtistJson;
import org.rococo.service.api.RestArtistClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {

    private final RestArtistClient artistClient;

    @Autowired
    public ArtistController(RestArtistClient artistClient) {
        this.artistClient = artistClient;
    }

    @GetMapping()
    public Page<ArtistJson> getAll(@RequestParam(required = false) String name,
                                   @PageableDefault Pageable pageable) {
        return artistClient.getAll(name, pageable);
    }

    @GetMapping("/{id}")
    public ArtistJson findArtistById(@PathVariable("id") String id) {
        return artistClient.findArtistById(id);
    }

    @PatchMapping()
    public ArtistJson updateArtist(@RequestBody ArtistJson artist) {
        return artistClient.update(artist);
    }

    @PostMapping()
    public ArtistJson addArtist(@RequestBody ArtistJson artist) {
        return artistClient.add(artist);
    }
}
