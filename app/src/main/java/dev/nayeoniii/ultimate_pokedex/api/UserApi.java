package dev.nayeoniii.ultimate_pokedex.api;

import dev.nayeoniii.ultimate_pokedex.dto.request.LoginDto;
import dev.nayeoniii.ultimate_pokedex.dto.request.PokemonDto;
import dev.nayeoniii.ultimate_pokedex.dto.response.RefreshTokenDto;
import dev.nayeoniii.ultimate_pokedex.dto.response.SignupDto;
import dev.nayeoniii.ultimate_pokedex.dto.response.UserDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface UserApi {

    @POST("login")
    Call<RefreshTokenDto> login(@Body LoginDto user);

    @POST("signup")
    Call<RefreshTokenDto> signup(@Body SignupDto user);

    @POST("refresh-token")
    Call<RefreshTokenDto> refreshToken();

    @GET("user/me")
    Call<UserDto> me();

    @GET("user/me")
    Call<SignupDto> u();

    @GET("pokemon/1")
    Call<PokemonDto> x();

    @PATCH("user/me")
    Call<UserDto> updateMe();

}
