package proyectooliver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class Ventana3 extends JFrame {
    private JPanel panel;
    private JLabel resultadosLabel;
    private JTable tablaCanciones;
    private DefaultTableModel modeloTabla;

    private ArrayList<String> listaArchivos = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private Slider progressSlider;
    private int archivoActual = -1;
    private Duration pauseTime;
    private boolean isSliderBeingDragged = false;  // Para controlar el estado de la barra

    public Ventana3() {
        this.setTitle("Reproductor Multimedia");
        this.setSize(1080, 720);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(720, 720));
        Componentes();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void Componentes() {
        panel();
        etiquetas();
        botones();
        tablaArchivos();
        initJavaFXPanel();
    }

    private void panel() {
        panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);
        panel.setBackground(Color.darkGray);
    }

    private void etiquetas() {
        JLabel texto = new JLabel("Archivos Multimedia", SwingConstants.CENTER);
        texto.setBounds(150, 150, 170, 40);
        texto.setForeground(Color.red);
        texto.setOpaque(true);
        texto.setBackground(Color.black);
        texto.setFont(new Font("times new roman", 1, 30));
        panel.add(texto);

        JLabel texto2 = new JLabel("BIENVENIDO AL REPRODUCTOR", SwingConstants.CENTER);
        texto2.setBounds(150, 40, 760, 40);
        texto2.setForeground(Color.red);
        texto2.setFont(new Font("times new roman", 1, 30));
        texto2.setBackground(Color.BLACK);
        texto2.setOpaque(true);
        panel.add(texto2);

        resultadosLabel = new JLabel("", SwingConstants.CENTER);
        resultadosLabel.setBounds(510, 375, 560, 40);
        resultadosLabel.setForeground(Color.white);
        resultadosLabel.setFont(new Font("times new roman", 1, 20));
        panel.add(resultadosLabel);
    }

    private void botones() {
        JButton boton2 = new JButton("Regresar");
        boton2.setFont(new Font("times new roman", 1, 25));
        boton2.setBounds(800, 620, 170, 40);
        boton2.addActionListener(e -> pararReproduccion());
        boton2.addActionListener(e -> {
            setVisible(false);
            Ventana1 ventanaInicio = new Ventana1();
            ventanaInicio.setVisible(true);
        });
        panel.add(boton2);

        JButton boton3 = new JButton("Buscar Archivos");
        boton3.setFont(new Font("times new roman", 1, 25));
        boton3.setBounds(150, 200, 170, 40);
        boton3.addActionListener(e -> buscarArchivosMultimedia());
        panel.add(boton3);

        JButton botonReproducir = new JButton("Reproducir");
        botonReproducir.setFont(new Font("times new roman", 1, 25));
        botonReproducir.setBounds(500, 200, 170, 40);
        botonReproducir.addActionListener(e -> reproducirArchivoSeleccionado());
        panel.add(botonReproducir);

        JButton botonParar = new JButton("Parar");
        botonParar.setFont(new Font("times new roman", 1, 25));
        botonParar.setBounds(150, 375, 170, 40);
        botonParar.addActionListener(e -> pararReproduccion());
        panel.add(botonParar);

        JButton botonReanudar = new JButton("Reanudar");
        botonReanudar.setFont(new Font("times new roman", 1, 25));
        botonReanudar.setBounds(330, 375, 170, 40);
        botonReanudar.addActionListener(e -> reanudarReproduccion());
        panel.add(botonReanudar);
    }

    private void tablaArchivos() {
        modeloTabla = new DefaultTableModel(new String[]{"Archivo"}, 0);
        tablaCanciones = new JTable(modeloTabla);
        tablaCanciones.setBackground(Color.lightGray);
        JScrollPane scrollPane = new JScrollPane(tablaCanciones);
        scrollPane.setBounds(150, 250, 600, 100);
        panel.add(scrollPane);
    }

    private void buscarArchivosMultimedia() {
        File carpeta = new File("C:\\Users\\Mayby\\Desktop\\USB Oliver\\Contenido");
        File[] archivos = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp4"));

        listaArchivos.clear();
        modeloTabla.setRowCount(0);

        if (archivos != null) {
            for (File archivo : archivos) {
                modeloTabla.addRow(new String[]{archivo.getName()});
                listaArchivos.add(archivo.getAbsolutePath());
            }
        }
        long tamañoTotal = 0;
        int cantidadArchivos = listaArchivos.size();
        resultadosLabel.setText("Cantidad de archivos: " + cantidadArchivos);
        

    for (String ruta : listaArchivos) {
        File archivo = new File(ruta);
        tamañoTotal += archivo.length() / 1000000; // Convertir a GB
    }

    resultadosLabel.setText("Cantidad de archivos: " + cantidadArchivos + " | Tamaño total: " + tamañoTotal + " MB aprox");

        panel.revalidate();
        panel.repaint();
        
    }

    private void reproducirArchivoSeleccionado() {
        int filaSeleccionada = tablaCanciones.getSelectedRow();
        if (filaSeleccionada != -1) {
            archivoActual = filaSeleccionada;
            String rutaArchivo = listaArchivos.get(archivoActual);
            reproducirMedia(rutaArchivo);
        } else {
            JOptionPane.showMessageDialog(panel, "Por favor, selecciona un archivo.");
        }
    }

    private void reproducirMedia(String rutaArchivo) {
        pararReproduccion();

        Platform.runLater(() -> {
            Media media = new Media(new File(rutaArchivo).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            mediaPlayer.setOnReady(() -> {
                mediaPlayer.play();

                // Actualizar el progreso del slider cuando el video está en reproducción
                mediaPlayer.currentTimeProperty().addListener((observable, oldTime, newTime) -> {
                    if (!isSliderBeingDragged) {
                        progressSlider.setValue(newTime.toSeconds());
                    }
                });

                progressSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
            });

            // Cambiar el tiempo de reproducción cuando el usuario manipule el slider
            progressSlider.setOnMousePressed(e -> isSliderBeingDragged = true);
            progressSlider.setOnMouseReleased(e -> {
                isSliderBeingDragged = false;
                mediaPlayer.seek(Duration.seconds(progressSlider.getValue()));
            });
        });
    }

    private void pararReproduccion() {
    if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
        pauseTime = mediaPlayer.getCurrentTime();
        mediaPlayer.pause();
    }
}

private void reanudarReproduccion() {
    if (mediaPlayer != null && pauseTime != null) {
        mediaPlayer.seek(pauseTime);  // Asegurarse de reanudar desde el último tiempo de pausa
        mediaPlayer.play();
    }
}


    private void initJavaFXPanel() {
    JFXPanel fxPanel = new JFXPanel();
    fxPanel.setBounds(150, 425, 600, 350); // Aumentamos el tamaño del panel para acomodar la barra
    panel.add(fxPanel);

    Platform.runLater(() -> {
        StackPane root = new StackPane();
        mediaView = new MediaView();
        
        // Caja vertical para organizar el MediaView y el Slider
        VBox vbox = new VBox();
        vbox.getChildren().addAll(mediaView, progressSlider = new Slider());
        vbox.setSpacing(5); // Espacio entre el video y la barra de progreso

        root.getChildren().add(vbox);

        Scene scene = new Scene(root, 600, 350);
        fxPanel.setScene(scene);
    });
}
}
