package android.dev.com.karan.android.kisancodingchallenge.serviceinterface;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import android.dev.com.karan.android.kisancodingchallenge.BuildConfig;
import android.dev.com.karan.android.kisancodingchallenge.utilities.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TwilioMessageCreator {
    private ApiNetwork apiNetwork;
    private OnMessageSend onMessageSend;

    public TwilioMessageCreator() {
        apiNetwork = new ApiNetwork();
    }

    public void create(String to, String body) {
        apiNetwork.getNetworkService(BuildConfig.AUTHORIZATION, Constants.TWILIO_API_END).sendSms(BuildConfig.ACCOUNT_SID, BuildConfig.ACCOUNT_SENDER, to, body)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.code() == 201) {
                            if (response.body() != null) {
                                try {
                                    JsonObject jsonElement = response.body();
                                    if (!jsonElement.get("sid").isJsonNull()) {
                                        onMessageSend.onMessageSend(true);
                                    } else {
                                        onMessageSend.onMessageSend(false);
                                    }
                                } catch (JsonIOException e) {
                                    e.printStackTrace();
                                    onMessageSend.onMessageSend(false);
                                }

                            } else {
                                onMessageSend.onMessageSend(false);
                            }
                        } else {
                            onMessageSend.onMessageSend(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        onMessageSend.onMessageSend(false);
                    }
                });
    }


    public void setOnMessageSendListener(OnMessageSend sendListener) {
        this.onMessageSend = sendListener;
    }

    public interface OnMessageSend {
        void onMessageSend(boolean isSuccessful);
    }

}