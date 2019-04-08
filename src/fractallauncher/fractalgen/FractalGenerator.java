package fractallauncher.fractalgen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import processing.core.PApplet;

class FractalGenerator {

    List<Float> rotateDegrees;
    List<Float> lengths;
    private float endX = 0, endY = 0, degree = 0;
    private PApplet pa;

    public FractalGenerator(PApplet pa) {
        this.pa = pa;
        rotateDegrees = new ArrayList<>();
        lengths = new ArrayList<>();
    }

    public void add(float rotateDegree, float length) {
        rotateDegrees.add(rotateDegree);
        lengths.add(length);
        degree += rotateDegree;
        endX += length * PApplet.cos(PApplet.radians(degree));
        endY += length * PApplet.sin(PApplet.radians(degree));
    }

    public float getDistance() {
        return PApplet.sqrt(endX * endX + endY * endY);
    }

    public float getDeclination() {
        return PApplet.degrees(PApplet.atan2(endY, endX));
    }

    public List<Float> getRotateDegees() {
        return rotateDegrees;
    }

    public List<Float> getLengths() {
        return lengths;
    }

    public void fractal(float size, int n) {
        pa.pushMatrix();

        Iterator<Float> rotateDegrees = this.getRotateDegees().iterator();
        Iterator<Float> lengths = this.getLengths().iterator();
        float distance = this.getDistance();
        float declination = this.getDeclination();
        pa.rotate(PApplet.radians(-declination));

        while (rotateDegrees.hasNext() && lengths.hasNext()) {
            float degree = rotateDegrees.next();
            float length = lengths.next();
            if (n == 1) {
                pa.rotate(PApplet.radians(degree));
                pa.line(0, 0, size * length, 0);
                // pa.point(size*length,0); //点で描画しても面白い
                pa.translate(size * length, 0);
            } else {
                pa.rotate(PApplet.radians(degree));
                fractal(size * (length / distance), n - 1);
                pa.translate(size * length, 0);
            }
        }

        pa.popMatrix();
    }

}
