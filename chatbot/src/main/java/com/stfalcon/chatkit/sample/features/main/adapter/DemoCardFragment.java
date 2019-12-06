package com.stfalcon.chatkit.sample.features.main.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.api.ChatbotWebService;
import com.stfalcon.chatkit.sample.api.model.Status;
import com.stfalcon.chatkit.sample.api.model.Token;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Карта начальной активити где располагается
 * ВХОД и РЕГИСТРАЦИЯ
 * Created by mikhail
 */
public class DemoCardFragment extends Fragment
        implements View.OnClickListener {

    private static final String ARG_ID = "id";
    private static final String ARG_TITLE = "title";
    private static final String ARG_BTN_TITLE = "bnt_title";
    private static final String ARG_INPUT = "input";

    private int id;
    private String title, btn_title;
    private boolean input;
    private OnActionListener actionListener;

    public DemoCardFragment() {

    }

    public static DemoCardFragment newInstance(int id, String title, String description,
                                               boolean input) {
        DemoCardFragment fragment = new DemoCardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_BTN_TITLE, description);
        args.putBoolean(ARG_INPUT, input);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.id = getArguments().getInt(ARG_ID);
            this.title = getArguments().getString(ARG_TITLE);
            this.btn_title = getArguments().getString(ARG_BTN_TITLE);
            this.input = getArguments().getBoolean(ARG_INPUT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_demo_card, container, false);

        TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        Button btnLogin = (Button) v.findViewById(R.id.btn_login);
        if (!input) {
            EditText etLogin = (EditText) v.findViewById(R.id.etLogin);
            EditText etPassword = (EditText) v.findViewById(R.id.etPassword);
            etLogin.setVisibility(View.GONE);
            etPassword.setVisibility(View.GONE);
        }

        tvTitle.setText(title);
        btnLogin.setText(btn_title);
        btnLogin.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
            switch (id) {
                case MainActivityPagerAdapter.ID_ANONYMOUS:
                    anonymous_login();
                    break;
                case MainActivityPagerAdapter.ID_SIGNIN:
                    login_by_name();
                    break;
                case MainActivityPagerAdapter.ID_SIGNUP:
                    signup();
                    break;

            }
        }
    }

    private String getToken() {
        return null;
    }

    private void anonymous_login() {


        onAction();
    }

    private void login_by_name() {

    }

    private void signin(final String login, final String password) {
        ChatbotWebService
                .getInstance()
                .getChatbotAPI()
                .login(login, password)
                .enqueue(new Callback<Token>() {
                             @Override
                             public void onResponse(Call<Token> call, Response<Token> response) {
                                 Token token = response.body();
                                 signin(token.getToken());
                             }

                             @Override
                             public void onFailure(Call<Token> call, Throwable t) {
                                 Toast.makeText(getContext(), "Дружище! Тут какая-та ошибка!", Toast.LENGTH_LONG).show();
                             }
                         }
                );
    }

    private void signin(String token) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.saved_token), token);
        editor.commit();
        actionListener.onAction(token);

    }



    private void signup() {
        EditText etLogin = (EditText) getView().findViewById(R.id.etLogin);
        EditText etPassword = (EditText) getView().findViewById(R.id.etPassword);
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();
        singup(login, password);

    }

    private void singup(final String login, final String password) {
        ChatbotWebService
                .getInstance()
                .getChatbotAPI()
                .registration(login, password)
                .enqueue(new Callback<Status>() {
                             @Override
                             public void onResponse(Call<Status> call, Response<Status> response) {
                                 Status status = response.body();
                                 if (status.getStatus().equals("ok")) {
                                     signin(login, password);
                                 }
                             }

                             @Override
                             public void onFailure(Call<Status> call, Throwable t) {
                                 Toast.makeText(getContext(), "Дружище! Тут какая-та ошибка!", Toast.LENGTH_LONG).show();
                             }
                         }
                );
    }

    public void onAction() {
        if (actionListener != null) {
            actionListener.onAction("");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActionListener) {
            actionListener = (OnActionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnActionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        actionListener = null;
    }

    public interface OnActionListener {
        void onAction(String token);
    }
}