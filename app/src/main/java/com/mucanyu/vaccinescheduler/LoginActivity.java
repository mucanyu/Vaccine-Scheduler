package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sourcey.materiallogindemo.DatabaseHelper.DAO;
import com.sourcey.materiallogindemo.LoggedUser.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login function has started");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Giriş yapılıyor...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Login authentication

        DAO dao = new DAO(this);
        dao.openDB();
        final int userID = dao.validateLogin(email, password);
        dao.closeDB();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (userID != -1) {
                            Log.d("db", "Başarıyla giriş yapıldı");

                            onLoginSuccess(userID);
                        } else {
                            Log.d("db", "Giriş başarısız");
                            onLoginFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 1150
        );
        // TODO: ^--- Login logic ends here ---^
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_SIGNUP) {
//            if (resultCode == RESULT_OK) {
//                // TODO: Implement successful signup logic here
//                // By default we just finish the Activity and log them in automatically
//                this.finish();
//            }
//        }
//    }

    @Override
    public void onBackPressed() {
        // MainActivity'e gitmesini engellemek icin
        moveTaskToBack(true);
    }

    public void onLoginSuccess(int userID) {
        // TODO: Login başarılıysa .....
        _loginButton.setEnabled(true);

        SharedPref.context = getApplicationContext();
        SharedPref.getMyInstance().setId(userID);

//        Log.w("SharedPref", String.valueOf(SharedPref.getMyInstance().getId()));

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        finish();
    }

    public void onLoginFailed() {
        _emailText.setError("Giriş başarısız. Tekrar kontrol edin");
        _passwordText.setError("Giriş başarısız. Tekrar kontrol edin");
//        Toast.makeText(getBaseContext(), "Giriş başarısız!", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Geçerli bir mail adresi giriniz");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 18) {
            _passwordText.setError("Parolayı boş bırakamazsınız");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
