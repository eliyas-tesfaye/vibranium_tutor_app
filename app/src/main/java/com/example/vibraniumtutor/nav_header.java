package com.example.vibraniumtutor;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class nav_header extends AppCompatActivity {

    ImageView profile_picture;
//    Uri imageuri;
    StorageReference storageReference;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header);

        profile_picture = findViewById(R.id.profile_picture);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


    }



}
