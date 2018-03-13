/* 
 *  Created By Patel Jay
 *  On 12 Mar 2018
 */
package ecb;

import java.util.Scanner;

public class ECB {

    private static Scanner in;

    public static void main(String[] args) {
        in = new Scanner(System.in);

        System.out.println("Enter Text:");
        String msg = in.nextLine();

        setData(msg);
    }

    private static void setData(String msg) {
        try {
            String iv;

            System.out.println("More Than 3 Bits For IV");
            System.out.println("Iv:");
            iv = in.next();

            if (iv.length() < 4) {
                throw new NumberFormatException("Enter More Than 3 Bit");
            }

            for (int j = 0; j < iv.length(); j++) {
                int n = Integer.parseInt(iv.charAt(j) + "");
                if (n > 1 || n < 0) {
                    throw new NumberFormatException("Enter Only 0 And 1");
                }
            }
            String cb1 = ECBMode(msg, iv);
            String cb2 = ECBMode(cb1, iv);

            System.out.println("ECB:\t" + cb1 + "\t" + cb2);

        } catch (NumberFormatException e) {
            System.out.println(e.getLocalizedMessage());
            setData(msg);
        }
    }

    private static String ECBMode(String msg, String IV) {
        try {
            int lfsr[] = lfsr(IV);

            String output = "";

            for (int j = 0; j < msg.length(); j++) {
                String pBit = "", cBit = "";
                pBit = Integer.toBinaryString(msg.charAt(j));

                int k = 8;
                int plain[] = new int[k];

                for (int i = pBit.length() - 1; i >= 0; i--) {
                    plain[--k] = Integer.parseInt(pBit.charAt(i) + "");
                }

                for (int i = 0; i < plain.length; i++) {
                    int t = (lfsr[i] + plain[i]) % 2;
                    cBit += t;
                }

                int t = Integer.parseInt(cBit, 2);
                output += (char) t;
            }

//            System.out.println("ECB:- " + output);
            return output;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private static int[] lfsr(String iv) {
        try {
            int size = (int) (Math.pow(2, iv.length()) - 1);
            int[] queue = new int[size];
            Integer.parseInt(iv);

            if (iv.length() < 4) {
                throw new NumberFormatException("Enter More Than 3 Bit");
            }

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
            System.out.println(e.getMessage());
        }
        return null;
    }
}
