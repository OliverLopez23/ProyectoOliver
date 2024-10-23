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
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;


//VENTANA HEREDA DE JFRAME PARA CREAR LA INTERFAZ GRAFICA, SE USA EXTENDS PARA HEREDAR
public class Ventana2 extends JFrame{
    public JPanel panel;
    private JLabel resultadosLabel;
    private JTable tablaCanciones;
    private DefaultTableModel modeloTabla;
    
    private Clip clip; // Para controlar la reproducción
    private ArrayList<String> listaCanciones = new ArrayList<>(); // Lista de canciones
    private int cancionActual = -1; // Índice de la canción actual
     
    //METODO CONSTRUCTOR 
    public Ventana2(){
        //damos un nombre a la app
        this.setTitle("Musica");
        
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
        tablaCanciones();
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
        JLabel texto = new JLabel("Canciones",SwingConstants.CENTER);
        
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
        
        JLabel texto2 = new JLabel("BIENVENIDO A MUSICA",SwingConstants.CENTER);
        texto2.setBounds(150, 40, 760, 40);
        texto2.setForeground(Color.red);
        texto2.setFont(new Font("times new roman",1,30));
        texto2.setBackground(Color.BLACK);
        texto2.setOpaque(true);
        panel.add(texto2);
        
        resultadosLabel = new JLabel("", SwingConstants.CENTER);
        resultadosLabel.setBounds(150, 400, 760, 40);
        resultadosLabel.setForeground(Color.white);
        resultadosLabel.setFont(new Font("times new roman", 1, 20));
        panel.add(resultadosLabel);
}
  
        // <------------------------------- BOTONES ---------------------------------->
private void botones() {

    JButton boton2 = new JButton("Regresar");
    boton2.setFont(new Font("times new roman", 1, 25));
    boton2.setBounds(800, 620, 170, 40);
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
    
    JButton boton3 = new JButton("Buscar mus");
        boton3.setFont(new Font("times new roman", 1, 25));
        boton3.setBounds(150, 200, 170, 40);
        boton3.addActionListener(e -> buscarArchivosMP3());
        panel.add(boton3);

        JButton botonReproducir = new JButton("A escuchar");
        botonReproducir.setFont(new Font("times new roman", 1, 25));
        botonReproducir.setBounds(500, 200, 170, 40);
        botonReproducir.addActionListener(e -> {
            int filaSeleccionada = tablaCanciones.getSelectedRow();
            if (filaSeleccionada != -1) {
                String cancionSeleccionada = modeloTabla.getValueAt(filaSeleccionada, 0).toString();
                String rutaArchivo = "C:\\Users\\Mayby\\Desktop\\USB Oliver\\Contenido\\" + cancionSeleccionada;
                reproducirAudio(rutaArchivo);
            } else {
                JOptionPane.showMessageDialog(panel, "Por favor, selecciona una canción.");
            }
        });
        panel.add(botonReproducir);
        
         // Botón para parar música
        JButton botonParar = new JButton("Parar");
        botonParar.setFont(new Font("times new roman", 1, 25));
        botonParar.setBounds(150, 450, 170, 40);
        botonParar.addActionListener(e -> pararMusica());
        panel.add(botonParar);

        // Botón para siguiente canción
        JButton botonSiguiente = new JButton("Siguiente");
        botonSiguiente.setFont(new Font("times new roman", 1, 25));
        botonSiguiente.setBounds(400, 450, 170, 40);
        botonSiguiente.addActionListener(e -> siguienteCancion());
        panel.add(botonSiguiente);

        // Botón para anterior canción
        JButton botonAnterior = new JButton("Anterior");
        botonAnterior.setFont(new Font("times new roman", 1, 25));
        botonAnterior.setBounds(150, 500, 170, 40);
        botonAnterior.addActionListener(e -> anteriorCancion());
        panel.add(botonAnterior);

        // Botón para reanudar música
        JButton botonReanudar = new JButton("Reanudar");
        botonReanudar.setFont(new Font("times new roman", 1, 25));
        botonReanudar.setBounds(400, 500, 170, 40);
        botonReanudar.addActionListener(e -> reanudarMusica());
        panel.add(botonReanudar);
    }

    // Inicializar tabla vacía
    private void tablaCanciones() {
        modeloTabla = new DefaultTableModel(new String[]{"Canción"}, 0);
        tablaCanciones = new JTable(modeloTabla);
        tablaCanciones.setBackground(Color.lightGray);
        JScrollPane scrollPane = new JScrollPane(tablaCanciones);
        scrollPane.setBounds(150, 250, 600, 150);
        panel.add(scrollPane);
    }

    private void buscarArchivosMP3() {
    File carpeta = new File("C:\\Users\\Mayby\\Desktop\\USB Oliver\\Contenido");
    File[] archivos = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".wav"));

    listaCanciones.clear(); // Limpiar la lista antes de agregar nuevas canciones
    ArrayList<String[]> nombresCanciones = new ArrayList<>();
    long tamañoTotal = 0;

    if (archivos != null) {
        for (File archivo : archivos) {
            nombresCanciones.add(new String[]{archivo.getName()});
            listaCanciones.add(archivo.getAbsolutePath()); // Agregar ruta a la lista
            tamañoTotal += archivo.length() / 1000000;
        }
    }

    // Llenar la tabla con las canciones encontradas
    modeloTabla.setRowCount(0); // Limpiar la tabla antes de agregar nuevas filas
    for (String[] cancion : nombresCanciones) {
        modeloTabla.addRow(cancion);
    }

    int cantidadArchivos = nombresCanciones.size();
    resultadosLabel.setText("Cantidad de archivos: " + cantidadArchivos + " | Tamaño total: " + tamañoTotal + " mb aprox");

    panel.revalidate();
    panel.repaint();
}

    private void reproducirCancion() {
        int filaSeleccionada = tablaCanciones.getSelectedRow();
        if (filaSeleccionada != -1) {
            cancionActual = filaSeleccionada;
            String rutaArchivo = listaCanciones.get(cancionActual);
            reproducirAudio(rutaArchivo);
        } else {
            JOptionPane.showMessageDialog(panel, "Por favor, selecciona una canción.");
        }
    }

    private void reproducirAudio(String rutaArchivo) {
        try {
            pararMusica(); // Parar cualquier música que esté sonando
            File audioFile = new File(rutaArchivo);
            if (!audioFile.exists()) {
                System.out.println("El archivo no existe: " + rutaArchivo);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            System.out.println("El formato de archivo no es compatible: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error de entrada/salida: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.out.println("No hay líneas de audio disponibles: " + e.getMessage());
        }
    }

    private void pararMusica() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    private void reanudarMusica() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }

    private void siguienteCancion() {
        if (listaCanciones.isEmpty()) return;

        cancionActual = (cancionActual + 1) % listaCanciones.size();
        reproducirCancionPorIndice(cancionActual);
    }

    private void anteriorCancion() {
        if (listaCanciones.isEmpty()) return;

        cancionActual = (cancionActual - 1 + listaCanciones.size()) % listaCanciones.size();
        reproducirCancionPorIndice(cancionActual);
    }

    private void reproducirCancionPorIndice(int indice) {
        if (indice >= 0 && indice < listaCanciones.size()) {
            String rutaArchivo = listaCanciones.get(indice);
            reproducirAudio(rutaArchivo);
        }
    }
}