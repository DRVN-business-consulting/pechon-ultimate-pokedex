package dev.nayeoniii.ultimate_pokedex.dto.request;

import java.util.List;
import java.util.Map;

public class PokemonDto {
    private final Map<String, String> name;   // Map for multiple language names
    private final List<String> type;          // List for type which is an array of strings
    private final String description;         // Description is a string
    private final Map<String, String> image;

    public PokemonDto(Map<String, String> name, List<String> type, String description, Map<String, String> image) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.image = image;
    }

    public String getName(String language) {
        return name.getOrDefault(language, "Name not available");
    }

    public List<String> getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getImage() {
        return image;
    }
}

