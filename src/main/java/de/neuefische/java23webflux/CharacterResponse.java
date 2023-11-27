package de.neuefische.java23webflux;

import java.net.http.HttpResponse;
import java.util.List;

public record CharacterResponse(
        ResponseInfo info,
        List<Character> results
) {
}
