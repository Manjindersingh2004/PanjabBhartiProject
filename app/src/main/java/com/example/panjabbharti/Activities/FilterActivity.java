package com.example.panjabbharti.Activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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
import com.example.panjabbharti.R;

import java.util.ArrayList;
import java.util.Calendar;

public class FilterActivity extends AppCompatActivity {

    AppCompatButton back,dobBtn,panjabiYes,panjabiNo;
    TextView applyFilter;
    RecyclerView recyclerView;

    ArrayList<String> qualificationList=new ArrayList<>();
    String selectedDob="";
    boolean panjabiQualified=true;
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
        QualificationFilterAdapter.selectedQualification="";
        findIds();
        putDataInArraylist();
        setPanjabiQualifiedUi(1);
        clickEvents();


        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.HORIZONTAL));
        adapter= new QualificationFilterAdapter(FilterActivity.this,qualificationList);
        recyclerView.setAdapter(adapter);

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
                Toast.makeText(this, QualificationFilterAdapter.selectedQualification+" "+selectedDob+" "+panjabiQualified, Toast.LENGTH_SHORT).show();
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

    private void putDataInArraylist() {
        qualificationList.add("B.tech");
        qualificationList.add("M.tech");
        qualificationList.add("10th");
        qualificationList.add("12th");
        qualificationList.add("BA");
    }

    private void findIds() {
        back=findViewById(R.id.back_btn_filter);
        applyFilter=findViewById(R.id.apply_btn_filter);
        recyclerView=findViewById(R.id.recycler_view_filter);
        dobBtn=findViewById(R.id.btn_dob);
        panjabiYes=findViewById(R.id.panjabi_yes);
        panjabiNo=findViewById(R.id.panjabi_no);
    }
}