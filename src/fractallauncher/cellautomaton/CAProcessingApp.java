package fractallauncher.cellautomaton;

import java.awt.Window;
import java.awt.event.WindowListener;

import controlP5.ControlP5;
import controlP5.Textfield;
import processing.awt.PSurfaceAWT.SmoothCanvas;
import processing.core.PApplet;

public class CAProcessingApp extends PApplet {

    private CA1d ca;
    private ControlP5 cp5;
    private Textfield txf;
    private int cellSize;
    private boolean isRandomInit;
    private int[] rules;

    @Override
    public void settings() {
        size(700, 700);
    }

    @Override
    public void setup() {
        Window win = ((SmoothCanvas) this.getSurface().getNative()).getFrame();
        for (WindowListener evt : win.getWindowListeners())
            win.removeWindowListener(evt);

        cp5 = new ControlP5(this);
        cp5.addButton("reGenBtnOnclicked").setLabel("ReGenerate").setPosition(0, 0).setSize(100, 40);

        cellSize = MyInputDialog.getCellSize();
        isRandomInit = MyInputDialog.getIsInitRandom();

        txf = cp5.addTextfield("textfield")
                .setPosition(width - 70, 0)
                .setLabel("Rule (0-255)")
                .setColorLabel(color(0, 0, 0))
                .setSize(65, 20)
                .setColor(color(0, 128, 0))
                .setText("90")
                .setColorBackground(color(255, 255, 255))
                .setInputFilter(ControlP5.INTEGER);

        rules = getRuleArray(90); // 30 90 135 110 102 114
        ca = new CA1d(width / cellSize, height / cellSize, cellSize, rules, isRandomInit);
        ca.draw(this);
    }

    public void reGenBtnOnclicked() {
        clear();
        int rule;
        String str = txf.getText();
        if ("".equals(str) || str == null) {
            rule = 90;
        } else {
            rule = Integer.valueOf(str);
        }
        if (rule < 0 || rule > 255) {
            rule = 90;
        }
        rules = getRuleArray(rule);
        ca = new CA1d(width / cellSize, height / cellSize, cellSize, rules, isRandomInit);
        ca.draw(this);
        loop();
    }

    public void clear() {
        fill(211, 211, 211);
        noStroke();
        rect(0, 0, width, height);
        stroke(0);
        fill(255, 255, 255);
    }

    @Override
    public void draw() {
        ca.progressStep();
        ca.draw(this);
    }

    public int[] getRuleArray(int rule) {
        int[] array = new int[8]; // 111 110 101 100 011 010 001 000
        for (int i = 0; i < 8; i++) {
            array[7 - i] = (rule >>> i) & 1;
        }
        return array;
    }

}