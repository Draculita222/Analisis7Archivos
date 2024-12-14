package mati.a7a.files;

import mati.a7a.columns.IColumn;

import java.util.Map;

public record Row (Map<IColumn, String> map) {
}
