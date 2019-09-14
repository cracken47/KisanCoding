package android.dev.com.karan.android.kisancodingchallenge.javaviews;

import android.Manifest;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import android.dev.com.karan.android.kisancodingchallenge.R;
import android.dev.com.karan.android.kisancodingchallenge.models.ContactsModel;
import android.dev.com.karan.android.kisancodingchallenge.utilities.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class ContactDetailsActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 102;
    private static final String TAG = ContactDetailsActivity.class.getName();
    @BindView(R.id.toolbarContactDetails)
    Toolbar toolbarContactDetails;
    @BindView(R.id.tvContactName)
    TextView tvContactName;
    @BindView(R.id.tvContactPhone)
    TextView tvContactPhone;
    @BindView(R.id.tvContactEmail)
    TextView tvContactEmail;
    @BindView(R.id.fabContactCall)
    FloatingActionButton fabContactCall;
    @BindView(R.id.btContactSendMessage)
    AppCompatButton btContactSendMessage;
    @BindView(R.id.tvContactGender)
    TextView tvContactGender;
    private ContactsModel contactsModel;
    private Snackbar snackbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        ButterKnife.bind(this);
        setToolbar();
        getLocalParcelableData();
        bindDataToViews();
        checkCallPermissions();
    }

    private void bindDataToViews() {
        if (contactsModel != null) {
            tvContactName.setText(contactsModel.getFirstName() + " " + contactsModel.getLastName());
            tvContactEmail.setText(contactsModel.getEmail());
            tvContactPhone.setText(contactsModel.getPhone());
            tvContactGender.setText(contactsModel.getGender());
        } else {
            finish();
        }
    }

    private void getLocalParcelableData() {
        contactsModel = getIntent().getParcelableExtra(Constants.PARCELABLE_CONTACT_DATA);
    }

    private void setToolbar() {
        toolbarContactDetails.setTitle(getString(R.string.title_contact_details));
        setSupportActionBar(toolbarContactDetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarContactDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.fabContactCall, R.id.btContactSendMessage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fabContactCall:
                makeCall();
                break;
            case R.id.btContactSendMessage:
                composeSms();
                break;
        }
    }

    private void composeSms() {
        //here open the compose otp sms dialog
        ComposeDialogFragment dialogFragment = ComposeDialogFragment.getInstance(contactsModel);
        dialogFragment.setStyle(DialogFragment.STYLE_NO_FRAME,DialogFragment.STYLE_NO_TITLE);
        dialogFragment.show(getFragmentManager(), TAG);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            //this is for call and its granted,make call now
            makeCall();
        }
    }

    private void makeCall() {
        //here check for permission if granted and make call if granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + contactsModel.getPhone().trim()));
            startActivity(intent);
        } else {
            checkCallPermissions();
        }
    }

    private void checkCallPermissions() {
        //check for permissions for calling here
        int permissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (permissionGranted != PackageManager.PERMISSION_GRANTED) {
            //permission not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                //show user why you need that permission here
                makeToast(getString(R.string.permission_call));
            } else {
                //this if first time may be so request permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);
            }
        }
    }

    private void makeToast(String msg) {
        snackbar = Snackbar.make(btContactSendMessage, msg, Snackbar.LENGTH_INDEFINITE).setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }
}
