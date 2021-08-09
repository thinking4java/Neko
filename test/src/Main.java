import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    private static BigInteger fn(int n) {
        if (n == 1) {
            return new BigInteger("1");
        }
        return new BigInteger(String.valueOf(n)).multiply(fn(n - 1));
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            int n = in.nextInt();
            System.out.println(sum(fn(n)));
        }
    }

    public static long sum(BigInteger value) {
        long lValue = value.longValue();
        int length = String.valueOf(lValue).length();
        long sum = 0;
        long k = lValue;
        for (int i = length - 1; i >= 0; i--) {
            sum += k / Math.pow(10, i);
            k %= Math.pow(10, i);
        }
        return sum;
    }

}
