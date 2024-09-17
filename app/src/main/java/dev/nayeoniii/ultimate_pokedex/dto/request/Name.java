package dev.nayeoniii.ultimate_pokedex.dto.request;

import java.util.List;
import java.util.Map;

public class Name {
    private final Map<String, String> name;

    public Name(Map<String, String> name) {
        this.name = name;
    }

    public Map<String, String> getName() {
        return name;
    }
}


