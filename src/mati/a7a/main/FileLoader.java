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

        if(list.length != 7) {
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

            if(result.containsKey(stereotype)) {
                throw new ProcessException("Archivo duplicado: " + fileName);
            }

            File f = new File(directory.getAbsolutePath() + "/" + fileName);

            result.put(stereotype, f);
        }

        return result;
    }

}
