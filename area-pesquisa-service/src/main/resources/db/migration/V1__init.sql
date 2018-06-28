create table area_pesquisa (
  id serial,
  nome varchar(100) not null,
  descricao varchar(500),
  PRIMARY KEY(id)
);

create table unidade (
  id serial,
  nome varchar(100) not null,
  cidade varchar(100) not null,
  PRIMARY KEY(id)
);

create table professor (
  id serial,
  nome varchar(100) not null,
  grau_academico varchar(100) not null,
  unidade_id integer not null,
  PRIMARY KEY(id),
  FOREIGN KEY(unidade_id) REFERENCES unidade(id)
);

create table professor_area_pesquisa (
  professor_id integer not null,
  area_pesquisa_id integer not null,
  PRIMARY KEY(professor_id, area_pesquisa_id),
  FOREIGN KEY(professor_id) REFERENCES professor(id),
  FOREIGN KEY(area_pesquisa_id) REFERENCES area_pesquisa(id)
);
