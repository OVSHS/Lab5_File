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
public class GUI extends JFrame {
    private JTextArea areaSalida;
    private int inicioEntrada;
    private File directorioActual;
    public GUI() {
        setTitle("CMD");
        setSize(800,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        areaSalida = new JTextArea();
        areaSalida.setBackground(Color.BLACK);
        areaSalida.setForeground(Color.WHITE);
        areaSalida.setFont(new Font("Consolas", Font.PLAIN, 14));
        areaSalida.setCaretColor(Color.WHITE);
        areaSalida.setLineWrap(true);
        areaSalida.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(areaSalida);
        add(scroll, BorderLayout.CENTER);
        directorioActual = new File(System.getProperty("user.dir"));
        imprimirBanner();
        imprimirPrompt();
        areaSalida.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_BACK_SPACE) {
                    if(areaSalida.getCaretPosition()<=inicioEntrada) {
                        e.consume();
                    }
                }
                if(e.getKeyCode()==KeyEvent.VK_ENTER) {
                    e.consume();
                    //agregar para procesar entrada
                }
            }
        });
        this.setVisible(true);
    }
    private void imprimirBanner() {
        String banner = "Microsoft Windows [Version 10.0.26100.3194]\n(c) Microsoft Corporation. Todos los derechos reservados.\n\n";
        areaSalida.setText(banner);
    }
    private void imprimirPrompt() {
        String prompt = directorioActual.getAbsolutePath() + ">";
        areaSalida.append(prompt);
        inicioEntrada = areaSalida.getDocument().getLength();
        areaSalida.setCaretPosition(inicioEntrada);
    }
    //falta agregar para que procese entrada
    
    
}
