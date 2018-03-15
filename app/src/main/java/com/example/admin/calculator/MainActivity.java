package com.example.admin.calculator;

import android.app.TabActivity;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String calculate_string = "";
    List<String> operations = new ArrayList<>();
    List<CalculatorOperation> calculatorOperations = new ArrayList<>();
    List<ExpressionElement> postfixElements = new ArrayList<>();
    TextView txtResult;
    Button btnZero;
    Button btnOne;
    Button btnTwo;
    Button btnThree;
    Button btnFour;
    Button btnFive;
    Button btnSix;
    Button btnSeven;
    Button btnEight;
    Button btnNine;
    Button btnResult;
    Button btnClear;
    Button btnPlus;
    Button btnMinus;
    Button btnMutiply;
    Button btnDevision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = findViewById(R.id.txtResult);
        btnZero = findViewById(R.id.btnZero);
        btnOne = findViewById(R.id.btnOne);
        btnTwo = findViewById(R.id.btnTwo);
        btnThree = findViewById(R.id.btnThree);
        btnFour = findViewById(R.id.btnFour);
        btnFive = findViewById(R.id.btnFive);
        btnSix = findViewById(R.id.btnSix);
        btnSeven = findViewById(R.id.btnSeven);
        btnEight = findViewById(R.id.btnEight);
        btnNine = findViewById(R.id.btnNine);
        btnResult = findViewById(R.id.btnResult);
        btnClear = findViewById(R.id.btnClear);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnMutiply = findViewById(R.id.btnMultiply);
        btnDevision = findViewById(R.id.btnDivision);

        txtResult.setText("");
        setButtonListener();

        setOperations();
    }

    public void setButtonListener(){
        btnZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertParamToCalculateString("0");
            }
        });
        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertParamToCalculateString("1");
            }
        });
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertParamToCalculateString("2");
            }
        });
        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertParamToCalculateString("3");
            }
        });
        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertParamToCalculateString("4");
            }
        });
        btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertParamToCalculateString("5");
            }
        });
        btnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertParamToCalculateString("6");
            }
        });
        btnSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertParamToCalculateString("7");
            }
        });
        btnEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertParamToCalculateString("8");
            }
        });
        btnNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertParamToCalculateString("9");
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertParamToCalculateString("+");
            }
        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertParamToCalculateString("-");
            }
        });
        btnMutiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertParamToCalculateString("x");
            }
        });
        btnDevision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertParamToCalculateString("/");
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteParamInCalculateString();
            }
        });

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
                reset();
            }
        });
    }

    private void calculate() {
        Log.d("string", calculate_string);
        List<String> inputElements = getElementsInExpression(calculate_string);
        String m = "";
        List<ExpressionElement> elements = changeToPostfix(inputElements);
        for (int i = 0; i < elements.size(); i++){
            m += elements.get(i).getElement();
        }
        Log.d("postfix", m);
        calculate_string = "";
        Float result = calculateFromPostfix(elements);
        txtResult.setText(String.valueOf(result));
    }

    private List<String> getElementsInExpression(String calculate_str) {
        List<String> elementsInExpression = new ArrayList<>();
        int nextBeginOperationIndex = 0;
        for (int i = 0; i < calculate_str.length(); i++){
            Log.d("char_1", String.valueOf(calculate_str.charAt(i)));
            if (isOperation(String.valueOf(calculate_str.charAt(i)))){
                elementsInExpression.add(calculate_str.substring(nextBeginOperationIndex, i));
                elementsInExpression.add(calculate_str.substring(i, i+1));
                nextBeginOperationIndex = i+1;
            }
        }
        elementsInExpression.add(calculate_str.substring(nextBeginOperationIndex));
        return  elementsInExpression;
    }

    private List<ExpressionElement> changeToPostfix(List<String> expression_args) {
        for(int i = 0; i < expression_args.size(); i++){
            Log.d("char", expression_args.get(i));
            if(isOperation(expression_args.get(i))){
                int currentOperationPriority = getPriorityOperation(expression_args.get(i));
                while (calculatorOperations.size() > 0 && currentOperationPriority <= getLastOpeartion().getPriority()){
                    CalculatorOperation addedOperation = getLastOpeartion();
                    postfixElements.add(new ExpressionElement(2, addedOperation.getPerformance()));
                    removeLastOperation();
                }
                calculatorOperations.add(new CalculatorOperation(getPriorityOperation(expression_args.get(i)), expression_args.get(i)));
            }else{
                postfixElements.add(new ExpressionElement(1, expression_args.get(i)));
            }
        }

        for (int i = calculatorOperations.size() - 1; i >= 0; i--){
            postfixElements.add(new ExpressionElement(2, calculatorOperations.get(i).getPerformance()));
        }

        return postfixElements;
    }

    private float calculateFromPostfix(List<ExpressionElement> postfixElements){
        float result = 0;
        for(int i = 0; i < postfixElements.size(); i++){
            if (postfixElements.get(i).getType() == 2){
                result = calculate2paras(postfixElements.get(i-2).getElement(), postfixElements.get(i-1).getElement(), postfixElements.get(i).getElement());
                Log.d("TAGGGG", String.valueOf(result));
                postfixElements.remove(i-2);
                postfixElements.remove(i-2);
                postfixElements.remove(i-2);
                postfixElements.add(i-2, new ExpressionElement(1, String.valueOf(result)));
                break;
            }
        }
        if (postfixElements.size() > 1){
            result = calculateFromPostfix(postfixElements);
        }
        return result;
    }

    private float calculate2paras(String a, String b, String operation){
        Log.d("TAGGGG", a + " " + b + " " + operation);
        switch (operation){
            case "+": return Float.valueOf(a) + Float.valueOf(b);
            case "-": return Float.valueOf(a) - Float.valueOf(b);
            case "x": return Float.valueOf(a) * Float.valueOf(b);
            case "/": return Float.valueOf(a) / Float.valueOf(b);
            default: return 0;
        }
    }

    private CalculatorOperation getLastOpeartion(){
        return calculatorOperations.get(calculatorOperations.size() - 1);
    }

    private void removeLastOperation() {
        calculatorOperations.remove(calculatorOperations.size() - 1);
    }

    private void deleteParamInCalculateString() {
        calculate_string = calculate_string.substring(0, calculate_string.length() - 1);
        txtResult.setText(calculate_string);
    }

    private void insertParamToCalculateString(String param){
        calculate_string += param;
        txtResult.setText(calculate_string);
    }

    private void setOperations(){
        operations.add("+");
        operations.add("-");
        operations.add("x");
        operations.add("/");
    }

    private Boolean isOperation(String x){
        if (operations.contains(x)){
            return true;
        }else{
            return false;
        }
    }

    private int getPriorityOperation(String operation){
        switch (operation){
            case "+": return 1;
            case "-": return 1;
            case "x": return 2;
            case "/": return 2;
            default: return 0;
        }
    }

    private void reset(){
        calculate_string = "";
        calculatorOperations = new ArrayList<>();
        postfixElements = new ArrayList<>();
    }

    private void testChangeToPostfix() {
        List<String> string_test = new ArrayList<>();
        string_test.add("32");
        string_test.add("+");
        string_test.add("54");
        string_test.add("x");
        string_test.add("6");

        String m = "";
        List<ExpressionElement> elements = changeToPostfix(string_test);
        for (int i = 0; i < elements.size(); i++){
            m += elements.get(i).getElement();
        }

        Log.d("TAG", m);
    }
}

class CalculatorOperation{
    private int priority;
    private String performance;

    public CalculatorOperation(int priority, String performance) {
        this.priority = priority;
        this.performance = performance;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }
}

class ExpressionElement{
    // type = 1: number, type = 2: operation
    private int type;
    private String element;

    public ExpressionElement(int type, String element) {
        this.type = type;
        this.element = element;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }
}