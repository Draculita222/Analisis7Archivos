package mati.a7a.main;

import mati.a7a.files.FileStereotype;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileLoader {

    public static Map<FileStereotype, File> load(File directory) throws ProcessException {
        if(!directory.isDirectory()) {
            throw new ProcessException("No se seleccionó un directorio");
        }

        String[] list = directory.list();
        // MMEROI cambiar
        if(list.length != 2) {
            throw new ProcessException("Cantidad invalida de archivos en la carpeta seleccionada");
        }

        Map<FileStereotype, File> result = new HashMap<>();
        for(String fileName : list) {
            FileStereotype stereotype = null;
            for(FileStereotype s : FileStereotype.values()) {
                if(fileName.startsWith(s.baseName)) {
                    stereotype = s;
                    break;
                }
            }

            if(!fileName.endsWith(".csv")) {
                throw new ProcessException("Archivo no tiene extensión .csv");
            }

            if(stereotype == null) {
                throw new ProcessException("No se reconoce archivo: " + fileName);
            }

            File f = null;
            switch (stereotype) {
                case A -> {
                    f = new File(directory.getAbsolutePath() + "/" + fileName);
                }
                case B -> {
                    f = new File(directory.getAbsolutePath() + "/" + fileName);
                }
                case C -> {
                }
                case D -> {
                }
                case E -> {
                }
                case F -> {
                }
                case G -> {
                }
            }

            if(result.containsKey(stereotype)) {
               throw new ProcessException("Archivo duplicado: " + fileName);
            }

            result.put(stereotype, f);
        }

        return result;
    }

}
