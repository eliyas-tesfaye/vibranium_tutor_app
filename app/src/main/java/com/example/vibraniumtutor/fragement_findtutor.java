package com.example.vibraniumtutor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class fragement_findtutor extends Fragment {

    private Spinner online, subject, location, education;
    ArrayAdapter<CharSequence> onliine_adapter, subject_adapter, education_adapter, location_adapter;
    Button btn_findtutor;
    String item_online, item_subject, item_education, item_location;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    DocumentReference df_o;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_findtutor, container, false);

        online = root.findViewById(R.id.online_spinner);
        subject = root.findViewById(R.id.subject_spinner);
        education = root.findViewById(R.id.educationlevel_spinner);
        location = root.findViewById(R.id.location_spinner);
        btn_findtutor = root.findViewById(R.id.btn_findtutor);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        onliine_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.online_array, android.R.layout.simple_spinner_item);
        onliine_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        subject_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.subject_array, android.R.layout.simple_spinner_item);
        subject_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        education_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.education_array, android.R.layout.simple_spinner_item);
        education_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        location_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.location_array, android.R.layout.simple_spinner_item);
        location_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        online.setAdapter(onliine_adapter);
        subject.setAdapter(subject_adapter);
        education.setAdapter(education_adapter);
        location.setAdapter(location_adapter);


        btn_findtutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> userInfo = new HashMap<>();
                FirebaseUser user = mAuth.getCurrentUser();
//                String Spinner_o = online.getSelectedItem().toString();
//                String Spinner_s = subject.getSelectedItem().toString();
//                String Spinner_e = education.getSelectedItem().toString();
//                String Spinner_l = location.getSelectedItem().toString();
//

//                userInfo.put("uID", user.getUid());
//                userInfo.put("tutor_type", Spinner_o);
//                userInfo.put("subject_type", Spinner_s);
//                userInfo.put("education_type", Spinner_e);
//                userInfo.put("location_type", Spinner_l);
//
//                df_o.set(userInfo);
//                df_s.set(userInfo);
            }
        });

        return root;
    }

}
