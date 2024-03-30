package com.example.panjabbharti.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panjabbharti.R;

import java.util.ArrayList;

public class QualificationFilterAdapter extends RecyclerView.Adapter<QualificationFilterAdapter.ViewHolder> {
    Context context;
    public static ArrayList<String> qualificationList=new ArrayList<>();
    public static String selectedQualification;

    public QualificationFilterAdapter(Context context, ArrayList<String> arrayList){
        this.context=context;
        qualificationList=arrayList;
    }

    @NonNull
    @Override
    public QualificationFilterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_view_item_filter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QualificationFilterAdapter.ViewHolder holder, int position) {

        holder.btn.setText(qualificationList.get(position));
        if(selectedQualification.equals(qualificationList.get(position))){
            holder.btn.setTextColor(ContextCompat.getColor(context,R.color.white));
            holder.btn.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.btn_bg_2));
        }
        else{
            holder.btn.setTextColor(ContextCompat.getColor(context,R.color.black));
            holder.btn.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.btn_bg_1));
        }

        holder.btn.setOnClickListener(v -> {
            if(selectedQualification.isEmpty()){
                selectedQualification=qualificationList.get(holder.getAdapterPosition());
            } else if (!selectedQualification.equals(qualificationList.get(holder.getAdapterPosition()))) {
                selectedQualification=qualificationList.get(holder.getAdapterPosition());
            }
            else{
                selectedQualification="";
            }
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return qualificationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatButton btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn=itemView.findViewById(R.id.filter_btn_item);
        }
    }
}
