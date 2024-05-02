package com.example.panjabbharti.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.panjabbharti.Adapters.QualificationFilterAdapter;
import com.example.panjabbharti.Classes.DataModal;
import com.example.panjabbharti.Constants.Keys;
import com.example.panjabbharti.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class FilterActivity extends AppCompatActivity {

    AppCompatButton back,dobBtn,panjabiYes,panjabiNo;
    TextView applyFilter;

    ProgressBar progressbar;
    public  RecyclerView recyclerView;

    public  ArrayList<String> qualificationList=new ArrayList<>();
    String selectedDob="";
    boolean panjabiQualified=true;
    String selectedDept="";
    QualificationFilterAdapter adapter;
    String[] months={" January ", " February ", " March ", " April ", " May ", " June ", " July ", " August ", " September ", " October ", " November ", " December "};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findIds();
        setPanjabiQualifiedUi(1);
        clickEvents();
        Intent intent = getIntent();
        selectedDept = intent.getStringExtra(Keys.DEPARTMENT);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.HORIZONTAL));
        adapter= new QualificationFilterAdapter(FilterActivity.this,qualificationList);
        progressbar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setAdapter(adapter);
        QualificationFilterAdapter.selectedQualification="";
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        String department=intent.getStringExtra(Keys.DEPARTMENT);
        Log.d("MYTAG","Dept :- "+department);
        firestore.collection(department.trim()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Set<String> uniqueQualifications = new HashSet<>();
                qualificationList.clear();
                for(DocumentSnapshot dm: task.getResult()){
                    if(dm!=null && dm.exists()){
                        DataModal dataModal=dm.toObject(DataModal.class);
                        assert dataModal != null;
                        uniqueQualifications.addAll(dataModal.getQualification().values());
                        qualificationList=new ArrayList<>(uniqueQualifications);
                    }
                }
                recyclerView.setAdapter(new QualificationFilterAdapter(getApplicationContext(),qualificationList));
                progressbar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                Log.d("MYTAG","Views Updated");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("MYTAG","Error:-"+e.getMessage());
            }
        });
    }
    private void clickEvents() {
        back.setOnClickListener(v -> finish());
        applyFilter.setOnClickListener(v -> {
            if(QualificationFilterAdapter.selectedQualification.isEmpty()){
                Toast.makeText(this, "Please Select Qualification", Toast.LENGTH_SHORT).show();
            }
            else if (selectedDob.isEmpty()) {
                Toast.makeText(this, "Please Select Dob", Toast.LENGTH_SHORT).show();
            }
            else{
                LocalDate dateOfBirth = LocalDate.parse(selectedDob);
                double temp1=(double) (Duration.between(dateOfBirth.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays());
                double temp = Math.ceil( temp1/(double) 365);
                int age = (int)temp;
                
                if(age>=18) {
                    Intent intent = new Intent(FilterActivity.this, JobsStatus.class);
                    intent.putExtra(Keys.DEPARTMENT, selectedDept);
                    intent.putExtra(Keys.QUALIFICATION, QualificationFilterAdapter.selectedQualification);
                    intent.putExtra(Keys.DATE_OF_BIRTH, selectedDob);
                    intent.putExtra(Keys.PANJABI_QUALIFIED, panjabiQualified);
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "Minimum Age to apply is 18 years", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dobBtn.setOnClickListener(v -> {
            if(selectedDob.isEmpty()){
                Calendar calendar=Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);

                DatePickerDialog dialog= new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
                    String date=dayOfMonth+months[month1]+ year1;
                    dobBtn.setText(date);
                    dobBtn.setTextColor(ContextCompat.getColor(FilterActivity.this,R.color.white));
                    dobBtn.setBackgroundDrawable(ContextCompat.getDrawable(FilterActivity.this,R.drawable.btn_bg_2));

                    month1++;
                    String formattedDay = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                    String formattedMonth = (month1 < 10) ? "0" + month1 : String.valueOf(month1);
                    selectedDob= year1 + "-" + formattedMonth + "-" + formattedDay;
                },year,month,day);
                dialog.show();

            }else {
                selectedDob="";
                dobBtn.setText(R.string.select_dob);
                dobBtn.setTextColor(ContextCompat.getColor(this,R.color.black));
                dobBtn.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.btn_bg_1));
            }
        });


        panjabiYes.setOnClickListener(v -> {
            if(panjabiQualified){
                setPanjabiQualifiedUi(0);
            }else{
                setPanjabiQualifiedUi(1);
            }
        });
        panjabiNo.setOnClickListener(v -> {
            if(!panjabiQualified){
                setPanjabiQualifiedUi(1);
            }else{
                setPanjabiQualifiedUi(0);
            }
        });
    }

    private void setPanjabiQualifiedUi(int n) {
        if(n==1){
            panjabiQualified=true;
            panjabiYes.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.btn_bg_2));
            panjabiYes.setTextColor(ContextCompat.getColor(this,R.color.white));
            panjabiNo.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.btn_bg_1));
            panjabiNo.setTextColor(ContextCompat.getColor(this,R.color.black));
        }else{
            panjabiQualified=false;
            panjabiYes.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.btn_bg_1));
            panjabiYes.setTextColor(ContextCompat.getColor(this,R.color.black));
            panjabiNo.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.btn_bg_2));
            panjabiNo.setTextColor(ContextCompat.getColor(this,R.color.white));
        }
    }
    private void findIds() {
        back=findViewById(R.id.back_btn_filter);
        applyFilter=findViewById(R.id.apply_btn_filter);
        recyclerView=findViewById(R.id.recycler_view_filter);
        dobBtn=findViewById(R.id.btn_dob);
        panjabiYes=findViewById(R.id.panjabi_yes);
        panjabiNo=findViewById(R.id.panjabi_no);
        progressbar=findViewById(R.id.progressbar);

    }
}