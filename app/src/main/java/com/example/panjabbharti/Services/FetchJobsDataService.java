package com.example.panjabbharti.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.panjabbharti.Activities.JobsStatus;
import com.example.panjabbharti.Classes.DataModal;
import com.example.panjabbharti.Constants.Keys;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.Key;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;

public class FetchJobsDataService extends Service {
    FirebaseFirestore firestore;
    String collection_name,qualification_selected;
    int age;

    String dob;
    boolean pQualified;
    DataModal dataModal;
    boolean isConnected =false;

    Context context;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        firestore=FirebaseFirestore.getInstance();




        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
