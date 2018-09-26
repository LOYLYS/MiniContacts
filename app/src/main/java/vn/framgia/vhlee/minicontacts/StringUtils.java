package vn.framgia.vhlee.minicontacts;

public class StringUtils {
    public static String append(String... args) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            builder.append(args[i]);
        }
        return builder.toString();
    }
}
