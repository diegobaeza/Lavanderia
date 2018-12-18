package cl.baeza.diego.lavanderia.vista;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import cl.baeza.diego.lavanderia.R;
import cl.baeza.diego.lavanderia.controlador.Utilidades;
import cl.baeza.diego.lavanderia.db.ConexionSQLiteHelper;

public class NumeroActivity extends AppCompatActivity implements View.OnClickListener {

    private static final char space = ' ';
    private EditText etTelefono, etCodigo;
    private TextView tvIniNumero;
    private Button btnVerificar;
    private Button btnEnviarCodigo;


    public static final int RC_SIGN_IN = 001;
    private static final String TAG = NumeroActivity.class.getSimpleName();

    private FirebaseAuth mAuth;

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private String verificationid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numero);

        mAuth = FirebaseAuth.getInstance();

        etTelefono = findViewById(R.id.etTelefono);
        etCodigo = findViewById(R.id.etCodigo);
        tvIniNumero = findViewById(R.id.tvIniNumero);
        btnVerificar = findViewById(R.id.btnVerificar);
        btnEnviarCodigo = findViewById(R.id.btnEnviarCodigo);

        btnEnviarCodigo.setOnClickListener(this);
        btnVerificar.setOnClickListener(this);


        etTelefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    final char c = s.charAt(s.length() - 1);
                    if (space == c) {
                        s.delete(s.length() - 1, s.length());
                    }
                }
                // Insert char where needed.
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    char c = s.charAt(s.length() - 1);
                    // Only if its a digit where there should be a space we insert a space
                    if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                        s.insert(s.length() - 1, String.valueOf(space));
                    }
                }
            }
        });


        if (mAuth.getCurrentUser() != null) {
            // User is signed in (getCurrentUser() will be null if not signed in)
            Intent intent = new Intent(NumeroActivity.this, ServiciosActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Toast.makeText(NumeroActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                verificationid = s;
            }
        };
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch(id){
            case R.id.btnEnviarCodigo:
                if (!validatePhoneNumberAndCode()) {
                    return;
                }
                startPhoneNumberVerification("+569" + etTelefono.getText().toString());
                break;

            case R.id.btnVerificar:
                if (!validateSMSCode()) {
                    return;
                }

                verifyPhoneNumberWithCode(verificationid, etCodigo.getText().toString());
                break;
        }

    }


    private void startPhoneNumberVerification(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks


    }


    private void verifyPhoneNumberWithCode(String verificationId, String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();


                            Intent i = new Intent(NumeroActivity.this, ServiciosActivity.class);
                            String telefono = "+569" + etTelefono.getText().toString();

                            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(NumeroActivity.this,"usuarios",null,1);
                            SQLiteDatabase db = conn.getWritableDatabase();

                            ContentValues values = new ContentValues();
                            values.put(Utilidades.CAMPO_ID,1);
                            values.put(Utilidades.CAMPO_TELEFONO,telefono);

                            db.insert(Utilidades.TABLA_USUARIO,Utilidades.CAMPO_ID,values);

                            startActivity(i);

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid

                                etCodigo.setError("Codigo invalido.");

                            }

                        }
                    }
                });
    }


    private boolean validatePhoneNumberAndCode() {
        String phoneNumber = etTelefono.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            etTelefono.setError("Numero de telefono invalido.");
            return false;
        }


        return true;
    }

    private boolean validateSMSCode(){
        String code = etCodigo.getText().toString();
        if (TextUtils.isEmpty(code)) {
            etCodigo.setError("Codigo incorrecto.");
            return false;
        }

        return true;
    }


}
