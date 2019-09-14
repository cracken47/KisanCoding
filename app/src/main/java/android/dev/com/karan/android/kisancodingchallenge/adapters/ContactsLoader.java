package android.dev.com.karan.android.kisancodingchallenge.adapters;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.dev.com.karan.android.kisancodingchallenge.models.ContactsModel;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class ContactsLoader extends AsyncTaskLoader<List<ContactsModel>> {

    private List<ContactsModel> contactsModels;
    private String TAG = ContactsLoader.class.getName();

    public ContactsLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (contactsModels == null) {
            //force load for new data
            forceLoad();
            Log.d(TAG, " onStartLoading() called for new data");
        } else {
            //give back the cached data back
            deliverResult(contactsModels);
            Log.d(TAG, " onStartLoading() called for cached data");
        }
    }

    @Override
    public List<ContactsModel> loadInBackground() {
        ArrayList<ContactsModel> modelArrayList = new ArrayList<>();
        String json = null;
        try {
            InputStream is = getContext().getAssets().open("contacts_mocks.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
            JsonElement jsonElement = new JsonParser().parse(json);
            Gson gson = new Gson();
            Type typeToken = new TypeToken<List<ContactsModel>>() {
            }.getType();
            List<ContactsModel> contactsModelList = gson.fromJson(jsonElement, typeToken);
            modelArrayList.addAll(contactsModelList);
            Log.d(TAG, " parsing contacts successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modelArrayList;
    }

    @Override
    public void deliverResult(List<ContactsModel> data) {
        contactsModels = data;
        super.deliverResult(data);
    }
}
