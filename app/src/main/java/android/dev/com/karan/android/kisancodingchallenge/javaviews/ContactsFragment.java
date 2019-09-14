package android.dev.com.karan.android.kisancodingchallenge.javaviews;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import android.dev.com.karan.android.kisancodingchallenge.R;
import android.dev.com.karan.android.kisancodingchallenge.adapters.ContactsLoader;
import android.dev.com.karan.android.kisancodingchallenge.adapters.ContactsViewRecyclerAdapter;
import android.dev.com.karan.android.kisancodingchallenge.models.ContactsModel;
import android.dev.com.karan.android.kisancodingchallenge.utilities.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;



public class ContactsFragment extends Fragment implements ContactsViewRecyclerAdapter.OnClickListener {

    @BindView(R.id.rvContacts)
    RecyclerView rvContacts;
    Unbinder unbinder;
    @BindView(R.id.pbContacts)
    ProgressBar pbContacts;
    private ContactsViewRecyclerAdapter recyclerAdapter;
    private List<ContactsModel> contactsModelList = new ArrayList<>();
    private LoaderManager.LoaderCallbacks<List<ContactsModel>> loaderCallbacks;
    private int LOADER_CODE = 101;
    private final String TAG = ContactsFragment.class.getName();

    public static ContactsFragment getInstance() {
        return new ContactsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContactListener();
    }

    private void initContactListener() {
        loaderCallbacks = new LoaderManager.LoaderCallbacks<List<ContactsModel>>() {
            @Override
            public Loader<List<ContactsModel>> onCreateLoader(int id, Bundle args) {
                return new ContactsLoader(getActivity());
            }

            @Override
            public void onLoadFinished(Loader<List<ContactsModel>> loader, List<ContactsModel> data) {
                if (data.size() > 0) {
                    pbContacts.setVisibility(View.GONE);
                    contactsModelList.clear();
                    contactsModelList.addAll(data);
                    recyclerAdapter.notifyDataSetChanged();
                } else {
                    //checking if fragment is added or not
                    if (isAdded()) {
                        makeToast(getString(R.string.error_msg));
                    }
                }
                Log.d(TAG, " onLoadFinished  called");
            }

            @Override
            public void onLoaderReset(Loader<List<ContactsModel>> loader) {
                //do nothing for now
                Log.d(TAG, " onLoader reset called");
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupRecyclerView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeDataMembers();
    }

    private void initializeDataMembers() {
        getLoaderManager().initLoader(LOADER_CODE, null, loaderCallbacks);
    }

    private void setupRecyclerView() {
        recyclerAdapter = new ContactsViewRecyclerAdapter(contactsModelList);
        rvContacts.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvContacts.setItemAnimator(new DefaultItemAnimator());
        rvContacts.setHasFixedSize(true);
        rvContacts.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvContacts.setAdapter(recyclerAdapter);
        recyclerAdapter.setClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void makeToast(String msg) {
        Snackbar.make(rvContacts, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(int position) {
        //handle the click event on contacts
        openContactDetails(position);
    }

    private void openContactDetails(int position) {
        Intent intent = new Intent(getActivity(), ContactDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.PARCELABLE_CONTACT_DATA, contactsModelList.get(position));
        startActivity(intent);
    }

}
