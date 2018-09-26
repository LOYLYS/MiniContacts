package vn.framgia.vhlee.minicontacts;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ItemContactListener {
    public static final String SORT_ORDER = "DISPLAY_NAME ASC";
    public static final String TEL_ACTION = "tel:";
    public static final int REQUEST_READ_CONTACTS = 101;
    public static final int REQUEST_CALL_PHONE = 102;
    private ContactAdapter mAdapter;
    private String mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPermission(Manifest.permission.READ_CONTACTS, REQUEST_READ_CONTACTS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CALL_PHONE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermission(Manifest.permission.CALL_PHONE, REQUEST_CALL_PHONE);
                } else {
                    showMessage(findViewById(R.id.linear_main_layout),
                            getString(R.string.msg_error_call));
                }
                break;
            case REQUEST_READ_CONTACTS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermission(Manifest.permission.READ_CONTACTS, REQUEST_READ_CONTACTS);
                } else {
                    finish();
                }
                break;
        }
    }

    @Override
    public void onContactClick(Contact contact) {
        showMessage(findViewById(R.id.linear_main_layout), contact.getName());
    }

    @Override
    public void onCallClick(int position) {
        Contact contact = mAdapter.getContact(position);
        mPhoneNumber = contact.getPhone();
        checkPermission(Manifest.permission.CALL_PHONE, REQUEST_CALL_PHONE);
    }

    @Override
    public void onFavouriteClick(int position) {
        Contact contact = mAdapter.getContact(position);
        if (contact.isFavourite()) {
            contact.setFavourite(false);
            removeFromProvider(contact);
        } else contact.setFavourite(true);
        mAdapter.notifyItemChanged(position);
        insertToProvider(contact);
    }

    private void removeFromProvider(Contact contact) {
        int value = getContentResolver().
                delete(Constants.CONTENT_URI,
                        Constants.SQL_WHERE, new String[]{contact.getId()});
    }

    private void insertToProvider(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(Constants.COL_ID, contact.getId());
        values.put(Constants.COL_NAME, contact.getName());
        values.put(Constants.COL_PHONE, contact.getPhone());
        Uri uri = getContentResolver().insert(Constants.CONTENT_URI, values);
        showMessage(findViewById(R.id.linear_main_layout), uri.toString());
    }

    public static Intent getCallIntent(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(StringUtils.append(TEL_ACTION, phoneNumber)));
        return callIntent;
    }

    public void showMessage(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    private void checkPermission(String permission, int requestCode) {
        String[] permissions = new String[]{permission};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, requestCode);
            } else doFunction(permission);
        }
    }

    @SuppressLint("MissingPermission")
    private void makeCall(String phoneNumber) {
        Intent intent = getCallIntent(phoneNumber);
        startActivity(intent);
    }

    private void readContacts() {
        Uri contactsTable = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        Cursor cursor = getContentResolver()
                .query(contactsTable, projection,
                        null, null, SORT_ORDER);
        while (cursor.moveToNext()) {
            String id = cursor.getString(Constants.KEY_ID);
            String name = cursor.getString(Constants.KEY_NAME);
            String phone = cursor.getString(Constants.KEY_PHONE);
            mAdapter.addContact(new Contact(id, name, phone));
        }
    }

    private void initUI() {
        RecyclerView recycler = findViewById(R.id.recycler_contacts);
        mAdapter = new ContactAdapter(this);
        mAdapter.setItemClickListener(this);
        DividerItemDecoration decoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recycler.addItemDecoration(decoration);
        recycler.setAdapter(mAdapter);
    }

    private void doFunction(String permission) {
        switch (permission) {
            case Manifest.permission.READ_CONTACTS:
                readContacts();
                break;
            case Manifest.permission.CALL_PHONE:
                makeCall(mPhoneNumber);
                break;
        }
    }
}
