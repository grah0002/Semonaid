package com.gerontechies.semonaid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gerontechies.semonaid.R;

public class PasswordActivity extends AppCompatActivity {

    EditText pass;
    Button button ;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        getSupportActionBar().hide();

        Typeface font = ResourcesCompat.getFont(getApplicationContext(),R.font.montserrat);
        pass = (EditText) findViewById(R.id.editText_pass);
        pass.setTypeface(font);

        builder = new AlertDialog.Builder(this);
        button = (Button) findViewById(R.id.btn_pass);
        button.setTypeface(font);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String password = pass.getText().toString();

                if(password.isEmpty()){
                    builder.setMessage("Please enter the password to proceed")

                            .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();

                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Error");
                    alert.show();
                } else {
                    if(password.equals("teamb12")){
                        Intent intent = new Intent(PasswordActivity.this, SplashActivity.class);
                        startActivity(intent);
                    } else{
                        //  builder.setMessage("Oops! Looks like you do not have access to this application!") .setTitle("Error");

                        //Setting message manually and performing action on button click
                        builder.setMessage("You shall not pass!\nOops, Looks like you do not have access to this application!")

                                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                        dialog.cancel();

                                    }
                                });
                        //Creating dialog box
                        AlertDialog alert = builder.create();
                        //Setting the title manually
                        alert.setTitle("Error");
                        alert.show();
                    }
                }
                }

        });


    }
}
