package com.oliverlopez.proyectooliver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//VENTANA HEREDA DE JFRAME PARA CREAR LA INTERFAZ GRAFICA, SE USA EXTENDS PARA HEREDAR
public class Ventana3 extends JFrame{
public JPanel panel;
     
    //METODO CONSTRUCTOR 
    public Ventana3(){
        //damos un nombre a la app
        this.setTitle("Video");
        
        //ESTABLECEMOS EL ANCHO Y LARGO DE LA VENTANA
        this.setSize(1080,720);
        
        //centramos la ventana
        this.setLocationRelativeTo(null);
        
        //indicamos la dimension minima de la pantalla
        this.setMinimumSize(new Dimension(720,720));
        
        //le pasamos el metodo donde creamos el panel y las etiquetas y botones
        Componentes();
 
        //cerramos la ejecucion de la app
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    private void Componentes(){
        
        panel();
        etiquetas();
        botones();
    }
    
    //<------------------- PANEL ------------------------->
    private void panel(){
        
        //creacion de un panel
        panel = new JPanel();
        
        // desactivamos el diseño por defecto del panel 
        panel.setLayout(null);
        
        //agregamos el panel a la ventana
        this.getContentPane().add(panel); 
        
        //le damos color a nuestro panel
        panel.setBackground(Color.darkGray);
    }
    
    // <------------------------------- ETIQUETAS ---------------------------------->
    private void etiquetas(){
        
        //creacion de una etiqueta con el texto inicializado y centrado//
        JLabel texto = new JLabel("Accion",SwingConstants.CENTER);
        
        //ubicamos nuestra etiqueta en el panel
        texto.setBounds(150, 150, 170, 40);
        
        //cambiamos el color de las letras
        texto.setForeground(Color.red);
        
        //le quitamos el diseño de la etiqueta por default
        texto.setOpaque(true);
        
        //cambiamos el color del fondo de la etiqueta
        texto.setBackground(Color.black);
        
        texto.setFont(new Font("times new roman",1,30));
        
        //agregamos la etiqueta al panel
        panel.add(texto);
        
        JLabel texto2 = new JLabel("BIENVENIDO A VIDEOS",SwingConstants.CENTER);
        texto2.setBounds(150, 40, 760, 40);
        texto2.setForeground(Color.red);
        texto2.setFont(new Font("times new roman",1,30));
        texto2.setBackground(Color.BLACK);
        texto2.setOpaque(true);
        panel.add(texto2);
}
  
        // <------------------------------- BOTONES ---------------------------------->
 private void botones(){
    
        JButton boton = new JButton("Ir a ver ");
        boton.setFont(new Font("times new roman",1,25));
        boton.setBounds(150, 200, 170, 40);
        panel.add(boton);
        
        
        JButton boton2 = new JButton("Regresar");
        boton2.setFont(new Font("times new roman",1,25));
        boton2.setBounds(800,620, 170, 40);
        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ocultar la ventana actual (Ventana1)
                setVisible(false);

                // Crear y mostrar la ventana2
                Ventana1 VentanaInicio = new Ventana1();
                VentanaInicio.setVisible(true);
            }
        });
        panel.add(boton2);
        
        
 }
}
