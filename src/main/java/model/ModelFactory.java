package model;

import controller.atleta.AtletaCrudService;
import controller.atleta.AtletaCrudServiceImpl;
import controller.competicion.CompeticionCrudService;
import controller.competicion.CompeticionCrudServiceImpl;
import controller.organizador.OrganizadorCrudService;
import controller.organizador.OrganizadorCrudServiceImpl;

public class ModelFactory {

	public static AtletaCrudService forAtletaCrudService() {
		return new AtletaCrudServiceImpl();
	}
	
	public static CompeticionCrudService forCarreraCrudService() {
		return new CompeticionCrudServiceImpl();
	}
	
	public static OrganizadorCrudService forOrganizadorCrudService() {
		return new OrganizadorCrudServiceImpl();
	}
	
}