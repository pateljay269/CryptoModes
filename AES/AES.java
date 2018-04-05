
/*
 * Project - AES
 * Created By Patel Jay.
 * On 19 3 2018.
 */

import java.util.Scanner;

public class AES {

    private static final int ROUND = 10;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String input, ky;

        System.out.print("Enter Plain Text: ");
        input = in.nextLine();
        System.out.print("Enter Iv: ");
        ky = in.nextLine();

//        input = "1234567812345678";
//        ky = "123456";
        String[][] key = keyGen(ky);

        String c1 = encrypt(input, key);

        System.out.println("Cipher Text:" + c1);

    }

    private static String encrypt(String input, String[][] key) {
        String output = "";

        for (int h = 0; h < input.length(); h += 16) {
            String s1;
            try {
                s1 = input.substring(h, h + 16);
            } catch (Exception e) {
                s1 = input.substring(h, input.length());
            }

            String Ai[] = new String[16];
            for (int i = 0; i < 16; i++) {
                Ai[i] = "00";
            }

            for (int j = 0; j < s1.length(); j++) {
                Ai[j] = Cons.char2Hex(s1.charAt(j));
            }

            //Key Addition Layer-Key0
            for (int i = 0; i < 16; i++) {
                Ai[i] = Cons.hexXOR(Ai[i], key[0][i]);
            }

            for (int g = 0; g < ROUND; g++) {

                if (g == ROUND - 1) {
                    break;
                }

                Ai = Cons.permutation(Ai, Cons.SUB);
                String Bi[][] = Cons.leftshiftRow(Ai);  //Shift Row
                Bi = Cons.mixCol(Bi);                   //Mix Column
                Ai = Cons.merge(Cons.colRow(Bi));

                String keyR[] = key[g + 1];

                for (int i = 0; i < Ai.length; i++) {
                    Ai[i] = Cons.hexXOR(Ai[i], keyR[i]);
                }
            }

            //Last Round
            Ai = Cons.permutation(Ai, Cons.SUB);
            Ai = Cons.merge(Cons.leftshiftRow(Ai));
            Ai = Cons.merge(Cons.colRow(Cons.half4(Ai)));

            for (int i = 0; i < Ai.length; i++) {
                Ai[i] = Cons.hexXOR(Ai[i], key[key.length - 1][i]);
            }

            output += Cons.hex2Char(Ai);
        }

        return output;
    }

    private static String[][] keyGen(String sKey) {

        int len = sKey.length();
        if (len <= 16) {
            int k = 16 - len;
            for (int i = 0; i < k; i++) {
                sKey += "p";
            }
        }

        String key[][] = new String[ROUND + 1][16];
        for (int j = 0; j < 16; j++) {
            key[0][j] = Cons.char2Hex(sKey.charAt(j));
        }

        String ky[][] = Cons.half4(key[0]);

        for (int g = 1; g < key.length; g++) {
            String pKey[][] = ky;
            ky = Cons.rotWord(ky);
            ky[0] = Cons.permutation(ky[0], Cons.SUB);

            for (int i = 0; i < ky[0].length; i++) {
                String t2 = pKey[0][i], t1 = ky[0][i], temp = "";
                for (int j = 0; j < t1.length(); j++) {
                    String t3 = Cons.hexXOR(t1.charAt(j) + "", t2.charAt(j) + "");
                    if (i == 0) {
                        String tt = Cons.RCON[g - 1].charAt(j) + "";
                        temp += Cons.hexXOR(tt, t3);
                    } else {
                        temp += t3;
                    }
                }
                ky[0][i] = temp;
            }

            for (int i = 1; i < ky.length; i++) {
                for (int j = 0; j < ky[i].length; j++) {
                    String t1 = pKey[i][j], t2 = ky[i - 1][j], temp = "";
                    for (int k = 0; k < t1.length(); k++) {
                        temp += Cons.hexXOR(t1.charAt(k) + "", t2.charAt(k) + "");
                    }
                    ky[i][j] = temp;
                }
            }
            key[g] = Cons.merge(ky);
        }

        return key;
    }

}
