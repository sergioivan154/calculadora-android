package ejemplo.devworms.calculadora;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView cajaTexto;
    private ArrayList<Button>buttonsNumbers; //Arreglo de botones del 0 al 9
    private ArrayList<Button>buttonsOperators; //Arreglo de botones de operaciones básicas

    private Button btnPotencia;
    private Button btnRaiz;
    private Button btnIgual;
    private Button btnC;;

    private void initComponents()
    {


        cajaTexto =(TextView) findViewById(R.id.editTextPantalla);

        btnPotencia = (Button) findViewById(R.id.buttonPow);
        btnRaiz = (Button) findViewById(R.id.buttonSqrt);
        btnIgual  = (Button) findViewById(R.id.buttonIqualTo);
        btnC = (Button) findViewById(R.id.buttonC);

        buttonsNumbers = new ArrayList<>();
        buttonsOperators = new ArrayList<>();

        //Numeros - Variante uno, listeners
        buttonsNumbers.add((Button) findViewById(R.id.buttonZero)) ;
        buttonsNumbers.add((Button) findViewById(R.id.buttonOne)) ;
        buttonsNumbers.add((Button) findViewById(R.id.buttonTwo));
        buttonsNumbers.add((Button) findViewById(R.id.buttonThree));
        buttonsNumbers.add((Button) findViewById(R.id.buttonFour));
        buttonsNumbers.add((Button) findViewById(R.id.buttonFive));
        buttonsNumbers.add((Button) findViewById(R.id.buttonSix));
        buttonsNumbers.add((Button) findViewById(R.id.buttonSeven));
        buttonsNumbers.add((Button) findViewById(R.id.buttonEight));
        buttonsNumbers.add((Button) findViewById(R.id.buttonNine));

        //Operadores
        buttonsOperators.add((Button) findViewById(R.id.buttonSum));
        buttonsOperators.add((Button) findViewById(R.id.buttonSub));
        buttonsOperators.add((Button) findViewById(R.id.buttonTimes));
        buttonsOperators.add((Button) findViewById(R.id.buttonDiv));


        //Acciones para numeros
        for (Button boton:buttonsNumbers) {
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String expresion = cajaTexto.getText().toString();
                    expresion = (expresion.equals("0") ? ((Button)view).getText().toString(): expresion + ((Button)view).getText().toString());
                    cajaTexto.setText(expresion);
                }
            });
        }

        //Acciones para operadores con el metodo 2
        for (Button boton:buttonsOperators) {
            boton.setOnClickListener(this);
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initComponents();

    }

    @Override
    public void onClick(View view) {
        String expresion = cajaTexto.getText().toString();
        expresion = (expresion.equals("0") ? ((Button)view).getText().toString(): expresion + ((Button)view).getText().toString());
        cajaTexto.setText(expresion);
    }
}
