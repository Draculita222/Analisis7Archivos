package mati.a7a.validations;

import mati.a7a.columns.IColumn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UniqueA {

    public Map<String, List<Integer>> analyze(IColumn column, List<String> values) {
        Map<String, List<Integer>> temp = new HashMap<>();
        int line = 1;
        for(String v : values) {
            if(!temp.containsKey(v)) {
                temp.put(v, new ArrayList<>());
            }
            temp.get(v).add(line);
            line++;
        }
        Map<String, List<Integer>> result = new HashMap<>();
        for(Map.Entry<String, List<Integer>> entry : temp.entrySet()) {
            if(entry.getValue().size() > 1) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    public String createMessage(IColumn column, Map<String, List<Integer>> result) {
        String s = "";
        String n = "";
        for(Map.Entry<String, List<Integer>> entry : result.entrySet()) {
            s += n + "El valor <" + entry.getKey() + "> esta repetido en las lineas: ";
            String sep = "";
            for(Integer i : entry.getValue()) {
                s += sep + i ;
                sep = ", ";
            }
            n = "\n";
        }
        return s;
    }

}
