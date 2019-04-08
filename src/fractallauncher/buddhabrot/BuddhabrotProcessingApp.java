package fractallauncher.buddhabrot;

import java.awt.Window;
import java.awt.event.WindowListener;

import controlP5.CheckBox;
import controlP5.ColorPicker;
import controlP5.ControlP5;
import controlP5.Slider;
import controlP5.Textfield;
import processing.awt.PSurfaceAWT.SmoothCanvas;
import processing.core.PApplet;
import processing.core.PImage;

public class BuddhabrotProcessingApp extends PApplet {

    private ControlP5 cp5;
    private Textfield txf1, txf2, txf3;
    private ColorPicker cp;
    private BuddhabrotGenerator gen;
    private CheckBox checkbox;

    @Override
    public void settings() {
        size(700, 700);
    }

    @Override
    public void setup() {
        Window win = ((SmoothCanvas) this.getSurface().getNative()).getFrame();
        for (WindowListener evt : win.getWindowListeners())
            win.removeWindowListener(evt);

        background(0, 0, 0);

        cp5 = new ControlP5(this);

        //発散判定の試行回数
        txf1 = cp5.addTextfield("textfield1")
                .setPosition(width - 210, 0)
                .setColorLabel(color(255, 255, 255))
                .setSize(65, 20)
                .setLabel("N")
                .setColor(color(0, 0, 0))
                .setText("100")
                .setColorBackground(color(255, 255, 255))
                .setInputFilter(ControlP5.INTEGER);

        //繰り返し回数
        txf2 = cp5.addTextfield("textfield2")
                .setPosition(width - 140, 0)
                .setColorLabel(color(255, 255, 255))
                .setSize(65, 20)
                .setLabel("R")
                .setColor(color(0, 0, 0))
                .setText("1000000")
                .setColorBackground(color(255, 255, 255))
                .setInputFilter(ControlP5.INTEGER);

        //繰り返し回数
        txf3 = cp5.addTextfield("textfield3")
                .setPosition(width - 70, 0)
                .setColorLabel(color(255, 255, 255))
                .setSize(65, 20)
                .setLabel("alpha diff")
                .setColor(color(0, 0, 0))
                .setText("10.0")
                .setColorBackground(color(255, 255, 255))
                .setInputFilter(ControlP5.FLOAT);

        cp = cp5.addColorPicker("picker")
                .setPosition(width - 210, 40)
                .setColorValue(color(77, 103, 222))
                .setWidth(205);

        checkbox = cp5.addCheckBox("checkBox")
                .setPosition(0, 45)
                .setColorForeground(color(120))
                .setColorActive(color(255))
                .setColorLabel(color(255))
                .addItem("Anti-buddabrot", 0);

        cp5.addButton("genButtonOnClicked")
                .setLabel("Generate")
                .setPosition(0, 0)
                .setSize(100, 40);

        cp5.addButton("clrButtonOnClicked")
                .setLabel("Clear")
                .setColorLabel(color(0, 0, 0))
                .setPosition(105, 0)
                .setSize(100, 40)
                .setColorBackground(color(255, 255, 255));

        ((Slider) (cp5.get("picker-red"))).setWidth(205);
        ((Slider) (cp5.get("picker-green"))).setWidth(205);
        ((Slider) (cp5.get("picker-blue"))).setWidth(205);
        ((Slider) (cp5.get("picker-alpha"))).setWidth(205);

        int threadNum = MyInputDialog.getThreadNum();
        gen = new BuddhabrotGenerator(this, threadNum);
        PImage image = gen.buddhabrot(color(77, 103, 222), 100L, 1000000L, 10.0d); //300x300に対してはr*diffが大体100万くらいがベスト //700x700は1000万くらい
        image(image, 0, 0);
    }

    @Override
    public void draw() {

    }

    public void genButtonOnClicked() {
        long n;
        String str = txf1.getText();
        if ("".equals(str) || str == null) n = 100L;
        else
            n = Long.valueOf(str);

        long r;
        str = txf2.getText();
        if ("".equals(str) || str == null) {
            r = 1000000L;
        } else {
            r = Long.valueOf(str);
        }

        double alphaDiff;
        str = txf3.getText();
        if ("".equals(str) || str == null) {
            alphaDiff = 10.0d;
        } else {
            alphaDiff = Double.valueOf(str);
        }


        gen.setAntiMode(checkbox.getItem(0).getState());
        PImage image = gen.buddhabrot(cp.getColorValue(), n, r, alphaDiff);
        image(image, 0, 0);
    }

    public void clrButtonOnClicked() {
        fill(0, 0, 0);
        noStroke();
        rect(0, 0, width, height);
    }
}