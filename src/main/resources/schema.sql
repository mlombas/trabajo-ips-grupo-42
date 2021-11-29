--Primero se deben borrar todas las tablas (de detalle a maestro) y lugo anyadirlas (de maestro a detalle)
--(en este caso en cada una de las aplicaciones (tkrun y descuento) se usa solo una tabla, por lo que no hace falta)

--Para giis.demo.tkrun:

drop table Competicion;
drop table Atleta;
drop table Inscripcion;
drop table Categoria;
drop table Clasificacion;
drop table Plazo;
drop table PuntoIntermedio;
drop table PuntoIntermedioClasificacion;
drop table PlazoCancelacion;

create table Competicion (
	id varchar(255) primary key not null, 
	nombreCarrera varchar(255) not null,
	tipoCarrera varchar(255) not null,
	distancia varchar(255) not null,
	fecha date not null,
	plazas int not null,
	cuota decimal(10,2),
	fechaInicio date, 
	fechaFin date, 
	estadoCarrera varchar(255) not null,
	dorsalesReservados int,
	descripcion varchar(255),
	
	check(distancia>0),
	check(plazas>0),
	check(dorsalesReservados>=0)
);

create table Atleta (
	email varchar(255) primary key not null,
	dni varchar(255),
	nombre varchar(255) not null,
	fechaNacimiento date not null, 
	sexo varchar(255) not null,
	club varchar(255)
);

create table Inscripcion (
	idCompeticion varchar(255) not null,
	emailAtleta varchar(255) not null,
	nombreAtleta varchar(255) not null,
	dniAtleta varchar(255),
	clubAtleta varchar(255),
	nombreCompeticion varchar(255),
	categoria varchar(255) not null,
	fechaInscripcion date not null,
	cuotaInscripcion decimal(10,2) not null,
	estadoInscripcion varchar(255) not null,
	fechaCambioEstado date,
	dorsal int,
	devolver decimal(10,2),
	
	primary key(idCompeticion,emailAtleta),
	foreign key(idCompeticion) references Competicion(id),
	foreign key(emailAtleta) references Atleta(email),
	foreign key(nombreAtleta) references Atleta(nombre),
	foreign key(dniAtleta) references Atleta(dni),
	foreign key(cuotaInscripcion) references Competicion(cuota),
	foreign key(nombreCompeticion) references Competicion(nombreCarrera),
	foreign key(categoria) references Categoria(nombreCategoria)
);

create table Categoria (
	nombreCategoria varchar(255) not null,
	idCompeticion varchar(255) not null,
	edadMinima int not null,
	edadMaxima int not null,
	sexo varchar(255),
	
	primary key(nombreCategoria,idCompeticion),
	foreign key(idCompeticion) references Competicion(id)
);

create table Clasificacion(
	idCompeticion varchar(255) not null,
	emailAtleta varchar(255) not null,
	dorsal int,
	tiempoSalida int,
	tiempoLlegada int,
	posicion int,
	club varchar(255),
	diferenciaTiempo int,
	minsByKm int,
	
	primary key(idCompeticion,emailAtleta),
	foreign key(idCompeticion) references Competicion(id),
	foreign key(emailAtleta) references Atleta(email),
	foreign key(dorsal) references Inscripcion(dorsal)
);

create table Plazo(
	id varchar(255) not null,
	idCompeticion varchar(255) not null,
	fechaInicio date not null, 
	fechaFin date not null,
	cuota decimal(10,2) not null,
	
	primary key (id,idCompeticion),
	foreign key(idCompeticion) references Competicion(id),
	
	check(fechaInicio<=fechaFin), 
	check(cuota>0)
);

create table PuntoIntermedio(
	id varchar(255) not null,
	idCompeticion varchar(255) not null,
	tiempoMaximo int,
	distanciaSalida int not null,
	
	primary key (id,idCompeticion),
	foreign key(idCompeticion) references Competicion(id)
);

create table PuntoIntermedioClasificacion(
	idPuntoIntermedio varchar(255) not null,
	emailAtleta varchar(255) not null,
	idCompeticion varchar(255) not null,
	tiempo int,
	
	primary key (idPuntoIntermedio, idCompeticion, emailAtleta),
	foreign key(idCompeticion) references Competicion(id),
	foreign key(idPuntoIntermedio) references PuntoIntermedio(id),
	foreign key(emailAtleta) references Atleta(email)
	
);

create table PlazoCancelacion(
	id varchar(255) not null,
	idCompeticion varchar(255) not null,
	fechaInicio date not null, 
	fechaFin date not null,
	porcentaje decimal(10,2) not null,
	
	primary key (id,idCompeticion),
	foreign key(idCompeticion) references Competicion(id),
	
	check(fechaInicio<=fechaFin), 
	check(porcentaje>=0)
	check(porcentaje<=100)
);