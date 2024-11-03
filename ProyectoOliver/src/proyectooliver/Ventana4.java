package proyectooliver;

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
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

//VENTANA HEREDA DE JFRAME PARA CREAR LA INTERFAZ GRAFICA, SE USA EXTENDS PARA HEREDAR
public class Ventana4 extends JFrame{
    public JPanel panel;
    private JLabel resultadosLabel;
    private JLabel cuentaLabel;
    private JTable tablaImagenes;
    private DefaultTableModel modeloTabla;
    private JLabel imagenLabel; // Donde se mostrará la imagen seleccionada
    private ArrayList<String> listaFavoritas = new ArrayList<>(); // Lista de canciones favoritas
    
    private ArrayList<String> listaImagenes = new ArrayList<>(); // Lista de imágenes
    private int indiceImagenActual = -1;
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
        resultadosLabel.setBounds(300, 400, 760, 40);
        resultadosLabel.setForeground(Color.white);
        resultadosLabel.setFont(new Font("times new roman", 1, 20));
        panel.add(resultadosLabel);
        
        cuentaLabel = new JLabel("", SwingConstants.CENTER);
        cuentaLabel.setBounds(100, 400, 150, 40);
        cuentaLabel.setForeground(Color.white);
        cuentaLabel.setFont(new Font("times new roman", 1, 20));
        panel.add(cuentaLabel);
        
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
    
    JButton botonMostrarfav = new JButton("Mostrar Fav");
    botonMostrarfav.setFont(new Font("times new roman", 1, 25));
    botonMostrarfav.setBounds(330, 200, 170, 40);
    botonMostrarfav.addActionListener(e -> actualizarFavoritos());
    panel.add(botonMostrarfav);
    
    JButton botonFavoritos = new JButton("Favoritos");
    botonFavoritos.setFont(new Font("times new roman", 1, 25));
    botonFavoritos.setBounds(800, 200, 170, 40);
    botonFavoritos.addActionListener(e -> agregarAFavoritos());
    panel.add(botonFavoritos);
    
    JButton botonEliminar = new JButton("Eliminar");
    botonEliminar.setFont(new Font("times new roman", 1, 25));
    botonEliminar.setBounds(800, 450, 170, 40);
    botonEliminar.addActionListener(e -> eliminarImagen());
    panel.add(botonEliminar);

    // Botón para abrir la imagen seleccionada
    JButton botonAbrir = new JButton("Abrir Imagen");
    botonAbrir.setFont(new Font("times new roman", 1, 25));
    botonAbrir.setBounds(600, 200, 170, 40);
    botonAbrir.addActionListener(e -> abrirImagenSeleccionada());
    panel.add(botonAbrir);
    
    JButton botonAnterior = new JButton("Anterior");
    botonAnterior.setFont(new Font("times new roman", 1, 25));
    botonAnterior.setBounds(800, 520, 170, 40);
    botonAnterior.addActionListener(e -> mostrarImagenAnterior());
    panel.add(botonAnterior);

    JButton botonSiguiente = new JButton("Siguiente");
    botonSiguiente.setFont(new Font("times new roman", 1, 25));
    botonSiguiente.setBounds(800, 570, 170, 40);
    botonSiguiente.addActionListener(e -> mostrarImagenSiguiente());
    panel.add(botonSiguiente);
}

    // Inicializar tabla vacía
    private void tablaImagenes() {
    modeloTabla = new DefaultTableModel(new String[]{"Imagen"}, 0);
    tablaImagenes = new JTable(modeloTabla);
    tablaImagenes.setBackground(Color.lightGray);
    JScrollPane scrollPane = new JScrollPane(tablaImagenes);
    scrollPane.setBounds(150, 250, 600, 150);
    panel.add(scrollPane);

    
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
        

        if (archivos != null) {
            for (File archivo : archivos) {
                nombresImagenes.add(new String[]{archivo.getName()});
                listaImagenes.add(archivo.getAbsolutePath()); // Agregar ruta a la lista
                
            }
        }

        // Llenar la tabla con las imágenes encontradas
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de agregar nuevas filas
        for (String[] imagen : nombresImagenes) {
            modeloTabla.addRow(imagen);
        }

        actualizarResultados(); // Actualizar resultados después de buscar

    panel.revalidate();
    panel.repaint();
    }

    private void abrirImagenSeleccionada() {
    int filaSeleccionada = tablaImagenes.getSelectedRow();
    if (filaSeleccionada != -1) {
        mostrarImagen(filaSeleccionada);
    } else {
        JOptionPane.showMessageDialog(panel, "Por favor, selecciona una imagen.");
    }
}
    
    private void mostrarImagen(int indice) {
    if (indice >= 0 && indice < listaImagenes.size()) {
        String rutaImagen = listaImagenes.get(indice);
        try {
            Image img = ImageIO.read(new File(rutaImagen));
            Image scaledImg = img.getScaledInstance(imagenLabel.getWidth(), imagenLabel.getHeight(), Image.SCALE_SMOOTH);
            imagenLabel.setIcon(new ImageIcon(scaledImg));
            cuentaLabel.setText("Imagen: " + (indice + 1) + " de " + listaImagenes.size());
            indiceImagenActual = indice; // Actualizar el índice actual
        } catch (Exception e) {
            cuentaLabel.setText("Error al abrir la imagen.");
        }
    }
}
    
    private void mostrarImagenAnterior() {
    if (indiceImagenActual > 0) {
        mostrarImagen(indiceImagenActual - 1);
    } else {
        JOptionPane.showMessageDialog(panel, "No hay imagen anterior.");
    }
}

    
    private void mostrarImagenSiguiente() {
    if (indiceImagenActual < listaImagenes.size() - 1) {
        mostrarImagen(indiceImagenActual + 1);
    } else {
        JOptionPane.showMessageDialog(panel, "No hay más imágenes.");
    }
}


    
    private void actualizarResultados() {
    long tamañoTotal = 0;
    int cantidadArchivos = listaImagenes.size(); // Cambia a la lista actual (favoritas o originales)

    for (String ruta : listaImagenes) {
        File archivo = new File(ruta);
        tamañoTotal += archivo.length() / 1000; // Convertir a kb
    }

    resultadosLabel.setText("Cantidad de archivos: " + cantidadArchivos + " | Tamaño total: " + tamañoTotal + " KB aprox");
}
    
    private void agregarAFavoritos() {
        int filaSeleccionada = tablaImagenes.getSelectedRow();
        if (filaSeleccionada != -1) {
            String rutaArchivo = listaImagenes.get(filaSeleccionada);
            File archivoOriginal = new File(rutaArchivo);
            File carpetaFavoritos = new File("C:\\Users\\Mayby\\Desktop\\USB Oliver\\Contenido\\ImagenesFavoritas");

            if (!carpetaFavoritos.exists()) {
                carpetaFavoritos.mkdirs(); // Crea la carpeta si no existe
            }

            File archivoFavorito = new File(carpetaFavoritos, archivoOriginal.getName());

            try {
                Files.copy(archivoOriginal.toPath(), archivoFavorito.toPath());
                JOptionPane.showMessageDialog(panel, "Imagen agregada a favoritos.");
                
            } catch (IOException e) {
                JOptionPane.showMessageDialog(panel, "Error al copiar la imagen: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(panel, "Por favor, selecciona una imagen.");
        }
    }

    private void actualizarFavoritos() {
    listaFavoritas.clear();
    modeloTabla.setRowCount(0); // Limpiar la tabla

    File carpetaFavoritos = new File("C:\\Users\\Mayby\\Desktop\\USB Oliver\\Contenido\\ImagenesFavoritas");
    File[] archivosFavoritos = carpetaFavoritos.listFiles((dir, name) -> 
            name.toLowerCase().endsWith(".jpg") || 
            name.toLowerCase().endsWith(".png") || 
            name.toLowerCase().endsWith(".jfif") ||
            name.toLowerCase().endsWith(".webp"));

    if (archivosFavoritos != null) {
        for (File archivo : archivosFavoritos) {
            modeloTabla.addRow(new String[]{archivo.getName()});
            listaFavoritas.add(archivo.getAbsolutePath());
        }
    }

    listaImagenes = new ArrayList<>(listaFavoritas); // Actualiza listaCanciones con listaFavoritas
    actualizarResultados(); // Actualizar resultados después de cargar favoritos

    panel.revalidate();
    panel.repaint();
}
    
   private void eliminarImagen() {
    int filaSeleccionada = tablaImagenes.getSelectedRow();
    if (filaSeleccionada != -1) {
        String rutaArchivo = listaImagenes.get(filaSeleccionada);
        File archivoAEliminar = new File(rutaArchivo);

        // Verifica si el archivo existe y lo elimina
        if (archivoAEliminar.exists()) {
            int confirmacion = JOptionPane.showConfirmDialog(panel, "¿Estás seguro de que deseas eliminar la imagen?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (archivoAEliminar.delete()) {
                    modeloTabla.removeRow(filaSeleccionada); // Eliminar de la tabla
                    listaImagenes.remove(filaSeleccionada); // Actualizar la lista de imagenes
                    JOptionPane.showMessageDialog(panel, "Imagen eliminada exitosamente.");
                    actualizarResultados(); // Actualizar resultados después de eliminar
                } else {
                    JOptionPane.showMessageDialog(panel, "Error al eliminar la Imagen.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(panel, "La canción no existe.");
        }
    } else {
        JOptionPane.showMessageDialog(panel, "Por favor, selecciona una Imagen para eliminar.");
    }
}
}

