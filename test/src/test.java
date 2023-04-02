import java.math.BigInteger;

public class test {
    public static void main(String[] args) {
        System.out.println(solution(1070));
    }

    static int solution(int index) {
        String a = "";
        for (int i = 0; i <index; i++) {
            a =fibo(index).toString();
        }
        return Character.getNumericValue(a.charAt(a.length() - 1));
    }

    static BigInteger fibo(int index){
        BigInteger f0 = new BigInteger("0");
        BigInteger f1 = new BigInteger("1");
        BigInteger fn = new BigInteger("1");
        if(index<0) {
            return new BigInteger("-1");
        }else if((index==0)||(index==1)) {
            return new BigInteger(index+"");
        }else {
            for(int i=2; i<index; i++) {
                f0 = f1;
                f1 = fn;
                fn = f0.add(f1);
            }
        }
        return fn;
    }

    static String solution(String s) {
        if (s.equals("#")) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '#') {
                builder = builder.append(s.charAt(i));
            } else {
                int length = builder.length();
                if (length > 0) {
                    builder = builder.deleteCharAt(length - 1);
                }
            }
        }
        return builder.toString();
    }
}
