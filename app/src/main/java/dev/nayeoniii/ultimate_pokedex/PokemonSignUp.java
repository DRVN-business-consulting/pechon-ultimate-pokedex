package dev.nayeoniii.ultimate_pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import dev.nayeoniii.ultimate_pokedex.api.API;
import dev.nayeoniii.ultimate_pokedex.dto.request.LoginDto;
import dev.nayeoniii.ultimate_pokedex.dto.response.ErrorDto;
import dev.nayeoniii.ultimate_pokedex.dto.response.RefreshTokenDto;
import dev.nayeoniii.ultimate_pokedex.dto.response.SignupDto;
import dev.nayeoniii.ultimate_pokedex.dto.response.UserDto;
import dev.nayeoniii.ultimate_pokedex.prefs.AppPreferences;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonSignUp extends AppCompatActivity {

    Button btnSignupf, btnClear;
    EditText txtName, txtAge, txtAddress, txtUname, txtPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokedex_signup);

        txtName = findViewById(R.id.edtName);
        txtAge = findViewById(R.id.edtAge);
        txtAddress = findViewById(R.id.edtAddr);
        txtUname = findViewById(R.id.edtUName);
        txtPassword = findViewById(R.id.edtPassword);

        btnSignupf = findViewById(R.id.btnSignupf);
        btnClear = findViewById(R.id.btnReset);

        AppPreferences.initialize(this);

        btnSignupf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getName = txtName.getText().toString();
                String getAge = txtAge.getText().toString();
                String getAddress = txtAddress.getText().toString();
                String getUname = txtUname.getText().toString();
                int getPass = Integer.parseInt(txtPassword.getText().toString());

                if (AppPreferences.getInstance().getAccessToken() == null) {
                    signup(getName, getAge, getAddress, getUname, getPass);
                } else {
                    me();
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtName.setText("");
                txtAge.setText("");
                txtAddress.setText("");
                txtUname.setText("");
                txtPassword .setText("");
            }
        });


    }

    private void signup(String getUname, String getPw, String getName,  String getAddr, int getAge) {
        API.userApi()
                .signup(new SignupDto(getUname, getPw, getName, getAddr, getAge))
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
                                    Toast.makeText(PokemonSignUp.this, errorDto.getDetail(), Toast.LENGTH_SHORT).show();
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
                        Log.e("NAYEONIIDEBUG", "Failed to signup", t);
                        Toast.makeText(PokemonSignUp.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void me() {
        API.userApi().u().enqueue(new Callback<SignupDto>() {
            @Override
            public void onResponse(@NonNull Call<SignupDto> call, @NonNull Response<SignupDto> response) {
                if (response.isSuccessful()) {
                    SignupDto signupDto = response.body();
                    if (signupDto != null) {
                        Log.d("NAYEONIIDEBUG", "Username: " + signupDto.getUsername());
                        Toast.makeText(PokemonSignUp.this, "Successfully created user: " + signupDto.getUsername(), Toast.LENGTH_SHORT).show();
                        Intent pkl = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(pkl);
                    }
                } else {
                    ResponseBody errorBody = null;
                    try {
                        errorBody = response.errorBody();
                        if (errorBody != null) {
                            String json = errorBody.string();
                            ErrorDto errorDto = API.gson.fromJson(json, ErrorDto.class);
                            Toast.makeText(PokemonSignUp.this, errorDto.getDetail(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<SignupDto> call, Throwable t) {
                Log.e("NAYEONIIDEBUG", "Failed to fetch user", t);
                Toast.makeText(PokemonSignUp.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
