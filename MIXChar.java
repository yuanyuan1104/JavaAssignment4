package q3;

import java.util.Scanner;

/**
 * Class MIXChar
 * <p>Reads a line from user input, if all the characters are valid MIXChar
 * convert them to have the correct numerical values. Then encode them into
 * a long array. Finally, convert the MIXChar characters to a Java string and
 * print out them.
 * </p>
 *
 * @author Zhang Yuanyuan
 * @version 2017-11-30
 */
public class MIXChar {
    /**base of MIX computer.*/
    private static final int BASE = 56;
    /**represents special symbol DELTA.*/
    private static final char DELTA = '\u0394';
    /**represents special symbol SIGMA.*/
    private static final char SIGMA = '\u03A3';
    /**represents special symbol PI.*/
    private static final char PI = '\u03A0';
    /**char array represents all MIXChar characters.*/
    private static char[] mixChar = {' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 
        'H', 'I', DELTA, 'J', 'K', 'L',
        'M', 'N', 'O', 'P', 'Q', 'R', SIGMA, PI, 'S', 'T', 'U', 'V', 'W', 'X',
        'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', ',', 
        '(', ')', '+', '-', '*', '/', '=', '$', '<', '>', '@', ';', ':', '\''};
    /**char value of MIXChar.*/
    private char mix;
    
    /**
     * Contructs MIXChar objects.
     * @param mix sets the char value for MIXChar
     */
    public MIXChar(char mix) {
        this.mix = mix;
    } 

    /**
     * <p>Drives the program.</p>
     *
     * @param args unused
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("input a line for encoding and decoding: ");
        String str = scan.nextLine();
        
        try {
            MIXChar[] mc = toMIXChar(str);
            long[] l = encode(mc);
            
            System.out.println("the long[]:");
            for (long l1 : l) {
                System.out.println(l1);
            }
            
            MIXChar[] mc1 = decode(l);
            System.out.println("\nJava string:");
            for (int j = 0; j < mc1.length; j++) {
                System.out.print(mc1[j].toString());
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        scan.close();
        System.out.println("\nQuestion three was called and ran sucessfully.");
    }
    
    /**
     * Checks if a input char is a MIXChar.
     * @param c sets the input to check
     * @return true if input corresponds to a MIXChar character, 
     *         false otherwise 
     */
    public static boolean isMIXChar(char c) {
        for (int i = 0; i < mixChar.length; i++) {
            if (c == mixChar[i]) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Converts a char to the corresponding MIXChar.
     * @param c sets the input char to convert to MIXChar
     * @return mc as a MIXChar
     */
    public static MIXChar toMIXChar(char c) {
        MIXChar mc;
        for (int i = 0; i < mixChar.length; i++) {
            if (isMIXChar(c)) {
                mc = new MIXChar(c);
                return mc;
            }
        }
        throw new IllegalArgumentException("conversion not possible, "
            + "one or more characters are not in the MIXChar table");
    }
    
    /**
     * Converts MIXChar character to corresponding Java char.
     * @param x sets the MIXChar to convert
     * @return c as a char
     */
    public static char toChar(MIXChar x) {
        char c = ' ';
        for (int i = 0; i < mixChar.length; i++) {
            x = new MIXChar(c);
            if (c == mixChar[i]) {
                return c;
            }
        }
        return c;
    }
    
    /**
     * Converts MIXChar array to a string.
     * @param mixC sets MIXChar array to convert 
     * @return str as a string
     */
    public static String toString(MIXChar[] mixC) {
        String str = "";
        for (MIXChar mc : mixC) {
            str += toChar(mc);
        }
        return str;
    }
    
    /**
     * Converts a string to array of MIXChar characters 
     * corresponding to the chars in s.
     * @param s sets a string to convert
     * @return mc as a MIXChar array
     */
    public static MIXChar[] toMIXChar(String s) {
        final int elements = s.length();
        MIXChar[] mc = new MIXChar[elements];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (isMIXChar(c)) {
                mc[i] = toMIXChar(c);
            } else {
                throw new IllegalArgumentException("conversion not "
                    + "possible, one or more characters are not in the "
                    + "MIXChar table");
                }
        }
        return mc;
    }
    
    /**
     * Encodes the MIXChar array to a long array holding the values
     * of the m packed 11 MIXChar characters per long.
     * @param m sets a MIXChar array to encode
     * @return longArray as a long array
     */
    public static long[] encode(MIXChar[] m) {
        final int pack = 11;
        int size = (int) Math.ceil(1.0 * m.length / pack);
        long[] longArray = new long[size];
        for (int k = 0; k < m.length; k++) {
            int i = k / pack;
            int j = k % pack;
            longArray[i] += (long) Math.pow(BASE, j) * m[k].ordinal();
        }
        return  longArray;
    }
    
    /**
     * Decodes long array l to MIXChar array with characters corresponding
     * to unpacking the input array(assuming 11 characters are packed per long,
     * but the last long may having fewer than 11).
     * @param l sets the long array to decode
     * @return newMc as a MIXChar array
     */
    public static MIXChar[] decode(long[] l) {
        final int pack = 11;
        int size = l.length * pack;
        MIXChar[] mc = new MIXChar[size];
        for (int i = 0; i < l.length; i++) {
            long li = l[i];
            for (int j = pack - 1; j >= 0; j--) {
                long divisor = (long) Math.pow(BASE, j);
                int k = i * pack + j;
                int index = (int) Long.divideUnsigned(li, divisor);
                mc[k] = toMIXChar(mixChar[index]);
                li = Long.remainderUnsigned(li, divisor);
            }
        }
        // Find the # of trailing spaces.
        int trailingSpaces = 0;
        for (int c = mc.length - 1; c >= 0; c--) {
            if (mc[c].ordinal() == 0) {
                trailingSpaces += 1;
            } else {
                break;
            }
        }
        // Create the array for the return - copy the chars with the 
        // trailing spaces truncated.
        int newSize = size - trailingSpaces;
        MIXChar[] newMc = new MIXChar[newSize];
        System.arraycopy(mc, 0, newMc, 0, newSize);

        return newMc;    
    }
    
    /**
     * Returns the numerical value of a MIXChar.
     * @return value as an int
     */
    public int ordinal() {
        int value = 0;
        for (int i = 0; i < mixChar.length; i++) {
            if (mix == mixChar[i]) {
                value = i;
            }
        }    
        return value;
    }
    
    /**
     * Returns string containing this MIXChar as a Java char.
     * @return str an a string
     */
    public String toString() {
        MIXChar mc = new MIXChar(mix);
        String str = "" + mc.mix;
        return str;
    }
}
