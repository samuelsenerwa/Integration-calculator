package com.example.myapplication;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class RuleActivity extends Activity{


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rule_selection);

        EditText a = (EditText)findViewById(R.id.etxt1);
       // a.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        EditText b = (EditText)findViewById(R.id.etxt2);
      //  b.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        EditText in=(EditText)findViewById(R.id.etxt3);
       // in.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        final TextView test = (TextView)findViewById(R.id.txtest);
        final TextView test1 = (TextView)findViewById(R.id.txtest1);

        Button btnTrapezoidal = (Button)findViewById(R.id.btntra);
        Button btnSimpson=(Button)findViewById(R.id.btnsimpson);
        Button btnRomberg=(Button)findViewById(R.id.btnromberg);


        Bundle extras = getIntent().getExtras();
        final String txtequ = extras.getString("equation");


        if (extras == null) {
            test.setText("Null equation");
            test1.setText("Null equation");
        }
        test.setText("Result:");
        test1.setText("Equation: "+txtequ);

        //Trapezoidal Rule button function
        btnTrapezoidal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check1 = a.getText().toString().trim();
                String check2 = b.getText().toString().trim();
                String check3 = in.getText().toString().trim();

                if (check1.matches("") || check2.matches("") || check3.matches("")) {
                    Toast.makeText(getApplicationContext(), "One of the field are missing", Toast.LENGTH_SHORT).show();
                } else {
                    double firstval = Double.parseDouble(a.getText().toString());
                    double secondval = Double.parseDouble(b.getText().toString());
                    int loop = Integer.parseInt(in.getText().toString());
                    test.setText("Result:" + integrateTrapezoidal(firstval, secondval, loop, txtequ));
                }
            }
        });

        btnRomberg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check1 = a.getText().toString().trim();
                String check2 = b.getText().toString().trim();
                String check3 = in.getText().toString().trim();

                if (check1.matches("") || check2.matches("") || check3.matches("")) {
                    Toast.makeText(getApplicationContext(), "One of the field are missing", Toast.LENGTH_SHORT).show();
                } else {
                    double firstval = Double.parseDouble(a.getText().toString());
                    double secondval = Double.parseDouble(b.getText().toString());
                    int loop= Integer.parseInt(in.getText().toString());
                    test.setText("Result:" + integrateRomberg(firstval, secondval, loop, txtequ));
                }}
        });

        btnSimpson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check1 = a.getText().toString().trim();
                String check2 = b.getText().toString().trim();
                String check3 = in.getText().toString().trim();

                if (check1.matches("") || check2.matches("") || check3.matches("")) {
                    Toast.makeText(getApplicationContext(), "One of the field are missing", Toast.LENGTH_SHORT).show();
                } else {
                    double firstval = Double.parseDouble(a.getText().toString());
                    double secondval = Double.parseDouble(b.getText().toString());
                    int loop= Integer.parseInt(in.getText().toString());
                    test.setText("Result:" + integrateSimpson(firstval, secondval, loop, txtequ));
                }}
        });
    }
    double f(double x,String equation) {
        //  expression = expression.replace("x",x);
        Expression e = new ExpressionBuilder(equation)
                .variables("x")
                .build()
                .setVariable("x", x);
        double result = e.evaluate();
        return result;
    }

    double integrateTrapezoidal(double a, double b, int N,String equation) {
        double h = (b - a) / N;              // step size
        double sum = 0.5 * (f(a,equation) + f(b,equation));    // area
        for (int i = 1; i < N; i++) {
            double x = a + h * i;
            sum = sum + f(x,equation);
        }
        return sum * h;
    }

    double integrateSimpson(double a, double b, int N, String equation) {

        double h = (b - a) / (N - 1);     // step size

        // 1/3 terms
        double sum = 1.0 / 3.0 * (f(a,equation) + f(b,equation));

        // 4/3 terms
        for (int i = 1; i < N - 1; i += 2) {
            double x = a + h * i;
            sum += 4.0 / 3.0 * f(x,equation);
        }

        // 2/3 terms
        for (int i = 2; i < N - 1; i += 2) {
            double x = a + h * i;
            sum += 2.0 / 3.0 * f(x,equation);
        }

        return sum * h;
    }

    double integrateRomberg(double a,double b, int max,String equation)
    {
        max+=1;
        double[] s = new double[max];//first index will not be used
        double var = 0;//var is used to hold the value R(n-1,m-1), from the previous row so that 2 arrays are not needed
        double lastVal = Double.NEGATIVE_INFINITY;


        for(int k = 1; k < max; k++)
        {
            for(int i = 1; i <= k; i++)
            {
                if(i == 1)
                {
                    var = s[i];
                    s[i] = integrateTrapezoidal(a,b,(int)Math.pow(2, k-1),equation);
                }
                else
                {
                    s[k]= ( Math.pow(4, i - 1)*s[i-1]-var )/(Math.pow(4, i - 1) - 1);
                    var = s[i];
                    s[i]= s[k];
                }
            }

            if( Math.abs(lastVal - s[k]) < 1e-15 )//there is only approximatly 15.955 accurate decimal digits in a double, this is as close as we will get
                return s[k];
            else lastVal = s[k];
        }


        return s[max-1];
    }

}
