package fractallauncher.buddhabrot;

import javax.swing.JOptionPane;

public class MyInputDialog {
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
}