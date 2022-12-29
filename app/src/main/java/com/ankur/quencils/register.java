package com.ankur.quencils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseRegistrar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class register extends AppCompatActivity {
    EditText regemail,regmobile,regpassword,regname,DOB;
    Button regsubmit;
    TextView toLogin;
    String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    ProgressDialog progressdialog ;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    FirebaseAuth mAuth=FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        regname=findViewById(R.id.reg_name);
        regemail=findViewById(R.id.reg_email);
        regmobile=findViewById(R.id.reg_mobile);
        regpassword= findViewById(R.id.reg_password);
        DOB=findViewById(R.id.reg_dob);
        regsubmit= findViewById(R.id.reg_submit);
        toLogin=findViewById(R.id.toLogin);
        progressdialog=new ProgressDialog(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
         databaseReference=firebaseDatabase.getReference("Users");





        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        regsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegister();
            }
        });


    }

    public  void userRegister()
    {

        String name = regname.getText().toString();
        String email =regemail.getText().toString();
        String mobile= regmobile.getText().toString();
        String password = regpassword.getText().toString();
        String dob= DOB.getText().toString();

        registerModel registerModel = new registerModel();

        if (!email.matches(regex))
        {
            regemail.setError("Enter Correct Email");
        }
        if (password.isEmpty()|| password.length()<6)
        {
            regpassword.setError("Enter Correct Password");
        }
        else
        {
            progressdialog.setMessage("Wait while Registration");
            progressdialog.setTitle("Registration");
            progressdialog.setCanceledOnTouchOutside(false);
            progressdialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())

                    {
                        registerModel.setName(name);
                        registerModel.setMobile(mobile);
                        registerModel.setEmail(email);
                        registerModel.setDOB(dob);

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                  databaseReference.setValue(registerModel);
                                Toast.makeText(register.this, "user Register Successfully", Toast.LENGTH_SHORT).show();
                                progressdialog.dismiss();


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                progressdialog.dismiss();
                                Toast.makeText(register.this, "Error", Toast.LENGTH_SHORT).show();
                                sendUserToNextActivity();
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(register.this, "Already Registered", Toast.LENGTH_SHORT).show();
                        progressdialog.dismiss();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}