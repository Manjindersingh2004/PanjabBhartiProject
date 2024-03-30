package com.example.panjabbharti.Activities;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panjabbharti.Adapters.JobStatusAdapter;
import com.example.panjabbharti.Classes.DataModal;
import com.example.panjabbharti.Classes.JobStatus;
import com.example.panjabbharti.Constants.Keys;
import com.example.panjabbharti.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class JobsStatus extends AppCompatActivity {
    RecyclerView jobStatusRecycler;
    ProgressBar progressBar;
    TextView no_data;
    ArrayList<JobStatus> dataset;

    JobStatusAdapter jobStatusAdapter;
    boolean isConnected;
    FirebaseFirestore firestore;
    Intent intent;
    private String collection_name;
    private String qualification_selected;
    private String dob;
    private boolean pQualified;
    private int age;
    private DataModal dataModal;

    AppCompatButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_jobs_status);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        intent=getIntent();
        jobStatusRecycler = findViewById(R.id.jobStatusRecycler);
        progressBar=findViewById(R.id.progressBar);
        no_data=findViewById(R.id.no_data);
        firestore=FirebaseFirestore.getInstance();
        dataset=new ArrayList<>();
        collection_name=intent.getStringExtra(Keys.DEPARTMENT);
        qualification_selected=intent.getStringExtra(Keys.QUALIFICATION);
        dob=intent.getStringExtra(Keys.DATE_OF_BIRTH);
        pQualified=intent.getBooleanExtra(Keys.PANJABI_QUALIFIED,false);

        progressBar.setVisibility(View.VISIBLE);
        back=findViewById(R.id.backBtn_job);
        //Converting dob to age
        LocalDate dateOfBirth = LocalDate.parse(dob);
        double temp1=(double) (Duration.between(dateOfBirth.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays());
        double temp = Math.ceil( temp1/(double) 365);
        age = (int)temp;
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        Network network = connectivityManager.getActiveNetwork();
        if (network !=null) {
            isConnected = connectivityManager.getNetworkCapabilities(network).hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        }
        //Check if internet is available or not
        if (isConnected) {
            firestore.collection(collection_name.trim()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        QuerySnapshot result = task.getResult();
                        for (QueryDocumentSnapshot iterator : result) {
                            dataModal = iterator.toObject(DataModal.class);
                            Log.d("MYTAG", String.valueOf(age));
                            if (age <= dataModal.getAgeMax() && age >= dataModal.getAgeMin()) {
                                for (String qualification : dataModal.getQualification().values()) {
                                    Log.d("MYTAG", String.valueOf(qualification_selected.trim())+" "+qualification);

                                    if (qualification.equals(qualification_selected.trim())) {
                                        if (pQualified == dataModal.isPanjabiRequired()) {
                                            Log.d("MYTAG", String.valueOf(dataModal.isPanjabiRequired()));
   Log.d("MYTAG", String.valueOf(Duration.between(dataModal.getEndDate().toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay(), LocalDate.now().atStartOfDay()).toDays()));
                                            int temp_days=(int)Duration.between(dataModal.getEndDate().toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();
                                            if ( temp_days< 0) {
                                                temp_days *= -1;
                                            }
                                                if (temp_days>0) {
                                                    //  Populating Dataset with values and accordingly show other views
                                                    dataset.add(new JobStatus(dataModal.getStartDate().toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),dataModal.getEndDate().toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),iterator.getId(),dataModal.getNotificationURL(),dataModal.getFormURL()));
                                                    Log.d("MYTAG","Data Added");
                                                }
                                                //  Populating Dataset with values and accordingly show other views
//                                                dataset.add(new JobStatus(dataModal.getStartDate().toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),dataModal.getEndDate().toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),iterator.getId()));
                                            }
                                        }
                                    }
                                }
                            }
                        Log.d("MYTAG", String.valueOf(dataset.size()));
                        }
                    if (!dataset.isEmpty()) {
                        Log.d("MYTAG","Not Empty");
                        progressBar.setVisibility(View.INVISIBLE);
                        jobStatusAdapter = new JobStatusAdapter(dataset,JobsStatus.this);
                        jobStatusRecycler.setLayoutManager(new LinearLayoutManager(JobsStatus.this,LinearLayoutManager.VERTICAL,false));
                        jobStatusRecycler.setAdapter(jobStatusAdapter);
                    }else {
                        Log.d("MYTAG","Empty");

                        no_data.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        no_data.setText(R.string.no_jobs);
                    }
                    }
                });



        }else {
            no_data.setVisibility(View.VISIBLE);
            no_data.setText(R.string.no_connection);
            progressBar.setVisibility(View.INVISIBLE);

        }

        back.setOnClickListener(v -> finish());

    }
}