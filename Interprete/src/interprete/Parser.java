/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interprete;

/**
 *
 * @author Coatl
 */
import java.util.List;

public class Parser {

    private final List<Token> tokens;
    
    private final Token clase = new Token(TipoToken.CLASS, "class");
    private final Token funcion = new Token(TipoToken.FUN, "fun");
    private final Token variable = new Token(TipoToken.VAR, "var");
    private final Token verdad = new Token(TipoToken.TRUE, "true");
    private final Token falso = new Token(TipoToken.FALSE, "false");
    private final Token nulo = new Token(TipoToken.NULL, "null");
    private final Token este = new Token(TipoToken.THIS, "this");
    private final Token y = new Token(TipoToken.AND, "and");
    private final Token o = new Token(TipoToken.OR, "or");
    private final Token para = new Token(TipoToken.FOR, "for");
    private final Token si = new Token(TipoToken.IF, "if");
    private final Token demas = new Token(TipoToken.ELSE, "else");
    private final Token imprimir = new Token(TipoToken.PRINT, "print");
    private final Token regresar = new Token(TipoToken.RETURN, "return");
    private final Token mientras = new Token(TipoToken.WHILE, "while");
    private final Token numero = new Token(TipoToken.NUMBER, "");
    private final Token cadena = new Token(TipoToken.STRING, "");
    private final Token superman = new Token(TipoToken.SUPER, "super");
    private final Token exclamacion = new Token(TipoToken.NOT, "!");
    private final Token diferente = new Token(TipoToken.DIFERENTE, "!=");
    private final Token asignacion = new Token(TipoToken.ASIG, "=");
    private final Token igual = new Token(TipoToken.IGUAL, "==");
    private final Token resta = new Token(TipoToken.RESTA, "-");
    private final Token suma = new Token(TipoToken.SUMA, "+");
    private final Token division = new Token(TipoToken.DIV, "/");
    private final Token multiplicacion = new Token(TipoToken.MULTI, "*");
    private final Token id = new Token(TipoToken.ID, "");
    private final Token menor = new Token(TipoToken.MENOR, "<");
    private final Token menor_igual = new Token(TipoToken.MENOR_IGUAL, "<=");
    private final Token mayor = new Token(TipoToken.MAYOR, ">");
    private final Token mayor_igual = new Token(TipoToken.MAYOR_IGUAL, ">=");
    private final Token la = new Token(TipoToken.LLAV_ABRE, "{");
    private final Token lc = new Token(TipoToken.LLAV_CIERRE, "}");
    private final Token pa = new Token(TipoToken.PAR_ABRE, "(");
    private final Token pc = new Token(TipoToken.PAR_CIERRE, ")");
    private final Token coma = new Token(TipoToken.COMA, ",");
    private final Token punto = new Token(TipoToken.PUNTO, ".");
    private final Token punto_coma = new Token(TipoToken.PUNTO_COMA, ";");
    /*private final Token select = new Token(TipoToken.SELECT, "select");
    private final Token from = new Token(TipoToken.FROM, "from");
    private final Token distinct = new Token(TipoToken.DISTINCT, "distinct");
    private final Token coma = new Token(TipoToken.COMA, ",");
    private final Token punto = new Token(TipoToken.PUNTO, ".");
    private final Token asterisco = new Token(TipoToken.ASTERISCO, "*");*/
    private final Token finCadena = new Token(TipoToken.END, "");

    private int i = 0;
    private boolean hayErrores = false;

    private Token preanalisis;

    public Parser(List<Token> tokens){
        this.tokens = tokens;
    }

    public void parse(){
        i = 0;
        preanalisis = tokens.get(i);
        DECLARATION();

        if(!hayErrores && !preanalisis.equals(finCadena)){
            System.out.println("Error en la posición " + preanalisis.posicion + ". No se esperaba el token " + preanalisis.tipo);
        }
        else if(!hayErrores && preanalisis.equals(finCadena)){
            System.out.println("Consulta válida");
        }
    }
 //DECLARACIONES
    void DECLARATION(){
        if(hayErrores) return;
        CLASS_DECL();
        FUN_DECL();
        VAR_DECL();
        STATEMENT();
    }
    
    void CLASS_DECL(){
        if(preanalisis.equals(clase)){
            coincidir(clase);
            coincidir(id);
            CLASS_INHER();
            coincidir(la);
            FUNCTIONS();
            coincidir(lc);
            DECLARATION();
        }
        
    }
    
    void CLASS_INHER(){
       if(preanalisis.equals(menor)){
            coincidir(menor);
            coincidir(id);
        }
    }
    
    void FUN_DECL(){
        if(preanalisis.equals(funcion)){
            coincidir(funcion);
            FUNCTION();
            DECLARATION();
        }
    }
    
    void VAR_DECL(){
        if(preanalisis.equals(variable)){
            coincidir(variable);
            coincidir(id);
            VAR_INIT();
            coincidir(punto_coma);
            DECLARATION();
        }
    }
    
    void VAR_DECL_FOR(){
        if(preanalisis.equals(variable)){
            coincidir(variable);
            coincidir(id);
            VAR_INIT();
            coincidir(punto_coma);
        }
    }
    
    void VAR_INIT(){
        if(preanalisis.equals(asignacion)){
            coincidir(asignacion);
            EXPRESSION();
        }
    }
    
    //SENTENCIAS
    
      
    void STATEMENT(){
        if(preanalisis.equals(para)){
            FOR_STMT();
        }else if(preanalisis.equals(si)){
            IF_STMT();
        }else if(preanalisis.equals(imprimir)){
            PRINT_STMT();
        }else if(preanalisis.equals(regresar)){
            RETURN_STMT();
        }else if(preanalisis.equals(mientras)){
            WHILE_STMT();
        }else if(preanalisis.equals(la)){
            BLOCK();
        }else if(preanalisis.equals(lc)){
            
        }else{
          EXPR_STMT();
        }
    }
    
    void EXPR_STMT(){
        if(preanalisis.equals(finCadena)) return;
        EXPRESSION();
        coincidir(punto_coma);
        DECLARATION();
    }
    
    void FOR_STMT(){
        if(preanalisis.equals(para)){
            coincidir(para);
            coincidir(pa);
            FOR_STMT_1();
            FOR_STMT_2();
            FOR_STMT_3();
            coincidir(pc);
            STATEMENT();
        }
    }
    
    void FOR_STMT_1(){
        if(preanalisis.equals(punto_coma)){
            coincidir(punto_coma);
        }else if(preanalisis.equals(variable)){
            VAR_DECL_FOR();
        }else{
            EXPR_STMT();
        }
    }
    
    void FOR_STMT_2(){
        if(preanalisis.equals(punto_coma)){
            coincidir(punto_coma);
        }else{
            EXPRESSION();
            coincidir(punto_coma);
        }
    }
    
    void FOR_STMT_3(){
        if(preanalisis.equals(pc)){
        
        }else{
            EXPRESSION();
        }
    }
    
    void IF_STMT(){
        if(preanalisis.equals(si)){
            coincidir(si);
            coincidir(pa);
            EXPRESSION();
            coincidir(pc);
            STATEMENT();
            ELSE_STATEMENT();
        }
    }
    
    void ELSE_STATEMENT(){
        if(preanalisis.equals(demas)){
            coincidir(demas);
            STATEMENT();
        }
    }
    
    void PRINT_STMT(){
        if(preanalisis.equals(imprimir)){
            coincidir(imprimir);
            EXPRESSION();
            coincidir(punto_coma);
            if(preanalisis.equals(lc)){
            
            }else{
                STATEMENT();
            }
        }
    }
    
    void RETURN_STMT(){
        if(preanalisis.equals(regresar)){
            coincidir(regresar);
           RETURN_EXP_OPC();
            coincidir(punto_coma);
        }
    }
    
    void RETURN_EXP_OPC(){
        if(preanalisis.equals(punto_coma)){
        
        }else{
            EXPRESSION();
        }
    }
    
    void WHILE_STMT(){
        if(preanalisis.equals(mientras)){
            coincidir(mientras);
            coincidir(pa);
            EXPRESSION();
            coincidir(pc);
            STATEMENT();
        }
    }
    
    void BLOCK(){
        if(preanalisis.equals(la)){
            coincidir(la);
            BLOCK_DECL();
            coincidir(lc);
        }
    }
    
    void BLOCK_DECL(){
        if(preanalisis.equals(lc)){
        }else{
            DECLARATION();
        }
    }
    
    //EXPRESIONES
    
    void EXPRESSION(){
        ASSIGNMENT();
    }
    
    void ASSIGNMENT(){
        LOGIC_OR();
        ASSIGNMENT_OPC();
    }
    
    void ASSIGNMENT_OPC(){
        if(preanalisis.equals(asignacion)){
            coincidir(asignacion);
            EXPRESSION();
        }
    }
    
    void LOGIC_OR(){
        LOGIC_AND();
        LOGIC_OR_2();
    }
    
    void LOGIC_OR_2(){
        if(preanalisis.equals(o)){
            coincidir(o);
            LOGIC_AND();
            LOGIC_OR_2();
        }
    }
    
    void LOGIC_AND(){
        EQUALITY();
        LOGIC_AND_2();
    }
    
    void LOGIC_AND_2(){
        if(preanalisis.equals(y)){
            coincidir(y);
            EQUALITY();
            LOGIC_AND_2();
        }
    }
    
    void EQUALITY(){
        COMPARISON();
        EQUALITY_2();
    }
    
    void EQUALITY_2(){
        if(preanalisis.equals(diferente)){
            coincidir(diferente);
            COMPARISON();
            EQUALITY_2();
        }else if(preanalisis.equals(igual)){
            coincidir(igual);
            COMPARISON();
            EQUALITY_2();
        }
    }
    
    void COMPARISON(){
        TERM();
        COMPARISON_2();
    }
    
    void COMPARISON_2(){
        if(preanalisis.equals(mayor)){
            coincidir(mayor);
            TERM();
            COMPARISON_2();
        }else if(preanalisis.equals(mayor_igual)){
            coincidir(mayor_igual);
            TERM();
            COMPARISON_2();
        }else if(preanalisis.equals(menor)){
            coincidir(menor);
            TERM();
            COMPARISON_2();
        }else if(preanalisis.equals(menor_igual)){
            coincidir(menor_igual);
            TERM();
            COMPARISON_2();
        }
    }
    
    void TERM(){
        FACTOR();
        TERM_2();
    }
    
    void TERM_2(){
        if(preanalisis.equals(resta)){
            coincidir(resta);
            FACTOR();
            TERM_2();
        }else if(preanalisis.equals(suma)){
            coincidir(suma);
            FACTOR();
            TERM_2();
        }
    }
    
    void FACTOR(){
        UNARY();
        FACTOR_2();
    }
    
    void FACTOR_2(){
        if(preanalisis.equals(division)){
            coincidir(division);
            UNARY();
            FACTOR_2();
        }else if(preanalisis.equals(multiplicacion)){
            coincidir(multiplicacion);
            UNARY();
            FACTOR_2();
        }
    }
    
    void UNARY(){
        if(preanalisis.equals(exclamacion)){
            coincidir(exclamacion);
            UNARY();
            FACTOR_2();
        }else if(preanalisis.equals(resta)){
            coincidir(resta);
            UNARY();
            FACTOR_2();
        }else{
            CALL();
        }
    }
    
    void CALL(){
        PRIMARY();
        CALL_2();
    }
    
    void CALL_2(){
        if(preanalisis.equals(pa)){
            coincidir(pa);
            ARGUMENTS_OPC();
            coincidir(pc);
            CALL_2();
        }else if(preanalisis.equals(punto)){
            coincidir(punto);
            coincidir(id);
            CALL_2();
        }
    }
    
    void PRIMARY(){
        if(hayErrores) return;
        if(preanalisis.equals(verdad)){
            coincidir(verdad);
        }else if(preanalisis.equals(falso)){
            coincidir(falso);
        }else if(preanalisis.equals(nulo)){
            coincidir(nulo);
        }else if(preanalisis.equals(este)){
            coincidir(este);
        }else if(preanalisis.equals(numero)){
            coincidir(numero);
        }else if(preanalisis.equals(cadena)){
            coincidir(cadena);
        }else if(preanalisis.equals(id)){
            coincidir(id);
        }else if(preanalisis.equals(pa)){
            coincidir(pa);
            EXPRESSION();
            coincidir(pc);
        }else if(preanalisis.equals(superman)){
            coincidir(superman);
            coincidir(punto);
            coincidir(id);
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba una expresion primaria");
        }
    }
    
    //OTRAS
    
    
    void FUNCTIONS(){
        FUNCTION();
    }
    
    void FUNCTION(){
        if(preanalisis.equals(id)){
            coincidir(id);
            coincidir(pa);
            PARAMETERS_OPC();
            coincidir(pc);
            BLOCK();
        }
    }
    
    
    void PARAMETERS_OPC(){
        PARAMETERS();
    }
    
    void PARAMETERS(){
        if(preanalisis.equals(id)){
            coincidir(id);
            PARAMETERS_2();
        }
    }
    
    void PARAMETERS_2(){
        if(preanalisis.equals(coma)){
            coincidir(coma);
            coincidir(id);
            PARAMETERS_2();
        }
    }
   
    void ARGUMENTS_OPC(){
        ARGUMENTS();
    }
    
    void ARGUMENTS(){
        if(preanalisis.equals(pc)){
            
        }else{
            EXPRESSION();
            ARGUMENTS_2();
        }
    }
    
    void ARGUMENTS_2(){
        if(preanalisis.equals(coma)){
            coincidir(coma);
            EXPRESSION();
            ARGUMENTS_2();
        }
    }
  
    void coincidir(Token t){
        if(hayErrores) return;

        if(preanalisis.tipo == t.tipo){
            i++;
            preanalisis = tokens.get(i);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba un  " + t.tipo);

        }
    }

}

