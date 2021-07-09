package com.example.vibraniumtutor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

;

public class Register_Activity extends AppCompatActivity {

    TextView txtReg;
    EditText fname_reg, lname_reg, uname_reg, email_reg, pass_reg, Cpass_reg;
    Button btn_Reg;

    FirebaseAuth mAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtReg = findViewById(R.id.txt_Reg);
        fname_reg = findViewById(R.id.fname_reg);
        lname_reg = findViewById(R.id.lname_reg);
        uname_reg = findViewById(R.id.uname_reg);
        email_reg = findViewById(R.id.email_reg);
        pass_reg = findViewById(R.id.pass_reg);
        Cpass_reg = findViewById(R.id.Cpass_reg);
        btn_Reg = findViewById(R.id.btn_Reg);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        btn_Reg.setOnClickListener(view -> {
            createUser();
        });

        txtReg.setOnClickListener(v -> {

            Intent intent = new Intent(Register_Activity.this, Login_Activity.class);
            startActivity(intent);
        });
    }

    private void createUser() {

        String fname = fname_reg.getText().toString();
        String lnam = lname_reg.getText().toString();
        String uname = uname_reg.getText().toString();
        String email = email_reg.getText().toString();
        String pass = pass_reg.getText().toString();
        String Cpass = Cpass_reg.getText().toString();

        if (TextUtils.isEmpty(fname)) {
            fname_reg.setError("email can not be empity");
            fname_reg.requestFocus();
        } else if (TextUtils.isEmpty(lnam)) {
            lname_reg.setError("password field can not be empity");
            lname_reg.requestFocus();
        } else if (TextUtils.isEmpty(uname)) {
            uname_reg.setError("email can not be empity");
            uname_reg.requestFocus();

        } else if (TextUtils.isEmpty(email)) {
            email_reg.setError("password field can not be empity");
            email_reg.requestFocus();
        } else if (TextUtils.isEmpty(pass)) {
            pass_reg.setError("email can not be empity");
            pass_reg.requestFocus();
        } else if (TextUtils.isEmpty(Cpass)) {
            Cpass_reg.setError("password field can not be empity");
            Cpass_reg.requestFocus();
        } else if (!pass.equals(Cpass)) {
            Cpass_reg.setError("password is not similar");
            Cpass_reg.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener(authResult -> {
                FirebaseUser user = mAuth.getCurrentUser();

                Toast.makeText(Register_Activity.this, "user registered successfully", Toast.LENGTH_SHORT).show();

                DocumentReference df = firestore.collection("Users").document(user.getUid());
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("f_name", fname_reg.getText().toString());
                userInfo.put("l_name", lname_reg.getText().toString());
                userInfo.put("u_name", uname_reg.getText().toString());
                userInfo.put("email", email_reg.getText().toString());
                userInfo.put("isTutor", 1);

                df.set(userInfo);

                startActivity(new Intent(Register_Activity.this, Login_Activity.class));
                finish();

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Register_Activity.this, "faild to create account", Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}