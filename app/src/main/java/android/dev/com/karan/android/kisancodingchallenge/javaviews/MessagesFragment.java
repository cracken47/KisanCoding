package android.dev.com.karan.android.kisancodingchallenge.javaviews;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import android.dev.com.karan.android.kisancodingchallenge.R;
import android.dev.com.karan.android.kisancodingchallenge.adapters.MessagesRecyclerAdapter;
import android.dev.com.karan.android.kisancodingchallenge.models.MessagesModel;
import android.dev.com.karan.android.kisancodingchallenge.serviceinterface.MessagesNetworkCall;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;



public class MessagesFragment extends Fragment implements MessagesRecyclerAdapter.OnClickMessage, MessagesNetworkCall.OnMessagesReceive {
    @BindView(R.id.rvMessages)
    RecyclerView rvMessages;
    @BindView(R.id.pbMessages)
    ProgressBar pbMessages;
    Unbinder unbinder;
    private MessagesRecyclerAdapter messagesRecyclerAdapter;
    private List<MessagesModel> messagesModelList = new ArrayList<>();
    private Snackbar snackbar;

    public static MessagesFragment getInstance() {
        return new MessagesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        unbinder = ButterKnife.bind(this, view);
        setRecyclerView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDataFromServer();
    }

    private void getDataFromServer() {
        MessagesNetworkCall messagesNetworkCall = new MessagesNetworkCall();
        messagesNetworkCall.setOnReceiveResponse(this);
        messagesNetworkCall.getSentMessages();
    }

    private void setRecyclerView() {
        messagesRecyclerAdapter = new MessagesRecyclerAdapter(messagesModelList);
        rvMessages.setItemAnimator(new DefaultItemAnimator());
        rvMessages.setHasFixedSize(true);
        rvMessages.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvMessages.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMessages.setAdapter(messagesRecyclerAdapter);
        messagesRecyclerAdapter.setOnMessagesClick(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onMessageClick(int position) {
        //here handle the click event
        showDialog(position);
    }

    private void showDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(messagesModelList.get(position).getTo());
        builder.setMessage(messagesModelList.get(position).getBody());
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void onReceive(List<MessagesModel> messagesModels) {
        //here you got messages
        if (isAdded()) {
            if (messagesModels.size() > 0) {
                pbMessages.setVisibility(View.GONE);
                messagesModelList.clear();
                messagesModelList.addAll(messagesModels);
                messagesRecyclerAdapter.notifyDataSetChanged();
            } else {
                makeToast(getString(R.string.no_messages_found));
            }
        }
    }

    @Override
    public void onReceiveError() {
        if (isAdded()){
            makeToast(getString(R.string.error_msg));
        }
    }

    private void makeToast(String msg) {
        pbMessages.setVisibility(View.GONE);
        snackbar = Snackbar.make(rvMessages, msg, Snackbar.LENGTH_INDEFINITE).setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }
}
