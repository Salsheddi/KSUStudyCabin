package com.example.ksustudycabin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class signin extends AppCompatActivity {
    ImageButton btn;
    EditText userpassword ,useremail;
    Button buttonSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        btn = findViewById(R.id.buttongoback);
        useremail = (EditText) findViewById(R.id.useremail);
        userpassword = (EditText) findViewById(R.id.userpassword);
        buttonSignIn = findViewById(R.id.buttonsignin);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = useremail.getText().toString().trim();
                String password = userpassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(signin.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                DBHandler dbHandler = new DBHandler(signin.this);
                if (dbHandler.checkUserCredentials(email, password)) {
                    // User exists, move to home page
                    Intent intent = new Intent(signin.this, HomepageActivity.class);
                    startActivity(intent);
                } else {
                    // User does not exist, prompt to register
                    Toast.makeText(signin.this, "User does not exist, please register", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}