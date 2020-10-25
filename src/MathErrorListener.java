package br.ufscar.dc.compiladores.math.converter;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

/*====================================================================================*/
/*        TRABALHO REALIZADO PARA A DISCIPLINA DE CONSTRUÇÃO DE COMPILADORES          */
/*                             PROF: DANIEL LUCRÉDIO                                  */
/*====================================================================================*/
/*    NOME: Micael Valterlânio da Silva               RA: 744349                      */
/*    NOME: Wanderson Moreira                         RA: 744360                      */
/*====================================================================================*/

public class MathErrorListener extends BaseErrorListener{
    String error_message = null; //vai armazenar a mensagem de erro que queremos exibir

    public String getErrorMessage() {
        return error_message;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        String erro_simbolo = ((Token) offendingSymbol).getText();
        
        if (((Token) offendingSymbol).getType() == Token.EOF) // verificação apenas para imprimir o EOF da forma que precisa ser impressa, sem "<" e ">"
            erro_simbolo = "EOF";
        
        if (error_message == null) // verificação para apresentar apenas a primeira mensagem de erro
            error_message = ("Linha " + line + ": erro sintatico proximo a " + erro_simbolo + "\n");
    }
}
