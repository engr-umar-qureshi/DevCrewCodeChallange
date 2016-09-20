package com.umar.devcrewcodechallange.tasks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umar.devcrewcodechallange.interfaces.GenericCallback;
import com.umar.devcrewcodechallange.interfaces.ResponseType;
import com.umar.devcrewcodechallange.model.UserModel;
import com.umar.devcrewcodechallange.utility.ErrorResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by UmarQasim on 9/20/2016.
 */
public class GetUsersTask extends BaseAsyncTask<List<UserModel>,String,String,String> {

    public GetUsersTask(GenericCallback<List<UserModel>> successListener, GenericCallback<ErrorResponse> failureListener) {
        super(successListener, failureListener);
    }

    @Override
    protected String performNetworkCall(String... params) {
        return "{\"isSuccess\": true,\"message\": \"User list fetched successfuly\",\"content\": [{\"firstName\": \"Umar\",\"lastName\": \"Qasim\"},{\"firstName\": \"Ahsan\",\"lastName\": \"ijaz\"},{\"firstName\": \"shakeel\",\"lastName\": \"Anwer\"},{\"firstName\": \"shahid\",\"lastName\": \"khan\"},{\"firstName\": \"Naeem\",\"lastName\": \"Akram\"}]}";
    }

    @Override
    protected List<UserModel> parseToSuccessData(String responseString) throws JSONException {

        JSONArray jsonArrayContent= new JSONObject(responseString).getJSONArray(JSON_KEY_CONTENT);
        return new Gson().fromJson(jsonArrayContent.toString(), new TypeToken<List<UserModel>>() {
        }.getType());
    }
}
