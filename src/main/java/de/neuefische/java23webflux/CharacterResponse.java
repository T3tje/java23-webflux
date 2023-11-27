package de.neuefische.java23webflux;

import java.util.List;

public record CharacterResponse(
        List<Character> results
) {
}
