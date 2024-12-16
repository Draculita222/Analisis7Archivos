package mati.a7a.validations;

import mati.a7a.columns.IColumn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AtLeastOneEntry {

    /**
     * items -> tabla con valores
     * entry -> tabla en la cual debe haber al menos un entrada para cada item
     * */
    public List<String> oneEntryForAllItems(List<String> items, List<String> entries) {
        List<String> result = new ArrayList<>();
        for(String item : items) {
            if(!entries.contains(item)) {
                result.add(item);
            }
        }
        return result;
    }

    public String createMessage(IColumn column, List<String> result) {
        String str = "No existen entradas para los valores: ";
        String sep = "";
        for(String v : result) {
            str += sep + v;
            sep = ", ";
        }
        return str;
    }

}
