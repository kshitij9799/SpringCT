package com.example.springct1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.springct1.data.Main;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import kotlin.jvm.Throws;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginFragment extends Fragment {

    public Button loginButton;
    public EditText userName,password;
    public TextView loginError;

    public MainActivity mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginButton= view.findViewById(R.id.login_button);
        userName = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();

                JSONObject jsonObject = new JSONObject();

                if (userName.getText() != null && password.getText() != null) {
                    try {

                        jsonObject.put("email",userName.getText());
                        jsonObject.put("password",password.getText());

                    } catch (Exception e) {}
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
                    Request request = new Request.Builder()
                            .url("https://reqres.in/api/login")
                            .post(requestBody).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if (response.code() == 200) {
                                setFragment(new HomeFragment());
                            }

                        }
                    });
                }
            }
        });

        return view;
    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view_tag,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}