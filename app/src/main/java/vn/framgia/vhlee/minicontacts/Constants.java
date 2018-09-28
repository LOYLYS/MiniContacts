package vn.framgia.vhlee.minicontacts;

import android.net.Uri;

public class Constants {
    public static final String AUTHORITY =
            "vhlee.minicontacts";
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/contacts");
    public static final String COL_ID = "_ID";
    public static final String COL_NAME = "NAME";
    public static final String COL_PHONE = "PHONE";
    public static final String SQL_WHERE = "_ID = ?";
    public static final String DEFAULT_SORT = "NAME ASC";
    public static final int KEY_ID = 0;
    public static final int KEY_NAME = 1;
    public static final int KEY_PHONE = 2;
}
