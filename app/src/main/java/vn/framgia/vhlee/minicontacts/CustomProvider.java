package vn.framgia.vhlee.minicontacts;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class CustomProvider extends ContentProvider {
    private static final int INDEX_UNIT = 1;
    private static final int CONTACTS = 1;
    private static final int CONTACT_ID = 2;
    private static final String CONTACTS_MATCHER = "contacts";
    private static final String CONTACT_ID_MATCHER = "contacts/#";
    private static final String CONTACTS_TYPE =
            "vnd.android.cursor.dir/vnd.vhlee.minicontacts.provider.contacts";
    private static final String CONTACT_ID_TYPE =
            "vnd.android.cursor.item/vnd.vhlee.minicontacts.provider.contacts";
    private static final UriMatcher uriMatcher = getUriMatcher();
    private ContactDataBase mContactDataBase = null;

    public static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Constants.AUTHORITY, CONTACTS_MATCHER, CONTACTS);
        uriMatcher.addURI(Constants.AUTHORITY, CONTACT_ID_MATCHER, CONTACT_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mContactDataBase = new ContactDataBase(context);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {
        String id = null;
        if (uriMatcher.match(uri) == CONTACT_ID) {
            id = uri.getPathSegments().get(INDEX_UNIT);
        }
        return mContactDataBase.getContacts(id, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case CONTACTS:
                return CONTACTS_TYPE;
            case CONTACT_ID:
                return CONTACT_ID_TYPE;
            default:
                return "";
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        try {
            long id = mContactDataBase.insert(values);
            Uri returnUri = ContentUris.withAppendedId(Constants.CONTENT_URI, id);
            return returnUri;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String id = null;
        if (uriMatcher.match(uri) == CONTACT_ID) {
            id = uri.getPathSegments().get(INDEX_UNIT);
        }
        return mContactDataBase.delete(id);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}
