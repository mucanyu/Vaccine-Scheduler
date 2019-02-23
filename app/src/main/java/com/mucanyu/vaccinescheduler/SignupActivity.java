package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sourcey.materiallogindemo.DatabaseHelper.DAO;
import com.sourcey.materiallogindemo.DatabaseHelper.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_tcNo) EditText _tcNoText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _hasAccountAlreadyLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _hasAccountAlreadyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Hesap oluşturuluyor...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String tcNo = _tcNoText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implementing my own signup logic here.
        DAO dao = new DAO(this);
        dao.openDB();

        User newUser = new User(name, email, tcNo, password);
        dao.createUser(newUser);
        Log.d("db", "Kullanici olusturuldu!");
        dao.closeDB();
        // TODO: ^--- Signup ends ---^

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onSignupSuccess();

                        progressDialog.dismiss();
                    }
                }, 1000);
    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);

        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Kayıt başarısız", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String tcNo = _tcNoText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 2) {
            _nameText.setError("İsim az 3 karakter uzunluğunda olmalıdır");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        // TODO: Şimdilik e-mail validasyonu kapalı
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Geçerli bir mail adresi giriniz");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        // TODO: Şimdilik TC kimlik numarası validasyonu kapalı. Test aşaması bitince aktifteştir
        if (tcNo.isEmpty() || tcNo.length() != 11 /* || !isTCKNCorrect(tcNo) */) {
            _tcNoText.setError("Geçerli bir TC numarası giriniz");
            valid = false;
        } else {
            _tcNoText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 18) {
            _passwordText.setError("Parolanız 4-18 karakter uzunluğunda olmalıdır");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Parolalar eşleşmiyor");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }

    /*
    **  TC Kimlik numarası doğrulama algoritması
    */
    private boolean isTCKNCorrect(String id) {
        if (id == null) return false;

        if (id.length() != 11) return false;

        char[] chars = id.toCharArray();
        int[] a = new int[11];

        for(int i=0; i<11; i++) {
            a[i] = chars[i] - '0';
        }

        if(a[0] == 0) return false;
        if(a[10] % 2 == 1) return false;

        if(((a[0] + a[2] + a[4] + a[6] + a[8]) * 7 - (a[1] + a[3] + a[5] + a[7])) % 10 != a[9]) return false;

        if((a[0] + a[1] + a[2] + a[3] + a[4] + a[5] + a[6] + a[7] + a[8] + a[9]) % 10 != a[10]) return false;

        return true;
    }
}