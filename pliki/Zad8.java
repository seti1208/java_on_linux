import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Zad8 {
    public static void main(String[] args) throws IOException {
        String filePath = "/Users/seti1208/IdeaProjects/src/src/matrix-file.txt";

        int n = 10; // matrix height (lines)
        int m = 15; // matrix width (columns)

        Random random = new Random();

        try (
                FileOutputStream fos = new FileOutputStream(filePath);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                OutputStreamWriter osw = new OutputStreamWriter(bos, "utf-8");
        ) {
            for (int i = 0; i < n; ++i) { // matrix lines
                for (int j = 0; j < m; ++j) { // matrix columns
                    int cell = random.nextInt(1000);

                    osw.write(String.valueOf(cell));
                    osw.write(' ');
                }
                osw.write('\n');
            }
        }

        int[][] matrix = new int[n][m];

        try (
                FileInputStream fis = new FileInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(fis);
                InputStreamReader isr = new InputStreamReader(bis, "utf-8");
        ) {
            Scanner plikWe = new Scanner(isr);

            for (int i = 0; i < n; ++i) { // matrix lines
                if (!plikWe.hasNextBigInteger()) {
                    System.err.println("no matrix lines enouth");

                    return;
                }

                for (int j = 0; j < m; ++j) { // matrix columns
                    if (!plikWe.hasNextBigInteger()) {
                        System.err.println("no matrix emenents enouth");

                        return;
                    }

                    matrix[i][j] = plikWe.nextInt();
                }
            }
        }

        double[] lineAvg = new double[n];
        double[] colAvg = new double[m];

        for (int i = 0; i < n; ++i) { // matrix lines
            double lineSum = 0;

            for (int j = 0; j < m; ++j) { // matrix columns
                lineSum += matrix[i][j];
            }

            lineAvg[i] = lineSum / n;
        }

        for (int j = 0; j < m; ++j) { // matrix columns
            double colSum = 0;

            for (int i = 0; i < n; ++i) { // matrix lines
                colSum += matrix[i][j];
            }

            colAvg[j] = colSum / m;
        }

        for (int i = 0; i < n; ++i) { // matrix lines
            System.out.println(
                    "avg for line #" + i + ": " + lineAvg[i]
            );
        }

        for (int j = 0; j < m; ++j) { // matrix columns
            System.out.println(
                    "avg for col #" + j + ": " + colAvg[j]
            );
        }
    }
}
