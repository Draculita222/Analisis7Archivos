package mati.a7a.files;

import mati.a7a.columns.IColumn;

import java.util.Map;

public class Row {
	
	public Map<IColumn, String> map;
	
	public Row(Map<IColumn, String> map) {
		this.map = map;
	}
}
