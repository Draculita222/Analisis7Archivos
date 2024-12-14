package mati.a7a.results;

import mati.a7a.columns.IColumn;

public record ValidationError(IColumn column, String message) {

}
