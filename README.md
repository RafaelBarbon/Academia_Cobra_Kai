# Academia Cobra Kai
![GitHub Repository Size](https://img.shields.io/github/repo-size/h-ssiqueira/Academia_Cobra_Kai?label=Repository%20Size&style=for-the-badge)

![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)
![Windows](https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white)
![MAC](https://img.shields.io/badge/MAC-000000?style=for-the-badge&logo=macos&logoColor=white)

![Vs Code](https://img.shields.io/badge/Visual_Studio_Code-007ACC?style=for-the-badge&logo=visual%20studio%20code&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Arduino](https://img.shields.io/badge/Arduino-00979D?style=for-the-badge&logo=arduino&logoColor=white)
![Diagrams](https://img.shields.io/badge/Diagrams_net-F08705?style=for-the-badge&logo=diagramsdotnet&logoColor=white)

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![C](https://img.shields.io/badge/C-00599C?style=for-the-badge&logo=c&logoColor=white)


## Sumário
* [Descrição do projeto](#Descrição-do-projeto)
* [Descrição do problema](#Descrição-do-problema)
* [Definição dos requesitos](#Definição-dos-requisitos)
* [Projeto de software](#Projeto-de-software)
* [Apresentação do software](#Apresentação-do-software)
* [Como executar](#Como-executar)
* [Licença](#Licença)
* [Autores](#Autores)

<p align="center"> <img src="imgs/Diagrama_Caso_de_Uso.jpg"> </p>

<p align="center"> <img src="imgs/Diagrama%20de%20Sequencia%20aluno.jpg"> </p>

<p align="center"> <img src="imgs/Diagrama%20de%20Sequencia%20professor.jpg"> </p>

<p align="center"> <img src="imgs/Hierarquia%20dos%20modulos.jpg"> </p>

<p align="center"> <img src="imgs/Arquitetura%20do%20ambiente.jpg"> </p>

<p align="center"> <img src="imgs/Diagrama%20de%20classes.jpg"> </p>

<p align="center"> <img src="imgs/Diagrama%20entidade%20relacionamento.jpg"> </p>

<p align="center"> <img src="imgs/Diagrama%20relacional.jpg"> </p>

## Descrição do projeto
Sistema de gerenciamento de uma academia de artes marciais utilizando JAVA, SQL (Banco de Dados - MySQL) e Linguagem C (arduino). Maiores detalhes a respeito do [enunciado](PROJETO.pdf), [documentação](RAH%20-%20Desenvolvimento%20de%20Sistemas.pdf) e [apresentação do projeto](https://prezi.com/view/QVZTghRVZIM5FeBVW7Gn/) podem ser encontrados clicando nos links.

## Descrição do problema
A Academia Cobra Kai necessita de um programa para gerenciamento da entrada de alunos, professores e a verificação do pagamento da mensalidade. O banco e responsável por disponibilizar uma planilha com as informações sobre o pagamento realizado pelos alunos.

<p align="center"> <img src="Cobra_Kai.jpg"> </p>

O modelo de gestão anterior da academia é de que o aluno deveria trazer consigo sua carteirinha e o comprovante de pagamento da mensalidade do mês vigente para que o mesmo possa adentrar a academia e frequentar as aulas. Porém a academia só teria a resposta oficial do banco no mês seguinte. Algumas das causas do problema são identificadas no diagrama de causa e efeito abaixo:

<p align="center"> <img src="imgs/Diagrama%20causa%20e%20efeito.jpeg"> </p>

## Definição dos requesitos

## Projeto de software

## Apresentação do software

## Como executar

Para executar o código é necessário software MySQL executando em segundo plano, uma conexão de internet (envio de emails), versão 11 do compilador java e o nível de compilação deve ser maior ou igual a 1.8.

Todos os arquivos .java, .jar e .jpg devem estar no mesmo diretório que o arquivo executável.

* Caso seja executado no sistema operacional windows, execute o arquivo cobra_kai.bat em modo administrador.

* Caso executado no sistema operacional Linux, execute o comando `chmode +x cobra_kay.sh && ./cobra_kay.sh`.

* Caso executado no sistema operacional MAC, execute o comando `chmode +x cobra_kay.command && ./cobra_kay.command`.

Ao iniciar o programa solicita a senha do banco de dados (definido como padrão o endereço localhost (127.0.0.1)), caso a senha esteja incorreta, o programa não sincronizará mudanças nem carregará informações do banco de dados para manipulação.

## Licença
Este projeto está licenciado com a [licença MIT](LICENSE)

## Autores
- [Alcides Beato](https://github.com/alcidesbeato)
- [Henrique Siqueira](https://github.com/h-ssiqueira)
- [Rafael Barbon](https://github.com/RafaelBarbon)
