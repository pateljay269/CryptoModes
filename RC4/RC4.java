/*
 * Project - RC4
 * Created By Patel Jay.
 * On 20 3 2018.
 */

import java.util.Scanner;

public class RC4 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String msg = "", key = "";
        System.out.print("Enter Text: ");
        msg = in.nextLine();

        System.out.print("Enter IV: ");
        key = in.next();

        int s[] = new int[256];
        char input[] = msg.toCharArray();
        char ky[] = key.toCharArray();

        for (int i = 0; i < 256; i++) {
            s[i] = i;
        }

        for (int i = 0, j = 0; i < 256; i++) {
            j = (j + s[i] + ky[i % ky.length]) % 256;
            int temp = s[i];
            s[i] = s[j];
            s[j] = temp;
        }

        int pt[] = new int[msg.length()];
        int ct[] = new int[msg.length()];
        int dpt[] = new int[msg.length()];

        for (int i = 0; i < msg.length(); i++) {
            pt[i] = (int) input[i];
        }

        for (int i = 0, j = 0, l = 0; l < msg.length(); l++) {
            i = (i + 1) % 256;
            j = (j + s[1]) % 256;
            int temp = s[i];
            s[i] = s[j];
            s[j] = temp;

            int k = s[(s[i] + s[j]) % 256];
            ct[l] = k ^ pt[l];
            dpt[l] = k ^ ct[l];

        }

        System.out.println("\nCT:" + char2Str(ct));
        System.out.println("PT:" + char2Str(dpt));

    }

    private static String char2Str(int ar[]) {
        String str = "";

        for (int anAr : ar) {
            str += (char) anAr;
        }
        return str;
    }

}
