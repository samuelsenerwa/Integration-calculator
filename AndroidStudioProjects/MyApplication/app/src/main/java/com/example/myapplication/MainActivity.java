package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSet = (Button) findViewById(R.id.btnSetEquation);
        final EditText txtequ = (EditText) findViewById(R.id.txtEquation);

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check = txtequ.getText().toString().trim();
                if (check.matches("") || check==null){
                    Toast.makeText(getApplicationContext(), "Please enter equation", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent rule = new Intent(getApplicationContext(), RuleActivity.class);
                    rule.putExtra("equation", txtequ.getText().toString());
                    startActivity(rule);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this adds item to the action var if it is present
        getMenuInflater().inflate(R.menu.main, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle item clicks here

        int id = item.getItemId();
        if (id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}