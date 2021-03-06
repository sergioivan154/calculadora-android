package ejemplo.devworms.calculadora;
import java.text.DecimalFormat;
import java.util.Stack;
import android.util.Log;

/**
 * Created by sergio on 14/03/16.
 */
public class Utils {

    public static String realizarCalculo(String textoEnPantalla){
        //Depurar la expresion algebraica

        String expr = depurar(textoEnPantalla);
        String[] arrayInfix = expr.split(" ");

        //Declaración de las pilas
        Stack < String > E = new Stack < String > (); //Pila entrada
        Stack < String > P = new Stack < String > (); //Pila temporal para operadores
        Stack < String > S = new Stack < String > (); //Pila salida

        //Añadir la array a la Pila de entrada (E)
        for (int i = arrayInfix.length - 1; i >= 0; i--) {
            E.push(arrayInfix[i]);
        }

        try {
            //Algoritmo Infijo a Postfijo
            while (!E.isEmpty()) {
                switch (pref(E.peek())){
                    case 1:
                        P.push(E.pop());
                        break;
                    case 3:
                    case 4:
                    case 5:
                        while(pref(P.peek()) >= pref(E.peek())) {
                            S.push(P.pop());
                        }
                        P.push(E.pop());
                        break;
                    case 2:
                        while(!P.peek().equals("(")) {
                            S.push(P.pop());
                        }
                        P.pop();
                        E.pop();
                        break;
                    default:
                        S.push(E.pop());
                }
            }

            //Eliminacion de `impurezas´ en la expresiones algebraicas
            String infix = expr.replace(" ", "");
            String postfix = S.toString().replaceAll("[\\]\\[,]", "");


            //Entrada (Expresión en Postfija)
            expr = postfix; // equivale a 2*(23+6)-1
            String[] post = expr.split(" ");

            //Declaración de las pilas
            E = new Stack < String > (); //Pila entrada
            P = new Stack < String > (); //Pila de operandos

            //Añadir post (array) a la Pila de entrada (E)
            for (int i = post.length - 1; i >= 0; i--) {
                E.push(post[i]);
            }

            //Mostrar resultados:
            Log.i("infija","Expresion Infija: " + infix);
            Log.i("infija", "Expresion Postfija: " + postfix);

            //Algoritmo de Evaluación Postfija
            String operadores = "+-*/^√sencostanctgseccsc";
            while (!E.isEmpty()) {
                String peek = E.peek().toLowerCase();
                if (operadores.contains("" + peek)) {
                    P.push(evaluar(E.pop(), P.pop(),  (P.size()>0 ? P.pop():"0")) + "");
                }else {
                    P.push(E.pop());
                }
            }

            String valor = P.peek();

           /* int numeroDecimales = valor.indexOf(".");
            String mascara = "###,###";
            numeroDecimales = valor.length() - numeroDecimales;
            if(numeroDecimales>0){
                mascara += ".";
                if(numeroDecimales > 6)
                    numeroDecimales = 6;
                for (int i = 0; i<(numeroDecimales) ; i++) {
                    mascara += "#";
                }
            }

            DecimalFormat format=new DecimalFormat(mascara);
*/
            //Mostrar resultados:
            return valor.equals("Infinity") || valor.equals("NaN") ? "Error": valor;

        }catch(Exception ex){
            return ex.getMessage();
        }
    }

    //Depurar expresión algebraica
    private static String depurar(String s) {
        s = s.replaceAll("\\s+", ""); //Elimina espacios en blanco

        //Balanceo de parentesis
        int parentesisIzq = s.length() - s.replace("(", "").length();
        int parentesisDer = s.length() - s.replace(")", "").length();
        if(parentesisIzq>parentesisDer) {
            for (int i=0; i< (parentesisIzq-parentesisDer); i++){
                s +=")";
            }
        }
        else if(parentesisIzq<parentesisDer) {
            for (int i=0; i< (parentesisDer-parentesisIzq); i++){
                s ="("+s;
            }
        }

        s = "(" + s + ")";
        String simbols = "+-*/()^√";
        String str = "";

        //Deja espacios entre operadores
        for (int i = 0; i < s.length(); i++) {
            if (simbols.contains("" + s.charAt(i))) {
                str += " " + s.charAt(i) + " ";
            }else str += s.charAt(i);
        }
        return str.replaceAll("\\s+", " ").trim();
    }

    //Jerarquia de los operadores
    private static int pref(String op) {
        int prf = 99;
        if (op.equals("^") || op.equals("√")
        || op.toLowerCase().equals("sen")|| op.toLowerCase().equals("cos")
        || op.toLowerCase().equals("tan")|| op.toLowerCase().equals("ctg")
        || op.toLowerCase().equals("sec")|| op.toLowerCase().equals("csc"))prf = 5;
        if (op.equals("*") || op.equals("/")) prf = 4;
        if (op.equals("+") || op.equals("-")) prf = 3;
        if (op.equals(")")) prf = 2;
        if (op.equals("(")) prf = 1;
        return prf;
    }

    private static double evaluar(String op, String n2, String n1) {
        double num1 = Double.parseDouble(n1);
        double num2 = Double.parseDouble(n2);
        if (op.equals("+")) return (num1 + num2);
        if (op.equals("-")) return (num1 - num2);
        if (op.equals("*")) return (num1 * num2);
        if (op.equals("/"))  return (num1 / num2);
        if (op.equals("^")) return Math.pow(num1, num2);
        if (op.equals("√")) return Math.sqrt(num2);
        if (op.toLowerCase().equals("sen")) return Math.sin(num2);
        if (op.toLowerCase().equals("cos")) return Math.cos(num2);
        if (op.toLowerCase().equals("tan")) return Math.tan(num2);
        if (op.toLowerCase().equals("ctg")) return 1.0/Math.tan(num2);
        if (op.toLowerCase().equals("sec")) return 1.0/Math.cos(num2);
        if (op.toLowerCase().equals("csc")) return 1.0 /Math.sin(num2);

        return 0;
    }

}
