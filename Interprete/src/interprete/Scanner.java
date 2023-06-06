/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interprete;

/**
 *
 * @author Coatl
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Scanner {
    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private static final Map<String, TipoToken> keywords;
    static {
        keywords = new HashMap<>();
        keywords.put("super", TipoToken.SUPER);
        keywords.put("true", TipoToken.TRUE);
        keywords.put("false", TipoToken.FALSE);
        keywords.put("null", TipoToken.NULL);
        keywords.put("this", TipoToken.THIS);
        keywords.put("and", TipoToken.AND);
        keywords.put("or", TipoToken.OR);
        keywords.put("while", TipoToken.WHILE);
        keywords.put("if", TipoToken.IF);
        keywords.put("else", TipoToken.ELSE);
        keywords.put("return", TipoToken.RETURN);
        keywords.put("print", TipoToken.PRINT);
        keywords.put("for", TipoToken.FOR);
        keywords.put("fun", TipoToken.FUN);
        keywords.put("var", TipoToken.VAR);
        keywords.put("class", TipoToken.CLASS);
    }

    Scanner(String source){
        String output = eliminarComentarios(source);
        this.source = output + " ";
    }

    List<Token> scanTokens(){
        int estado = 0;
        char caracter = 0;
        String lexema = "";
        int inicioLexema = 0;

        for(int i=0; i<source.length(); i++){
            caracter = source.charAt(i);
            char nxtcaracter = (i + 1 < source.length()) ? source.charAt(i + 1) : '\0';
            
            switch (estado){
                case 0:
                    if(caracter == '+'){
                        tokens.add(new Token(TipoToken.SUMA, "+", i + 1));
                    }
                    else if(caracter == '-')
                    {
                        tokens.add(new Token(TipoToken.RESTA, "-", i + 1));
                    }
                    else if(caracter == '*')
                    {
                    tokens.add(new Token(TipoToken.MULTI, "*", i + 1));
                    }
                    else if(caracter == '/')
                    {
                    tokens.add(new Token(TipoToken.DIV, "/",i + 1));
                    }
                    else if(caracter == '=')
                    {
                        if(nxtcaracter == '=')
                        {
                            tokens.add(new Token(TipoToken.IGUAL, "==", i + 1));
                            i++;
                        }
                        else
                        {
                            tokens.add(new Token(TipoToken.ASIG, "=", i + 1));
                        }
                    }
                    else if(caracter == '(')
                    {
                        tokens.add(new Token(TipoToken.PAR_ABRE, "(", i + 1));
                    }
                    else if(caracter == ')')
                    {
                        tokens.add(new Token(TipoToken.PAR_CIERRE, ")", i + 1));
                    }
                    else if(caracter == '{')
                    {
                        tokens.add(new Token(TipoToken.LLAV_ABRE, "{", i + 1));
                    }
                    else if(caracter == '}')
                    {
                        tokens.add(new Token(TipoToken.LLAV_CIERRE, "}", i + 1));
                    } 
                    else if(caracter == '<')
                    {
                        if(nxtcaracter == '=')
                        {
                            tokens.add(new Token(TipoToken.MENOR_IGUAL, "<=", i + 1));
                            i++;
                        }
                        else
                        {
                            tokens.add(new Token(TipoToken.MENOR, "<", i + 1));
                        }
                    }
                    else if(caracter == '>')
                    {
                        if(nxtcaracter == '=')
                        {
                            tokens.add(new Token(TipoToken.MENOR_IGUAL, "<=", i + 1));
                            i++;
                        }
                        else
                        {
                            tokens.add(new Token(TipoToken.MAYOR, ">", i + 1));
                        }
                    }
                    else if(caracter == '!')
                    {
                        if(nxtcaracter == '=')
                        {
                            tokens.add(new Token(TipoToken.DIFERENTE, "!=", i + 1));
                            i++;
                        }
                        else
                        {
                            tokens.add(new Token(TipoToken.NOT, "!", i + 1));
                        }
                    }
                    
                    else if(caracter == ','){
                        tokens.add(new Token(TipoToken.COMA, ",", i + 1));
                    }
                    else if(caracter == '.'){
                        tokens.add(new Token(TipoToken.PUNTO, ".", i + 1));
                    }
                    else if(caracter == ';')
                    {
                        tokens.add(new Token(TipoToken.PUNTO_COMA, ";", i + 1));
                    }
                    else if(Character.isAlphabetic(caracter)){
                        estado = 1;
                        lexema = lexema + caracter;
                        inicioLexema = i;
                    }
                    else if(Character.isDigit(caracter))
                    {
                        estado = 2;
                        lexema = lexema + caracter;
                        inicioLexema = i;
                    }
                    else if(caracter == '"')
                    {
                        estado = 3;
                        lexema = lexema + caracter;
                        inicioLexema = i;
                    }
                    break;

                case 1:
                    if(Character.isAlphabetic(caracter) || Character.isDigit(caracter) ){
                        lexema = lexema + caracter;
                    }
                    else
                    {
                        TipoToken tt = keywords.get(lexema);
                        if(tt == null)
                        {
                            tokens.add(new Token(TipoToken.ID, lexema, inicioLexema + 1));
                        }
                        else
                        {
                            tokens.add(new Token(tt, lexema, inicioLexema + 1));
                        }
                        estado = 0;
                        i--;
                        lexema = "";
                        inicioLexema = 0;
                    }
                    break;
                case 2:
                    if(Character.isDigit(caracter) || caracter == '.' )
                    {
                        lexema = lexema + caracter;
                    }
                    else
                    {
                        if (lexema.contains("."))
                        {
                           try
                           {
                            tokens.add(new Token(TipoToken.NUMBER, lexema, inicioLexema + 1));
                           } 
                           catch (NumberFormatException e)
                           {
                            tokens.add(new Token(TipoToken.ERROR, lexema, inicioLexema + 1));
                           } 
                        }
                        else 
                        {
                            try
                            {
                                tokens.add(new Token(TipoToken.NUMBER, lexema, inicioLexema + 1));
                            } 
                            catch (NumberFormatException e)
                            {
                                tokens.add(new Token(TipoToken.ERROR, lexema, inicioLexema + 1));
                            }
                        }
                        estado = 0;
                        i--;
                        lexema = "";
                        inicioLexema = 0;
                    }
                    break;
                case 3:
                    lexema = lexema + caracter;
                    if (caracter == '"') 
                    {
                        if (lexema.length() > 1 && lexema.charAt(lexema.length() - 2) != '\\')
                        {
                            tokens.add(new Token(TipoToken.STRING, lexema, inicioLexema + 1));
                            estado = 0;
                            lexema = "";
                            inicioLexema = 0;
                        }
                    }
                    break;
            }
        }

        if (estado != 0) {
            throw new Error("Error: No se encontró el cierre de las comillas para el lexema de tipo string.");
        }
        tokens.add(new Token(TipoToken.END, "", source.length()));

        return tokens;
    }
    
    public static String eliminarComentarios(String input) {
        StringBuilder output = new StringBuilder();
        int n = input.length();
        boolean dentroComentario = false;
        
        for (int i = 0; i < n; i++) {
            if (!dentroComentario) {
                if (i < n - 1 && input.charAt(i) == '/' && input.charAt(i + 1) == '*') {
                    dentroComentario = true;
                    i++; // Salta el siguiente caracter (*)
                } else {
                    output.append(input.charAt(i));
                }
            } else {
                if (i < n - 1 && input.charAt(i) == '*' && input.charAt(i + 1) == '/') {
                    dentroComentario = false;
                    i++; // Salta el siguiente caracter (/)
                }
            }
        }
        
        return output.toString();
    }

}