package fractallauncher.utils;

public class PVectorD {

    public double x, y;

    @SuppressWarnings("unused")
    private PVectorD() {
    }

    public PVectorD(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

}

