/* 
 *  Created By Patel Jay
 *  On 12 Mar 2018
 */
package allmodes;

import java.util.Scanner;

public class AllModes {

    private static Scanner in;

    public static void main(String[] args) {
        in = new Scanner(System.in);

        System.out.println("Enter Text:");
        String msg = in.nextLine();

        setData(msg);
    }

    private static void setData(String msg) {
        try {
            String iv, key;

            System.out.println("More Than 3 Bits For IV & Key");
            System.out.println("Iv:");
            iv = in.next();
            System.out.println("Key:");
            key = in.next();

            if (iv.length() < 4 || key.length() < 4) {
                throw new NumberFormatException("Enter More Than 3 Bit");
            }

            for (int j = 0; j < key.length(); j++) {
                int n = Integer.parseInt(key.charAt(j) + "");
                if (n > 1 || n < 0) {
                    throw new NumberFormatException("Enter Only 0 And 1 In Key");
                }
            }

            for (int j = 0; j < iv.length(); j++) {
                int n = Integer.parseInt(iv.charAt(j) + "");
                if (n > 1 || n < 0) {
                    throw new NumberFormatException("Enter Only 0 And 1 In IV");
                }
            }

            System.out.println("Mode:\tEncrypt\t->\tDecrypt\n");

            String ec1 = ECB(msg, iv);
            String ec2 = ECB(ec1, iv);
            System.out.println("ECB:\t" + ec1 + "\t->\t" + ec2);

            //true -For Encryption & false -For Decryption
            String cf1 = CFM(msg, iv, key, true);
            String cf2 = CFM(cf1, iv, key, false);
            System.out.println("CFM:\t" + cf1 + "\t->\t" + cf2);

            //true -For Encryption & false -For Decryption
            String cb1 = CBC(msg, iv, key, true);
            String cb2 = CBC(cb1, iv, key, false);
            System.out.println("CBC:\t" + cb1 + "\t->\t" + cb2);

            String of1 = OFM(msg, iv, key);
            String of2 = OFM(of1, iv, key);
            System.out.println("OFM:\t" + of1 + "\t->\t" + of2);

            String ct1 = CTR(msg, iv, key);
            String ct2 = CTR(ct1, iv, key);
            System.out.println("CTR:\t" + ct1 + "\t->\t" + ct2);
        } catch (NumberFormatException e) {
            System.out.println(e.getLocalizedMessage());
            setData(msg);
        }
    }

    private static String CBC(String msg, String IV, String ky, boolean EncDnc) {
        try {
            int lfsr[] = lfsr(IV);
            int key[] = lfsr(ky);

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
                    int t = (lfsr[i] + plain[i] + key[i]) % 2;
                    cBit += t;

                    if (EncDnc) {
                        lfsr[i] = t;
                    } else {
                        lfsr[i] = plain[i];
                    }
                }

                int t = Integer.parseInt(cBit, 2);
                output += (char) t;
            }

//            System.out.println("CBC:- " + output);
            return output;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private static String CTR(String msg, String nonce, String ky) {
        try {
            int count = 0, key[];
            key = lfsr(ky);

            String output = "";

            for (int j = 0; j < msg.length(); j++) {
                String pBit = "", cBit = "";
                pBit = Integer.toBinaryString(msg.charAt(j));

                int k = 8;
                int plain[] = new int[k];

                int lfsr[] = new int[8];
                for (int i = 0; i < 4; i++) {
                    lfsr[i] = Integer.parseInt(nonce.charAt(i) + "");
                }

                String countBit = Integer.toBinaryString(count);
                for (int i = countBit.length() - 1, l = 8; i >= 0; i--) {
                    lfsr[--l] = Integer.parseInt(countBit.charAt(i) + "");
                }

                for (int i = pBit.length() - 1; i >= 0; i--) {
                    plain[--k] = Integer.parseInt(pBit.charAt(i) + "");
                }

                for (int i = 0; i < plain.length; i++) {
                    int t = (lfsr[i] + key[i] + plain[i]) % 2;
                    cBit += t;
                }

                int t = Integer.parseInt(cBit, 2);
                output += (char) t;

                if (count < 15) {
                    count++;
                } else {
                    count = 0;
                }
            }

//            System.out.println("CTR:- " + output);
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private static String OFM(String msg, String IV, String ky) {
        try {
            int lfsr[] = lfsr(IV), key[] = lfsr(ky);

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
                    int eb = (lfsr[i] + key[i]) % 2;
                    int t = (eb + plain[i]) % 2;
                    cBit += t;

                    lfsr[i] = eb;
                }

                int t = Integer.parseInt(cBit, 2);
                output += (char) t;
            }

//            System.out.println("OFM:- " + output);
            return output;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private static String CFM(String msg, String IV, String ky, boolean EncDnc) {
        try {
            int lfsr[] = lfsr(IV), key[] = lfsr(ky);

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
                    int t = (lfsr[i] + plain[i] + key[i]) % 2;
                    cBit += t;

                    if (EncDnc) {
                        lfsr[i] = t;
                    } else {
                        lfsr[i] = plain[i];
                    }
                }

                int t = Integer.parseInt(cBit, 2);
                output += (char) t;
            }

//            System.out.println("CFM:- " + output);
            return output;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private static String ECB(String msg, String IV) {
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
