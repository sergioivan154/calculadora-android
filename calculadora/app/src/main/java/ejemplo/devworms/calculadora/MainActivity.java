package ejemplo.devworms.calculadora;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private TextView cajaTexto;
    private ArrayList<Integer>numeros;


    private void initComponents(){
        cajaTexto = (TextView)findViewById(R.id.editTextPantalla);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initComponents();

    }


    /**
     * Este metodo es para la funcion C
     * @param view
     * @return
     */
    public void escribirC(View view) {
        cajaTexto.setText("");
    }

    /**
     * Este metodo es para numeros y sirve asignarle la misma accion a cada boton variando su comportamiento por lo que escriben en pantalla
     * @param view
     * @return
     */
    public void escribirNumero(View view) {
        String expresion = cajaTexto.getText().toString();
        expresion = (expresion.equals("0") ? ((Button)view).getText().toString(): expresion + ((Button)view).getText().toString());
        cajaTexto.setText(expresion);
    }

    /**
     * Este metodo es para operadores y sirve asignarle la misma accion a cada boton variando su comportamiento por lo que escriben en pantalla
     * @param view
     * @return
     */
    public void escribirOperador(View view) {

        String expresion = cajaTexto.getText().toString(); // se saca el texto escrito ahsta el momento
        String operador = ((Button) view).getText().toString();
        String parentesis = obtenerOperadoresInicialesValidos(operador);

        if (!expresion.isEmpty() || parentesis.equals("(")) {

            if (!expresion.isEmpty()){
                String ultimoCaracter = expresion.substring(expresion.length() - 1); //se obtiene el ultimo caracter con el proposito de evitar que se escriban dos operadores juntos

                if (this.isNumeric(ultimoCaracter)) { // si el ultimo caracter es un numero
                    cajaTexto.setText(expresion + operador); // es posible concatenar este caracter con un operador
                } else {
                    ultimoCaracter = operador; // se reemplaza el operador anterior por el actual, ejemplo: si hay un + y el operador actual es un -, entonces el + se es sobre escrito por un menos

                }
            }else{
                cajaTexto.setText(operador + parentesis);
            }

        }
    }

    public String obtenerOperadoresInicialesValidos(String operador){
        String resultado = "";
        String operadorTemp = operador.toLowerCase();
        if (
                operadorTemp.equals("√") ||
                operadorTemp.equals("sen") ||
                operadorTemp.equals("cos") ||
                operadorTemp.equals("tan") ||
                operadorTemp.equals("ctg") ||
                operadorTemp.equals("sec") ||
                operadorTemp.equals("csc"))
        {
            resultado = "(";
        }

        return resultado;

    }

    /**
     * Este metodo es para la funcion =
     * @param view
     * @return
     */
    public void caluclar(View view) {
         String resultado = Utils.realizarCalculo(cajaTexto.getText().toString());
        cajaTexto.setText(resultado);
    }

    /**
     * Este metodo sirve para identificar si una cadena es un numero o no
     * @param cadena
     * @return
     */
    private boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            System.err.println(nfe.getMessage());
            return false;
        }
    }

}
