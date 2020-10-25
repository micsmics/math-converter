package br.ufscar.dc.compiladores.math.converter;

import br.ufscar.dc.compiladores.math.converter.MathParser.ExpressaoContext;
import java.util.HashMap;
import java.util.Map;

/*====================================================================================*/
/*        TRABALHO REALIZADO PARA A DISCIPLINA DE CONSTRUÇÃO DE COMPILADORES          */
/*                             PROF: DANIEL LUCRÉDIO                                  */
/*====================================================================================*/
/*    NOME: Micael Valterlânio da Silva               RA: 744349                      */
/*    NOME: Wanderson Moreira                         RA: 744360                      */
/*====================================================================================*/

public class TabelaDeSimbolos {
    private final Map<String, ExpressaoContext> tabela;
    
    public TabelaDeSimbolos() {
        this.tabela = new HashMap<>();
    }
    
    public void adicionar(String nome, ExpressaoContext conteudo) {
        tabela.put(nome, conteudo);
    }
    
    public void alterar(String nome, ExpressaoContext conteudo) {
        tabela.replace(nome, conteudo);
    }
    
    public boolean existe(String nome) {
        return tabela.containsKey(nome);
    }
    
    public ExpressaoContext verificar(String nome) {
        return tabela.get(nome);
    }
}
