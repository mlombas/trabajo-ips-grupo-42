package model;

import util.AtletaNoValidoException;
import util.ModelException;

public interface Command {

	Object execute() throws AtletaNoValidoException, ModelException;
	
}
