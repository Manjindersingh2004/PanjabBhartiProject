package com.example.panjabbharti.Activities;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.panjabbharti.Constants.Keys;
import com.example.panjabbharti.R;

import java.time.Duration;
import java.time.LocalDate;

public class JobData extends AppCompatActivity {
    TextView jobName,start,end,daysToEnd;
    String jName,sDate,eDate,notificationUrl,webUrl;
    int dToEnd;
    Intent receiveData;

    Button download,openWeb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_job_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        jobName=findViewById(R.id.jobName);
        start=findViewById(R.id.start);
        end=findViewById(R.id.end);
        daysToEnd=findViewById(R.id.daysToEnd);
        download=findViewById(R.id.download);
        openWeb=findViewById(R.id.openWeb);


        receiveData=getIntent();
        jName=receiveData.getStringExtra(Keys.JOB_NAME);
        sDate=receiveData.getStringExtra(Keys.START_DATE);
        eDate=receiveData.getStringExtra(Keys.END_DATE);
        notificationUrl=receiveData.getStringExtra(Keys.DOWLOAD_LINK);
        webUrl=receiveData.getStringExtra(Keys.FORM_LINK);

        jobName.setText(jName);
        start.setText(sDate);
        end.setText(eDate);

         dToEnd= (int)Duration.between(LocalDate.now().atStartOfDay(),LocalDate.parse(eDate).atStartOfDay()).toDays();

        daysToEnd.setText(dToEnd+" Days left");

        download.setOnClickListener(v -> {
            DownloadManager downloadManager = getSystemService(DownloadManager.class);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(notificationUrl)) ;
            request.setTitle("Downloading PDF").setDescription("Downloading Official Notification").setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"Notification.pdf").setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            downloadManager.enqueue(request);
        });

        openWeb.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl)));
        });


    }
}