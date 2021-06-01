import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


/**
 * @author Luan Truong
 */

// This will be out main GUI
public class SudokuFrame extends javax.swing.JFrame {

    private final int SIZE = 9;
    private JTextField message;
    private JTextField[][] inputs;
    private int[][] results;
    private JButton okButton;
    private boolean solved = false;
    private Printer myPrinter;
    // End of variables declaration



    /**
     * Creates new form SudokuFrame
     */
    public SudokuFrame() {
        initComponents();
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        myPrinter = new Printer();
    }



    /**
     * This method is called from within the constructor to initialize the GUI.
     */
    private void initComponents() {

        // Variables declaration
        JPanel panel = new JPanel();
        JPanel[][] subPanels = new JPanel[3][3];
        message = new javax.swing.JTextField();
        inputs = new JTextField[SIZE][SIZE];
        okButton = new JButton("Solve");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel.setLayout(new java.awt.GridLayout(3, 3, 0, 0));

        message.setFont(new java.awt.Font("Tahoma", Font.BOLD, 15));
        message.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        message.setText("Please input the existing numbers in the sudoku below");
        message.setBackground(Color.WHITE);
        message.setEditable(false);
        message.setFocusable(false);

        okButton.setFocusPainted(false);
        okButton.addActionListener(action());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
                        .addComponent(message, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(260, 260, 260)
                                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                count++;
                subPanels[i][j] = new JPanel();
                if (count % 2 == 0)
                    subPanels[i][j].setBackground(new Color(179, 223, 244));
                else
                    subPanels[i][j].setBackground(new Color(252, 236, 182));
                subPanels[i][j].setLayout(new java.awt.GridLayout(3, 3, 0, 0));
                subPanels[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                panel.add(subPanels[i][j]);
            }
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                inputs[i][j] = new JTextField();
                inputs[i][j].setFont(new Font("Verdana", Font.BOLD, 28));
                inputs[i][j].setHorizontalAlignment(javax.swing.JTextField.CENTER);
                inputs[i][j].setOpaque(false);
                subPanels[i / 3][j / 3].add(inputs[i][j]);
            }
        }
        pack();
    }




    /**
    * Actions performed when the button is clicked
     */
    private ActionListener action() {
        return e -> {
            /* If the sudoku input is not solved yet, then clicking the button
             * will solve the sudoku and return the result to the GUI */
            if (!solved) {
                int[][] inputArr = returnInput();
                Solver mySolver = new Solver(returnInput());
                results = mySolver.returnResult();

                // if the input sudoku is solved successfully
                if (results != null) {
                    // Change the message box to inform the sudoku is solved
                    message.setBackground(new Color(142, 251, 123));
                    message.setText("The Result is:");
                    // Display the solved sudoku
                    for (int i = 0; i < SIZE; i++)
                        for (int j = 0; j < SIZE; j++) {
                            inputs[i][j].setText(Integer.toString(results[i][j]));
                            inputs[i][j].setEditable(false);
                        }
                }
                // if the input sudoku is unsolvable
                else {
                    // Change the message box to inform that the sudoku is unsolvable
                    message.setBackground(Color.RED);
                    message.setText("The given Sudoku is Unsolvable!");
                }
                okButton.setText("OK");
                solved = true;
                // Output to the history text file
                myPrinter.write(inputArr, results);
            }
            /* If the input sudoku is solved and result is displayed, then
             * clicking the button will clear out the cells and get ready for new inputs */
            else {
                // Reset the input boxes to their initial state
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        inputs[i][j].setText("");
                        inputs[i][j].setEditable(true);
                        inputs[i][j].setOpaque(false);
                    }
                }
                solved = false;
                // Reset the message box and the button
                okButton.setText("Solve");
                message.setBackground(Color.WHITE);
                message.setText("Please input the existing numbers in the sudoku below");
            }
        };
    }




    /**
    * Get the inputs from the text boxes and turn them into a 9x9 array of integer
     */
    private int[][] returnInput() {
        int[][] numbers = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (inputs[i][j].getText() == null || inputs[i][j].getText().equals("")) {
                    numbers[i][j] = 0;
                }
                else {
                    numbers[i][j] = Integer.parseInt(inputs[i][j].getText());
                    inputs[i][j].setOpaque(true);
                    inputs[i][j].setBackground(Color.LIGHT_GRAY);
                }
            }
        }
        return numbers;
    }
}
