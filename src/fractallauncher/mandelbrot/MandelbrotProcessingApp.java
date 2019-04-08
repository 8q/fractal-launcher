package fractallauncher.mandelbrot;

import java.awt.Window;
import java.awt.event.WindowListener;

import controlP5.ControlP5;
import controlP5.Textfield;
import controlP5.Textlabel;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import fractallauncher.utils.Utils;
import fractallauncher.utils.PVectorD;

public class MandelbrotProcessingApp extends PApplet {

    private PImage image;
    private PVector p0 = new PVector(-1.0f, -1.0f), p = new PVector(-1.0f, -1.0f);
    private PVectorD p1 = new PVectorD(-2.0d, -2.0d), p2 = new PVectorD(2.0d, 2.0d);
    private MandelbrotGenerator generator;
    private ControlP5 cp5;
    private Textfield txf;
    private Textlabel label;

    @Override
    public void settings() {
        size(700, 700);
    }

    @Override
    public void setup() {
        Window win = ((PSurfaceAWT.SmoothCanvas) this.getSurface().getNative()).getFrame();
        for (WindowListener evt : win.getWindowListeners())
            win.removeWindowListener(evt);

        background(0);
        cp5 = new ControlP5(this);

        txf = cp5.addTextfield("textfield")
                .setPosition(width - 70, 0)
                .setColorLabel(color(0, 0, 0))
                .setSize(65, 20)
                .setLabel("Repeat count")
                .setColor(color(0, 0, 0))
                .setText("100")
                .setColorBackground(color(255, 255, 255))
                .setInputFilter(ControlP5.INTEGER);

        label = cp5.addLabel("label")
                .setText(p1.toString() + p2.toString())
                .setColor(color(10, 10, 10))
                .setPosition(0, 0);

        double power = MyInputDialog.getPower();
        int colorType = MyInputDialog.getColorType();
        String gradientPath = colorType == 3 ? MyInputDialog.getGradientPath() : null;
        int threadNum = MyInputDialog.getThreadNum();
        MyInputDialog.DrawMode mode = MyInputDialog.getDrawMode();
        double[] juliaParams = mode == MyInputDialog.DrawMode.Juliabrot ? MyInputDialog.getJuliabrotParams() : null;

        generator = new MandelbrotGenerator(this, colorType, power, threadNum);
        if (colorType == 3) {
            generator.setGradientImage(loadImage(gradientPath));
        }
        if (mode == MyInputDialog.DrawMode.Juliabrot) {
            generator.setJuliabrotMode(true);
            generator.setJuliabrotParams(juliaParams[0], juliaParams[1]);
        }
        image = generator.mandelbrot(p1, p2, 100);
    }

    @Override
    public void draw() {
        image(image, 0, 0);
        if (mousePressed && p0.x != -1.0f) {
            p.set(mouseX, mouseY);
        }
        stroke(255, 0, 0);
        strokeWeight(5);
        if (p.x != -1.0f) {
            rect(p0.x, p0.y, p.x - p0.x, p.x - p0.x);
        }
        fade();
    }

    public void fade() {
        fill(0, 30);
        noStroke();
        rect(0, 0, width, height);
        stroke(0);
        noFill();
    }

    public void clear() {
        fill(0);
        noStroke();
        rect(0, 0, width, height);
        stroke(0);
        noFill();
    }

    @Override
    public void mousePressed() {
        if (mouseX > width - 70 && mouseY < 40) return;
        p0.set(mouseX, mouseY);
    }

    @Override
    public void mouseReleased() {
        if (p.x == p0.x && p.y == p0.y) {
            p.set(-1.0f, -1.0f);
            p0.set(-1.0f, -1.0f);
            return;
        }
        clear();

        int n;
        String str = txf.getText();
        if ("".equals(str) || str == null) {
            n = 500;
        } else {
            n = Integer.valueOf(str);
        }

        double preP1x = p1.x, preP1y = p1.y, preP2x = p2.x, preP2y = p2.y;
        p1.set(Utils.map(p0.x, 0, width, preP1x, preP2x), Utils.map(p0.y, 0, height, preP1y, preP2y));
        p2.set(Utils.map(p.x, 0, width, preP1x, preP2x), Utils.map(p0.y + (p.x - p0.x), 0, height, preP1y, preP2y));
        System.out.println(p1.toString() + p2.toString());
        
        image = generator.mandelbrot(p1, p2, n);
        label.setText(p1.toString() + p2.toString());
        p.set(-1.0f, -1.0f);
        p0.set(-1.0f, -1.0f);
    }

}