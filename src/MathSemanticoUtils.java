package br.ufscar.dc.compiladores.math.converter;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.Token;

/*====================================================================================*/
/*        TRABALHO REALIZADO PARA A DISCIPLINA DE CONSTRUÇÃO DE COMPILADORES          */
/*                             PROF: DANIEL LUCRÉDIO                                  */
/*====================================================================================*/
/*    NOME: Micael Valterlânio da Silva               RA: 744349                      */
/*    NOME: Wanderson Moreira                         RA: 744360                      */
/*====================================================================================*/

public class MathSemanticoUtils {
    public static List<String> errosSemanticos = new ArrayList<>();
    
    public static void adicionarErroSemantico(Token t, String mensagem) {
        int linha = t.getLine();
        errosSemanticos.add(String.format("Linha %d: %s", linha, mensagem));
    }
}
