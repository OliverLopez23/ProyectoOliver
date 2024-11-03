package proyectooliver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.io.File;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Ventana5 extends JFrame {
    public JPanel panel;
    private JTable tablaDuplicados;
    private DefaultTableModel modeloTabla;
    private ArrayList<File> archivosDuplicados = new ArrayList<>();

    public Ventana5() {
        this.setTitle("Eliminar archivos duplicados");
        this.setSize(1080, 720);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(720, 720));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Componentes();
    }

    private void Componentes() {
        panel();
        etiquetas();
        botones();
        tablaDuplicados();
    }

    private void panel() {
        panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);
        panel.setBackground(Color.darkGray);
    }

    private void etiquetas() {
        JLabel texto = new JLabel("Archivos Duplicados", SwingConstants.CENTER);
        texto.setBounds(150, 40, 760, 40);
        texto.setForeground(Color.red);
        texto.setFont(new Font("times new roman", 1, 30));
        texto.setBackground(Color.black);
        texto.setOpaque(true);
        panel.add(texto);
    }

    private void botones() {
        JButton botonBuscar = new JButton("Buscar duplicados");
        botonBuscar.setFont(new Font("times new roman", 1, 25));
        botonBuscar.setBounds(150, 100, 250, 40);
        botonBuscar.addActionListener(e -> buscarDuplicados());
        panel.add(botonBuscar);

        JButton botonEliminar = new JButton("Eliminar duplicados");
        botonEliminar.setFont(new Font("times new roman", 1, 25));
        botonEliminar.setBounds(450, 100, 250, 40);
        botonEliminar.addActionListener(e -> eliminarDuplicados());
        panel.add(botonEliminar);
        
        JButton boton2 = new JButton("Regresar");
        boton2.setFont(new Font("times new roman", 1, 25));
        boton2.setBounds(600, 100, 170, 40);
        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Ventana1 VentanaInicio = new Ventana1();
                VentanaInicio.setVisible(true);
            }
        });
        panel.add(boton2);
    }

    private void tablaDuplicados() {
        modeloTabla = new DefaultTableModel(new String[]{"Archivo", "Ruta"}, 0);
        tablaDuplicados = new JTable(modeloTabla);
        tablaDuplicados.setBackground(Color.lightGray);
        JScrollPane scrollPane = new JScrollPane(tablaDuplicados);
        scrollPane.setBounds(150, 200, 780, 400);
        panel.add(scrollPane);
    }

    private void buscarDuplicados() {
        File carpeta = new File("C:\\Users\\Mayby\\Desktop\\USB Oliver\\Contenido"); // Cambia esta ruta
        archivosDuplicados.clear();
        modeloTabla.setRowCount(0);

        if (!carpeta.exists() || !carpeta.isDirectory()) {
            JOptionPane.showMessageDialog(this, "La carpeta no existe o no es válida.");
            return;
        }

        Map<String, File> archivosMap = new HashMap<>();
        buscarEnDirectorio(carpeta, archivosMap);

        if (archivosDuplicados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron archivos duplicados.");
        }
    }

    private void buscarEnDirectorio(File directorio, Map<String, File> archivosMap) {
        for (File archivo : directorio.listFiles()) {
            if (archivo.isDirectory()) {
                buscarEnDirectorio(archivo, archivosMap); // Llamada recursiva para subdirectorios
            } else if (archivo.isFile() && (archivo.getName().toLowerCase().endsWith(".mp3") ||
                                            archivo.getName().toLowerCase().endsWith(".mp4") ||
                                            archivo.getName().toLowerCase().endsWith(".jpg") ||
                                            archivo.getName().toLowerCase().endsWith(".png") ||
                                            archivo.getName().toLowerCase().endsWith(".jfif"))) {
                String hashArchivo = calcularHash(archivo);
                if (hashArchivo != null && archivosMap.containsKey(hashArchivo)) {
                    archivosDuplicados.add(archivo);
                    modeloTabla.addRow(new Object[]{archivo.getName(), archivo.getAbsolutePath()});
                } else if (hashArchivo != null) {
                    archivosMap.put(hashArchivo, archivo);
                }
            }
        }
    }

    private String calcularHash(File archivo) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] fileBytes = Files.readAllBytes(archivo.toPath());
            byte[] hashBytes = digest.digest(fileBytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void eliminarDuplicados() {
        if (archivosDuplicados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay archivos duplicados para eliminar.");
            return;
        }

        int archivosNoEliminados = 0;
        for (File archivo : archivosDuplicados) {
            if (!archivo.delete()) {
                archivosNoEliminados++;
            }
        }

        if (archivosNoEliminados == 0) {
            JOptionPane.showMessageDialog(this, "Archivos duplicados eliminados exitosamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Algunos archivos no se pudieron eliminar: " + archivosNoEliminados);
        }

        archivosDuplicados.clear();
        modeloTabla.setRowCount(0);
    }
}
