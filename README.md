<!-- LOGO -->
<br />
<p align="center">
  <a>
    <img src="/imagens/logo.png" alt="Logo" width="600" height="300">
  </a>

  <h3 align="center">Math-Converter</h3>

  <p align="center">
    Uma nova linguagem para escrita de equações matemáticas!
    <br />
  </p>
</p>

<!-- TÓPICOS -->
<!-- 

- instalação do projeto


- demonstração da linguagem

-->


<!-- PRE-REQUISITOS -->
## Pré-requisitos

Para a utilização do projeto é necessário ter instalado:
* [Node.js - 12.19.0 LTS ou superior](https://nodejs.org/en/)
* npm - 6.14.8 ou superior

    ```sh
    npm install npm@latest -g
    ```
* Máquina virtual Java na versão mais recente, 11.0.2 ou superior.

<!-- INSTALAÇÃO -->
## Instalação

1. Cópia do repositório
```sh
git clone https://github.com/micsmics/math-converter
```
2. Instalação das dependencias do projeto
```sh
npm install
```
Agora é só usar :smile:

<!-- COMO USAR -->
## Como usar

1. Crie um arquivo de texto e digite a expressão matemática de acordo com os exemplos exibidos da linguagem proposta pelo projeto.
2. Vá até a pasta do projeto
3. Execute o programa com o seguinte comando:

```sh
java -jar compilador/math-converter.jar arquivo-de-entrada.txt arquivo-de-saida.txt
```
Após a execução do comando acima, serão criados dois arquivos:
  - arquivo de saida, onde contém a expressão convertida para o formato LaTeX
  - programa em Node.js para geração da imagem da expressão com o nome run.js
  
  _é necessário que todos os arquivos (arquivo-de-entrada.txt e arquivo-de-saida.txt) estejam na mesma pasta do projeto math-converter_
  
 Para gerar a imagem da expressão, basta digitar o seguinte comando:
```sh
node run.js
```

Serão gerados dois arquivos:
  - imagem-da-expressao.png
  - imagem-da-expressao.svg
  
<!-- COMO É A LINGUAGEM -->
## Como é a linguagem?

A linguagem apresentada pelo nosso projeto é a seguinte:
```sh
  Para criação de uma matriz de 2 linhas e 3 colunas - matriz([1, 2, 3], [4, 5, 6])
  Para criar uma fração - fracao(A + B, C + D)
  Para utilizar uma expressão trigonométrica - sen^2 (x)
  Para criar uma raiz de n - raiz(n, 2 + x)
  Para colocar uma expressão entre chaves - chaves(A = B + C)
  Para criar um termo binomial - binomial(x, y)
  Para criar um somatório de x = 0 até 100 de uma expressão - somatorio(x = 0, 100, 2x + 3)
  Para criar um limite de x a ±∞ de uma expressão - limite(x, mais-ou-menos infinito, fracao(2, x))
  Para criar uma integral indefinida de uma expressão - integral(x + 2, dx)
  Para criar uma integral dupla indefinida de uma expressão - integral(x + 2 + y, dx, dy) ou integral_dupla(x + 2 + y, dx, dy)
  Para criar uma integral trila indefinida de uma expressão - integral(x + y + z, dx, dy, dz) ou integral_tripla(x + y + z, dx, dy, dz)
  Para criar uma integral definida entre 0 e 1 de uma expressão - integral([0, 1], x + 2, dx)
  Para criar uma integral dupla definida entre 0 e 1, e 0 e 10 de uma expressão - integral([0, 1], [0, 10, x + 2 + y, dx, dy) ou integral_dupla([0, 1], [0, 10, x + 2 + y, dx, dy)
  Para criar uma integral trila definida entre 0 e 1, 0 e 10, e 0 e 5 de uma expressão - integral([0, 1], [0, 10], [0, 5], x + y + z, dx, dy, dz) ou integral_tripla([0, 1], [0, 10], [0, 5], x + y + z, dx, dy, dz)
  Para criar uma integral de linha indefinida - integral_linha(x + 2, dx)
  Para criar uma integral de linha definida entre 0 e 10 - integral_linha([0, 10], x + 2, dx)
  Para declarar uma variável - $nome$ = {B + C}
  Para utilizar a variável em uma expressão - A = $nome$
```

<!-- EXEMPLOS DE EXPRESSOES GERADAS -->
## Exemplos de expressões geradas

![Exemplo Matriz](imagens/exemplo-matriz.png)
![Exemplo Fracao](imagens/exemplo-fracao.png)
![Exemplo Raiz](imagens/exemplo-raiz.png)
![Exemplo Chaves](imagens/exemplo-chaves.png)
![Exemplo Binomial](imagens/exemplo-binomial.png)


<!-- O QUE FOI IMPLEMENTADO -->

<!-- ANALISADOR LEXICO -->

<!-- EXEMPLOS ANALISADOR LEXICO -->

<!-- ANALISADOR SINTATICO -->

<!-- EXEMPLOS ANALISADOR SINTATICO -->

<!-- ANALISADOR SEMANTICO -->

<!-- EXEMPLOS ANALISADOR SEMANTICO -->

<!-- GERADOR DA EXPRESSAO EM LaTeX -->
