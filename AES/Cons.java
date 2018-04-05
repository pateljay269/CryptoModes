
/*
 * Project - DES
 * Created By Patel Jay.
 * On 21 3 2018.
 */

import java.math.BigInteger;

public class Cons {

    public static final String[] RCON = {"01", "02", "04", "08", "10", "20", "40", "80", "1b", "36"};

    public static final String[][] SUB = {
            {"63", "7C", "77", "7B", "F2", "6B", "6F", "C5", "30", "01", "67", "2B", "FE", "D7", "AB", "76"}, //0
            {"CA", "82", "C9", "7D", "FA", "59", "47", "F0", "AD", "D4", "A2", "AF", "9C", "A4", "72", "C0"}, //1
            {"B7", "FD", "93", "26", "36", "3F", "F7", "CC", "34", "A5", "E5", "F1", "71", "D8", "31", "15"}, //2
            {"04", "C7", "23", "C3", "18", "96", "05", "9A", "07", "12", "80", "E2", "EB", "27", "B2", "75"}, //3
            {"09", "83", "2C", "1A", "1B", "6E", "5A", "A0", "52", "3B", "D6", "B3", "29", "E3", "2F", "84"}, //4
            {"53", "D1", "00", "ED", "20", "FC", "B1", "5B", "6A", "CB", "BE", "39", "4A", "4C", "58", "CF"}, //5
            {"D0", "EF", "AA", "FB", "43", "4D", "33", "85", "45", "F9", "02", "7F", "50", "3C", "9F", "A8"}, //6
            {"51", "A3", "40", "8F", "92", "9D", "38", "F5", "BC", "B6", "DA", "21", "10", "FF", "F3", "D2"}, //7
            {"CD", "0C", "13", "EC", "5F", "97", "44", "17", "C4", "A7", "7E", "3D", "64", "5D", "19", "73"}, //8
            {"60", "81", "4F", "DC", "22", "2A", "90", "88", "46", "EE", "B8", "14", "DE", "5E", "0B", "DB"}, //9
            {"E0", "32", "3A", "0A", "49", "06", "24", "5C", "C2", "D3", "AC", "62", "91", "95", "E4", "79"}, //a
            {"E7", "C8", "37", "6D", "8D", "D5", "4E", "A9", "6C", "56", "F4", "EA", "65", "7A", "AE", "08"}, //b
            {"BA", "78", "25", "2E", "1C", "A6", "B4", "C6", "E8", "DD", "74", "1F", "4B", "BD", "8B", "8A"}, //c
            {"70", "3E", "B5", "66", "48", "03", "F6", "0E", "61", "35", "57", "B9", "86", "C1", "1D", "9E"}, //d
            {"E1", "F8", "98", "11", "69", "D9", "8E", "94", "9B", "1E", "87", "E9", "CE", "55", "28", "DF"}, //e
            {"8C", "A1", "89", "0D", "BF", "E6", "42", "68", "41", "99", "2D", "0F", "B0", "54", "BB", "16"} //f
    };

    public static final int[][] MixCol = {
            {2, 3, 1, 1},
            {1, 2, 3, 1},
            {1, 1, 2, 3},
            {3, 1, 1, 2}
    };

    public static String[][] rotWord(String[][] key) {
        String arr[][] = new String[4][4];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = "";
            }
        }

        int n = key[3].length;
        String temp = key[3][0];
        System.arraycopy(key[3], 1, arr[3], 0, n - 1);
        arr[3][n - 1] = temp;

        String t[] = arr[0];
        arr[0] = arr[3];
        arr[3] = t;

        return arr;
    }

    public static String hexXOR(String s1, String s2) {
        String xor = "";
        if (s1.length() == s2.length()) {
            for (int i = 0; i < s1.length(); i++) {
                int n1 = Integer.parseInt(s1.charAt(i) + "", 16);
                int n2 = Integer.parseInt(s2.charAt(i) + "", 16);
                int n3 = n1 ^ n2;
                xor += String.format("%X", n3);
            }
        }
        return xor;
    }

    public static String char2Hex(int ascii) {
        int ar[] = setBit8(ascii);
        String str = "";

        for (int i = 0; i < ar.length; i += 4) {
            String c = "";
            for (int j = 0; j < 4; j++) {
                c += ar[i + j];
            }
            str += bin2Hex(c);
        }
        return str;
    }

    private static String bin2Hex(String binary) {
        int dec = Integer.parseInt(binary, 2);
        return Integer.toString(dec, 16).toUpperCase();
    }

    public static String hex2Bin(String hex) {
        return new BigInteger(hex, 16).toString(2);
    }

    private static int[] hex2BinAr(String hex) {
        String b = new BigInteger(hex, 16).toString(2);
        int size = 4;
        int plain[] = new int[size];
        for (int k = size, j = b.length() - 1; j >= 0; j--) {
            plain[--k] = Integer.parseInt(b.charAt(j) + "");
        }
        return plain;
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

    public static String[] permutation(String ar[], String table[][]) {
        try {
            String arr[] = new String[ar.length];
            for (int i = 0; i < arr.length; i++) {
                String xy = ar[i];
                int x = Integer.parseInt(hex2Bin(xy.charAt(0) + ""), 2);
                int y = Integer.parseInt(hex2Bin(xy.charAt(1) + ""), 2);
                arr[i] = table[x][y];
            }
            return arr;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
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

    public static String[][] mixCol(String arB[][]) {
        String mixCol[][] = new String[4][4];

        arB = colRow(arB);
        for (int r = 0; r < mixCol.length; r++) {

            String col[] = new String[4];
            for (int j = 0; j < 4; j++) {
                String temp[] = new String[4];
                for (int c = 0; c < 4; c++) {

                    switch (MixCol[j][c]) {
                        case 2:
                            temp[c] = dot2(arB[r][c]);
                            break;

                        case 3:
                            String old = arB[r][c];
                            temp[c] = hexXOR(dot2(arB[r][c]), old);
                            break;

                        default:
                            temp[c] = arB[r][c];
                            break;
                    }
                }

                String xor = hexXOR(hexXOR(temp[0], temp[1]), hexXOR(temp[2], temp[3]));
                col[j] = xor;
            }
            mixCol[r] = col;
        }
        mixCol = colRow(mixCol);
        return mixCol;
    }

    public static String dot2(String hex) {
        final int[] MIXOR = {0, 0, 0, 1, 1, 0, 1, 1};

        int ar[] = new int[8];
        for (int j = 0, k = 0; j < hex.length(); j++) {
            int pBit[] = hex2BinAr(hex.charAt(j) + "");
            for (int aBit : pBit) {
                ar[k++] = aBit;
            }
        }

        boolean isXor = ar[0] == 1;
        ar = leftShift(ar, 1);
        ar[ar.length - 1] = 0;

        if (isXor) {
            for (int j = 0; j < ar.length; j++) {
                ar[j] = ar[j] ^ MIXOR[j];
            }
        }

        String Ohex = "";
        for (int aBit : ar) {
            Ohex += aBit;
        }

        Ohex = char2Hex((char) Integer.parseInt(Ohex, 2));

        return Ohex;
    }

    private static int[] leftShift(int arr[], int round) {
        for (int shift = 0; shift < round; shift++) {
            int n = arr.length;
            int temp = arr[0];
            System.arraycopy(arr, 1, arr, 0, n - 1);
            arr[n - 1] = temp;
        }
        return arr;
    }

    public static String[][] leftshiftRow(String[] ar) {
        String arr[][] = half4(ar);

        arr = colRow(arr);
        arr[0] = leftShift(arr[0], 0);
        arr[1] = leftShift(arr[1], 1);
        arr[2] = leftShift(arr[2], 2);
        arr[3] = leftShift(arr[3], 3);

        return arr;
    }

    private static String[] leftShift(String[] arr, int round) {
        for (int shift = 0; shift < round; shift++) {
            int n = arr.length;
            String temp = arr[0];
            System.arraycopy(arr, 1, arr, 0, n - 1);
            arr[n - 1] = temp;
        }
        return arr;
    }

    private static int[] setBit8(int ascii) {
        int size = 8;
        int plain[] = new int[size];
        String pt = Integer.toBinaryString(ascii);
        for (int k = size, j = pt.length() - 1; j >= 0; j--) {
            plain[--k] = Integer.parseInt(pt.charAt(j) + "");
        }
        return plain;
    }

    public static String hex2Char(String[] ar) {
        String ct = "";

        for (int i = 0; i < ar.length; i++) {
            String bin = "";
            for (int j = 0; j < ar[i].length(); j++) {
                bin += hex2Bin(ar[i].charAt(j) + "");
            }
            ct += (char) Integer.parseInt(bin, 2);
        }

        return ct;
    }

    //Column To Row <=> Row To Column Transform
    public static String[][] colRow(String[][] ar) {
        String arr[][] = new String[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arr[j][i] = ar[i][j];
            }
        }
        return arr;
    }
}
