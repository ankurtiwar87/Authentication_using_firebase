package com.ankur.quencils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText loginemail,loginpassword;
    Button loginBtn;
    TextView Register;
    String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    ProgressDialog progressdialog ;

    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseUser mUser=mAuth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        loginemail=findViewById(R.id.login_email);
        loginpassword=findViewById(R.id.login_password);
        loginBtn=findViewById(R.id.login_submit);
        Register=(TextView) findViewById(R.id.toRegister);
        progressdialog=new ProgressDialog(this);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),register.class);
                startActivity(intent);

            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin(loginemail.getText().toString(),loginpassword.getText().toString());
            }
        });
    }

    private void performLogin(String email ,String password) {


        if (!email.matches(regex))
        {
            loginemail.setError("Enter Correct Email");
        }
        if (password.isEmpty()|| password.length()<6)
        {
            loginpassword.setError("Enter Correct Password");
        }
        else {
            progressdialog.setMessage("Wait for Login");
            progressdialog.setTitle("Login");
            progressdialog.setCanceledOnTouchOutside(false);
            progressdialog.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                  progressdialog.dismiss();
                  Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    sendUserToNextActivity();

                }
                else {
                    progressdialog.dismiss();
                    Toast.makeText(MainActivity.this, "Error In Login", Toast.LENGTH_SHORT).show();
                }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(getApplicationContext(),DashBoard.class);
        startActivity(intent);
    }
}