/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab5_file;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author Mario
 */
public class Comandos {
   public static String ejecutar(String comando, File directorioActual) {
        if(comando == null || comando.length() == 0) {
            return "";
        }
        String cmd = comando.toLowerCase();
        if(cmd.startsWith("mkdir")) {
            if(comando.length() <= 5) return "Comando Incorrecto";
            String parametro = comando.substring(6).trim();
            return crearCarpeta(parametro, directorioActual);
        } else if(cmd.startsWith("mfile")) {
            if(comando.length() <= 5) return "Comando Incorrecto";
            String parametro = comando.substring(6).trim();
            return crearArchivo(parametro, directorioActual);
        } else if(cmd.startsWith("rm")) {
            if(comando.length() <= 2) return "Comando Incorrecto";
            String parametro = comando.substring(3).trim();
            return borrarElemento(parametro, directorioActual);
        } else if(cmd.equals("dir")) {
            return listar(directorioActual);
        } else if(cmd.equals("date")) {
            return obtenerFecha();
        } else if(cmd.equals("time")) {
            return obtenerHora();
        } else if(cmd.startsWith("rd") || cmd.startsWith("leer")) {
            String parametro = "";
            if(cmd.startsWith("rd")) {
                if(comando.length() <= 2) return "Comando Incorrecto";
                parametro = comando.substring(3).trim();
            } else {
                if(comando.length() <= 4) return "Comando Incorrecto";
                parametro = comando.substring(5).trim();
            }
            return leerArchivo(parametro, directorioActual);
        } else {
            return "\"" + comando + "\" no se reconoce como un comando interno o externo,\nprograma o archivo por lotes ejecutable.";
        }
    }
    
    private static String crearCarpeta(String nombre, File base) {
        File carpeta = new File(base, nombre);
        if(carpeta.exists()) return "La carpeta ya existe.";
        if(carpeta.mkdirs()) {
            return "Carpeta creada: " + carpeta.getName();
        } else {
            return "Error al crear la carpeta.";
        }
    }
    
    private static String crearArchivo(String nombre, File base) {
        File archivo = new File(base, nombre);
        if(archivo.exists()) return "El archivo ya existe.";
        try {
            if(archivo.createNewFile()) {
                return "Archivo creado: " + archivo.getName();
            } else {
                return "Error al crear el archivo.";
            }
        } catch(IOException ex) {
            return "Excepcion: " + ex.getMessage();
        }
    }
    
    private static String borrarElemento(String nombre, File base) {
        File elemento = new File(base, nombre);
        if(!elemento.exists()) return "Elemento no encontrado.";
        if(elemento.isDirectory()) {
            if(borrarCarpeta(elemento)) {
                return "Carpeta eliminada.";
            } else {
                return "Error al eliminar la carpeta.";
            }
        } else {
            if(elemento.delete()) {
                return "Archivo eliminado.";
            } else {
                return "Error al eliminar el archivo.";
            }
        }
    }
    private static boolean borrarCarpeta(File carpeta) {
        File[] hijos = carpeta.listFiles();
        if(hijos != null) {
            for(File f : hijos) {
                if(f.isDirectory()) {
                    if(!borrarCarpeta(f)) {
                        return false;
                    }
                } else {
                    if(!f.delete()) {
                        return false;
                    }
                }
            }
        }
        return carpeta.delete();
    }
    
    private static String listar(File base) {
        if(!base.isDirectory()) {
        return "Accion no permitida.";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("Contenido de ").append(base.getAbsolutePath()).append(":\n");
    File[] hijos = base.listFiles();
    int numDirs = 0;
    int numFiles = 0;
    long totalBytes = 0;
    if(hijos != null) {
        for(File f : hijos) {
            if(f.isDirectory()) {
                numDirs++;
                sb.append("Carpeta: ").append(f.getName()).append("\n");
            } else {
                numFiles++;
                totalBytes += f.length();
                sb.append("Archivo: ").append(f.getName()).append(" (").append(f.length()).append(" bytes)").append("\n");
            }
        }
    }
    sb.append(totalBytes).append(" bytes en archivos\n");
    sb.append(numDirs).append(" carpetas\n");
    sb.append(numFiles).append(" archivos\n");
    return sb.toString();
    }
    
    private static String obtenerFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Fecha: " + sdf.format(new Date());
    }
    
    private static String obtenerHora() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return "Hora: " + sdf.format(new Date());
    }
    
    private static String leerArchivo(String nombre, File base) {
        File archivo = new File(base, nombre);
        if(!archivo.exists()) return "Archivo no encontrado.";
        StringBuilder sb = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while((linea = br.readLine()) != null) {
                sb.append(linea).append("\n");
            }
        } catch(IOException ex) {
            return "Error: " + ex.getMessage();
        }
        return sb.toString();
    }
    
}
