package com.example.vibraniumtutor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class Login_Activity extends AppCompatActivity {

    TextView txtlogin;
    EditText emailLogin, passLogin;
    Button btnLogin;

    FirebaseAuth mAuth;
    FirebaseFirestore firestore;

    ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLogin = findViewById(R.id.email_login);
        passLogin = findViewById(R.id.pass_login);
        btnLogin = findViewById(R.id.btn_login);

        mAuth = FirebaseAuth.getInstance();

        firestore = FirebaseFirestore.getInstance();

        btnLogin.setOnClickListener(v -> {
            loginuser();
        });

        txtlogin = findViewById(R.id.txt_login);

        txtlogin.setOnClickListener(v -> {

            Intent intent = new Intent(Login_Activity.this, Register_Activity.class);
            startActivity(intent);
        });

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

    }

    private void loginuser() {
        mProgress.show();

        String email = emailLogin.getText().toString();
        String pass = passLogin.getText().toString();


        if (TextUtils.isEmpty(email)){
            emailLogin.setError("email can not be empity");
            emailLogin.requestFocus();
        } else if (TextUtils.isEmpty(pass)){
            passLogin.setError("password field can not be empity");
            passLogin.requestFocus();
        }else {
            mAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    CheckIsadmin(authResult.getUser().getUid());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    mProgress.dismiss();
                    Toast.makeText(getApplicationContext(), "error"+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void CheckIsadmin(String uid) {
        DocumentReference reference = firestore.collection("Users").document(uid);
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess :" +documentSnapshot.getData());
                if (documentSnapshot.getString("isTutor") != null){
                    finish();
                    Toast.makeText(Login_Activity.this,"login tutor", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), drawer_home_page.class));

                }else if (documentSnapshot.getString("isTutees") != null){
                    Toast.makeText(Login_Activity.this,"Login tutees", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Register_Activity.class));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                mProgress.dismiss();
                Toast.makeText(Login_Activity.this,"error occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(), drawer_home_page.class));
//            finish();
            FirebaseUser user = mAuth.getCurrentUser();
            DocumentReference reference = firestore.collection("Users").document(user.getUid());
            reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Log.d("TAG", "onSuccess :" +documentSnapshot.getData());
                    if (documentSnapshot.getString("isTutor") != null){
                        finish();
                        Toast.makeText(Login_Activity.this,"login tutor", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), drawer_home_page.class));

                    }
//                    else if (documentSnapshot.getString("isTutees") != null){
//                        Toast.makeText(Login_Activity.this,"Login tutees", Toast.LENGTH_LONG).show();
//                        startActivity(new Intent(getApplicationContext(), Register_Activity.class));
//                        finish();
//                    }
                }
            });
        }
    }
}