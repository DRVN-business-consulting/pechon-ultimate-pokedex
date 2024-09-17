package dev.nayeoniii.ultimate_pokedex.dto.response;

import com.google.gson.annotations.SerializedName;

public class SignupDto {
    private final String username;
    private final String password;
    private final String name;
    private final String address;
    private final int age;
    @SerializedName("auth_token")
    private String authToken;

    public SignupDto(String username, String password, String name, String address, int age) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }
}
