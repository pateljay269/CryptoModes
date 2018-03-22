/*
 * Project - DES
 * Created By Patel Jay.
 * On 16 3 2018.
 */

import java.util.Scanner;

public class DES {

    private static Scanner in;

    public static void main(String[] args) {
        in = new Scanner(System.in);

        String input;
        System.out.print("Enter Plain Text: ");
        input = in.nextLine();

        setData(input);
    }

    private static void setData(String input) {
        try {
            String key;
            System.out.print("Enter Iv(7 To 10 Bits Only): ");
            key = in.next();

            if (key.length() < 7 || key.length() > 10) {
                throw new NumberFormatException("Enter 7 To 10 Bits Only For Key");
            }

            int K64[] = new int[64];
            System.arraycopy(Const.lfsr(key), 0, K64, 0, 64);		//Copy First 64 Bit
            int K56[] = Const.permutation(K64, Const.PC1);		//PC-1 Table Permutation

            boolean isEnc = true;
            String c1 = encDec(input, K56, isEnc);		//For Encryption
            String c2 = encDec(c1, K56, !isEnc);		//For Decryption

            System.out.println("OutPut:");
            System.out.println(c1);
            System.out.println(c2);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            setData(input);
        }
    }

    private static String encDec(String input, int[] K56, boolean isEnc) {
        String output = "";

        for (int h = 0; h < input.length(); h += 8) {
            String s1;
            try {
                s1 = input.substring(h, h + 8);			//8 bytes = 64 Bits
            } catch (Exception e) {
                s1 = input.substring(h, input.length());
            }

            int PT[] = new int[64];
            for (int j = 0, k = 0; j < s1.length(); j++) {
                int bit8[] = Const.setBit8(s1.charAt(j));
                for (int aBit8 : bit8) {
                    PT[k++] = aBit8;                        //Set All 64 Bits In One Array
                }
            }

            int CD[][] = Const.half(K56);			//Divide Half C & D
            int IP[] = Const.permutation(PT, Const.IP);		//Initial Permutation Of Input Text
            int LR[][] = Const.half(IP);			//Divide Half L-Left & R-Right

            for (int i = 0; i < 16; i++) {
                if (!isEnc) {
                    if (i != 0)
                        CD = Const.rightShift(CD, Const.kShift[i]);	//Generate Key For Decryption
                } else {
                    CD = Const.leftShift(CD, Const.kShift[i]);		//Generate Key For Encryption
                }

                int kTrans[] = Const.merge(CD);				//Key Transformation
                int KEY[] = Const.permutation(kTrans, Const.PC2);	//Pc-2 Table Permutation

                LR = fFunc(LR, KEY);					//F-Function
            }

            LR = Const.swap(LR);					//After Last Round Swapping Left & Right

            int FP[] = Const.permutation(Const.merge(LR), Const.FP);	//Final Permutation

            String CT = "";

            for (int j = 0; j < FP.length; j += 8) {
                String bit = "";
                for (int i = 0; i < 8; i++) {
                    bit += FP[i + j];
                }
                int ascii = Integer.parseInt(bit, 2);
                CT += (char) ascii;
            }
            output += CT;
        }
        return output;
    }

    private static int[][] fFunc(int[][] LR, int[] k48) {

        int left[] = LR[0], right[] = LR[1];
        int expand[] = Const.permutation(right, Const.E);	//E Table Permutation
        int rXk[] = new int[48];

        for (int i = 0; i < expand.length; i++) {
            rXk[i] = (expand[i] + k48[i]) % 2;			//XOR Of expand & Key
        }

        //region S-Box
        int k = 0, ar32[] = new int[32];
        for (int i = 0, Box = 0; i < rXk.length; i += 6, Box++) {
            int row = 2 * rXk[0] + rXk[5];
            int col = 8 * rXk[1] + 4 * rXk[2] + 2 * rXk[3] + rXk[4];

            int x = Const.S[Box][row][col];             //SBox Table Used eg:S[TableNo][Row][Col]

            int[] sbox = Const.setBit4(x);
            for (int t : sbox) {
                ar32[k++] = t;
            }
        }
        //endregion

        ar32 = Const.permutation(ar32, Const.P);		//P Table Permutation

        for (int l = 0; l < ar32.length; l++) {
            ar32[l] = ar32[l] ^ left[l];			//XOR Of Left & F-function Output
        }

        int LRi[][] = new int[2][];
        LRi[0] = right;					//Swapping Of Left & Right
        LRi[1] = ar32;
        return LRi;
    }
}