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
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

// JavaFX imports
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Ventana2 extends JFrame {
    
    public JPanel panel;
    private JLabel resultadosLabel;
    private JTable tablaCanciones;
    private DefaultTableModel modeloTabla;
    
    private MediaPlayer mediaPlayer; // Para controlar la reproducción de MP3
    private ArrayList<String> listaCanciones = new ArrayList<>(); // Lista de canciones
    private ArrayList<String> listaFavoritas = new ArrayList<>(); // Lista de canciones favoritas
    private int cancionActual = -1; // Índice de la canción actual
    private Duration pauseTime;

    // Inicializamos JavaFX
    private final JFXPanel jfxPanel = new JFXPanel();
    
    public Ventana2() {
        // Configuración de la ventana
        this.setTitle("Musica");
        this.setSize(1080, 720);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(720, 720));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Añadir componentes
        Componentes();
    }

    private void Componentes() {
        panel();
        etiquetas();
        botones();
        tablaCanciones();
    }
    
    //<------------------- PANEL ------------------------->    
    private void panel() {
        panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);
        panel.setBackground(Color.darkGray);
    }
    
    // <------------------------------- ETIQUETAS ---------------------------------->
    private void etiquetas() {
        JLabel texto = new JLabel("Canciones", SwingConstants.CENTER);
        texto.setBounds(150, 150, 170, 40);
        texto.setForeground(Color.red);
        texto.setOpaque(true);
        texto.setBackground(Color.black);
        texto.setFont(new Font("times new roman", 1, 30));
        panel.add(texto);
        
        JLabel texto2 = new JLabel("BIENVENIDO A MUSICA", SwingConstants.CENTER);
        texto2.setBounds(150, 40, 760, 40);
        texto2.setForeground(Color.red);
        texto2.setFont(new Font("times new roman", 1, 30));
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
        boton2.addActionListener(e -> pararMusica());
        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
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
        
        JButton botonMostrarfav = new JButton("Mostrar Fav");
        botonMostrarfav.setFont(new Font("times new roman", 1, 25));
        botonMostrarfav.setBounds(320, 200, 170, 40);
        botonMostrarfav.addActionListener(e -> actualizarFavoritos());
        panel.add(botonMostrarfav);

        JButton botonReproducir = new JButton("A escuchar");
        botonReproducir.setFont(new Font("times new roman", 1, 25));
        botonReproducir.setBounds(600, 200, 170, 40);
        botonReproducir.addActionListener(e -> reproducirCancion());
        panel.add(botonReproducir);

        JButton botonParar = new JButton("Parar");
        botonParar.setFont(new Font("times new roman", 1, 25));
        botonParar.setBounds(150, 450, 170, 40);
        botonParar.addActionListener(e -> pararMusica());
        panel.add(botonParar);

        JButton botonSiguiente = new JButton("Siguiente");
        botonSiguiente.setFont(new Font("times new roman", 1, 25));
        botonSiguiente.setBounds(400, 450, 170, 40);
        botonSiguiente.addActionListener(e -> siguienteCancion());
        panel.add(botonSiguiente);

        JButton botonAnterior = new JButton("Anterior");
        botonAnterior.setFont(new Font("times new roman", 1, 25));
        botonAnterior.setBounds(150, 500, 170, 40);
        botonAnterior.addActionListener(e -> anteriorCancion());
        panel.add(botonAnterior);

        JButton botonReanudar = new JButton("Reanudar");
        botonReanudar.setFont(new Font("times new roman", 1, 25));
        botonReanudar.setBounds(400, 500, 170, 40);
        botonReanudar.addActionListener(e -> reanudarMusica());
        panel.add(botonReanudar);

        // Botón para agregar a favoritos
        JButton botonFavoritos = new JButton("Favoritos");
        botonFavoritos.setFont(new Font("times new roman", 1, 25));
        botonFavoritos.setBounds(800, 200, 170, 40);
        botonFavoritos.addActionListener(e -> agregarAFavoritos());
        panel.add(botonFavoritos);
        
        JButton botonEliminar = new JButton("Eliminar");
        botonEliminar.setFont(new Font("times new roman", 1, 25));
        botonEliminar.setBounds(600, 450, 170, 40);
        botonEliminar.addActionListener(e -> eliminarCancion());
        panel.add(botonEliminar);
        
    }

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
    File[] archivos = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));

    listaCanciones.clear();
    ArrayList<String[]> nombresCanciones = new ArrayList<>();

    if (archivos != null) {
        for (File archivo : archivos) {
            nombresCanciones.add(new String[]{archivo.getName()});
            listaCanciones.add(archivo.getAbsolutePath());
        }
    }

    modeloTabla.setRowCount(0);
    for (String[] cancion : nombresCanciones) {
        modeloTabla.addRow(cancion);
    }

    actualizarResultados(); // Actualizar resultados después de buscar

    panel.revalidate();
    panel.repaint();
}
    
    private void actualizarResultados() {
    long tamañoTotal = 0;
    int cantidadArchivos = listaCanciones.size(); // Cambia a la lista actual (favoritas o originales)

    for (String ruta : listaCanciones) {
        File archivo = new File(ruta);
        tamañoTotal += archivo.length() / 1000000; // Convertir a MB
    }

    resultadosLabel.setText("Cantidad de archivos: " + cantidadArchivos + " | Tamaño total: " + tamañoTotal + " MB aprox");
}
    
    private void agregarAFavoritos() {
        int filaSeleccionada = tablaCanciones.getSelectedRow();
        if (filaSeleccionada != -1) {
            String rutaArchivo = listaCanciones.get(filaSeleccionada);
            File archivoOriginal = new File(rutaArchivo);
            File carpetaFavoritos = new File("C:\\Users\\Mayby\\Desktop\\USB Oliver\\Contenido\\MusicasFavoritas");

            if (!carpetaFavoritos.exists()) {
                carpetaFavoritos.mkdirs(); // Crea la carpeta si no existe
            }

            File archivoFavorito = new File(carpetaFavoritos, archivoOriginal.getName());

            try {
                Files.copy(archivoOriginal.toPath(), archivoFavorito.toPath());
                JOptionPane.showMessageDialog(panel, "Canción agregada a favoritos.");
                
            } catch (IOException e) {
                JOptionPane.showMessageDialog(panel, "Error al copiar la canción: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(panel, "Por favor, selecciona una canción.");
        }
    }

    private void actualizarFavoritos() {
    listaFavoritas.clear();
    modeloTabla.setRowCount(0); // Limpiar la tabla

    File carpetaFavoritos = new File("C:\\Users\\Mayby\\Desktop\\USB Oliver\\Contenido\\MusicasFavoritas");
    File[] archivosFavoritos = carpetaFavoritos.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));

    if (archivosFavoritos != null) {
        for (File archivo : archivosFavoritos) {
            modeloTabla.addRow(new String[]{archivo.getName()});
            listaFavoritas.add(archivo.getAbsolutePath());
        }
    }

    listaCanciones = new ArrayList<>(listaFavoritas); // Actualiza listaCanciones con listaFavoritas
    actualizarResultados(); // Actualizar resultados después de cargar favoritos

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
        pararMusica();
        File archivo = new File(rutaArchivo);
        Media media = new Media(archivo.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    private void pararMusica() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            pauseTime = mediaPlayer.getCurrentTime();
            mediaPlayer.pause();
        }
    }

    private void reanudarMusica() {
        if (mediaPlayer != null && pauseTime != null) {
            mediaPlayer.seek(pauseTime);  // Asegurarse de reanudar desde el último tiempo de pausa
            mediaPlayer.play();
        }
    }

    private void siguienteCancion() {
        if (listaCanciones.isEmpty()) return;

        // Aumenta el índice y reinicia si llega al final de la lista
        cancionActual = (cancionActual + 1) % listaCanciones.size();
        
        reproducirCancionPorIndice(cancionActual);
    }

    private void anteriorCancion() {
        if (listaCanciones.isEmpty()) return;

        // Decrementa el índice y ajusta si llega al comienzo de la lista
        cancionActual = (cancionActual - 1 + listaCanciones.size()) % listaCanciones.size();
        
        reproducirCancionPorIndice(cancionActual);
    }

    // Método para reproducir canción según el índice actual
    private void reproducirCancionPorIndice(int indice) {
        if (indice >= 0 && indice < listaCanciones.size()) {
            String rutaArchivo = listaCanciones.get(indice);
            reproducirAudio(rutaArchivo);
        }
    }
    
    private void eliminarCancion() {
    int filaSeleccionada = tablaCanciones.getSelectedRow();
    if (filaSeleccionada != -1) {
        String rutaArchivo = listaCanciones.get(filaSeleccionada);
        File archivoAEliminar = new File(rutaArchivo);

        // Verifica si el archivo existe y lo elimina
        if (archivoAEliminar.exists()) {
            int confirmacion = JOptionPane.showConfirmDialog(panel, "¿Estás seguro de que deseas eliminar la canción?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (archivoAEliminar.delete()) {
                    modeloTabla.removeRow(filaSeleccionada); // Eliminar de la tabla
                    listaCanciones.remove(filaSeleccionada); // Actualizar la lista de canciones
                    JOptionPane.showMessageDialog(panel, "Canción eliminada exitosamente.");
                    actualizarResultados(); // Actualizar resultados después de eliminar
                } else {
                    JOptionPane.showMessageDialog(panel, "Error al eliminar la canción.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(panel, "La canción no existe.");
        }
    } else {
        JOptionPane.showMessageDialog(panel, "Por favor, selecciona una canción para eliminar.");
    }
}

}
