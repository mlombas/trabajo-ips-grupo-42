--Datos para carga inicial de la base de datos
delete from Competicion;
delete from Atleta;
delete from Inscripcion;
delete from Clasificacion;

insert into Competicion(id, nombreCarrera, tipoCarrera, distancia, cuota, fecha, fechaInicio, 
	fechaFin, plazas, estadoCarrera) values 
	('100','Carrera100','asfalto','2 km',30,'2021-11-25','2021-11-01','2021-11-24',50,'inscripción'),
	('101','Carrera101','montaña','2 km',30,'2021-11-25','2021-11-01','2021-11-24',50,'inscripción'),
	('102','Carrera102','asfalto','4 km',30,'2021-11-25','2021-11-01','2021-11-24',50,'inscripción'),
	('103','Carrera103','asfalto','8 km',45,'2022-10-25','2022-10-01','2022-10-24',50,'inscripción'),
	('104','Carrera104','montaña','35 km',90,'2021-11-25','2021-11-01','2021-11-24',50,'inscripción');

insert into Atleta(email, dni, nombre, fechaNacimiento, sexo) values
	('pedro@gmail.com','12121212','Pedro','2001-01-01','H'),
	('elena@gmail.com','10101010','Elena','2001-02-02','M'),
	('juanjo@gmail.com','13131313','Juanjo','2001-02-02','H'),
	('marta@gmail.com','14141414','Marta','2001-02-02','M');
	
insert into Inscripcion(idCompeticion, emailAtleta, nombreAtleta, dniAtleta, nombreCompeticion, categoria, fechaInscripcion,
 			cuotaInscripcion, estadoInscripcion, fechaCambioEstado, dorsal) values
 			('100','pedro@gmail.com','Pedro', 'dni','Carrera100','Juvenil H','2021-11-12',30,'Pre-inscrito','2021-11-12',330),
 			('100','elena@gmail.com','Elena', 'dni','Carrera100','Juvenil M','2021-11-12',30,'Pre-inscrito','2021-11-12',331),
 			('101','pedro@gmail.com','Pedro', 'dni', 'Carrera101','Juvenil H','2021-11-10',30,'Pre-inscrito','2021-11-03',1),
 			('100','marta@gmail.com','Marta', 'dni','Carrera100','Juvenil M','2021-11-12',30,'Pre-inscrito','2021-11-12',332);
 			
insert into Categoria(nombreCategoria, idCompeticion, edadMinima, edadMaxima, sexo) values 
			('Juvenil H', 100, 18, 28, 'H'),
			('Juvenil M', 100, 18, 28, 'M'),
			('Juvenil H', 101, 18, 28, 'H');
			
insert into Clasificacion(idCompeticion, emailAtleta, dorsal, tiempoSalida, tiempoLlegada, posicion) values
			('100', 'pedro@gmail.com', '330', '0', '150', '2'),
			('100', 'elena@gmail.com', '331', '2', '143', '1');
			
insert into Inscripcion(idCompeticion, emailAtleta, nombreAtleta, dniAtleta, nombreCompeticion, categoria, fechaInscripcion,
 			cuotaInscripcion, estadoInscripcion, fechaCambioEstado) values
			('100','juanjo@gmail.com','Juanjo', 'dni','Carrera100','Juvenil H','2021-11-12',30,'Pre-inscrito','2021-11-12');
						
insert into Clasificacion(idCompeticion, emailAtleta) values
			('100', 'juanjo@gmail.com');
			
insert into Clasificacion(idCompeticion, emailAtleta, dorsal, tiempoSalida) values
			('100', 'marta@gmail.com', '332', '6');
			
			
			
			
			
			
			
			
			