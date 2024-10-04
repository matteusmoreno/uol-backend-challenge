package br.com.matteusmoreno.uol_backend_challenge.controller;

import br.com.matteusmoreno.uol_backend_challenge.domain.Player;
import br.com.matteusmoreno.uol_backend_challenge.request.CreatePlayerRequest;
import br.com.matteusmoreno.uol_backend_challenge.response.PlayerDetailsResponse;
import br.com.matteusmoreno.uol_backend_challenge.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/create")
    public ResponseEntity<PlayerDetailsResponse> create(@RequestBody @Valid CreatePlayerRequest request, UriComponentsBuilder uriBuilder) throws IOException {
        Player player = playerService.createPlayer(request);
        URI uri = uriBuilder.path("/players/create/{id}").buildAndExpand(player.getId()).toUri();

        return ResponseEntity.created(uri).body(new PlayerDetailsResponse(player));
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<PlayerDetailsResponse>> findAll(@PageableDefault(sort = "name", size = 10) Pageable pageable) {
        Page<PlayerDetailsResponse> page = playerService.findAllPlayers(pageable);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}
