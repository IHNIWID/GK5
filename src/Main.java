import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Main extends JPanel {

    private static final long serialVersionUID = 1L;
    private BufferedImage img;
    private BufferedImage reseti;
    private BufferedImage filtered;
    private double matrix[][] = new double[3][3];
    private ppm k;

    public Main() {

        k = new ppm();
        loader();
        JFrame window = new JFrame("Frame");
        JMenuBar menuBar = new JMenuBar();
        JMenu editormenu = new JMenu("Filtry");
        JMenuItem usredniajacy = new JMenuItem("Filtr usredniajacy");

        usredniajacy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double matrix[][] = {{1D, 1D, 1D}, {1D, 1D, 1D}, {1D, 1D, 1D}};
                filtered = img;
                Filters.convolution(img, filtered, matrix);
                img = filtered;
                window.pack();
                window.repaint();
            }
        });
        JMenuItem medianowy = new JMenuItem("Filtr medianowy");
        medianowy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double matrix[][] = {{1D, 1D, 1D}, {1D, 1D, 1D}, {1D, 1D, 1D}};
                filtered = img;
                Filters.medianFilter(img, filtered, 3);
                img = filtered;
                window.pack();
                window.repaint();
            }
        });
        JMenuItem sobel = new JMenuItem("Filtr wykrywania krawedzi");
        sobel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double matrix[][] = {{1D, 2D, 1D}, {0D, 0D, 0D}, {-1D, -2D, -1D}};
                filtered = img;
                Filters.convolution(img, filtered, matrix);
                img = filtered;
                window.pack();
                window.repaint();
            }
        });
        JMenuItem mean = new JMenuItem("Filtr gornowprzepustowy");
        mean.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double matrix[][] = {{-1D, -1D, -1D}, {-1D, 9D, -1D}, {-1D, -1D, -1D}};
                filtered = img;
                Filters.convolution(img, filtered, matrix);
                img = filtered;
                window.pack();
                window.repaint();
            }
        });
        JMenuItem gauss = new JMenuItem("Filtr rozmywajacy Gaussa");
        gauss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double matrix[][] = {{1D, 2D, 1D}, {2D, 4D, 2D}, {1D, 2D, 1D}};
                filtered = img;
                Filters.convolution(img, filtered, matrix);
                img = filtered;
                window.pack();
                window.repaint();
            }
        });
        JMenuItem splot = new JMenuItem("Splot maski");
        splot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double matrix[][] = {{1D, -2D, 1D}, {-2D, 5D, -2D}, {1D, -2D, 1D}};
                filtered = img;
                Filters.convolution(img, filtered, matrix);
                img = filtered;
                window.pack();
                window.repaint();
            }
        });
        JMenuItem reset = new JMenuItem("Resetuj obraz");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loader();
                img = k.ppmi;
                window.pack();
                window.repaint();
            }
        });

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        menuBar.add(editormenu);
        editormenu.add(usredniajacy);
        editormenu.add(medianowy);
        editormenu.add(sobel);
        editormenu.add(mean);
        editormenu.add(gauss);
        editormenu.add(splot);
        editormenu.add(reset);
        //window.add(menuBar);
        window.setJMenuBar(menuBar);
        window.add(this);
        img = k.ppmi;

        window.setPreferredSize(new Dimension(k.width + 15, k.height + 60));
        window.pack();

        try {
            ImageIO.write(img, "jpg", new File("JPEGexport.jpg"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("Done");

    }

    public static void main(String[] args) throws IOException {
        new Main();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, 0, 0, null);
    }
    public  void loader(){
        try {						// tutaj wpisujemy plik ppm ktory chcemy wczytac
            //k.load("lena_384.ppm");// pm3
            k.load("etud.ppm");//pm6			
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
