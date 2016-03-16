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
    private boolean banderaPunto = true;

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
        cajaTexto.setText("0");
    }

    /**
     * Este metodo es para numeros y sirve asignarle la misma accion a cada boton variando su comportamiento por lo que escriben en pantalla
     * @param view
     * @return
     */
    public void escribirNumero(View view) {
        String expresion = cajaTexto.getText().toString();
        String textoBoton = ((Button)view).getText().toString();
        if (expresion.substring(expresion.length()-1).equals(")")){
            textoBoton = "*" + textoBoton;
        }
        expresion = (expresion.equals("0") || expresion.equals("Error") ? textoBoton : expresion + textoBoton);
        cajaTexto.setText(expresion);
    }

    /**
     * Este metodo es para operadores y sirve asignarle la misma accion a cada boton variando su comportamiento por lo que escriben en pantalla
     * @param view
     * @return
     */
    public void escribirOperador(View view) {

        this.validarOperadores(cajaTexto.getText().toString(), ((Button) view).getText().toString());
    }

    private void validarOperadores(String textoEnPantalla, String operadorButton){


        if (!textoEnPantalla.contains("."))
            banderaPunto = true;
        if(textoEnPantalla.toLowerCase().equals("error")){
            cajaTexto.setText("0");
            return;
        }

        String ultimoCaracter = textoEnPantalla.substring(textoEnPantalla.length() - 1);

        String simbols = "+-*/^.";

        switch (operadorButton.toLowerCase()){

            case ".":
                simbols = "+-*/^";
                if(simbols.contains(ultimoCaracter)) {
                    cajaTexto.setText(textoEnPantalla+"0.");
                    banderaPunto = false;
                }
                else{

                    if (banderaPunto == false || "()".contains(ultimoCaracter)){
                        cajaTexto.setText(textoEnPantalla);
                    }
                    else {
                        if(!simbols.contains(textoEnPantalla) && simbols.contains(""))
                        cajaTexto.setText(textoEnPantalla + operadorButton);
                        banderaPunto = false;
                    }

                }

                break;
            case "(":
                banderaPunto = true;
                if (!textoEnPantalla.equals("0"))

                    if(ultimoCaracter.equals(".")) {

                        cajaTexto.setText(textoEnPantalla + "0*" + operadorButton);
                    }else {
                        cajaTexto.setText(textoEnPantalla + "*" + operadorButton);
                    }
                else
                    cajaTexto.setText(operadorButton);
                break;
            case ")":
                if (ultimoCaracter.equals("("))
                    cajaTexto.setText(textoEnPantalla+"0"+operadorButton);
                else {
                    cajaTexto.setText(textoEnPantalla + operadorButton);

                }
                break;
            case "√":
            case "sen":
            case "cos":
            case "tan":
            case "sec":
            case "csc":
            case "ctg":
                if(!simbols.contains(ultimoCaracter)) { // si al final del texto escrito no existe ninguno de los simbolos de operacion se puede hacer el calculo de la razin cuadrada
                    if(cajaTexto.getText().toString().contains("Error"))
                        cajaTexto.setText("0");
                    else
                        cajaTexto.setText(operadorButton+"("+operar(cajaTexto.getText().toString())+")");

                    banderaPunto = true;
                }
                break;
            default:


                banderaPunto = true;
                if(!simbols.contains(ultimoCaracter)) {

                    cajaTexto.setText(textoEnPantalla+operadorButton);
                }
                else{
                    if (ultimoCaracter.equals("^"))
                        ultimoCaracter = "\\^";
                    String resultado = textoEnPantalla.replaceAll("["+ultimoCaracter+"]$", operadorButton);
                    cajaTexto.setText(resultado);
                }
                break;
        }

    }

    /**
     * Este metodo es para la funcion =
     * @param view
     * @return
     */
    public void calcular(View view) {

        cajaTexto.setText(operar(cajaTexto.getText().toString()));

    }

    public String operar(String textoEnPantalla){
        String simbols = "+-*/^";
        String resultado = "";
        String ultimoCaracter = cajaTexto.getText().toString().substring(cajaTexto.getText().toString().length()-1);
        if (!simbols.contains(ultimoCaracter)) {
            resultado = Utils.realizarCalculo(cajaTexto.getText().toString());
        }else{
            resultado = textoEnPantalla;
        }


        return resultado!= null && !resultado.isEmpty() ? resultado: "0";


    }



}
