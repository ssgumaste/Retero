package com.example.retero.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retero.R;
import com.example.retero.constants.Constant;
import com.example.retero.extras.AppPreference;
import com.example.retero.fragment.LoginFragment;
import com.example.retero.fragment.ProfileFragment;
import com.example.retero.fragment.RegistrationFragment;
import com.example.retero.services.MyInterface;
import com.example.retero.services.RetrofitClient;
import com.example.retero.services.ServiceApi;

//import chandan.prasad.myretrofitapplication.R;
//import chandan.prasad.myretrofitapplication.constants.Constant;
//import chandan.prasad.myretrofitapplication.extras.AppPreference;
//import chandan.prasad.myretrofitapplication.fragment.LoginFragment;
//import chandan.prasad.myretrofitapplication.fragment.ProfileFragment;
//import chandan.prasad.myretrofitapplication.fragment.RegistrationFragment;
//import chandan.prasad.myretrofitapplication.services.MyInterface;
//import chandan.prasad.myretrofitapplication.services.RetrofitClient;
//import chandan.prasad.myretrofitapplication.services.ServiceApi;

public class MainActivity extends AppCompatActivity implements MyInterface {
    public static AppPreference appPreference;
    public static String c_date;
    FrameLayout container_layout;
    public static ServiceApi serviceApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container_layout = findViewById(R.id.fragment_container);
        appPreference = new AppPreference(this);

        //Log.e("created_at: ", c_date);

        serviceApi = RetrofitClient.getApiClient(Constant.baseUrl.BASE_URL).create(ServiceApi.class);

        if (container_layout != null) {
            if (savedInstanceState != null) {
                return;
            }

            //check login status from sharedPreference
            if (appPreference.getLoginStatus()) {
                //when true
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, new ProfileFragment())
                        .commit();
            } else {
                // when get false
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, new LoginFragment())
                        .commit();
            }
        }

    } // ending onCreate


    // overridden from MyInterface
    @Override
    public void register() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new RegistrationFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void login(String name, String email, String created_at) {
        appPreference.setDisplayName(name);
        appPreference.setDisplayEmail(email);
        appPreference.setCreDate(created_at);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ProfileFragment())
                .commit();
    }

    @Override
    public void logout() {
        appPreference.setLoginStatus(false);
        appPreference.setDisplayName("Name");
        appPreference.setDisplayEmail("Email");
        appPreference.setCreDate("DATE");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new LoginFragment())
                .commit();
    }
}
