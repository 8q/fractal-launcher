package fractallauncher.mandelbrot;

import java.util.stream.IntStream;

import org.apache.commons.math3.complex.Complex;

import processing.core.PApplet;
import processing.core.PImage;
import fractallauncher.utils.Utils;
import fractallauncher.utils.PVectorD;

class MandelbrotGenerator {

    private PApplet pa;
    private int colorType;
    private double power;
    private int threadNum;
    private PImage gradient = null;
    private boolean isJuliaMode = false;

    //ジュリア集合の初期パラメーター
    private double juliaA = 0.285d;
    private double juliaB = 0.01d;

    public MandelbrotGenerator(PApplet pa, int colorType, double power, int threadNum) {
        this.pa = pa;
        this.colorType = colorType;
        this.power = power;
        this.threadNum = threadNum;
    }

    public void setGradientImage(PImage gradient) {
        this.gradient = gradient;
    }

    public void setJuliabrotMode(boolean isJuliaMode) {
        this.isJuliaMode = isJuliaMode;
    }

    public void setJuliabrotParams(double juliaA, double juliaB) {
        this.juliaA = juliaA;
        this.juliaB = juliaB;
    }

    public PImage mandelbrot(PVectorD p1, PVectorD p2, int n) {
        PImage image = new PImage(pa.width, pa.height, PApplet.RGB);
        IntStream.range(0, threadNum).parallel().forEach(tNumber -> {
            IntStream.range(0, pa.width).filter(i -> i % threadNum == tNumber).forEach(i -> {
                IntStream.range(0, pa.height).forEach(j -> {
                    double a = Utils.map(i, 0, pa.width, p1.x, p2.x);
                    double b = Utils.map(j, 0, pa.height, p1.y, p2.y);
                    int r = !isJuliaMode ? mandelbrotCalc(a, b, n, power) : juliabrotCalc(a, b, n, power);
                    image.pixels[i + j * pa.width] = getPlotColor(pa, r, colorType);
                });
            });
        });
        return image;
    }

    public int getPlotColor(PApplet pa, int count, int type) {
        switch (type) {
            case 1:
                return getPlotColorType1(pa, count);
            case 2:
                return getPlotColorType2(pa, count);
            case 3:
                return getPlotColorType3(pa, count);
            default:
                return getPlotColorType1(pa, count);
        }
    }

    public int getPlotColorType1(PApplet pa, int count) {
        if (count <= 0) return pa.color(0, 0, 0);

        int base = 64;
        int r, g, b;
        int d = (count % base) * 256 / base;
        int m = d / 32;

        switch (m) {
            // 青→マゼンタ
            case 0:
                r = 63 + 6 * d;
                g = 63;
                b = 255;
                break;
            // マゼンタ→白
            case 1:
                r = 255;
                g = 63 + 6 * (d - 32);
                b = 255;
                break;
            // 白→シアン
            case 2:
                r = 255 - 6 * (d - 64);
                g = 255;
                b = 255;
                break;
            // シアン→緑
            case 3:
                r = 63;
                g = 255;
                b = 255 - 6 * (d - 96);
                break;
            // 緑→黄
            case 4:
                r = 63 + 6 * (d - 128);
                g = 255;
                b = 63;
                break;
            // 黄→赤
            case 5:
                r = 255;
                g = 255 - 6 * (d - 160);
                b = 63;
                break;
            // 赤→黒
            case 6:
                r = 255 - 6 * (d - 192);
                g = 63;
                b = 63;
                break;
            // 黒→青
            case 7:
                r = 63;
                g = 63;
                b = 63 + 6 * (d - 224);
                break;
            default:
                r = 0;
                g = 0;
                b = 0;
                break;
        }
        return pa.color(r, g, b);
    }

    public int getPlotColorType2(PApplet pa, int count) {
        return pa.color((count % 256) * 5, (count % 256) * 2, (count % 256) * 1);
    }

    public int getPlotColorType3(PApplet pa, int count) {
        if (gradient == null) {
            System.out.println("Gradient Image not found");
            return pa.color(0, 0, 0);
        }
        if (count <= 0) return pa.color(0, 0, 0);

        return gradient.get(count % gradient.width, 0);
    }

    public int mandelbrotCalc(double a, double b, int n, double power) { // cを固定してz_0を複素平面上の点にするとジュリア集合
        Complex c = new Complex(a, b);
        if (c.abs() > 2) return 0;

        Complex z = new Complex(0, 0).add(c); // z_0を適当に変えると虫食いみたいになる
        for (int i = 1; i <= n; i++) {
            if (z.abs() > 2) {
                return i;
            }
            z = z.pow(power).add(c);
        }
        return 0;
    }

    public int juliabrotCalc(double a, double b, int n, double power) {
        Complex c = new Complex(juliaA, juliaB);
        if (c.abs() > 2) return 0;

        Complex z = new Complex(a, b).add(c);
        for (int i = 1; i <= n; i++) {
            if (z.abs() > 2) {
                return i;
            }
            z = z.pow(power).add(c);
        }
        return 0;
    }
}
