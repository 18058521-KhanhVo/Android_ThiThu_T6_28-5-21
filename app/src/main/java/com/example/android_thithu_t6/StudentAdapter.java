package com.example.android_thithu_t6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHoler> {

    private Context context;
    private LayoutInflater inflater;
    private List<Student> students;

    public StudentAdapter(Context context, List<Student> students) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.students = students;
    }

    @NonNull
    @Override
    public StudentViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new StudentViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHoler holder, int position) {
        Student student = students.get(position);
        holder.txtStudentInfo.setText(student.toString());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class StudentViewHoler extends RecyclerView.ViewHolder {
        private TextView txtStudentInfo;
        private Button btnEdit, btnDelete;

        public StudentViewHoler(@NonNull View itemView) {
            super(itemView);
            txtStudentInfo = itemView.findViewById(R.id.txtStudentInfo);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
