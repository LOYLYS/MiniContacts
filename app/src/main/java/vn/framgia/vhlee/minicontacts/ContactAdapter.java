package vn.framgia.vhlee.minicontacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {
    private List<Contact> mContacts;
    private LayoutInflater mInflater;

    public ContactAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = mInflater.inflate(R.layout.item_contact, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        holder.bindData(mContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return (mContacts != null) ? mContacts.size() : 0;
    }

    public void addContact(Contact contact) {
        mContacts.add(contact);
        notifyItemInserted(mContacts.size() - 1);
    }

    public class ContactHolder extends RecyclerView.ViewHolder {
        private TextView mAvatar;
        private TextView mName;
        private TextView mPhone;
        private ImageView mFavourite;

        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            mAvatar = itemView.findViewById(R.id.text_avatar);
            mName = itemView.findViewById(R.id.text_name);
            mPhone = itemView.findViewById(R.id.text_phone);
            mFavourite = itemView.findViewById(R.id.image_favourite);
        }

        public void bindData(Contact contact) {
            mAvatar.setText(String.valueOf(contact.getName().charAt(0)));
            mName.setText(contact.getName());
            mPhone.setText(contact.getPhone());
            if (contact.isFavourite()) {
                mFavourite.setImageResource(R.drawable.ic_favourited);
            } else {
                mFavourite.setImageResource(R.drawable.ic_favourite);
            }
        }
    }
}
