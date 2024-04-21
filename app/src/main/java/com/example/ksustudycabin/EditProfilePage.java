package com.example.ksustudycabin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;

public class EditProfilePage extends AppCompatActivity {

    private DBHandler dbHandler;
    private ProgressDialog pd;
    private EditText oldPasswordEditText, newPasswordEditText;
    ImageButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_page);

        // Initialize DBHandler
        dbHandler = new DBHandler(this);
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);

        // Initialize views
        TextView editPasswordTextView = findViewById(R.id.changepassword);
        editPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Changing Password");
                showPasswordChangeDialog();
            }
        });

        btn = findViewById(R.id.buttongoback);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), profile.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void showPasswordChangeDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_update_password, null);
        oldPasswordEditText = view.findViewById(R.id.oldpasslog);
        newPasswordEditText = view.findViewById(R.id.newpasslog);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view)
                .setTitle("Change Password")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String oldPassword = oldPasswordEditText.getText().toString().trim();
                        String newPassword = newPasswordEditText.getText().toString().trim();
                        if (TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword)) {
                            Toast.makeText(EditProfilePage.this, "Please enter both old and new passwords", Toast.LENGTH_SHORT).show();
                        } else {
                            updatePassword(oldPassword, newPassword);
                        }
                    }
                })
                .setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void updatePassword(String oldPassword, String newPassword) {
        pd.show();
        String email = dbHandler.getUserEmail();
        Log.d("UserEmail", "Retrieved email: " + email);
        boolean isValid = dbHandler.checkUserCredentials(email, oldPassword);
        boolean success;
        if (isValid) {
            success = dbHandler.updatePassword(email, newPassword);
            if (success) {
                pd.dismiss();
                Toast.makeText(EditProfilePage.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                pd.dismiss();
                Toast.makeText(EditProfilePage.this, "Error updating password", Toast.LENGTH_SHORT).show();
            }
        } else {
            pd.dismiss();
            Toast.makeText(EditProfilePage.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }

    }

}
