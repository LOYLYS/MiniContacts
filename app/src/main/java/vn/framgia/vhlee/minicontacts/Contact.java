package vn.framgia.vhlee.minicontacts;

public class Contact {
    private int mId;
    private String mName;
    private String mPhone;
    private boolean isFavourite;

    public Contact(int mId, String mName, String mPhone) {
        this.mId = mId;
        this.mName = mName;
        this.mPhone = mPhone;
        this.isFavourite = false;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
