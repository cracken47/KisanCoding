package android.dev.com.karan.android.kisancodingchallenge.adapters;

import android.dev.com.karan.android.kisancodingchallenge.R;
import android.dev.com.karan.android.kisancodingchallenge.models.ContactsModel;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class ContactsViewRecyclerAdapter extends RecyclerView.Adapter<ContactsViewRecyclerAdapter.ViewHolder> {
    private List<ContactsModel> contactsModels;
    private OnClickListener onclickListener;

    public ContactsViewRecyclerAdapter(List<ContactsModel> contactsModels) {
        this.contactsModels = contactsModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_contacts_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ContactsModel contactsModel = contactsModels.get(position);
        holder.textViewImage.setText(String.valueOf(contactsModel.getFirstName().charAt(0)));
        holder.textViewName.setText(contactsModel.getFirstName() + " " + contactsModel.getLastName());
    }

    @Override
    public int getItemCount() {
        return contactsModels.size();
    }

    public void setClickListener(OnClickListener clickListener) {
        this.onclickListener = clickListener;
    }

    public interface OnClickListener {
        void onClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewImage, textViewName;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewImage = (TextView) itemView.findViewById(R.id.tvContactsImage);
            textViewName = (TextView) itemView.findViewById(R.id.tvContactsName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onclickListener.onClick(getAdapterPosition());
        }
    }
}
