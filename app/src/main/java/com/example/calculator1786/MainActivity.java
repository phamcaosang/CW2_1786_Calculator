package com.example.calculator1786;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Declaring TextViews for displaying the result and the current solution
    TextView resultTv, solutionTv;

    // Declaring buttons for the calculator functionalities
    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setting the content view to the XML layout file
        setContentView(R.layout.activity_main);

        // Initializing the result and solution TextViews
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        // Assigning buttons to their IDs and setting click listeners
        assignId(buttonC,R.id.button_c);
        assignId(buttonBrackOpen,R.id.button_open_bracket);
        assignId(buttonBrackClose,R.id.button_close_bracket);
        assignId(buttonDivide,R.id.divide);
        assignId(buttonMultiply,R.id.multiple);
        assignId(buttonPlus,R.id.plus);
        assignId(buttonMinus,R.id.minus);
        assignId(buttonEquals,R.id.equal);
        assignId(button0,R.id.button_0);
        assignId(button1,R.id.button_1);
        assignId(button2,R.id.button_2);
        assignId(button3,R.id.button_3);
        assignId(button4,R.id.button_4);
        assignId(button5,R.id.button_5);
        assignId(button6,R.id.button_6);
        assignId(button7,R.id.button_7);
        assignId(button8,R.id.button_8);
        assignId(button9,R.id.button_9);
        assignId(buttonAC,R.id.button_ac);
        assignId(buttonDot,R.id.button_dot);
    }

    // Helper method to assign a button to an ID and set its onClick listener
    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    // Overriding the onClick method to handle button clicks
    @Override
    public void onClick(View view) {
        MaterialButton btn = (MaterialButton) view;
        String buttonText = btn.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        // Handling AC (All Clear) button to reset everything
        if (buttonText.equals("AC")){
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }

        // Handling '=' button to set the solution TextView to the result TextView's content
        if (buttonText.equals("=")){
            solutionTv.setText((resultTv.getText()));
            return;
        }

        // Handling 'C' (Clear) button to remove the last character
        if (buttonText.equals("C")){
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() -1);
        } else {
            // For other buttons, append their text to the solution
            dataToCalculate = dataToCalculate + buttonText;
        }

        // Update the solution TextView
        solutionTv.setText(dataToCalculate);
        // Calculate and display the result
        String finalResult = getResult(dataToCalculate);

        // Ensure no error message is displayed in the result TextView
        if (!finalResult.equals("Err")){
            resultTv.setText(finalResult);
        }
    }

    // Method to calculate the result of the current solution
    String getResult(String data){
        try{
            // Using Rhino to interpret the string as a JavaScript code for evaluation
            Context context  = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult =  context.evaluateString(scriptable, data, "Javascript", 1, null).toString();

            // If the result is a whole number, remove trailing ".0"
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        } catch (Exception e){
            // Return error string in case of an exception
            return "Err";
        }
    }
}
