package com.dimpossitorus.showcase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dimpos Sitorus on 15/05/2016.
 */
public class Response {
    private int status;
    private String message;
    private int id;
    private String title;
    private String description;
    private int qty;
    private String imgUrl;

    public Response() {
        status = 404;
        message = "No Data";
        id=0;
        title = "No Data";
        description = "No Data";
        imgUrl = null;
    }

    public Response (JSONObject response ) {
        JSONObject data;
        try {
            if (response.getInt("status")==200) {
                data = response.getJSONObject("data");
                status = response.getInt("status");
                message = response.getString("message");
                id = data.getInt("id");
                title = data.getString("title");
                description = data.getString("desc");
                qty = data.getInt("qty");
                imgUrl = data.getString("image");
            }
            else {
                status = response.getInt("status");
                message = response.getString("message");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int _status) {
        status = _status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String _message) {
        message = _message;
    }

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String _title) {
        title = _title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String _description) {
        description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int _qty) {
        qty = _qty;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String _imgUrl) {
        imgUrl = _imgUrl;
    }
}
