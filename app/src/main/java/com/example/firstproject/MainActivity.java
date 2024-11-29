package com.example.firstproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private boolean resultDisplayed = false;
    private TextView result;
    double firstNum,secondNum,specialNum,res,lastresult=0;
    char operator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        result=findViewById(R.id.textViewResult);
        result.setText("");
    }
    public void exitfunction(View view) {
        finishAffinity();
        System.exit(0);
    }
    public void numFunction(View view) {
        Button button = (Button) view;
        if (resultDisplayed) {
            firstNum=res;
            result.setText("");
            resultDisplayed = false;
        }
        result.append(button.getText().toString());
    }
    public void OpFunction(View view) {
        if (result.getText().toString().isEmpty()) {
            result.setText(getString(R.string.error_text));
            new Handler().postDelayed(() -> result.setText(""), 1000);
            return;
        }
        if (resultDisplayed) {
            firstNum = res;
            resultDisplayed = false;
        } else
            firstNum = Double.parseDouble(result.getText().toString());

        operator = ((Button) view).getText().toString().charAt(0);
        result.append(" " + operator + " ");
    }
    public void equalFunction(View view) {
        String[] parts = result.getText().toString().split(" ");
        if (parts.length < 3) {
            result.setText(getString(R.string.error_text));
            new Handler().postDelayed(() -> result.setText(""), 1000);
            return;
        }
        firstNum = Double.parseDouble(parts[0]);
        operator = parts[1].charAt(0);
        secondNum = Double.parseDouble(parts[2]);


        switch (operator) {
            case '+':
                res = firstNum + secondNum;
                break;
            case '-':
                res = firstNum - secondNum;
                break;
            case '*':
                res = firstNum * secondNum;
                break;
            case '/':
                if (secondNum == 0) {
                    result.setText(getString(R.string.error_text));
                    new Handler().postDelayed(() -> {
                        result.setText("");
                        clearFunction(view);
                    }, 1000);
                    return;
                }
                res = firstNum / secondNum;
                break;
            default:
                result.setText(getString(R.string.error_text));
        }
        if (res == (int) res)
            result.setText(String.valueOf((int) res));
         else
            result.setText(String.valueOf(res));

        lastresult = res;
        resultDisplayed = true;
    }
    public void specialOp(View view) {
        operator = ((Button) view).getText().toString().charAt(0);
        specialNum = Double.parseDouble((result.getText().toString()));
        switch (operator) {
            case '²':
                res = specialNum * specialNum;
                break;
            case '√':
                if (firstNum < 0) {
                    result.setText(getString(R.string.error_text));
                    clearFunction(view);
                    return;
                    }
                    res = Math.sqrt(specialNum);
                    break;
                }
        if (res == (int) res)
            result.setText(String.valueOf((int) res));

        else
            result.setText(String.valueOf(res));
        lastresult = res;
        resultDisplayed = true;

    }
    public void clearFunction(View view) {
        firstNum = 0;
        secondNum = 0;
        specialNum= 0;
        operator = '\0';
        result.setText("");
        resultDisplayed = false;
    }
    public void removeLast(View view) {
        String currentText = result.getText().toString();
        if (!currentText.isEmpty()) {
            String newText = currentText.substring(0, currentText.length() - 1);
            result.setText(newText);
        }
    }
    public void getlastres(View view) {
        if (result.getText().toString().isEmpty()) {
            if (lastresult == (int) lastresult) {
                firstNum = (int) lastresult;
                result.setText(String.valueOf((int) lastresult));
            } else {
                firstNum = lastresult;
                result.setText(String.valueOf(lastresult));
            }
        } else {
            if (lastresult == (int) lastresult) {
                secondNum = (int) lastresult;
                result.append(String.valueOf((int) lastresult));
            } else {
                secondNum = lastresult;
                result.append(String.valueOf(lastresult));
            }
        }
    }
}