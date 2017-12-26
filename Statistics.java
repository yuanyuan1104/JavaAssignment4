package q1;
import java.util.Scanner;

/**
 * Class Statistics
 * <p>
 * Reads the values into an array from System.in with no prompt, terminating 
 * when there is no more data in numbers.txt. Computes both the mean and 
 * standard deviation as floating-point values.
 * </p>
 *
 * @author Zhang Yuanyuan
 * @version 2017-11-24
 */
public class Statistics {
    /**number of elements stored in array.*/
    private static final int MAXMIUM_NUMBER = 50;
    /**array for storing numbers.*/
    private static int[] numbers = new int[MAXMIUM_NUMBER];
    /**counts the numbers in document.*/
    private static int index;
    /**
     * <p>This is the main method (entry point) that gets called by the JVM.</p>
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextInt()) {
            numbers[index] = scan.nextInt();
            System.out.println(numbers[index]);
            index++;
        }
        System.out.println("the mean is: " + mean());
        System.out.println("the standard deviation is: " + standardDeviation());
        scan.close();
        System.out.println("Question one was called and ran sucessfully.");
    }
    /**
     * Returns the average.
     * @return average as a double
     */
    private static double mean() {
        int sum = 0;
        double average;
        for (int i = 0; i < index; i++) {
            sum += numbers[i];
        }       
        average = (double) sum / index;
        return average;
    }
    /**
     * Returns standard deviation.
     * @return return standard deviation as a double
     */
    private static double standardDeviation() {
        double squareSum = 0;
        double sd = 0;
        for (int i = 0; i < index; i++) {
            squareSum += Math.pow(numbers[i] - mean(), 2);
        }
        sd = Math.sqrt(squareSum / (index - 1));
        return sd;
    }
};
