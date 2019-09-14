package android.dev.com.karan.android.kisancodingchallenge.javaviews;

import android.app.DialogFragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import android.dev.com.karan.android.kisancodingchallenge.R;
import android.dev.com.karan.android.kisancodingchallenge.models.ContactsModel;
import android.dev.com.karan.android.kisancodingchallenge.serviceinterface.TwilioMessageCreator;
import android.dev.com.karan.android.kisancodingchallenge.utilities.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.dev.com.karan.android.kisancodingchallenge.utilities.Constants.PARCELABLE_COMPOSE_DATA;


public class ComposeDialogFragment extends DialogFragment implements TwilioMessageCreator.OnMessageSend {

    @BindView(R.id.tvComposeTitle)
    TextView tvComposeTitle;
    @BindView(R.id.tvComposeMsg)
    TextView tvComposeMsg;
    @BindView(R.id.ComposeSend)
    FloatingActionButton ComposeSend;
    Unbinder unbinder;
    @BindView(R.id.pbComposer)
    ProgressBar pbComposer;
    @BindView(R.id.tvComposeClose)
    TextView tvComposeClose;
    @BindView(R.id.viewCompose)
    View viewCompose;
    private Random random;
    private int valueOtp;
    private ContactsModel contactsModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        random = new Random();
    }

    public static ComposeDialogFragment getInstance(ContactsModel contactsModel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(PARCELABLE_COMPOSE_DATA, contactsModel);
        ComposeDialogFragment composeDialogFragment = new ComposeDialogFragment();
        composeDialogFragment.setArguments(bundle);
        return composeDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_compose, container, false);
        unbinder = ButterKnife.bind(this, view);
        initializeMembers();
        bindValuesToView();
        return view;
    }

    private void bindValuesToView() {
        String headPart = getString(R.string.msg_compose_otp);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(headPart + " " + String.valueOf(valueOtp));
        stringBuilder.setSpan(new StyleSpan(Typeface.BOLD), headPart.length(), stringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvComposeMsg.setText(stringBuilder);
    }

    private void initializeMembers() {
        //random number generation of six digit with min 100000 and max 899999
        contactsModel = getArguments().getParcelable(PARCELABLE_COMPOSE_DATA);
        valueOtp = 100000 + random.nextInt(899999);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onMessageSend(boolean isSuccessful) {
        if (isAdded()) {
            if (isSuccessful) {
                makeToast(getString(R.string.success_send_sms));
                dismiss();
                getActivity().finish();
            } else {
                makeToast(getString(R.string.error_send_sms));
                dismiss();
            }
        }
    }

    private void makeToast(String msg) {
        pbComposer.setVisibility(View.GONE);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.tvComposeClose, R.id.ComposeSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvComposeClose:
                dismiss();
                break;
            case R.id.ComposeSend:
                //send OTP with API here
                sendOtp();
                break;
        }
    }

    private void sendOtp() {
        pbComposer.setVisibility(View.VISIBLE);
        ComposeSend.setEnabled(false);
        TwilioMessageCreator twilioMessageCreator = new TwilioMessageCreator();
        StringBuilder toPhoneNumer = new StringBuilder();
        toPhoneNumer.append(Util.filterPhoneNumber(contactsModel.getPhone()));
        twilioMessageCreator.setOnMessageSendListener(this);
        twilioMessageCreator.create(toPhoneNumer.toString(), tvComposeMsg.getText().toString());
    }
}
