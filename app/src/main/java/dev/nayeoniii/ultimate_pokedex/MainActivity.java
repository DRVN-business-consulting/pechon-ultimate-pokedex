package dev.nayeoniii.ultimate_pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import dev.nayeoniii.ultimate_pokedex.api.API;
import dev.nayeoniii.ultimate_pokedex.dto.request.LoginDto;

import dev.nayeoniii.ultimate_pokedex.dto.response.ErrorDto;
import dev.nayeoniii.ultimate_pokedex.dto.response.RefreshTokenDto;
import dev.nayeoniii.ultimate_pokedex.dto.response.UserDto;
import dev.nayeoniii.ultimate_pokedex.prefs.AppPreferences;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;
    EditText uname, password;
    TextView txtsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pokedex_login);

        btnLogin = findViewById(R.id.btnLogin);
        txtsignup = findViewById(R.id.tvSignup);
        uname = findViewById(R.id.utUname);
        password = findViewById(R.id.utPass);

//        Intent pkt = new Intent(getApplicationContext(), TrainerHomepage.class);

        Intent pksu = new Intent(getApplicationContext(), PokemonSignUp.class);


        AppPreferences.initialize(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the values from EditText
                String username = uname.getText().toString();
                String userpass = password.getText().toString();


                if (AppPreferences.getInstance().getAccessToken() == null) {
                    login(username,userpass);
                } else {
                    me();
                }

                // Check credentials
//                if (username.equals("Nayeoniii") && userpass.equals("123")) {
//                    Toast.makeText(MainActivity.this, "Welcome " + username, Toast.LENGTH_SHORT).show();
//                    startActivity(pkh);
//                } else {
//                    Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(pksu);
            }
        });


    }

    private void login(String getUname, String getPw) {
        API.userApi()
                .login(new LoginDto(getUname, getPw))
                .enqueue(new Callback<RefreshTokenDto>() {
                    @Override
                    public void onResponse(@NonNull Call<RefreshTokenDto> call, @NonNull Response<RefreshTokenDto> response) {
                        if (response.isSuccessful()) {
                            RefreshTokenDto refreshTokenDto = response.body();
                            if (refreshTokenDto != null) {
                                Log.d("NAYEONIIDEBUG", "Access Token: " + refreshTokenDto.getAccessToken());
                                Log.d("NAYEONIIDEBUG", "Token Type: " + refreshTokenDto.getTokenType());
                                AppPreferences.getInstance().setAccessToken(refreshTokenDto.getAccessToken());
                                me();
                            }
                        } else {
                            ResponseBody errorBody = null;
                            try {
                                errorBody = response.errorBody();
                                if (errorBody != null) {
                                    String json = errorBody.string();
                                    ErrorDto errorDto = API.gson.fromJson(json, ErrorDto.class);
                                    Toast.makeText(MainActivity.this, errorDto.getDetail(), Toast.LENGTH_SHORT).show();
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
                    public void onFailure(Call<RefreshTokenDto> call, Throwable t) {
                        Log.e("NAYEONIIDEBUG", "Failed to login", t);
                        Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void me() {
        API.userApi().me().enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(@NonNull Call<UserDto> call, @NonNull Response<UserDto> response) {
                if (response.isSuccessful()) {
                    UserDto userDto = response.body();
                    if (userDto != null) {
                        Log.d("NAYEONIIDEBUG", "Username: " + userDto.getUsername());
//                        Toast.makeText(MainActivity.this, "Hello, " + userDto.getUsername(), Toast.LENGTH_SHORT).show();
                        Intent pkh = new Intent(getApplicationContext(), PokemonHomepage.class);
                        pkh.putExtra("Username", userDto.getUsername());
                        startActivity(pkh);
                    }
                } else {
                    ResponseBody errorBody = null;
                    try {
                        errorBody = response.errorBody();
                        if (errorBody != null) {
                            String json = errorBody.string();
                            ErrorDto errorDto = API.gson.fromJson(json, ErrorDto.class);
                            Toast.makeText(MainActivity.this, errorDto.getDetail(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<UserDto> call, Throwable t) {
                Log.e("NAYEONIIDEBUG", "Failed to fetch user", t);
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}