package com.example.bogdan.chatapplication;

/**
 * Created by Bogdan on 2/7/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

        private EditText usernameInput;
        private Button connectButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login_activity);
            usernameInput = (EditText) findViewById(R.id.username_input);
            connectButton = (Button) findViewById(R.id.button2);
            //getActionBar().hide();
            connectButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (usernameInput.getText().toString().trim().length() > 0) {

                        String name = usernameInput.getText().toString().trim();

                        Intent intent = new Intent(LoginActivity.this,
                                MainActivity.class);
                        intent.putExtra("userName", name);

                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }


    }
