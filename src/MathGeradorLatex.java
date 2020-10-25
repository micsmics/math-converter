package br.ufscar.dc.compiladores.math.converter;

/*====================================================================================*/
/*        TRABALHO REALIZADO PARA A DISCIPLINA DE CONSTRUÇÃO DE COMPILADORES          */
/*                             PROF: DANIEL LUCRÉDIO                                  */
/*====================================================================================*/
/*    NOME: Micael Valterlânio da Silva               RA: 744349                      */
/*    NOME: Wanderson Moreira                         RA: 744360                      */
/*====================================================================================*/

public class MathGeradorLatex extends MathBaseVisitor<Void>{
    StringBuilder saida;
    TabelaDeSimbolos tabela;

    public MathGeradorLatex(TabelaDeSimbolos tabelaSemantico) {
        saida = new StringBuilder();
        this.tabela = tabelaSemantico;
    }

    @Override
    public Void visitMatriz(MathParser.MatrizContext ctx) {
        saida.append("\\begin{bmatrix}\n");
        for (var linha : ctx.linha_matriz()) {
            visitLinha_matriz(linha);
        }
        saida.append("\\end{bmatrix}\n");
        
        return null;
    }

    @Override
    public Void visitLinha_matriz(MathParser.Linha_matrizContext ctx) {
        
        saida.append(ctx.expressao(0).getText());
        for (int numExpressao = 1; numExpressao < ctx.expressao().size(); numExpressao++) {
            saida.append(" & " + ctx.expressao(numExpressao).getText());
        }
        saida.append("\\\\\n");
        
        return null;
    }

    @Override
    public Void visitChaves(MathParser.ChavesContext ctx) {
        saida.append("\\left\\{");
        visitExpressao(ctx.expressao());
        saida.append("\\right\\}");
        
        return null;
    }

    @Override
    public Void visitParenteses(MathParser.ParentesesContext ctx) {
        saida.append("\\left(");
        visitExpressao(ctx.expressao());
        saida.append("\\right)");
                
        return null;
    }

    @Override
    public Void visitLimite_integracao(MathParser.Limite_integracaoContext ctx) {
        saida.append("\\left.");
        visitExpressao(ctx.expressao());
        saida.append("\\right|");
        visitIntervalo(ctx.intervalo());
                
        return null;
    }

    @Override
    public Void visitIntegral_linha(MathParser.Integral_linhaContext ctx) {
        if (ctx.intervalo() == null || ctx.intervalo().size() == 0) {
            for (var letra : ctx.letra()) {
                saida.append("\\oint ");
            }
            visitExpressao(ctx.integracao);
            for (var letra : ctx.letra()) {
                saida.append("\\,\\mathrm{d}");
                saida.append(imprimeLetra(letra.getText().substring(1)));
            }
        } else {
            for (var intervalo : ctx.intervalo()) {
                saida.append("\\oint ");
                visitIntervalo(intervalo);
            }
            visitExpressao(ctx.integracao);
            for (var letra : ctx.letra()) {
                saida.append("\\,\\mathrm{d}");
                saida.append(imprimeLetra(letra.getText().substring(1)));
            }
        }
        
        return null;
    }

    @Override
    public Void visitIntegral_tripla(MathParser.Integral_triplaContext ctx) {
        if (ctx.intervalo() == null || ctx.intervalo().size() == 0) {
            saida.append("\\iiint ");
            visitExpressao(ctx.integracao);
            for (var letra : ctx.letra()) {
                saida.append("\\,\\mathrm{d}");
                saida.append(imprimeLetra(letra.getText().substring(1)));
            }
        } else {
            for (var intervalo : ctx.intervalo()) {
                saida.append("\\int ");
                visitIntervalo(intervalo);
            }
            visitExpressao(ctx.integracao);
            for (var letra : ctx.letra()) {
                saida.append("\\,\\mathrm{d}");
                saida.append(imprimeLetra(letra.getText().substring(1)));
            }
        }
        
        return null;
    }

    @Override
    public Void visitIntegral_dupla(MathParser.Integral_duplaContext ctx) {
        if (ctx.intervalo() == null || ctx.intervalo().size() == 0) {
            saida.append("\\iint ");
            visitExpressao(ctx.integracao);
            for (var letra : ctx.letra()) {
                saida.append("\\,\\mathrm{d}");
                saida.append(imprimeLetra(letra.getText().substring(1)));
            }
        } else {
            for (var intervalo : ctx.intervalo()) {
                saida.append("\\int ");
                visitIntervalo(intervalo);
            }
            visitExpressao(ctx.integracao);
            for (var letra : ctx.letra()) {
                saida.append("\\,\\mathrm{d}");
                saida.append(imprimeLetra(letra.getText().substring(1)));
            }
        }
        
        return null;
    }

    @Override
    public Void visitIntervalo(MathParser.IntervaloContext ctx) {
        saida.append("_{");
        visitExpressao(ctx.expressao(0));
        saida.append("}^{");
        visitExpressao(ctx.expressao(1));
        saida.append("}");
        
        return null;
    }

    @Override
    public Void visitIntegral(MathParser.IntegralContext ctx) {
        if (ctx.intervalo() == null || ctx.intervalo().size() == 0) {
            saida.append("\\");
            for (var letra : ctx.letra()) {
                saida.append("i");
            }
            saida.append("nt ");
            visitExpressao(ctx.integracao);
            for (var letra : ctx.letra()) {
                saida.append("\\,\\mathrm{d}");
                saida.append(imprimeLetra(letra.getText().substring(1)));
            }
        } else {
            for (var intervalo : ctx.intervalo()) {
                saida.append("\\int ");
                visitIntervalo(intervalo);
            }
            visitExpressao(ctx.integracao);
            for (var letra : ctx.letra()) {
                saida.append("\\,\\mathrm{d}");
                saida.append(imprimeLetra(letra.getText().substring(1)));
            }
        }
        
        return null;
    }

    @Override
    public Void visitLimite(MathParser.LimiteContext ctx) {
        saida.append("\\lim\\limits_{");
        visitLetra(ctx.letra());
        saida.append(" \\to ");
        if (ctx.sinal() != null) {
            visitSinal(ctx.sinal());
        }
        visitExpressao(ctx.expressao(0));
        saida.append("} ");
        visitExpressao(ctx.expressao(1));
        
        return null;
    }

    @Override
    public Void visitLetra_grega(MathParser.Letra_gregaContext ctx) {
        if (ctx.getText().length() > 1) {
            saida.append(" \\" + ctx.getText() + " ");
        }
        else {
            saida.append(ctx.getText());
        }
        
        return null;
    }
    
    String imprimeLetra(String letra) {
        if (letra.length() > 1) {
            return ("\\" + letra);
        }
        return letra;
    }

    @Override
    public Void visitFuncao_trigonometrica(MathParser.Funcao_trigonometricaContext ctx) {
        saida.append(" ");
        saida.append("\\");
        switch(ctx.getText()) {
            case "sen": saida.append("sin"); break;
            case "arcsen": saida.append("arcsin"); break;
            case "senh": saida.append("sinh"); break;
            default: saida.append(ctx.getText());
        }
        saida.append(" ");
        
        return null;
    }

    @Override
    public Void visitPalavra_reservada(MathParser.Palavra_reservadaContext ctx) {
        saida.append(" ");
        saida.append("\\");
        switch(ctx.getText()) {
            case "mais-ou-menos": saida.append("pm"); break;
            case "mod": saida.append("bmod"); break;
            case "infinito": saida.append("infty"); break;
            default: saida.append(ctx.getText());
        }
        saida.append(" ");
        
        return null;
    }

    @Override
    public Void visitLetra(MathParser.LetraContext ctx) {
        if (ctx.CADEIA() != null) {
            saida.append(ctx.CADEIA().getText());
        } else if (ctx.letra_grega() != null) {
            visitLetra_grega(ctx.letra_grega());
        }
        
        return null;
    }

    @Override
    public Void visitSinal(MathParser.SinalContext ctx) {
        saida.append(" ");
        switch(ctx.getText()) {
            case "mais-ou-menos": saida.append("\\pm"); break;
            default: saida.append(ctx.getText());
        }
        saida.append(" ");
        
        return null;
    }

    @Override
    public Void visitOperadores(MathParser.OperadoresContext ctx) {
        saida.append(ctx.getText());
        
        return null;
    }

    @Override
    public Void visitNotacao_logica(MathParser.Notacao_logicaContext ctx) {
        saida.append(" ");
        switch(ctx.getText()) {
            case "existe": saida.append("\\exists"); break;
            case "nao-existe": saida.append("\\nexists"); break;
            case "para-todo": saida.append("\\forall"); break;
            case "nao": saida.append("\\neg"); break;
            case "ate": saida.append("\\to"); break;
            case "seta-direita": saida.append("\\rightarrow"); break;
            case "seta-esquerda": saida.append("\\leftarrow"); break;
            case "recebe": saida.append("\\leftarrow"); break;
            case "implica": saida.append("\\implies"); break;
            case "implicado-por": saida.append("\\impliedby"); break;
            case "se-somente-se": saida.append("\\Leftrightarrow"); break;
            case "vazio": saida.append("\\varnothing"); break;
            case ",": saida.append(","); break;
            default: saida.append("\\" + ctx.getText());
        }
        saida.append(" ");
        
        return null;
    }

    @Override
    public Void visitSimbolo_relacional(MathParser.Simbolo_relacionalContext ctx) {
        saida.append(" ");
        switch(ctx.getText()) {
            case ">=": saida.append("\\geq"); break;
            case "<=": saida.append("\\leq"); break;
            case "<<": saida.append("\\ll"); break;
            case ">>": saida.append("\\gg"); break;
            case "!=": saida.append("\\neq"); break;
            case "diferente": saida.append("\\neq"); break;
            case "aprox": saida.append("\\approx"); break;
            case "portanto": saida.append("\\therefore"); break;
            case "contido": saida.append("\\subset"); break;
            case "contidoigual": saida.append("\\subseteq"); break;
            case "naocontidoigual": saida.append("\\nsubseteq"); break;
            case "pertence": saida.append("\\in"); break;
            case "pertencente": saida.append("\\in"); break;
            case "naopertence": saida.append("\\notin"); break;
            case "===": saida.append("\\equiv"); break;
            case "equivalente": saida.append("\\equiv"); break;
            case "mod": saida.append("\\bmod"); break;
            default: saida.append(ctx.getText());
        }
        saida.append(" ");
        
        return null;
    }
    
    @Override
    public Void visitSomatorio(MathParser.SomatorioContext ctx){
        saida.append("\\displaystyle\\sum_{");
        visitExpressao(ctx.expressao(0));
        saida.append("}^{");
        visitExpressao(ctx.expressao(1));
        saida.append("} ");
        visitExpressao(ctx.expressao(2));
        return null;
    }
    
    @Override
    public Void visitRaiz(MathParser.RaizContext ctx){
        saida.append("\\sqrt");
        if(ctx.indice_raiz != null){
            saida.append("[");
            visitExpressao(ctx.indice_raiz);
            saida.append("]");
        }
        saida.append("{");
        
        visitExpressao(ctx.expressao(ctx.expressao().size() - 1));
        
        saida.append("}");
        
        return null;
    }
    
    @Override
    public Void visitBinomial(MathParser.BinomialContext ctx){
        saida.append("\\binom{");
        if(ctx.expressao(0) != null){
            visitExpressao(ctx.expressao(0));
            saida.append("}");
            saida.append("{");
        }
        for(int numExpressao = 1; numExpressao < ctx.expressao().size(); numExpressao++){
            saida.append(ctx.expressao(numExpressao).getText());
        }
        saida.append("}");
        return null;
    }
    
    @Override
    public Void visitFracao(MathParser.FracaoContext ctx){
        saida.append("\\frac{");
        visitExpressao(ctx.expressao(0));
        saida.append("}{");
        visitExpressao(ctx.expressao(1));
        saida.append("}");
        
        return null;    
    }
    
    @Override
    public Void visitIndice(MathParser.IndiceContext ctx){
        saida.append("_{");
        if (ctx.numero() != null)
            visitNumero(ctx.numero());
        if (ctx.expressao() != null)
            visitExpressao(ctx.expressao());
        saida.append("}");
        return null; 
    }
    
    @Override
    public Void visitPotencia(MathParser.PotenciaContext ctx){
        saida.append("^{");
        if (ctx.numero() != null)
            visitNumero(ctx.numero());
        if (ctx.expressao() != null)
            visitExpressao(ctx.expressao());
        saida.append("}");
        return null; 
    }
    
    @Override
    public Void visitExpressao_trigonometrica(MathParser.Expressao_trigonometricaContext ctx){
        visitFuncao_trigonometrica(ctx.funcao_trigonometrica());
        
        if(ctx.potencia() != null){
            visitPotencia(ctx.potencia());
        }
        saida.append("(");
        visitExpressao(ctx.expressao());
        saida.append(")");
        
        return null;
    }

    @Override
    public Void visitParent(MathParser.ParentContext ctx) {
        saida.append(ctx.getText());
        
        return null;
    }

    @Override
    public Void visitNumero(MathParser.NumeroContext ctx) {
        saida.append(ctx.getText());        
                
        return null;
    }

    @Override
    public Void visitVariavel(MathParser.VariavelContext ctx) {
        visitExpressao(tabela.verificar(ctx.getText()));
        
        return null;
    }

    @Override
    public Void visitDeclaracao_variavel(MathParser.Declaracao_variavelContext ctx) {
        return null;
    }
}