package proyectooliver;

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
import java.io.File;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

//VENTANA HEREDA DE JFRAME PARA CREAR LA INTERFAZ GRAFICA, SE USA EXTENDS PARA HEREDAR
public class Ventana1 extends JFrame{
    public JPanel panel;
    private JTextField rutaTextField; // Campo de texto para mostrar la ruta seleccionada
    private String rutaSeleccionada; // Variable para almacenar la ruta seleccionada
     
    //METODO CONSTRUCTOR 
    public Ventana1(){
        //damos un nombre a la app
        this.setTitle("Proyecto");
        
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
        JLabel texto = new JLabel("Musica",SwingConstants.CENTER);
        
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
        
        JLabel texto2 = new JLabel("Videos",SwingConstants.CENTER);
        texto2.setBounds(350, 150, 170, 40);
        texto2.setForeground(Color.red);
        texto2.setFont(new Font("times new roman",1,30));
        texto2.setBackground(Color.BLACK);
        texto2.setOpaque(true);
        panel.add(texto2);
        
        JLabel texto3 = new JLabel("BIENVENIDO A SPOTIFY CHINO MY FRIENDS",SwingConstants.CENTER);
        texto3.setBounds(150, 40, 760, 40);
        texto3.setForeground(Color.red);
        texto3.setFont(new Font("times new roman",1,30));
        texto3.setBackground(Color.BLACK);
        texto3.setOpaque(true);
        panel.add(texto3);
        
        JLabel texto4 = new JLabel("Imagenes",SwingConstants.CENTER);
        texto4.setBounds(550, 150, 170, 40);
        texto4.setForeground(Color.red);
        texto4.setFont(new Font("times new roman",1,30));
        texto4.setBackground(Color.BLACK);
        texto4.setOpaque(true);
        panel.add(texto4);
        
        JLabel texto5 = new JLabel("Duplicados",SwingConstants.CENTER);
        texto5.setBounds(150, 300, 170, 40);
        texto5.setForeground(Color.red);
        texto5.setFont(new Font("times new roman",1,30));
        texto5.setBackground(Color.BLACK);
        texto5.setOpaque(true);
        panel.add(texto5);
        
        JLabel texto6 = new JLabel("Buscar ruta",SwingConstants.CENTER);
        texto6.setBounds(350, 300, 170, 40);
        texto6.setForeground(Color.red);
        texto6.setFont(new Font("times new roman",1,30));
        texto6.setBackground(Color.BLACK);
        texto6.setOpaque(true);
        panel.add(texto6);
        
    }
    
    private void botones(){
        
        JButton boton = new JButton("A escuchar");
        boton.setFont(new Font("times new roman",1,25));
        boton.setBounds(150, 200, 170, 40);
        boton.addActionListener(new ActionListener() {
            @Override
        public void actionPerformed(ActionEvent e) {
            // Ocultar la ventana actual (Ventana1)
            setVisible(false);

            // Obtener la ruta seleccionada
            String ruta = getRutaSeleccionada(); // Asegúrate de que getRutaSeleccionada() esté disponible

                // Crear y mostrar la ventana2
                Ventana2 ventana2 = new Ventana2();
                ventana2.setVisible(true);
            }
        });
        panel.add(boton);
        
        
        JButton boton2 = new JButton("A ver");
        boton2.setFont(new Font("times new roman",1,25));
        boton2.setBounds(350, 200, 170, 40);
        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ocultar la ventana actual (Ventana1)
                setVisible(false);

                // Crear y mostrar la ventana2
                Ventana3 ventana3 = new Ventana3();
                ventana3.setVisible(true);
            }
        });
        panel.add(boton2);
        
        JButton boton3 = new JButton("A mirar");
        boton3.setFont(new Font("times new roman",1,25));
        boton3.setBounds(550, 200, 170, 40);
        boton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ocultar la ventana actual (Ventana1)
                setVisible(false);

                // Crear y mostrar la ventana2
                Ventana4 ventana4 = new Ventana4();
                ventana4.setVisible(true);
            }
        });
        panel.add(boton3);
        
        JButton boton4= new JButton("Buscando");
        boton4.setFont(new Font("times new roman",1,25));
        boton4.setBounds(150, 350, 170, 40);
        boton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ocultar la ventana actual (Ventana1)
                setVisible(false);

                // Crear y mostrar la ventana2
                Ventana5 ventana5 = new Ventana5();
                ventana5.setVisible(true);
            }
        });
        panel.add(boton4);
        
         JButton boton5 = new JButton("A la ruta");
        boton5.setFont(new Font("times new roman",1,25));
        boton5.setBounds(350, 350, 170, 40);
        boton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarRuta(); // Llama al método para buscar la ruta
            }
        });
        panel.add(boton5);

        // Agregamos un campo de texto para mostrar la ruta seleccionada
        rutaTextField = new JTextField();
        rutaTextField.setBounds(150, 400, 600, 40);
        rutaTextField.setEditable(false); // Solo lectura
        panel.add(rutaTextField);
    }

    // Método para abrir el explorador de archivos y seleccionar una carpeta
    private void buscarRuta() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Solo carpetas

        int opcion = fileChooser.showOpenDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            File carpetaSeleccionada = fileChooser.getSelectedFile();
            rutaSeleccionada = carpetaSeleccionada.getAbsolutePath(); // Almacena la ruta en la variable
            rutaTextField.setText(rutaSeleccionada); // Muestra la ruta en el campo de texto
        }
    }

    // Método getter para obtener la ruta seleccionada
    public String getRutaSeleccionada() {
        return rutaSeleccionada;
    }
    
    

}