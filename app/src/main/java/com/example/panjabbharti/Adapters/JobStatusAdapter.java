package com.example.panjabbharti.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panjabbharti.Activities.JobData;
import com.example.panjabbharti.Classes.JobStatus;
import com.example.panjabbharti.Constants.Keys;
import com.example.panjabbharti.R;

import java.util.ArrayList;

public class JobStatusAdapter extends RecyclerView.Adapter<JobStatusAdapter.ViewHolder> {
    ArrayList<JobStatus> dataset;
    Context context;

    public JobStatusAdapter(ArrayList<JobStatus> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
    }

    @NonNull
    @Override
    public JobStatusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.job_info_card,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JobStatusAdapter.ViewHolder holder, int position) {
        holder.getJob_title().setText(dataset.get(position).getJob_title());
        holder.getStartDate().setText(dataset.get(position).getStartDate().toString());
        holder.getEndDate().setText(dataset.get(position).getEndDate().toString());
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, JobData.class);
            i.putExtra(Keys.JOB_NAME,dataset.get(position).getJob_title());
            i.putExtra(Keys.START_DATE,dataset.get(position).getStartDate().toString());
            i.putExtra(Keys.END_DATE,dataset.get(position).getEndDate().toString());
            i.putExtra(Keys.DOWLOAD_LINK,dataset.get(position).getNotificationURL());
            i.putExtra(Keys.FORM_LINK,dataset.get(position).getFormURL());

            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView job_title = itemView.findViewById(R.id.job_title);
        private TextView startDate = itemView.findViewById(R.id.startDate);
        private TextView endDate = itemView.findViewById(R.id.endDate);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }


        public TextView getJob_title() {
            return job_title;
        }

        public void setJob_title(TextView job_title) {
            this.job_title = job_title;
        }

        public TextView getStartDate() {
            return startDate;
        }

        public void setStartDate(TextView startDate) {
            this.startDate = startDate;
        }

        public TextView getEndDate() {
            return endDate;
        }

        public void setEndDate(TextView endDate) {
            this.endDate = endDate;
        }
    }
}
