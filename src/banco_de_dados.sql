create database if not exists academia
default character set utf8
default collate utf8_general_ci;

use academia;

create table if not exists banco(
	cpf_aluno varchar(15) not null,
	pago boolean,
	primary key (cpf_aluno)
)default charset = utf8;

create table if not exists aulas(
	codigo varchar(5) not null,
	horario varchar(20),
	faixa int,
	armas boolean,
	primary key (codigo)
)default charset = utf8;

create table if not exists alunos(
	codigo varchar(5) not null,
	nome varchar(50),
	cpf varchar(15),
	email varchar(50),
	telefone varchar(16),
	atraso int,
	mes_pago boolean,
	valor decimal(5,2),
	nascimento date, /*yyyy-mm-dd*/
	faixa int,
	auxiliar boolean,
	senha varchar(20),
	primary key (codigo),
	foreign key (cpf) references banco(cpf_aluno)
)default charset = utf8;

create table if not exists professores(
	codigo varchar(5) not null,
	nome varchar(50),
	cpf varchar(15) unique,
	email varchar(50),
	telefone varchar(16),
	senha varchar(20),
	nascimento date, /*yyyy-mm-dd*/
	primary key (codigo)
)default charset = utf8;

create table if not exists matriculas(
	id varchar(5) not null,
	id_aluno varchar(5),
	id_aula varchar(5),
	primary key (id),
	foreign key (id_aluno) references alunos(codigo),
	foreign key (id_aula) references aulas(codigo)
)default charset = utf8;

create table if not exists acessos(
	id varchar(5) not null,
	id_professor varchar(5) not null,
	horario datetime, /*yyyy-MM-dd hh-mm-ss*/
	primary key (id),
	foreign key (id_professor) references professores(codigo)
)default charset = utf8;

create table if not exists info(
	id varchar(2) not null,
	v1aula decimal(5,2) not null,
	v2aula decimal(5,2) not null,
	v3aula decimal(5,2) not null,
	v4aula decimal(5,2) not null,
	senha_mestre varchar(20) not null,
	senha_email varchar(20) not null,
	email varchar(50) not null,
	primary key(id)
)default charset = utf8;

insert ignore into info values ('1',100.00,170.00,200.00,250.00,'admin','Hd@4149!','dev.hours88@gmail.com');