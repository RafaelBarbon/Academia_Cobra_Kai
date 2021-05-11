create database if not exists academia
default character set utf8
default collate utf8_general_ci;

use academia;

create table if not exists aulas{
	codigo varchar(5),
	horario varchar(20),
	faixa int,
	armas boolean,
	primary key (codigo)
}default charset = utf8;

create table if not exists alunos{
	codigo varchar(5),
	nome varchar(50),
	cpf varchar(15),
	email varchar(50),
	telefone varchar(16),
	atraso int,
	mes-pago boolean,
	valor decimal(4,2),
	nascimento date, /*yyyy-mm-dd*/
	faixa int,
	auxiliar boolean,
	senha varchar(20),
	primary key (codigo),
	primary key (cpf)
}default charset = utf8;

create table if not exists professores{
	codigo varchar(5),
	nome varchar(50),
	cpf varchar(15),
	email varchar(50),
	telefone varchar(16),
	senha varchar(20),
	nascimento date /*yyyy-mm-dd*/
	primary key (codigo),
	primary key (cpf)
}default charset = utf8;

create table if not exists matriculas{
	id varchar(5),
	id-aluno varchar(5),
	id-aula varchar(5),
	primary key (id),
	foreign key (id-aluno) references alunos(codigo),
	foreign key (id-aula) references aulas(codigo)
}default charset = utf8;

create table if not exists acessos{
	id varchar(5),
	id-professor varchar(5),
	horario datetime, /*yyyy-MM-dd hh-mm-ss*/
	primary key (id),
	foreign key (id-professor) references professores(codigo)
}default charset = utf8;

create table if not exists banco{
	cpf varchar(15),
	pago boolean,
	primary key (cpf)
}default charset = utf8;

create table if not exists info{
	v1aula decimal(4,2),
	v2aula decimal(4,2),
	v3aula decimal(4,2),
	v4aula decimal(4,2),
	senha-mestre varchar(20),
	senha-email varchar(20),
	email varchar(20)
}default charset = utf8;