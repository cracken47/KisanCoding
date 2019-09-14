package android.dev.com.karan.android.kisancodingchallenge.adapters;

import android.dev.com.karan.android.kisancodingchallenge.R;
import android.dev.com.karan.android.kisancodingchallenge.models.MessagesModel;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MessagesRecyclerAdapter extends RecyclerView.Adapter<MessagesRecyclerAdapter.ViewHolder> {
    private OnClickMessage onClickMessage;
    private List<MessagesModel> messagesModelList;
    private SimpleDateFormat dateFormatIn = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z", Locale.getDefault());
    private SimpleDateFormat dateFormatOut = new SimpleDateFormat("E, d MMM yyyy", Locale.getDefault());

    public MessagesRecyclerAdapter(List<MessagesModel> messagesModelList) {
        this.messagesModelList = messagesModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_messages_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessagesModel messagesModel = messagesModelList.get(position);
        try {
            Date date = dateFormatIn.parse(messagesModel.getDateSent());
            holder.textViewDate.setText(dateFormatOut.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            holder.textViewDate.setText(messagesModel.getDateSent());
        }
        holder.textViewNumber.setText(messagesModel.getTo());
        holder.textViewStatus.setText(messagesModel.getStatus());
    }

    @Override
    public int getItemCount() {
        return messagesModelList.size();
    }

    public void setOnMessagesClick(OnClickMessage onMessagesClick) {
        this.onClickMessage = onMessagesClick;
    }

    public interface OnClickMessage {
        void onMessageClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewNumber, textViewStatus, textViewDate;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewDate = (TextView) itemView.findViewById(R.id.tvMessagesDate);
            textViewNumber = (TextView) itemView.findViewById(R.id.tvMessagesNumber);
            textViewStatus = (TextView) itemView.findViewById(R.id.tvMessagesStatus);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickMessage.onMessageClick(getAdapterPosition());
        }
    }
}
