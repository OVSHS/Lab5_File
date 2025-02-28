/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab5_file;
/**
 *
 * @author Maria Gabriela
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class GUI extends JFrame {
     private JTextArea areaSalida;
    private int inicioEntrada;
    private File directorioActual;
    private boolean modoEscritura = false;
    private File archivoEscritura = null;
    
    public GUI() {
        setTitle("CMD");
        setSize(800,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        areaSalida = new JTextArea();
        areaSalida.setBackground(Color.BLACK);
        areaSalida.setForeground(Color.WHITE);
        areaSalida.setFont(new Font("Consolas", Font.PLAIN, 14));
        areaSalida.setLineWrap(true);
        areaSalida.setWrapStyleWord(true);
        areaSalida.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "none");
        
        JScrollPane scroll = new JScrollPane(areaSalida);
        add(scroll, BorderLayout.CENTER);
        
        directorioActual = new File(System.getProperty("user.dir"));
        mostrarBanner();
        imprimirPrompt();
        areaSalida.requestFocusInWindow();
        
        areaSalida.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    if(areaSalida.getCaretPosition() <= inicioEntrada) {
                        e.consume();
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    procesarEntrada();
                }
            }
        });
    }
    
    private void mostrarBanner() {
        String banner = "Microsoft Windows [Version 10.0.26100.3194]\n" +
                        "(c) Microsoft Corporation. Todos los derechos reservados.\n\n";
        areaSalida.setText(banner);
    }
    
    private void imprimirPrompt() {
        String prompt = directorioActual.getAbsolutePath() + ">";
        areaSalida.append(prompt);
        inicioEntrada = areaSalida.getDocument().getLength();
        areaSalida.setCaretPosition(inicioEntrada);
    }
    
    private void procesarEntrada() {
        try {
            String texto = areaSalida.getText();
            String linea = texto.substring(inicioEntrada).trim();
            areaSalida.append("\n");
            if(modoEscritura) {
                String res = escribirArchivo(archivoEscritura, linea + "\n");
                areaSalida.append(res + "\n");
                modoEscritura = false;
                archivoEscritura = null;
                imprimirPrompt();
            } else {
                String[] partes = linea.split(" ", 2);
                String cmd = partes[0].toLowerCase();
                String parametro = "";
                if(partes.length == 2) {
                    parametro = partes[1].trim();
                }
                if(cmd.equals("wr") || cmd.equals("escribir")) {
                    if(parametro.isEmpty()) {
                        areaSalida.append("Uso: wr <archivo.ext>\n");
                        imprimirPrompt();
                    } else {
                        File arch = new File(directorioActual, parametro);
                        if(!arch.exists()) {
                            areaSalida.append("Archivo no encontrado.\n");
                            imprimirPrompt();
                        } else {
                            modoEscritura = true;
                            archivoEscritura = arch;
                            areaSalida.append("Modo escritura iniciado para el archivo: " + parametro + ". Ingrese el texto y presione Enter para guardar.\n");
                            // No se imprime prompt hasta que se presione Enter para guardar el texto
                            inicioEntrada = areaSalida.getDocument().getLength();
                        }
                    }
                } else {
                    String salidaComando = Comandos.ejecutar(linea, directorioActual);
                    areaSalida.append(salidaComando + "\n");
                    directorioActual = Entrada.actualizarDirectorio(linea, directorioActual, areaSalida);
                    imprimirPrompt();
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private String escribirArchivo(File archivo, String contenido) {
        try(FileWriter fw = new FileWriter(archivo, true)) {
            fw.write(contenido);
            return "Texto agregado al archivo.";
        } catch(IOException ex) {
            return "Error: " + ex.getMessage();
        }
    }
}
