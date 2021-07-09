package com.example.vibraniumtutor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class fragement_editprofile extends Fragment {
    EditText edit_fullname, edit_email1, edit_phone, City, Address;
    Button btncreate;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    ImageView profile_picture;
    Uri imageuri;
    StorageReference storageReference;
    FirebaseStorage storage;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_editprofile, container, false);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        profile_picture = root.findViewById(R.id.profile_picture);
        profile_picture.setOnClickListener(v -> {
            choosePicture();
        });
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        edit_email1 = root.findViewById(R.id.edit_email1);
        edit_fullname = root.findViewById(R.id.edit_fullname);
        edit_phone = root.findViewById(R.id.edit_phone);
        City = root.findViewById(R.id.City);
        Address = root.findViewById(R.id.address);
        btncreate = root.findViewById(R.id.create);
        btncreate.setOnClickListener(v -> {
            insertdata();
        });
        return root;
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageuri = data.getData();
            profile_picture.setImageURI(imageuri);
            UploadImage();
        }
    }

    private void UploadImage() {
        final String randomKey = UUID.randomUUID().toString();
        StorageReference reverseRef = storageReference.child("image/" + randomKey);
        reverseRef.putFile(imageuri).addOnSuccessListener(taskSnapshot -> {
            Toast.makeText(getContext(), "image upload successfully", Toast.LENGTH_LONG).show();
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getContext(), "error" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void insertdata() {
        String fullname = edit_fullname.getText().toString();
        String city = City.getText().toString();
        String phone = edit_phone.getText().toString();
        String email = edit_email1.getText().toString();
        String address = Address.getText().toString();
        if (TextUtils.isEmpty(fullname)) {
            edit_fullname.setError("full name can not be empity");
            edit_fullname.requestFocus();
        } else if (TextUtils.isEmpty(city)) {
            edit_email1.setError("City can not be empity");
            edit_email1.requestFocus();
        } else if (TextUtils.isEmpty(phone)) {
            edit_phone.setError("Phone can not be empity");
            edit_phone.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            City.setError("password field can not be empity");
            City.requestFocus();
        } else if (TextUtils.isEmpty(address)) {
            Address.setError("email can not be empity");
            Address.requestFocus();
        } else {
            FirebaseUser user = mAuth.getCurrentUser();
            DocumentReference df = firestore.collection("Users").document(user.getUid());
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("full_name", edit_fullname.getText().toString());
            userInfo.put("City", City.getText().toString());
            userInfo.put("phone_name", edit_phone.getText().toString());
            userInfo.put("email", edit_email1.getText().toString());
            userInfo.put("address", Address.getText().toString().trim());
            userInfo.put("img", imageuri);
            userInfo.put("isTutor", " ");
            df.set(userInfo);
        }
    }
}