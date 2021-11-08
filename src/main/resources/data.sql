--Datos para carga inicial de la base de datos
delete from Competicion;
delete from Atleta;
delete from Inscripcion;
delete from Clasificacion;

insert into Competicion(id, nombreCarrera, tipoCarrera, distancia, fecha, plazas, estadoCarrera) values 
	('100','Carrera100','asfalto','2 km','2021-11-25',50,'inscripción'),
	('101','Carrera101','montaña','2 km','2021-11-25',50,'inscripción'),
	('102','Carrera102','asfalto','4 km','2021-11-25',50,'inscripción'),
	('103','Carrera103','asfalto','8 km','2022-11-25',50,'inscripción'),
	('104','Carrera104','montaña','35 km','2021-11-25',50,'inscripción'),
	('105','Carrera105','montaña','7 km','2021-12-25',50,'finalizada'),
	('106','Carrera106','asfalto','2 km','2021-12-13',80,'finalizada');
	
insert into Plazo(id, idCompeticion, fechaInicio, fechaFin, cuota) values
	('Plazo1','100','2020-11-16','2021-11-20',30),
	('Plazo1','101','2021-11-16','2021-11-20',30),
	('Plazo1','102','2021-11-16','2021-11-20',30),
	('Plazo1','103','2021-11-16','2021-11-20',30),
	('Plazo1','104','2021-11-16','2021-11-20',30),
	('Plazo1','105','2021-11-16','2021-11-20',30),
	('Plazo1','106','2021-12-01','2021-12-10',30),
	('Plazo1','107','2021-12-01','2021-12-10',30);
	
insert into Competicion(id, nombreCarrera, tipoCarrera, distancia, fecha, plazas, estadoCarrera, dorsalesReservados) values 
	('107','Carrera107','asfalto','2 km','2021-12-13',80,'inscripcion_cerrada',20);
	
insert into Atleta(email, dni, nombre, fechaNacimiento, sexo) values
	('pedro@gmail.com','12121212','Pedro','2001-01-01','H'),
	('elena@gmail.com','10101010','Elena','2001-02-02','M'),
	('juanjo@gmail.com','13131313','Juanjo','2001-02-02','H'),
	('marta@gmail.com','14141414','Marta','2001-02-02','M'),
	('javi@gmail.com','15151515','Javier','2001-02-02','H');
	
insert into Inscripcion(idCompeticion, emailAtleta, nombreAtleta, dniAtleta, nombreCompeticion, categoria, fechaInscripcion,
 			cuotaInscripcion, estadoInscripcion, fechaCambioEstado, dorsal) values
 			
 			('105','pedro@gmail.com','Pedro', 'dni','Carrera105','Juvenil H','2021-12-12',35,'PARTICIPADO','2021-12-25',330),
 			('105','elena@gmail.com','Elena', 'dni','Carrera105','Juvenil M','2021-12-12',35,'PARTICIPADO','2021-12-25',331),
 			('105','juanjo@gmail.com','Juanjo', 'dni', 'Carrera105','Juvenil H','2021-12-10',35,'PARTICIPADO','2021-12-25',332),
 			('105','marta@gmail.com','Marta', 'dni','Carrera105','Juvenil M','2021-12-12',35,'PARTICIPADO','2021-12-25',333),
 			('105','javi@gmail.com','Javier', 'dni','Carrera105','Juvenil H','2021-12-12',35,'PARTICIPADO','2021-12-25',334),
 			
 			('106','pedro@gmail.com','Pedro', 'dni','Carrera106','Juvenil H','2021-12-05',30,'PARTICIPADO','2021-12-13',1),
 			('106','elena@gmail.com','Elena', 'dni','Carrera106','Juvenil M','2021-12-05',30,'PARTICIPADO','2021-12-13',2),
 			('106','juanjo@gmail.com','Juanjo', 'dni', 'Carrera106','Juvenil H','2021-12-05',30,'PARTICIPADO','2021-12-13',3),
 			('106','marta@gmail.com','Marta', 'dni','Carrera106','Juvenil M','2021-12-05',30,'PARTICIPADO','2021-12-13',4),
 			
 			('100','pedro@gmail.com','Pedro', 'dni','Carrera100','Juvenil H','2021-11-12',30,'PRE_INSCRITO','2021-11-12',330),
 			('100','elena@gmail.com','Elena', 'dni','Carrera100','Juvenil M','2021-11-12',30,'PRE_INSCRITO','2021-11-12',331),
 			('101','pedro@gmail.com','Pedro', 'dni', 'Carrera101','Juvenil H','2021-11-10',30,'PRE_INSCRITO','2021-11-03',1),
 			('100','marta@gmail.com','Marta', 'dni','Carrera100','Juvenil M','2021-11-12',30,'PRE_INSCRITO','2021-11-12',332);
 			
insert into Categoria(nombreCategoria, idCompeticion, edadMinima, edadMaxima, sexo) values 
		('Juvenil H', 100, 18, 28, 'H'),
		('Juvenil M', 100, 18, 28, 'M'),
	    ('Juvenil H', 105, 18, 28, 'H'),
	    ('Juvenil M', 105, 18, 28, 'M'),
	    ('Juvenil H', 106, 18, 28, 'H'),
	    ('Juvenil M', 106, 18, 28, 'M'),
		('Juvenil H', 107, 18, 28, 'H'),
	    ('Juvenil M', 107, 18, 28, 'M'),
	    ('Juvenil H', 101, 18, 28, 'H'),
	    ('Juvenil H', 103, 18, 28, 'H'),
	    ('Juvenil M', 103, 18, 28, 'M');
	    	
			
insert into Clasificacion(idCompeticion, emailAtleta, dorsal, tiempoSalida, tiempoLlegada) values
			('100', 'pedro@gmail.com', '330', '0', '150'),
			('100', 'elena@gmail.com', '331', '2', '143');
			
insert into Clasificacion(idCompeticion, emailAtleta, dorsal) values
			('105','pedro@gmail.com',330),
 			('105','elena@gmail.com', 331),
 			('105','juanjo@gmail.com', 332),
 			('105','marta@gmail.com', 333),			
			
			('106','pedro@gmail.com',1),
 			('106','elena@gmail.com', 2),
 			('106','juanjo@gmail.com', 3),
 			('106','marta@gmail.com', 4);
 			
insert into Inscripcion(idCompeticion, emailAtleta, nombreAtleta, dniAtleta, nombreCompeticion, categoria, fechaInscripcion,
 			cuotaInscripcion, estadoInscripcion, fechaCambioEstado) values
			('100','juanjo@gmail.com','Juanjo', 'dni','Carrera100','Juvenil H','2021-11-12',30,'PRE_INSCRITO','2021-11-12'),
			('100','javi@gmail.com','Javier', 'dni','Carrera100','Juvenil H','2021-11-12',30,'PRE_INSCRITO','2021-11-12'),
			
			('106','javi@gmail.com','Javier', 'dni','Carrera106','Juvenil H','2021-12-05',30,'INSCRITO','2021-12-13'),
			
			('107','juanjo@gmail.com','Juanjo', 'dni', 'Carrera107','Juvenil H','2021-12-04',30,'INSCRITO','2021-12-04'),
			('107','pedro@gmail.com','Pedro', 'dni','Carrera107','Juvenil H','2021-12-04',30,'INSCRITO','2021-12-04'),
 			('107','elena@gmail.com','Elena', 'dni','Carrera107','Juvenil M','2021-12-04',30,'PRE_INSCRITO','2021-12-04'),
 			('107','marta@gmail.com','Marta', 'dni','Carrera107','Juvenil M','2021-12-05',30,'INSCRITO','2021-12-05'),
 			('107','javi@gmail.com','Javier', 'dni', 'Carrera107','Juvenil H','2021-12-06',30,'INSCRITO','2021-12-06');
 			
						
insert into Clasificacion(idCompeticion, emailAtleta) values
			('100', 'juanjo@gmail.com');
			
insert into Clasificacion(idCompeticion, emailAtleta, dorsal, tiempoSalida) values
			('100', 'marta@gmail.com', '332', '6');