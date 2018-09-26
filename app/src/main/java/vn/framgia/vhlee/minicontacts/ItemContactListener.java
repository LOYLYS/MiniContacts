package vn.framgia.vhlee.minicontacts;

public interface ItemContactListener {
    void onContactClick(Contact contact);
    void onCallClick(int position);
    void onFavouriteClick(int position);
}
