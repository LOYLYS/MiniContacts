package vn.framgia.vhlee.minicontacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {
    private List<Contact> mContacts;
    private LayoutInflater mInflater;
    private ItemContactListener mContactListener;

    public ContactAdapter(Context context) {
        mContacts = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = mInflater.inflate(R.layout.item_contact, parent, false);
        return new ContactHolder(view, mContactListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        holder.bindData(mContacts.get(position));
        holder.onItemClick(position);
    }

    @Override
    public int getItemCount() {
        return (mContacts != null) ? mContacts.size() : 0;
    }

    public void setItemClickListener(ItemContactListener listener) {
        mContactListener = listener;
    }

    public void addContact(Contact contact) {
        mContacts.add(contact);
        notifyItemInserted(mContacts.size() - 1);
    }

    public void removeContact(int position) {
        mContacts.remove(position);
        notifyItemRemoved(position);
    }

    public Contact getContact(int position) {
        return mContacts.get(position);
    }

    public static class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mAvatar;
        private TextView mName;
        private TextView mPhone;
        private ImageView mFavourite;
        private ImageView mCall;
        private ItemContactListener mContactListener;
        private Contact mContact;

        public ContactHolder(@NonNull View itemView, ItemContactListener listener) {
            super(itemView);
            mAvatar = itemView.findViewById(R.id.text_avatar);
            mName = itemView.findViewById(R.id.text_name);
            mPhone = itemView.findViewById(R.id.text_phone);
            mFavourite = itemView.findViewById(R.id.image_favourite);
            mCall = itemView.findViewById(R.id.image_call);
            itemView.setOnClickListener(this);
            mCall.setOnClickListener(this);
            mFavourite.setOnClickListener(this);
            mContactListener = listener;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.image_call:
                    mContactListener.onCallClick(getAdapterPosition());
                    break;
                case R.id.image_favourite:
                    mContactListener.onFavouriteClick(getAdapterPosition());
                    break;
                default:
                    mContactListener.onContactClick(mContact);
            }
        }

        private void bindData(Contact contact) {
            mContact = contact;
            mAvatar.setText(String.valueOf(contact.getName().charAt(0)));
            mName.setText(contact.getName());
            mPhone.setText(contact.getPhone());
            if (contact.isFavourite()) {
                mFavourite.setImageResource(R.drawable.ic_favourited);
            } else {
                mFavourite.setImageResource(R.drawable.ic_favourite);
            }
        }

        public void onItemClick(final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContactListener.onContactClick(mContact);
                }
            });
            mCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContactListener.onCallClick(position);
                }
            });

            mFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContactListener.onFavouriteClick(position);
                }
            });
        }
    }
}
