package android.dev.com.karan.android.kisancodingchallenge.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MessagesModel {
    @SerializedName("sid")
    @Expose
    private String sid;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("date_updated")
    @Expose
    private String dateUpdated;
    @SerializedName("date_sent")
    @Expose
    private String dateSent;
    @SerializedName("account_sid")
    @Expose
    private String accountSid;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("messaging_service_sid")
    @Expose
    private Object messagingServiceSid;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("num_segments")
    @Expose
    private String numSegments;
    @SerializedName("num_media")
    @Expose
    private String numMedia;
    @SerializedName("direction")
    @Expose
    private String direction;
    @SerializedName("api_version")
    @Expose
    private String apiVersion;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("price_unit")
    @Expose
    private String priceUnit;
    @SerializedName("error_code")
    @Expose
    private Object errorCode;
    @SerializedName("error_message")
    @Expose
    private Object errorMessage;
    @SerializedName("uri")
    @Expose
    private String uri;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getDateSent() {
        return dateSent;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Object getMessagingServiceSid() {
        return messagingServiceSid;
    }

    public void setMessagingServiceSid(Object messagingServiceSid) {
        this.messagingServiceSid = messagingServiceSid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumSegments() {
        return numSegments;
    }

    public void setNumSegments(String numSegments) {
        this.numSegments = numSegments;
    }

    public String getNumMedia() {
        return numMedia;
    }

    public void setNumMedia(String numMedia) {
        this.numMedia = numMedia;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Object errorCode) {
        this.errorCode = errorCode;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
