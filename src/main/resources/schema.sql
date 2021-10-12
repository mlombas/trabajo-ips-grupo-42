--Primero se deben borrar todas las tablas (de detalle a maestro) y lugo anyadirlas (de maestro a detalle)
--(en este caso en cada una de las aplicaciones (tkrun y descuento) se usa solo una tabla, por lo que no hace falta)

--Para giis.demo.tkrun:

drop table Competicion;
drop table Atleta;
drop table Inscripcion;

create table Competicion (
	id int primary key not null, 
	nombreCarrera varchar(255) not null,
	tipoCarrera varchar(255) not null,
	distancia varchar(255) not null,
	cuota decimal(10,2) not null,
	fecha date not null, 
	fechaInicio date not null, 
	fechaFin date not null, 
	plazas int not null,
	estadoCarrera varchar(255) not null,
	
	check(inicio<=fin), 
	check(fin<fecha),
	check(distancia>0),
	check(plazas>0),
	check(cuota>0)
);

create table Atleta (
	email varchar(255) primary key not null,
	dni varchar(255) not null,
	nombre varchar(255) not null,
	fechaNacimiento date not null, 
	sexo varchar(255) not null
);

create table Inscripcion (
	idCompeticion varchar(255) not null,
	emailAtleta varchar(255) not null,
	nombreAtleta varchar(255) not null,
	categoria varchar(255) not null,
	fechaInscripcion date not null,
	estadoInscripcion varchar(255) not null,
	fechaCambioEstado date not null,
	posicion int,
	tiempo decimal(10,2),
	
	primary key(idCompeticion,emailAtleta),
	foreign key(idCompeticion) references Competicion(id),
	foreign key(emailAtleta) references Atleta(email),
	foreign key(nombreAtleta) references Atleta(nombre)
);

