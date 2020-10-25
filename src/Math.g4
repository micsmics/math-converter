/*====================================================================================*/
/*        TRABALHO REALIZADO PARA A DISCIPLINA DE CONSTRUÇÃO DE COMPILADORES          */
/*                             PROF: DANIEL LUCRÉDIO                                  */
/*====================================================================================*/
/*    NOME: Micael Valterlânio da Silva               RA: 744349                      */
/*    NOME: Wanderson Moreira                         RA: 744360                      */
/*====================================================================================*/

grammar Math;

NUM: ('0'..'9')+ ('.')? ('0'..'9')*;
CADEIA: ([a-zA-Z0-9])+;
VAR: '$' ([a-zA-Z0-9])* '$';
ERRO_VARIAVEL: ('$' ~('$')* ('\n' | EOF));
ERRO_NOME_VARIAVEL: '$' ( ([a-zA-Z0-9]) | (~([a-zA-Z0-9] | '$')) )* '$';
ERRO_CONTEUDO_VARIAVEL: ('{' ~('}')* EOF);
WS: ( ' ' | '\t' | '\n' | '\r' ) -> skip;     /* ignora os espaços em branco */

programa: expressao;
expressao: (matriz | chaves | parenteses | limite_integracao | integral_linha | integral_tripla | integral_dupla | integral | limite | somatorio | raiz | binomial | fracao | indice | potencia | expressao_trigonometrica | simbolo_relacional | notacao_logica | operadores | sinal | palavra_reservada | letra | numero | declaracao_variavel | variavel | parent)+;
matriz: 'matriz' '(' linha_matriz (',' linha_matriz)* ')';
linha_matriz: '[' expressao (','  expressao)* ']';
chaves: 'chaves' '(' expressao ')';
parenteses: 'parenteses' '(' expressao ')';
limite_integracao: 'limite_integracao' '(' expressao ',' intervalo ')';
integral_linha: 'integral_linha' '(' (intervalo ',')? (intervalo ',')*  integracao = expressao (',' letra)+ ')';
integral_tripla: 'integral_tripla' '(' (intervalo ',')? (intervalo ',')*  integracao = expressao (',' letra)+ ')';
integral_dupla: 'integral_dupla' '(' (intervalo ',')? (intervalo ',')*  integracao = expressao (',' letra)+ ')';
intervalo: '[' expressao ',' expressao ']';
integral: 'integral' '(' (intervalo ',')? (intervalo ',')*  integracao = expressao (',' letra)+ ')';
limite: 'limite' '(' letra ',' (sinal)? expressao ',' expressao ')';
somatorio: 'somatorio' '(' expressao ',' expressao ',' expressao ')';
raiz: 'raiz' '(' (indice_raiz = expressao ',')? expressao ')';
binomial: 'binomial' '(' (expressao)? (',' expressao)* ')';
fracao: 'fracao' '(' expressao ',' expressao ')';
indice: '_' ( numero | '(' expressao ')');
potencia: '^' ( numero | '(' expressao ')');
expressao_trigonometrica: funcao_trigonometrica (potencia)? '(' expressao ')';
simbolo_relacional: '>' |  '<' | '<=' | '>=' | '<<' | '>>' | '=' | '!=' | 'diferente' | 'aprox' | 'portanto' | 'contido' | 'contidoigual' | 'naocontidoigual' | 'pertence' | 'pertencente' | 'naopertence' | '===' | 'equivalente' | 'mod';
notacao_logica: 'existe' | 'nao-existe' | 'para-todo' | 'nao' | 'ate' | 'seta-direita' | 'seta-esquerda' | 'recebe' | 'implica' | 'implicado-por' | 'se-somente-se' | 'vazio';
operadores: '+' | '-' | '*' | '/';
parent: '(' | ')';
numero: NUM;
variavel: VAR;
sinal: '+' | '-' | 'mais-ou-menos';
letra: (CADEIA | letra_grega);
palavra_reservada: 'exp' | 'infinito' | 'mais-ou-menos' | 'mod';
funcao_trigonometrica: 'sen' | 'cos' | 'tan' | 'cot' | 'arcsen' | 'arccos' | 'arctan' | 'arccot' | 'senh' | 'cosh' | 'tanh' | 'coth' | 'sec' | 'csc';
letra_grega: 'A' | 'alpha' | 'B' | 'beta' | 'Gamma' | 'gamma' | 'Delta' | 'delta' | 'E' | 'epsilon' | 'varepsilon' | 'Z' | 'zeta' | 'H' | 'eta' | 'Theta' | 'theta' | 'vartheta' | 'I' | 'iota' | 'K' | 'kappa' | 'varkappa' | 'Lambda' | 'lambda' | 'M' | 'mu' | 'N' | 'nu' | 'Xi' | 'xi' | 'O' | 'o' | 'Pi' | 'pi' | 'varpi' | 'P' | 'rho' | 'varrho' | 'Sigma' | 'sigma' | 'varsigma' | 'T' | 'tau' | 'Upsilon' | 'upsilon' | 'Phi' | 'phi' | 'varphi' | 'X' | 'chi' | 'Psi' | 'psi' | 'Omega' | 'omega';
declaracao_variavel: VAR '=' '{' expressao '}';