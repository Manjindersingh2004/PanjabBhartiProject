package com.example.panjabbharti.Activities;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.panjabbharti.Constants.Keys;
import com.example.panjabbharti.R;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDate;

public class JobData extends AppCompatActivity {
    TextView jobName,start,end,daysToEnd;
    String jName,sDate,eDate,notificationUrl,webUrl;
    int dToEnd;
    Intent receiveData;

    Button download,openWeb,finish;
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
        finish=findViewById(R.id.finish);


        receiveData=getIntent();
        jName=receiveData.getStringExtra(Keys.JOB_NAME);
        sDate=receiveData.getStringExtra(Keys.START_DATE);
        eDate=receiveData.getStringExtra(Keys.END_DATE);
        notificationUrl=receiveData.getStringExtra(Keys.DOWLOAD_LINK);
        webUrl=receiveData.getStringExtra(Keys.FORM_LINK);

        jobName.setText(jName);
        start.setText(MessageFormat.format("Start Date : {0}", sDate));
        end.setText(MessageFormat.format("End Date : {0}", eDate));

         dToEnd= (int)Duration.between(LocalDate.now().atStartOfDay(),LocalDate.parse(eDate).atStartOfDay()).toDays();
         if (dToEnd<0){
             dToEnd*=-1;
         }
        daysToEnd.setText(MessageFormat.format("{0} Days left", dToEnd));

        download.setOnClickListener(v -> {
            DownloadManager downloadManager = getSystemService(DownloadManager.class);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(notificationUrl)) ;
            request.setTitle("Downloading PDF").setDescription("Downloading Official Notification").setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"NotificationPDF.pdf").setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            downloadManager.enqueue(request);
            Toast.makeText(this, "downloading...", Toast.LENGTH_LONG).show();
        });

        openWeb.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))));
        finish.setOnClickListener(v -> finish());

    }
}