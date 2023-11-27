package de.neuefische.java23webflux;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;


@RequestMapping("/api")
@RestController
public class CharactersController {
    private final String rickAndMortyUri = "https://rickandmortyapi.com/api/character";
    @GetMapping("/characters")
    public List<Character> getCharacters(@RequestParam(required = false) String status) {
        if (status != null) {
            // Wenn der Status-Parameter vorhanden ist, rufe die Methode zum Abrufen von Charakteren mit bestimmtem Status auf
            return getCharactersByStatus(status);
        } else {
            // Andernfalls rufe die Methode zum Abrufen aller Charaktere auf
            CharacterResponse response = Objects.requireNonNull(
                    WebClient.create()
                            .get()
                            .uri(rickAndMortyUri)
                            .retrieve()
                            .toEntity(CharacterResponse.class)
                            .block()
            ).getBody();

            assert response != null;
            return response.results();
        }
    }

    @GetMapping("/characters/{id}")
    public Character getCharacterById(@PathVariable String id) {
        return Objects.requireNonNull(
                WebClient.create()
                        .get()
                        .uri(rickAndMortyUri + "/" + id)
                        .retrieve()
                        .toEntity(Character.class)
                        .block()
        ).getBody();
    }

    private List<Character> getCharactersByStatus(String status) {
        CharacterResponse response = Objects.requireNonNull(
                WebClient.create()
                        .get()
                        .uri(rickAndMortyUri + "?status=" + status)
                        .retrieve()
                        .toEntity(CharacterResponse.class)
                        .block()
        ).getBody();

        return response.results();
    }


    @GetMapping("/characters/species-statistic")
    private int getStatistics( @RequestParam String species) {
        CharacterResponse response = Objects.requireNonNull(
                WebClient.create()
                        .get()
                        .uri(rickAndMortyUri+"?species="+species)
                        .retrieve()
                        .toEntity(CharacterResponse.class)
                        .block()
        ).getBody();

        return response.info().count();
    }
}
