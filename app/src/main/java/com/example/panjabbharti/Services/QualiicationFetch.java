package com.example.panjabbharti.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.panjabbharti.Activities.FilterActivity;
import com.example.panjabbharti.Adapters.QualificationFilterAdapter;
import com.example.panjabbharti.Classes.DataModal;
import com.example.panjabbharti.Constants.Keys;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class QualiicationFetch extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        String department=intent.getStringExtra(Keys.DEPARTMENT);
        firestore.collection(department.trim()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    Set<String> uniqueQualifications = new HashSet<>();
                    QualificationFilterAdapter.qualificationList.clear();
                    for(DocumentSnapshot dm: task.getResult()){
                        if(dm!=null && dm.exists()){
                            DataModal dataModal=dm.toObject(DataModal.class);
                            assert dataModal != null;
                            uniqueQualifications.addAll(dataModal.getQualification().values());
                            FilterActivity.qualificationList=new ArrayList<>(uniqueQualifications);
                        }
                    }
                    FilterActivity.recyclerView.setAdapter(new QualificationFilterAdapter(getApplicationContext(),FilterActivity.qualificationList));
                }
            }
        });

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
