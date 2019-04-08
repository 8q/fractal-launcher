package fractallauncher.buddhabrot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import fractallauncher.utils.Utils;
import org.apache.commons.math3.complex.Complex;

import processing.core.PApplet;
import processing.core.PImage;

class BuddhabrotGenerator {

    private PApplet pa;
    private int threadNum;
    private boolean isAntiMode = false;

    public BuddhabrotGenerator(PApplet pa, int threadNum) {
        this.pa = pa;
        this.threadNum = threadNum;
    }

    public void setAntiMode(boolean isAntiMode) {
        this.isAntiMode = isAntiMode;
    }

    public PImage buddhabrot(int color, long n, long r, double alphaDiff) {
        double x = -2.0d, y = -2.0d, x2 = 2.0d, y2 = 2.0d;
        PImage image = pa.createImage(pa.width, pa.height, PApplet.ARGB);
        image.loadPixels();
        Map<Integer, Double> alphaMap = new HashMap<>();
        for (int i = 0; i < pa.width * pa.height; i++) {
            image.pixels[i] = pa.color(pa.red(color), pa.green(color), pa.blue(color), 0); //RGBA
            alphaMap.put(i, 0.0d);
        }

        // TODO: alphaMapの配列化

        IntStream.range(0, threadNum).parallel().forEach(tNumber -> {
            LongStream.range(0, r).filter(i -> i % threadNum == tNumber).forEach(i -> {
                double a = Utils.map(pa.random(pa.width), 0, pa.width, x, x2);
                double b = Utils.map(pa.random(pa.height), 0, pa.height, y, y2);

                for (Complex c : !isAntiMode ? buddhabrotCalc(a, b, n) : antiBuddhabrotCalc(a, b, n)) {
                    double posx = c.getReal();
                    double posy = c.getImaginary();
                    if (posx < x || x2 < posx || posy < y || y2 < posy) continue;

                    int w = (int) Utils.map(c.getReal(), x, x2, 0, pa.width);
                    int h = (int) Utils.map(c.getImaginary(), y, y2, 0, pa.height);

                    int index = h * pa.width + w;
                    double alpha = alphaMap.get(index) + alphaDiff;
                    alphaMap.put(index, alpha);
                    image.pixels[index] = pa.color(pa.red(color), pa.green(color), pa.blue(color), (int) alpha);
                }
                if (tNumber == 0 && i % (r / 1000) == 0) {
                    PApplet.println(String.format("%.2f%%", 100.0d * i / r));
                }
            });
        });
        PApplet.println(String.format("%.2f%%", 100.0d));
        image.updatePixels();
        return image;
    }

    //戻り値の内容を反対にするとアンチブッダブロ
    public List<Complex> buddhabrotCalc(double a, double b, long n) {
        Complex c = new Complex(a, b);
        List<Complex> posList = new ArrayList<>();
        Complex z = new Complex(0, 0).add(c);
        for (int i = 1; i <= n; i++) {
            if (z.abs() > 2) {
                return posList;
            }
            posList.add(z);
            z = z.pow(2).add(c);
        }
        return new ArrayList<>();
    }

    public List<Complex> antiBuddhabrotCalc(double a, double b, long n) {
        Complex c = new Complex(a, b);
        List<Complex> posList = new ArrayList<>();
        Complex z = new Complex(0, 0).add(c);
        for (long i = 1; i <= n; i++) {
            if (z.abs() > 2) {
                return new ArrayList<>();
            }
            posList.add(z);
            z = z.pow(2).add(c);
        }
        return posList;
    }

}