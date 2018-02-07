package com.cursoandroid.wellington.projetoteste.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.cursoandroid.wellington.projetoteste.R;
import com.cursoandroid.wellington.projetoteste.data.SessionHandler;
import com.cursoandroid.wellington.projetoteste.model.User;
import com.cursoandroid.wellington.projetoteste.web.WebTaskLogin;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by usuario on 31/01/18.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private ProgressDialog pd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(this);

        Log.d("LIFECYCLE", "CRIOU");
    }

    @Override
    protected void onStart() {
        super.onStart();
        hideDialog();
        Log.d("LIFECYCLE", "INICIOU");
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LIFECYCLE", "TUDO CERTO. RODANDO");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LIFECYCLE", "PAUSOU");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LIFECYCLE", "PAROU");
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("LIFECYCLE", "REINICIOU");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LIFECYCLE", "MORREU");
    }

    public void showDialog(){
        pd = new ProgressDialog(this);
        pd.setMessage("Carregando");
        pd.show();
    }

    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm =
                    (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View v) {

        hideKeyboard();

        EditText fieldEmail = findViewById(R.id.field_email);
        String email = fieldEmail.getText().toString();

        EditText fieldPassword = findViewById(R.id.field_password);
        String password = fieldPassword.getText().toString();

       if(isFieldValidated(email, password)){

           WebTaskLogin taskLogin = new WebTaskLogin(this, email, password);
           taskLogin.execute();
       }

    }

    @Subscribe
    public void onError(Error error){
        failedLogin(error.getMessage());
    }

    @Subscribe
    public void onUser(User user){
        SessionHandler sessionHandler = new SessionHandler();
        sessionHandler.saveSession(user, this);
        loginSuccess();
    }


    public void failedLogin(String errorMessaage){
        hideDialog();
        Snackbar.make(getCurrentFocus(), errorMessaage, Snackbar.LENGTH_LONG).show();
    }

    private void loginSuccess() {
        hideDialog();
        finish();
    }


    public void hideDialog(){
        if(pd != null){
            pd.dismiss();
        }
    }

    public boolean isFieldValidated(String text, String password){
        if("".equals(text)){
            Snackbar.make(getCurrentFocus(), R.string.error_field_email, Snackbar.LENGTH_LONG).show();
           return false;
       }

       if("".equals(password)){
           Snackbar.make(getCurrentFocus(), R.string.error_field_password, Snackbar.LENGTH_LONG).show();
           return false;
       }

       return true;
    }
}
