package mati.a7a.unique;

import mati.a7a.columns.IColumn;
import mati.a7a.main.ProcessException;
import mati.a7a.results.ValidationError;
import mati.a7a.results.ValidationResult;

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

    public Map<String, List<String>> analize(IColumn a, IColumn b, List<String> valuesA, List<String> valuesB) throws ProcessException {
        if(valuesA.size() != valuesB.size()) {
            throw new ProcessException("Error al analizar unicidad para columnas: " + a.getName() + " y " + b.getName());
        }

        Map<String, List<String>> countMap = new HashMap<>();
        for(int i = 0; i < valuesA.size(); i++) {
            String va = valuesA.get(i);
            String vb = valuesB.get(i);
            if(!countMap.containsKey(va)) {
                countMap.put(va, new ArrayList<>());
            }
            List<String> values = countMap.get(va);
            if(!values.contains(vb)) {
                values.add(vb);
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
