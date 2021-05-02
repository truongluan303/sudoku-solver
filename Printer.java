import java.io.PrintStream;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Printer {


    public void write(int[][] input, int[][] result) {
        try {
            String filename = "history.txt";
            File myFile = new File(filename);
            myFile.createNewFile();

            FileOutputStream ofStream = new FileOutputStream(filename, true);
            PrintStream myPrinter = new PrintStream(ofStream);

            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"); // format date and time
            LocalDateTime instance = LocalDateTime.now(); // get current date and time

            myPrinter.println(formatter.format(instance));
            myPrinter.println("\nThe input sudoku is:");
            print(myPrinter, input);
            myPrinter.println("\n\nThe solution is:");
            print(myPrinter, result);
            myPrinter.println("\n==============================================");

            myPrinter.close();
            ofStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void print(PrintStream printer, int[][] output) {
        int SIZE = 9;
        if (output != null) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (output[i][j] == 0)
                        printer.print(" * ");
                    else
                        printer.print(" " + output[i][j] + " ");
                    if ((j + 1) % (SIZE / 3) == 0)
                        printer.print("|");
                }
                printer.println();
                if ((i + 1) % (SIZE / 3) == 0)
                    printer.println("------------------------------");
            }
        }
        else {
            printer.println("The Sudoku is unsolvable");
        }
    }
}