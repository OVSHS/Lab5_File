/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab5_file;
import java.io.File;
import java.io.IOException;
/**
 *
 * @author Mario
 */
public class MiFile {
     private File mifile;
    
    public MiFile(File file) {
        this.mifile = file;
    }
    
    public boolean crearFile() throws IOException {
        return mifile.createNewFile();
    }
    
    public boolean crearFolder() {
        return mifile.mkdirs();
    }
    
    public File getFile() {
        return mifile;
    }
    
    public boolean borrar() {
        return mifile.delete();
    }
}
