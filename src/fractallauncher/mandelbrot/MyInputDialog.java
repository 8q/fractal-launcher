package fractallauncher.mandelbrot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

class MyInputDialog {

    public enum DrawMode {
        Mandelbrot, Juliabrot
    }

    public static double getPower() {
        String str = JOptionPane.showInputDialog("Input Power (default: 2)");
        double power = 2.0d;
        if (!("".equals(str) || str == null)) {
            try {
                power = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Power: " + power);
        return power;
    }

    public static int getColorType() {
        Object[] possibleValues = {"1", "2", "3"};
        Object selectedValue = JOptionPane.showInputDialog(null, "Select Color Type", null,
                JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
        int colorType = 1;
        if (selectedValue != null && selectedValue instanceof String) {
            try {
                colorType = Integer.parseInt((String) selectedValue);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        System.out.println("ColorType: " + colorType);
        return colorType;
    }

    public static String getGradientPath() {
        File dir = new File("./img/gradients");
        List<String> nameList = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            nameList.add(file.getName());
        }

        Object[] possibleValues = nameList.toArray();
        Object selectedValue = JOptionPane.showInputDialog(null, "Select Gradient", null,
                JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
        String gradientName = null;
        if (selectedValue != null && selectedValue instanceof String) {
            gradientName = (String) selectedValue;
        }

        for (File file : files) {
            if (file.getName().equals(gradientName)) {
                System.out.println("Gradient: " + gradientName);
                return file.getPath();
            }
        }
        System.out.println("Gradient " + gradientName + " not found");
        return null;
    }

    public static int getThreadNum() {
        String str = JOptionPane.showInputDialog("Input ThreadNum (default: 2)");
        int threadNum = 2;
        if (!("".equals(str) || str == null)) {
            try {
                threadNum = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        System.out.println("ThreadNum: " + threadNum);
        return threadNum;
    }

    public static DrawMode getDrawMode() {

        DrawMode returnValue = DrawMode.Mandelbrot;
        Object selectionValue = JOptionPane.showInputDialog(null,
                "Choose a draw mode", null,
                JOptionPane.INFORMATION_MESSAGE, null,
                DrawMode.values(), DrawMode.values()[0]);

        if (selectionValue != null && selectionValue instanceof DrawMode) {
            returnValue = (DrawMode) selectionValue;
        }

        System.out.println("DrawMode: " + returnValue);
        return returnValue;
    }

    public static double[] getJuliabrotParams() {
        JTextField julia_a_field = new JTextField();
        JTextField julia_b_field = new JTextField();
        Object[] message = {
                "x0(default: 0.3):", julia_a_field,
                "y0(default: 0.01):", julia_b_field
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Juliabrot params", JOptionPane.OK_CANCEL_OPTION);
        double[] returnValue = new double[]{0.3, 0.01};
        if (option == JOptionPane.OK_OPTION) {
            if (!julia_a_field.getText().equals("") && !julia_b_field.getText().equals("")) {
                try {
                    returnValue[0] = Double.parseDouble(julia_a_field.getText());
                    returnValue[1] = Double.parseDouble(julia_b_field.getText());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("julia_a: " + returnValue[0] + ", julia_b: " + returnValue[1]);
        return returnValue;
    }

}
