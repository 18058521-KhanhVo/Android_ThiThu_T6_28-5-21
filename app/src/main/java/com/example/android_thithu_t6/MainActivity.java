package com.example.android_thithu_t6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SendingData {

    protected static Context context;

    private RecyclerView recyclerView;
    private TextView txtName, txtEmail, txtAge;
    private RadioButton rbtnMale, rbtnFemale;
    private Button btnSave;

    private List<Student> students;
    private Student tempforput;
    private String url = "https://60afb2eee6d11e00174f4e37.mockapi.io/students";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        recyclerView = findViewById(R.id.recyclerview);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtAge = findViewById(R.id.txtAge);
        rbtnMale = findViewById(R.id.rbtnMale);
        rbtnFemale = findViewById(R.id.rbtnFemale);
        btnSave = findViewById(R.id.btnSave);
        rbtnMale.setChecked(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getArrayJson(url);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postApi(url);
            }
        });
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

    private void postApi(String url) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String name = txtName.getText().toString();
                String email = txtEmail.getText().toString();
                String age = txtAge.getText().toString();
                String gender;
                if (rbtnMale.isChecked()) gender = "Male";
                else gender = "Female";

                HashMap<String, String>
                        params = new HashMap<>();
                params.put("fullname", name);
                params.put("email", email);
                params.put("gender", gender);
                params.put("age", age);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        students = null;
        getArrayJson(url);
    }


    private void addStudent(Student student) {
        if (students == null) students = new ArrayList<>();
        students.add(student);
    }

    @Override
    public void sendData(int id) {
        putApi(url, id);
    }

    private void putApi(String url, int id) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT, url + '/' + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error by Put data!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String name = txtName.getText().toString();
                String email = txtEmail.getText().toString();
                String age = txtAge.getText().toString();
                String gender;
                if (rbtnMale.isChecked()) gender = "Male";
                else gender = "Female";

                HashMap<String, String> params = new HashMap<>();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(MainActivity.this, "Enter name!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity.this, "Enter email!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(age)) {
                    Toast.makeText(MainActivity.this, "Enter age!", Toast.LENGTH_SHORT).show();
                } else {
                    params.put("fullname", name);
                    params.put("email", email);
                    params.put("gender", gender);
                    params.put("age", age);
                }
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        students = null;
        getArrayJson(url);
    }
}