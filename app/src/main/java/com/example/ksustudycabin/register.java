package com.example.ksustudycabin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class register extends AppCompatActivity {
    ImageButton btn ;
    Button btn2;
    EditText username,useremail,userpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn = findViewById(R.id.buttongoback);
        btn2=findViewById(R.id.buttonregister);
        username = (EditText) findViewById(R.id.username);
        useremail =(EditText) findViewById(R.id.useremail);
        userpassword =(EditText) findViewById(R.id.userpassword);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String email = useremail.getText().toString().trim();
                String pass = userpassword.getText().toString().trim();

                if(user.equals("") || email.equals("") || pass.equals("")) {
                    Toast.makeText(register.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Create an instance of the DBHandler
                    DBHandler dbHandler = new DBHandler(register.this);
                    // Check if the student already exists
                    if (!dbHandler.checkStudentExists(email)) {
                        // If the student doesn't exist, insert the new student
                        boolean insert = dbHandler.insertStudent(user, email, pass);
                        if (insert) {
                            Toast.makeText(register.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            // Navigate to the HomepageActivity or any other activity
                            Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(register.this, "User already exists!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}