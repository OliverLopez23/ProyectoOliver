package com.oliverlopez.proyectooliver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

//VENTANA HEREDA DE JFRAME PARA CREAR LA INTERFAZ GRAFICA, SE USA EXTENDS PARA HEREDAR
public class Ventana4 extends JFrame{
    public JPanel panel;
    private JLabel resultadosLabel;
    private JTable tablaImagenes;
    private DefaultTableModel modeloTabla;
    private JLabel imagenLabel; // Donde se mostrará la imagen seleccionada
    
    private ArrayList<String> listaImagenes = new ArrayList<>(); // Lista de imágenes
     
    //METODO CONSTRUCTOR 
    public Ventana4(){
        //damos un nombre a la app
        this.setTitle("Galería de Imágenes");
        
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
        tablaImagenes();
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
        
        JLabel texto2 = new JLabel("BIENVENIDO A LA GALERÍA DE IMÁGENES",SwingConstants.CENTER);
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
        
        imagenLabel = new JLabel(); // Etiqueta para mostrar la imagen
        imagenLabel.setBounds(150, 450, 600, 200);
        panel.add(imagenLabel);
    }
  
        // <------------------------------- BOTONES ---------------------------------->
    private void botones() {
        JButton botonRegresar = new JButton("Regresar");
        botonRegresar.setFont(new Font("times new roman", 1, 25));
        botonRegresar.setBounds(800, 620, 170, 40);
        botonRegresar.addActionListener(e -> {
            // Ocultar la ventana actual y regresar a la ventana inicial
            setVisible(false);
            Ventana1 VentanaInicio = new Ventana1();
            VentanaInicio.setVisible(true);
        });
        panel.add(botonRegresar);

        JButton botonBuscar = new JButton("Buscar imágenes");
        botonBuscar.setFont(new Font("times new roman", 1, 25));
        botonBuscar.setBounds(150, 200, 170, 40);
        botonBuscar.addActionListener(e -> buscarArchivosImagen());
        panel.add(botonBuscar);
    }

    // Inicializar tabla vacía
    private void tablaImagenes() {
        modeloTabla = new DefaultTableModel(new String[]{"Imagen"}, 0);
        tablaImagenes = new JTable(modeloTabla);
        tablaImagenes.setBackground(Color.lightGray);
        JScrollPane scrollPane = new JScrollPane(tablaImagenes);
        scrollPane.setBounds(150, 250, 600, 150);
        panel.add(scrollPane);

        // Agregar listener para seleccionar imagen
        tablaImagenes.getSelectionModel().addListSelectionListener(e -> abrirImagenSeleccionada());
    }

    private void buscarArchivosImagen() {
        File carpeta = new File("C:\\Users\\Mayby\\Desktop\\USB Oliver\\Contenido");
        File[] archivos = carpeta.listFiles((dir, name) -> 
            name.toLowerCase().endsWith(".jpg") || 
            name.toLowerCase().endsWith(".png") || 
            name.toLowerCase().endsWith(".jfif") ||
            name.toLowerCase().endsWith(".webp"));

        listaImagenes.clear(); // Limpiar la lista antes de agregar nuevas imágenes
        ArrayList<String[]> nombresImagenes = new ArrayList<>();
        long tamañoTotal = 0;

        if (archivos != null) {
            for (File archivo : archivos) {
                nombresImagenes.add(new String[]{archivo.getName()});
                listaImagenes.add(archivo.getAbsolutePath()); // Agregar ruta a la lista
                tamañoTotal += archivo.length() / 1000;
            }
        }

        // Llenar la tabla con las imágenes encontradas
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de agregar nuevas filas
        for (String[] imagen : nombresImagenes) {
            modeloTabla.addRow(imagen);
        }

        int cantidadArchivos = nombresImagenes.size();
        resultadosLabel.setText("Cantidad de archivos: " + cantidadArchivos + " | Tamaño total: " + tamañoTotal + " mb aprox");

        panel.revalidate();
        panel.repaint();
    }

    private void abrirImagenSeleccionada() {
        int filaSeleccionada = tablaImagenes.getSelectedRow();
        if (filaSeleccionada != -1) {
            String rutaImagen = listaImagenes.get(filaSeleccionada);
            try {
                Image img = ImageIO.read(new File(rutaImagen));
                Image scaledImg = img.getScaledInstance(imagenLabel.getWidth(), imagenLabel.getHeight(), Image.SCALE_SMOOTH);
                imagenLabel.setIcon(new ImageIcon(scaledImg));
            } catch (Exception e) {
                resultadosLabel.setText("Error al abrir la imagen.");
            }
        } else {
            JOptionPane.showMessageDialog(panel, "Por favor, selecciona una imagen.");
        }
    }
}
