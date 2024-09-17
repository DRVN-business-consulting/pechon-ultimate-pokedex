package dev.nayeoniii.ultimate_pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class PokemonDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_detail);

        // Get data from the intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("pokemon_name");
        int imageResId = intent.getIntExtra("pokemon_image_res_id", -1); // Default value of -1 if not found
        String description = intent.getStringExtra("pokemon_description");


        // Find views
        ImageView pokemonImage = findViewById(R.id.detailPokemonImage);
        TextView pokemonName = findViewById(R.id.detailPokemonName);
        TextView pokemonDescription = findViewById(R.id.detailPokemonDescription);

        // Set data to views
        pokemonName.setText(name);
        pokemonDescription.setText(description);
        pokemonImage.setImageResource(imageResId);
        if (imageResId != -1) { // Check if resource ID is valid
            pokemonImage.setImageResource(imageResId);
        } else {
            pokemonImage.setImageResource(R.drawable.ic_launcher_background); // Ensure you have a default image
        }
    }
}
