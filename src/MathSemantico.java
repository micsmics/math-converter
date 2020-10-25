package br.ufscar.dc.compiladores.math.converter;

/*====================================================================================*/
/*        TRABALHO REALIZADO PARA A DISCIPLINA DE CONSTRUÇÃO DE COMPILADORES          */
/*                             PROF: DANIEL LUCRÉDIO                                  */
/*====================================================================================*/
/*    NOME: Micael Valterlânio da Silva               RA: 744349                      */
/*    NOME: Wanderson Moreira                         RA: 744360                      */
/*====================================================================================*/

public class MathSemantico extends MathBaseVisitor<Void>{
    TabelaDeSimbolos tabela;
    
    public TabelaDeSimbolos getTabela() {
        return tabela;
    }

    @Override
    public Void visitPrograma(MathParser.ProgramaContext ctx) {
        tabela = new TabelaDeSimbolos();
        return super.visitPrograma(ctx);
    }
    
    @Override
    public Void visitExpressao(MathParser.ExpressaoContext ctx) {
        if (ctx.declaracao_variavel() != null && ctx.declaracao_variavel().size() > 0) {
            //adiciona variável declarada e o seu conteúdo na tabela de simbolos
            for (var declaracao : ctx.declaracao_variavel()) { // percorre a lista de declaracoes
                if (!tabela.existe(declaracao.VAR().getText())) {
                    tabela.adicionar(declaracao.VAR().getText(), declaracao.expressao());
                } else {
                    tabela.alterar(declaracao.VAR().getText(), declaracao.expressao());
                }
            }
        }
        
        if (ctx.variavel() != null && ctx.variavel().size() > 0) { // para cada variável utilizada na expressão
            // percorre toda a lista de variaveis
            for (var variavel : ctx.variavel()) {
                // verifica ela foi declarada, se não for adiciona um erro semântico
                if (!tabela.existe(variavel.getText())) {
                    MathSemanticoUtils.adicionarErroSemantico(variavel.VAR().getSymbol(), "variavel " + variavel.getText() + " nao declarada antes do uso");
                }
            }
        }
        
        return super.visitExpressao(ctx);
    }

    @Override
    public Void visitMatriz(MathParser.MatrizContext ctx) {
        int qtdColunas = -1;
        for (var linha : ctx.linha_matriz()) {
            if (qtdColunas == -1) {
                qtdColunas = linha.expressao().size();
            }
            else {
                if (qtdColunas != linha.expressao().size()) {
                    MathSemanticoUtils.adicionarErroSemantico(linha.getStart(), "O número de colunas da matriz deve ser o mesmo para todas as linhas");
                    return super.visitMatriz(ctx);
                }
            }
        }
        
        return super.visitMatriz(ctx);
    }

    @Override
    public Void visitBinomial(MathParser.BinomialContext ctx) {
        if (ctx.expressao().size() != 2) {
            MathSemanticoUtils.adicionarErroSemantico(ctx.getStart(), "uma expressao binomial deve ter obrigatoriamente dois termos");
        }
        
        return super.visitBinomial(ctx);
    }

    @Override
    public Void visitIntegral(MathParser.IntegralContext ctx) {
        if (ctx.intervalo().size() != ctx.letra().size()) {
            if (ctx.intervalo().size() != 0) {
                MathSemanticoUtils.adicionarErroSemantico(ctx.getStart(), "A quantidade de intervalos na integral e variáveis de integração deve ser a mesma");
            }
        }
        
        return super.visitIntegral(ctx);
    }

    @Override
    public Void visitIntegral_linha(MathParser.Integral_linhaContext ctx) {
        if (ctx.intervalo().size() != ctx.letra().size()) {
            if (ctx.intervalo().size() != 0) {
                MathSemanticoUtils.adicionarErroSemantico(ctx.getStart(), "A quantidade de intervalos na integral de linha e variáveis de integração deve ser a mesma");
            }
        }
        
        return super.visitIntegral_linha(ctx);
    }

    @Override
    public Void visitIntegral_dupla(MathParser.Integral_duplaContext ctx) {
        if (ctx.intervalo().size() != 0 && ctx.intervalo().size() != 2) {
            MathSemanticoUtils.adicionarErroSemantico(ctx.getStart(), "A quantidade de intervalos na integral dupla deve ser 0 (no caso de uma integral indefinida) ou dois intervalos");
        }
        
        if (ctx.letra().size() != 2) {
            MathSemanticoUtils.adicionarErroSemantico(ctx.getStart(), "São necessárias duas variáveis de integração na integral dupla");
        }
        
        return super.visitIntegral_dupla(ctx); 
    }

    @Override
    public Void visitIntegral_tripla(MathParser.Integral_triplaContext ctx) {
        if (ctx.intervalo().size() != 0 && ctx.intervalo().size() != 3) {
            MathSemanticoUtils.adicionarErroSemantico(ctx.getStart(), "A quantidade de intervalos na integral tripla deve ser 0 (no caso de uma integral indefinida) ou três intervalos");
        }
        
        if (ctx.letra().size() != 3) {
            MathSemanticoUtils.adicionarErroSemantico(ctx.getStart(), "São necessárias três variáveis de integração na integral tripla");
        }
        
        return super.visitIntegral_tripla(ctx);
    }
}