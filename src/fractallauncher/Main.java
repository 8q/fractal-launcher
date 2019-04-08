package fractallauncher;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fractallauncher.buddhabrot.BuddhabrotProcessingApp;
import fractallauncher.cellautomaton.CAProcessingApp;
import fractallauncher.fractalgen.FractalGenProcessingApp;
import fractallauncher.mandelbrot.MandelbrotProcessingApp;
import processing.core.PApplet;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setBounds(300, 300, 320, 240);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel1 = new JPanel();
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.CENTER);
        panel1.setLayout(layout);
        JButton button1 = new JButton();
        button1.setText("Gen");
        button1.addActionListener(e -> PApplet.main(FractalGenProcessingApp.class));
        JButton button2 = new JButton();
        button2.setText("MB");
        button2.addActionListener(e -> PApplet.main(MandelbrotProcessingApp.class));
        JButton button3 = new JButton();
        button3.setText("CA");
        button3.addActionListener(e -> PApplet.main(CAProcessingApp.class));
        JButton button4 = new JButton();
        button4.setText("BB");
        button4.addActionListener(e -> PApplet.main(BuddhabrotProcessingApp.class));
        panel1.add(button1);
        panel1.add(button2);
        panel1.add(button3);
        panel1.add(button4);

        JPanel panel2 = new JPanel();
        FlowLayout layout2 = new FlowLayout();
        layout2.setAlignment(FlowLayout.CENTER);
        panel2.setLayout(layout2);
        JLabel label = new JLabel();
        label.setText("Flactal Lancher");
        panel2.add(label);

        JPanel panel3 = new JPanel();
        FlowLayout layout3 = new FlowLayout();
        layout3.setAlignment(FlowLayout.CENTER);
        panel3.setLayout(layout3);
        JLabel image = new JLabel();
        ImageIcon icon = new ImageIcon("./img/image.png");
        image.setIcon(icon);
        panel3.add(image);

        frame.add(panel1, BorderLayout.PAGE_END);
        frame.add(panel2, BorderLayout.PAGE_START);
        frame.add(panel3, BorderLayout.CENTER);
        frame.setVisible(true);
    }

}