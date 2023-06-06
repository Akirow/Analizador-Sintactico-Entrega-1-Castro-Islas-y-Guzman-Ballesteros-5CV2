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
public enum TipoToken {
     //PALABRAS RESERVADAS
        SUPER, TRUE,
        FALSE, NULL,
        THIS, NUMBER,
        STRING, AND,
        OR, WHILE,
        ELSE, FOR,
        RETURN, PRINT,
        FUN, CLASS,
        IF, VAR,
        //OPERADORES
        SUMA, RESTA,
        MULTI, DIV,
        IGUAL, ASIG,
        DIFERENTE, MAYOR,
        MENOR, MAYOR_IGUAL,
        MENOR_IGUAL,
        //SIMBOLOS
        PAR_ABRE, PAR_CIERRE,
        LLAV_ABRE, LLAV_CIERRE,
        COMA, PUNTO, PUNTO_COMA,
        NOT,
        //OTROS
        ID, END, 
        ERROR, 
}

