/*
 * Project - DES
 * Created By Patel Jay.
 * On 19 3 2018.
 */

public class AES {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String input;
        char c;

        System.out.print("Enter Plain Text: ");
        input = in.nextLine();
        System.out.print("Enter Iv(Any 1 Character): ");
        c = in.next().charAt(0);

        String key = Cons.char2Bin(c);

        setData(input, key);
    }

    private static void setData(String input, String ky) {
        int tky[] = new int[128];
        System.arraycopy(Const.lfsr(ky), 0, tky, 0, 128);
        String key = "";

        for (int h = 0; h < 128; h += 8) {
            String str = "";
            for (int i = 0; i < 8; i++) {
                str += tky[h + i];
            }
            key += (char) Integer.parseInt(str, 2);
        }

        System.out.print(key);

        boolean isEnc = true;
        String te = encDec(input, key, isEnc);
    }

    private static String encDec(String input, String ky, boolean isEnc) {
        String output = "";
        
        
        return output;
    }
}
