package mati.a7a.results;

import mati.a7a.columns.IColumn;

public class ValidationError{
	
 public IColumn column; public String message;



public ValidationError(IColumn column, String message) {
	super();
	this.column = column;
	this.message = message;
}
 
 

}
