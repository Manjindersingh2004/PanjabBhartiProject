package com.example.panjabbharti.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panjabbharti.Activities.FilterActivity;
import com.example.panjabbharti.Classes.Department_Model;
import com.example.panjabbharti.R;

import java.util.ArrayList;

public class Adapter_Departments extends RecyclerView.Adapter<Adapter_Departments.ViewHolder> {
    Context context;
    ArrayList<Department_Model> departmentModelArrayList;
    LayoutInflater layoutInflater;
    public void setFilteredList(ArrayList<Department_Model> filterList){
        departmentModelArrayList=filterList;
        notifyDataSetChanged();
    }
    public Adapter_Departments(Context context,ArrayList<Department_Model> departmentModelArrayList){
        this.context=context;
        this.departmentModelArrayList=departmentModelArrayList;
        this.layoutInflater=LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public Adapter_Departments.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.department_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Departments.ViewHolder holder, int position) {
        Department_Model departmentModel = departmentModelArrayList.get(position);
        holder.department_image.setImageResource(departmentModel.getCourse_image());
        holder.department_name.setText(departmentModel.getDepartment_name());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FilterActivity.class);
                intent.putExtra("dept",departmentModel.getDepartment_name());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return departmentModelArrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView department_image;
        public TextView department_name;
        public CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            department_image=itemView.findViewById(R.id.department_image);
            department_name=itemView.findViewById(R.id.department_name);
            cardView=itemView.findViewById(R.id.card_view);
        }
    }
}
