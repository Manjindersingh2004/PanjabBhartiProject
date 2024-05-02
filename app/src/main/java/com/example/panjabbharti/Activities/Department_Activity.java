package com.example.panjabbharti.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panjabbharti.Adapters.Adapter_Departments;
import com.example.panjabbharti.Classes.Department_Model;
import com.example.panjabbharti.R;

import java.util.ArrayList;

public class Department_Activity extends AppCompatActivity {
    ArrayList<Department_Model> departmentModelArrayList;
    RecyclerView recyclerView;
    SearchView searchView;
    Adapter_Departments adapterDepartments;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_department);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Permission granted, you can now write to external storage.
        // Permission denied, handle accordingly.
        ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (!isGranted) {
                        Toast.makeText(this, "Permission Required for Proper App Working", Toast.LENGTH_SHORT).show();
                    }
                });

        // Check if the app needs to request the permission.
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Request the permission.
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }

        recyclerView = findViewById(R.id.department_recycler_view);
        textView=findViewById(R.id.no_department_text);
        searchView=findViewById(R.id.department_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterText(newText);
                return true;
            }
        });
        departmentModelArrayList= new ArrayList<Department_Model>();
        departmentModelArrayList.add(new Department_Model("Punjab Subordinate Service Selection Board",R.drawable.revenue));
        //departmentModelArrayList.add(new Department_Model("Education",R.drawable.education));
        departmentModelArrayList.add(new Department_Model("Punjab Police",R.drawable.police));
        departmentModelArrayList.add(new Department_Model("Punjab State Power Corporation Limited",R.drawable.electricity));
        departmentModelArrayList.add(new Department_Model("Baba Farid University",R.drawable.school));
        adapterDepartments = new Adapter_Departments(this,departmentModelArrayList);
        GridLayoutManager gridLayoutManager =new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterDepartments);
    }

    private void filterText(String newText) {
    ArrayList<Department_Model> filteredList = new ArrayList<>();
    for(Department_Model newDepartment: departmentModelArrayList){
        if(newDepartment.getDepartment_name().toLowerCase().contains(newText.toLowerCase())){
            filteredList.add(newDepartment);
            }
        }
        adapterDepartments.setFilteredList(filteredList);
        if(filteredList.isEmpty()){
            textView.setVisibility(View.VISIBLE);
        }else{
            textView.setVisibility(View.GONE);
        }
    }
}