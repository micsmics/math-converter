package br.ufscar.dc.compiladores.math.converter;

import br.ufscar.dc.compiladores.math.converter.MathParser.ExpressaoContext;
import br.ufscar.dc.compiladores.math.converter.MathParser.ProgramaContext;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

/*====================================================================================*/
/*        TRABALHO REALIZADO PARA A DISCIPLINA DE CONSTRUÇÃO DE COMPILADORES          */
/*                             PROF: DANIEL LUCRÉDIO                                  */
/*====================================================================================*/
/*    NOME: Micael Valterlânio da Silva               RA: 744349                      */
/*    NOME: Wanderson Moreira                         RA: 744360                      */
/*====================================================================================*/

public class Principal {
    public static void main(String[] args) throws IOException {
        // verifica se foram passados os dois argumentos
        if (args.length != 2) {
            System.err.println("Por favor, insira os argumentos de acordo com o padrão:");
            System.err.println("java -jar \\compilador\\math-converter.jar arquivo-de-entrada.txt arquivo-de-saida.txt\n");
            return;
        }   
        
        CharStream cs = CharStreams.fromFileName(args[0]); //usada para ler arquivo de entrada
        FileOutputStream writer = new FileOutputStream(args[1]); //usada para escrever no arquivo de saída
        MathLexer lex = new MathLexer(cs);
        MathErrorListener mathErrorListener = new MathErrorListener(); //cria uma instancia de LaErrorListener que vai sobrescrever o syntaxError()
        
        Token t = null;
        
        // laço de repetição para ler todos os Tokens até que seja retornado um Token.EOF indicando o fim de arquivo
        while((t = lex.nextToken()).getType() != Token.EOF) {
            String token_right = "'" + t.getText() + "'"; // usada na verificação para montagem do token de acordo com o que é necessário imprimir <...,token_right>
            
            // verifica se foi indentificado algum dos erros léxicos definidos na gramática
            switch (MathLexer.VOCABULARY.getDisplayName(t.getType())) {
                case "ERRO_VARIAVEL":
                    writer.write(("Linha " + t.getLine() + ": variável nao fechada\n").getBytes());
                    writer.write(("Fim da compilacao\n").getBytes());
                    writer.close();
                    return;
                case "ERRO_NOME_VARIAVEL":
                    writer.write(("Linha " + t.getLine() + ": caractere inválido no nome da variável\n").getBytes());
                    writer.write(("Fim da compilacao\n").getBytes());
                    writer.close();
                    return;
                case "ERRO_CONTEUDO_VARIAVEL":
                    writer.write(("Linha " + t.getLine() + ": limitador de conteudo da variável não fechado\n").getBytes());
                    writer.write(("Fim da compilacao\n").getBytes());
                    writer.close();
                    return;
            }
        }
        
        //move o ponteiro de leitura pro começo do arquivo, adiciona o listener no LaLexer
        cs.seek(0);
        lex.setInputStream(cs);
        lex.removeErrorListeners();
        lex.addErrorListener(mathErrorListener);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        //instancia o parser e inicia a analise sintatica
        MathParser parser = new MathParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(mathErrorListener);
        parser.expressao();
       
        //apresenta a mensagem de erro sintático e de fim de compilacao
        if (mathErrorListener.getErrorMessage() != null) {
            writer.write(mathErrorListener.getErrorMessage().getBytes());
            writer.close();
            return;
        }
        
        //move o ponteiro de leitura pro começo do arquivo, adiciona o listener no MathLexer
        cs.seek(0);
        lex.setInputStream(cs);
        tokens = new CommonTokenStream(lex);
        parser = new MathParser(tokens);
        //instancia a arvore e inicia a analise semantica
        ProgramaContext arvore = parser.programa();
        MathSemantico as = new MathSemantico();
        as.visitPrograma(arvore);
        List<String> errosSemanticos = MathSemanticoUtils.errosSemanticos;
        
        for (var erroSemantico : errosSemanticos) {
            writer.write((erroSemantico + "\n").getBytes());
        }
        
        // leitura do arquivo de exemplo: run.js
        FileReader nodeFile = new FileReader(System.getProperty("user.dir") + "/src/exemplo.js"); 
        String stringNodeFile = "";
        int i; 
        while ((i = nodeFile.read()) != -1) 
            stringNodeFile += (char) i;
        
        if(MathSemanticoUtils.errosSemanticos.isEmpty()) {
            MathGeradorLatex magl = new MathGeradorLatex(as.getTabela());
            magl.visitPrograma(arvore);
            writer.write(magl.saida.toString().getBytes());
            
            FileOutputStream writerNodeFile = new FileOutputStream(System.getProperty("user.dir") + "/run.js"); //usada para escrever no arquivo de saída
            
            String programaSaida = stringNodeFile.replaceAll("---expressao---", magl.saida.toString().replaceAll("\\\\", "\\\\\\\\\\\\\\\\").replaceAll("\n", ""));
            writerNodeFile.write(programaSaida.getBytes());
        } else {
            writer.write(("Fim da compilacao\n").getBytes());
        }
        
        writer.close();
    }
    
}
