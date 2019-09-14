package android.dev.com.karan.android.kisancodingchallenge.serviceinterface;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import android.dev.com.karan.android.kisancodingchallenge.BuildConfig;
import android.dev.com.karan.android.kisancodingchallenge.models.MessagesModel;
import android.dev.com.karan.android.kisancodingchallenge.utilities.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MessagesNetworkCall {
    private final String TAG = MessagesNetworkCall.class.getName();
    private ApiNetwork apiNetwork;
    private OnMessagesReceive onReceiveMessages;

    public MessagesNetworkCall() {
        apiNetwork = new ApiNetwork();
    }

    public void getSentMessages() {
        apiNetwork.getNetworkService(BuildConfig.AUTHORIZATION, Constants.TWILIO_API_END).getListOFSmsSent(BuildConfig.ACCOUNT_SID)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.code() == 200) {
                            try {
                                if (!response.body().get("messages").getAsJsonArray().isJsonNull()) {
                                    Gson gson = new Gson();
                                    TypeToken<List<MessagesModel>> list = new TypeToken<List<MessagesModel>>() {
                                    };
                                    List<MessagesModel> messagesModelList = gson.fromJson(response.body().get("messages"), list.getType());
                                    onReceiveMessages.onReceive(messagesModelList);
                                } else {
                                    onReceiveMessages.onReceiveError();
                                }

                            } catch (NullPointerException | JsonIOException e) {
                                e.printStackTrace();
                                onReceiveMessages.onReceiveError();
                            }

                        } else {
                            onReceiveMessages.onReceiveError();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        onReceiveMessages.onReceiveError();
                    }
                });
    }

    public void setOnReceiveResponse(OnMessagesReceive onReceiveResponse) {
        this.onReceiveMessages = onReceiveResponse;
    }

    public interface OnMessagesReceive {
        void onReceive(List<MessagesModel> messagesModels);

        void onReceiveError();
    }
}
