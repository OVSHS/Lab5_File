/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab5_file;
import java.io.File;
import javax.swing.JTextArea;
/**
 *
 * @author Maria Gabriela
 */
public class Entrada {
   public static File actualizarDirectorio(String comando, File directorioActual, JTextArea area) {
        String cmd = comando.toLowerCase();
        if(cmd.startsWith("cd ")) {
            int pos = comando.indexOf(" ");
            if(pos != -1 && comando.length() > pos + 1) {
                String parametro = comando.substring(pos + 1).trim();
                File nuevo = new File(directorioActual, parametro);
                if(nuevo.exists() && nuevo.isDirectory()) {
                    return nuevo;
                } else {
                    area.append("Directorio no encontrado.\n");
                    return directorioActual;
                }
            }
        } else if(cmd.equals("..")) {
            File padre = directorioActual.getParentFile();
            if(padre != null) {
                return padre;
            }
        }
        return directorioActual;
    }
}
