package fractallauncher.cellautomaton;

import javax.swing.JOptionPane;

class MyInputDialog {

    public static int getCellSize() {
        String str = JOptionPane.showInputDialog("Input Cell Size (default: 2)");
        int cellSize = 2;

        if (!("".equals(str) || str == null)) {
            try {
                cellSize = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Cell Size: " + cellSize);

        return cellSize;
    }

    public static boolean getIsInitRandom() {
        Object[] possibleValues = {"Center", "Random"};
        Object selectedValue = (String) JOptionPane.showInputDialog(null, "Select Init Type", null,
                JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
        boolean isRandom = false;
        if (selectedValue != null && selectedValue instanceof String) {
            if (((String) selectedValue).equals("Random")) {
                isRandom = true;
            }
        }
        if (isRandom)
            System.out.println("Init opt: Random");
        if (!isRandom)
            System.out.println("Init opt: Center");
        return isRandom;
    }

}
