package fractallauncher.cellautomaton;

import java.util.Arrays;

import processing.core.PApplet;

class CA1d {

    private int width;
    private int height;
    private float cellSize;
    private int[] rules;
    private int[] cells;
    private int step = 0;

    public CA1d(int w, int h, float cellSize, int[] rules, boolean isInitRandom) {
        this.width = w;
        this.height = h;
        this.cellSize = cellSize;
        this.rules = rules;
        this.cells = new int[w * h];
        Arrays.fill(cells, 0);

        if (isInitRandom == true) {
            for (int i = 0; i < width; i++) {
                cells[i] = Math.random() < 0.02 ? 1 : 0;
            }
        } else {
            cells[w / 2] = 1;
        }
    }

    public int getCell(int x, int y) {
        return cells[x + y * width];
    }

    public void setCell(int x, int y, int value) {
        cells[x + y * width] = value;
    }

    public void progressStep() {
        if (isFinished()) return;

        step++;

        int left, right, center;

        for (int i = 0; i < width; i++) {
            if (i == 0) {
                left = getCell(width - 1, step - 1);
            } else {
                left = getCell(i - 1, step - 1);
            }

            center = getCell(i, step - 1);

            if (i == width) {
                right = getCell(0, step - 1);
            } else {
                right = getCell(i + 1, step - 1);
            }

            setCell(i, step, execRules(left, center, right));
        }
    }

    public boolean isFinished() {
        return this.step == this.height - 1;
    }

    public void draw(PApplet pa) {
        for (int i = 0; i < this.width; i++) {
            if (getCell(i, step) % 2 == 1) {
                pa.noStroke();
                pa.fill(255, 99, 71); // tomato
            } else {
                pa.noFill();
                pa.noStroke();
            }
            pa.rect(cellSize * i, cellSize * step, cellSize, cellSize);
        }
    }

    public int execRules(int a, int b, int c) {
        if (a == 1 && b == 1 && c == 1) return rules[0];
        else if (a == 1 && b == 1 && c == 0) return rules[1];
        else if (a == 1 && b == 0 && c == 1) return rules[2];
        else if (a == 1 && b == 0 && c == 0) return rules[3];
        else if (a == 0 && b == 1 && c == 1) return rules[4];
        else if (a == 0 && b == 1 && c == 0) return rules[5];
        else if (a == 0 && b == 0 && c == 1) return rules[6];
        else if (a == 0 && b == 0 && c == 0) return rules[7];
        else return 0;
    }

}
