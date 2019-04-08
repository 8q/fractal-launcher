package fractallauncher.fractalgen;

import java.awt.Window;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import controlP5.ControlP5;
import controlP5.Textfield;
import processing.awt.PSurfaceAWT.SmoothCanvas;
import processing.core.PApplet;
import processing.core.PVector;

public class FractalGenProcessingApp extends PApplet {

    private ControlP5 cp5;
    private Textfield txf;
    private List<PVector> pointList = new ArrayList<>();
    private int pcx = -1, pcy = -1; // preClickedXY

    @Override
    public void settings() {
        size(700, 700);
    }

    @Override
    public void setup() {
        Window win = ((SmoothCanvas) this.getSurface().getNative()).getFrame();
        for (WindowListener evt : win.getWindowListeners())
            win.removeWindowListener(evt);

        strokeWeight(1);
        background(211, 211, 211);
        cp5 = new ControlP5(this);
        cp5.addButton("genBtnOnclicked")
                .setLabel("Generate")
                .setPosition(0, 0)
                .setSize(100, 40);
        cp5.addButton("clrBtnOnclicked")
                .setLabel("Clear")
                .setColorLabel(color(0, 0, 0))
                .setPosition(105, 0)
                .setSize(100, 40)
                .setColorBackground(color(255, 255, 255));
        txf = cp5.addTextfield("textfield")
                .setPosition(width - 70, 0)
                .setLabel("Recursion depth")
                .setColorLabel(color(0, 0, 0))
                .setSize(65, 20)
                .setColor(color(255, 0, 0))
                .setText("3")
                .setColorBackground(color(255, 255, 255))
                .setInputFilter(ControlP5.INTEGER);

    }

    @Override
    public void draw() {

    }

    @Override
    public void mousePressed() {
        if (mouseY <= 40) return;

        pointList.add(new PVector(mouseX, mouseY));
        ellipse(mouseX, mouseY, 10, 10);
        if (pcx != -1 && pcy != -1) {
            stroke(200, 0, 0);
            line(pcx, pcy, mouseX, mouseY);
            stroke(0);
        }
        pcx = mouseX;
        pcy = mouseY;
    }

    public void genBtnOnclicked() {
        if (pointList.size() <= 0) return;

        clear();

        int n;
        String str = txf.getText();
        if ("".equals(str) || str == null) {
            n = 1;
        } else if (str.length() > 2) {
            n = Integer.valueOf(str.substring(0, 1));
        } else {
            n = Integer.valueOf(str);
        }

        Iterator<PVector> points = pointList.iterator();
        PVector p0 = points.next();
        FractalGenerator gen = new FractalGenerator(this);
        PVector pp = p0;
        float pDeg = 0.0f;
        while (points.hasNext()) {
            PVector p = points.next();
            float deg = degrees(atan2(p.y - pp.y, p.x - pp.x));
            float l = sqrt(pow(p.x - pp.x, 2) + pow(p.y - pp.y, 2));
            gen.add(deg - pDeg, l);
            pp = p;
            pDeg = deg;
        }

        translate(p0.x, p0.y);
        PVector pe = pointList.get(pointList.size() - 1);
        rotate(atan2(pe.y - p0.y, pe.x - p0.x));
        gen.fractal(1, n);
        rotate(-atan2(pe.y - p0.y, pe.x - p0.x));
        translate(-p0.x, -p0.y);

        pointList = new ArrayList<>();
        pcx = -1;
        pcy = -1;
    }

    public void clrBtnOnclicked() {
        clear();
        pointList = new ArrayList<>();
        pcx = -1;
        pcy = -1;
    }

    public void clear() {
        fill(211, 211, 211);
        noStroke();
        rect(0, 0, width, height);
        stroke(0);
        fill(255, 255, 255);
    }
}
