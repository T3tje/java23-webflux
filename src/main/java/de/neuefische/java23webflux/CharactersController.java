package de.neuefische.java23webflux;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;


@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CharactersController {
    private final CharacterService characterService;
    private final String rickAndMortyUri = "https://rickandmortyapi.com/api/character";
    @GetMapping("/characters")
    public List<Character> getCharacters(@RequestParam(required = false) String status) {
        if (status != null) {
            // Wenn der Status-Parameter vorhanden ist, rufe die Methode zum Abrufen von Charakteren mit bestimmtem Status auf
            return characterService.getCharactersByStatus(status);
        } else {
            // Andernfalls rufe die Methode zum Abrufen aller Charaktere auf
            return characterService.getAllCharacters();
        }
    }

    @GetMapping("/characters/{id}")
    public Character getCharacterById(@PathVariable String id) {
        return characterService.getCharacterById(id);
    }

    @GetMapping("/characters/species-statistic")
    private int getStatistics( @RequestParam String species) {
        return characterService.getStatistics(species);
    }
}
