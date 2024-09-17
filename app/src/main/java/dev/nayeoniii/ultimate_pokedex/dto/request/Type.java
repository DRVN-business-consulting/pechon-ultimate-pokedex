package dev.nayeoniii.ultimate_pokedex.dto.request;

public class Type {
    private final String type;
    private final String description;

    public Type(String type, String description){
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
