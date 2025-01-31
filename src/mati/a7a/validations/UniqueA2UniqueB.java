package mati.a7a.validations;

import mati.a7a.columns.IColumn;
import mati.a7a.files.IndexAndValue;
import mati.a7a.main.ProcessException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dadas 2 columnas A y B
 * A    B
 * 1    M
 * 2    N
 * 1    N -- mal
 * Detecta el caso en que existan una entrada A relacionada con más de una entrada B
 * */
public class UniqueA2UniqueB {

    public Map<String, List<String>> analize(IColumn a, IColumn b, List<IndexAndValue> valuesA, List<IndexAndValue> valuesB) throws ProcessException {
        Map<String, List<String>> countMap = new HashMap<>();
        for(int i = 0; i < valuesA.size(); i++) {
        	IndexAndValue va = valuesA.get(i);
        	IndexAndValue vb = valuesB.get(i);
        	if(va == null || vb == null) {
        		continue;
        	}
            if(!countMap.containsKey(va.value)) {
                countMap.put(va.value, new ArrayList<>());
            }
            List<String> values = countMap.get(va.value);
            if(!values.contains(vb.value)) {
                values.add(vb.value);
            }
        }

        Map<String, List<String>> result = new HashMap<>();
        for(Map.Entry<String, List<String>> entry : countMap.entrySet()) {
            if(entry.getValue().size() > 1) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    public String createMessage(IColumn a, IColumn b, Map<String, List<String>> result) {
        String s = "";
        for(Map.Entry<String, List<String>> entry : result.entrySet()) {
            s += "El valor <" + entry.getKey() + "> de la columna " + a.getName() + " tiene asociado más de un valor en la columna " + b.getName() + ": " ;
            String sep = "";
            for(String value : entry.getValue()) {
                s += sep + "<" + value + ">";
                sep = ", ";
            }
        }
        return s;
    }


}
