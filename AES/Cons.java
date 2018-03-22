/*
 * Project - DES
 * Created By Patel Jay.
 * On 20 3 2018.
 */

import java.math.BigInteger;

public class Cons {

    public static final String[] RCON = {"01", "02", "04", "08", "10", "20", "40", "80", "1b", "36"};

    public static final int[][] MixColInt = {
            {02, 03, 01, 01},
            {01, 02, 03, 01},
            {01, 01, 02, 03},
            {03, 01, 01, 02}
    };

    public static final String[][] MixColStr = {
            {"02", "03", "01", "01"},
            {"01", "02", "03", "01"},
            {"01", "01", "02", "03"},
            {"03", "01", "01", "02"}
    };

    public static void prAr(String ar[]) {
        System.out.print("");
        for (String a : ar) {
            System.out.print(a + " ");
        }
        System.out.println("");
    }

    public static String char2Bin(int ascii) {
        int ar[] = setBit8(ascii);
        String str = "";
        for (int a : ar) {
            str += a;
        }
        return str;
    }

    public static String char2Hex(int ascii) {
        int ar[] = setBit8(ascii);
        String str = "";
        for (int a : ar) {
            str += a;
        }
        return bin2Hex(str);
    }

    private static String bin2Hex(String binary) {
        int dec = Integer.parseInt(binary, 2);
        return Integer.toString(dec, 16).toUpperCase();
    }

    private static String hex2Bin(String hex) {
        return new BigInteger(hex, 16).toString(2);
    }

    public static String[] merge(String arr[][]) {
        String ar[] = new String[arr.length * arr[0].length];
        int k = 0;
        for (String[] i : arr) {
            for (String j : i) {
                ar[k++] = j;
            }
        }
        return ar;
    }

    public static String[] leftShift(String[] arr, int round) {
        for (int shift = 0; shift < round; shift++) {
            int n = arr.length;
            String temp = arr[0];
            System.arraycopy(arr, 1, arr, 0, n - 1);
            arr[n - 1] = temp;
        }
        return arr;
    }

    public static String[][] half4(String arr[]) {
        String ar[][] = new String[4][4];
        for (int i = 0, k = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++, k++) {
                ar[i][j] = arr[k];
            }
        }
        return ar;
    }

    public static int[] lfsr(String iv) {
        try {
            int size = (int) (Math.pow(2, iv.length()) - 1);
            int[] queue = new int[size];
            Double.parseDouble(iv);

            for (int i = 0; i < iv.length(); i++) {
                int t = Integer.parseInt(iv.charAt(i) + "");
                if (t < 0 || t > 1) {
                    throw new NumberFormatException("Enter Only 0 And 1");
                }
            }

            int j = 0;
            for (int i = iv.length() - 1; i >= 0; i--) {
                queue[j++] = Integer.parseInt(iv.charAt(i) + "");
            }

            for (int i = iv.length(); i < size; i++) {
                int t = (queue[i - iv.length()] + queue[i - iv.length() + 1]) % 2;
                queue[j++] = t;
            }

            return queue;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static int[] setBit8(int ascii) {
        int size = 8;
        int plain[] = new int[size];
        String pt = Integer.toBinaryString(ascii);
        for (int k = size, j = pt.length() - 1; j >= 0; j--) {
            plain[--k] = Integer.parseInt(pt.charAt(j) + "");
        }
        return plain;
    }

}
