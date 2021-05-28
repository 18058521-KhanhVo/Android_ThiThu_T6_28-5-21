package com.example.android_thithu_t6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView txtName, txtEmail, txtAge;
    private RadioButton rbtnMale, rbtnFemale;
    private Button btnSave;

    private List<Student> students;
    private String url = "https://60afb2eee6d11e00174f4e37.mockapi.io/students";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtAge = findViewById(R.id.txtAge);
        rbtnMale = findViewById(R.id.rbtnMale);
        rbtnFemale = findViewById(R.id.rbtnFemale);
        btnSave = findViewById(R.id.btnSave);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getArrayJson(url);
    }

    private void getArrayJson(String url) {
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject o = (JSONObject) response.getJSONObject(i);
                                        Student student = new Student(o.getInt("id"), o.getString("fullname"), o.getString("email"), o.getString("gender"), o.getInt("age"));

                                        addStudent(student);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                StudentAdapter adapter = new StudentAdapter(getApplicationContext(), students);
                                recyclerView.setAdapter(adapter);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error by get Json Array!", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void addStudent(Student student) {
        if (students == null) students = new ArrayList<>();
        students.add(student);
    }
}