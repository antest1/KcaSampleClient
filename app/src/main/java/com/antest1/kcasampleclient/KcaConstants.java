package com.antest1.kcasampleclient;

import android.net.Uri;

public class KcaConstants {
    public static final String AUTHORITY = "com.antest1.kcasniffer.contentprovider";
    public static final String PATH  = "/request";

    public static final Uri CONTENT_URI = Uri.parse("content://".concat(AUTHORITY).concat(PATH));
    public static final String BROADCAST_ACTION = "com.antest1.kcasniffer.broadcast";

    public static final String KC_PACKAGE_NAME = "com.dmm.dmmlabo.kancolle";
}
