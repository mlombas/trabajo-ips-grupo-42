--Datos para carga inicial de la base de datos
delete from Competicion;
delete from Atleta;
delete from Inscripcion;

insert into Competicion(id, nombreCarrera, tipoCarrera, distancia, cuota, fecha, fechaInicio, 
	fechaFin, plazas, estadoCarrera) values 
	('100','Carrera100','asfalto','2 km',30,'2021-10-25','2021-10-01','2021-10-24',50,'inscripción'),
	('101','Carrera101','montaña','2 km',30,'2021-10-25','2021-10-01','2021-10-24',50,'inscripción');
	

insert into Atleta(email, dni, nombre, fechaNacimiento, sexo) values
	('pedro@gmail.com','12121212','Pedro','2001-01-01','H'),
	('elena@gmail.com','10101010','Elena','2001-02-02','M');
	
insert into Inscripcion(idCompeticion, emailAtleta, nombreAtleta,nombreCompeticion, categoria, fechaInscripcion,
 			cuotaInscripcion, estadoInscripcion, fechaCambioEstado) values
 			('100','pedro@gmail.com','Pedro','Carrera100','Juvenil H','2021-10-12',30,'Pre-inscrito','2021-10-12'),
 			('101','pedro@gmail.com','Pedro','Carrera101','Juvenil H','2021-10-10',30,'Pre-inscrito','2021-10-03');