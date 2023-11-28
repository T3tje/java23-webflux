package de.neuefische.java23webflux;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Service
public class CharacterService {
    private final WebClient webClient;

    public CharacterService(@Value("${character.base.url}") String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    public List<Character> getAllCharacters (){
        CharacterResponse response = Objects.requireNonNull(
                webClient
                        .get()
                        .uri("/")
                        .retrieve()
                        .toEntity(CharacterResponse.class)
                        .block()
        ).getBody();

        assert response != null;
        return response.results();
    }

    public List<Character> getCharactersByStatus(String status) {
        CharacterResponse response = Objects.requireNonNull(
                webClient
                        .get()
                        .uri( "/?status=" + status)
                        .retrieve()
                        .toEntity(CharacterResponse.class)
                        .block()
        ).getBody();

        return response.results();
    }

    public Character getCharacterById(String id) {
        return Objects.requireNonNull(
                webClient
                        .get()
                        .uri("/" + id)
                        .retrieve()
                        .toEntity(Character.class)
                        .block()
        ).getBody();
    }

    public int getStatistics(String species) {
        CharacterResponse response = Objects.requireNonNull(
                WebClient.create()
                        .get()
                        .uri("/?species="+species)
                        .retrieve()
                        .toEntity(CharacterResponse.class)
                        .block()
        ).getBody();

        return response.info().count();
    }
}
