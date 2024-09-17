package dev.nayeoniii.ultimate_pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dev.nayeoniii.ultimate_pokedex.api.API;
import dev.nayeoniii.ultimate_pokedex.dto.request.LoginDto;
import dev.nayeoniii.ultimate_pokedex.dto.request.Name;
import dev.nayeoniii.ultimate_pokedex.dto.request.PokemonDto;
import dev.nayeoniii.ultimate_pokedex.dto.response.ErrorDto;
import dev.nayeoniii.ultimate_pokedex.dto.response.RefreshTokenDto;
import dev.nayeoniii.ultimate_pokedex.dto.response.UserDto;
import dev.nayeoniii.ultimate_pokedex.prefs.AppPreferences;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class PokemonHomepage extends AppCompatActivity {
    Button logout;
    TextView txtWelcome;

    private RecyclerView recyclerView;
    private PokemonAdapter adapter;
    private List<Pokemon> pokemonList;

    String getName, getDescription, getImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokedex_homepage);

        recyclerView = findViewById(R.id.recyclerListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        logout = findViewById(R.id.btnLogout);
        txtWelcome = findViewById(R.id.txtUserWelcome);

        Intent intent = getIntent();

        txtWelcome.setText("Welcome " + intent.getStringExtra("Username"));


        x();


        // Initialize data
        pokemonList = new ArrayList<>();
        pokemonList.add(new Pokemon("Bulbasaur", R.drawable.bulbasaur, "Bulbasaur is an Grass-Poison Type Pokémon.")); // Ensure these images are in res/drawable
        pokemonList.add(new Pokemon("Charmander", R.drawable.charmander,"Charmander is an Fire Type Pokémon."));
        pokemonList.add(new Pokemon("Squirtle", R.drawable.squirtle,"Squirtle is an Water Type Pokémon."));

        // Set adapter
        adapter = new PokemonAdapter(this, pokemonList);
        recyclerView.setAdapter(adapter);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPreferences.getInstance().setAccessToken(null);
                Intent x = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(x);
            }
        });

    }

    private void x() {
        API.userApi().x().enqueue(new Callback<PokemonDto>() {
            @Override
            public void onResponse(@NonNull Call<PokemonDto> call, @NonNull Response<PokemonDto> response) {
                if (response.isSuccessful()) {
                    PokemonDto pokemonDto = response.body();
                    if (pokemonDto != null) {

//                        pokemonList.add(new )

                        Log.d("NAYEONIIDEBUG", "description: " + pokemonDto.getDescription());
//                        getName = pokemonDto.getName("english");
//                        getDescription = pokemonDto.getDescription();
//                        pokemonList.add(new Pokemon(pokemonDto.getName("english"), R.drawable.bulbasaur, pokemonDto.getDescription()));
//                        pokemonList.add(new Pokemon("Bulbasaur", R.drawable.bulbasaur, "Bulbasaur is an Grass-Poison Type Pokémon.")); // Ensure these images are in res/drawable
////                        Toast.makeText(MainActivity.this, "Hello, " + userDto.getUsername(), Toast.LENGTH_SHORT).show();
//                        Intent pkh = new Intent(getApplicationContext(), PokemonHomepage.class);
//                        pkh.putExtra("Username", userDto.getUsername());
//                        startActivity(pkh);
                    }
                } else {
                    ResponseBody errorBody = null;
                    try {
                        errorBody = response.errorBody();
                        if (errorBody != null) {
                            String json = errorBody.string();
                            ErrorDto errorDto = API.gson.fromJson(json, ErrorDto.class);
                            Toast.makeText(PokemonHomepage.this, errorDto.getDetail(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (errorBody != null) {
                            errorBody.close();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PokemonDto> call, Throwable t) {
                Log.e("NAYEONIIDEBUG", "Failed to fetch pokemon", t);
                Toast.makeText(PokemonHomepage.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), PokemonHomepage.class);
        startActivity(i);
    }
}
