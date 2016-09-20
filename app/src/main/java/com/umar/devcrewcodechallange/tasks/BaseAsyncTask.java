package com.umar.devcrewcodechallange.tasks;

import android.os.AsyncTask;

import com.umar.devcrewcodechallange.interfaces.GenericCallback;
import com.umar.devcrewcodechallange.interfaces.ResponseType;
import com.umar.devcrewcodechallange.utility.ErrorResponse;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by UmarQasim on 9/20/2016.
 */
public abstract class BaseAsyncTask<R,B,P,E> extends AsyncTask<B, P, E> {

    GenericCallback<ErrorResponse> failureListener;
    GenericCallback<R> successListener;
    FinishTaskCallback finishListener;

    private final String JSON_KEY_STATUS="isSuccess";
    private final String JSON_KEY_MESSAGE="message";
    protected final String JSON_KEY_CONTENT="content";

    ResponseType webResponce;
    String message="";

    R successData;

    public void setFinishListener(FinishTaskCallback finishListener) {
        this.finishListener = finishListener;
    }

    public BaseAsyncTask(GenericCallback<R> successListener, GenericCallback<ErrorResponse> failureListener) {
        this.successListener = successListener;
        this.failureListener = failureListener;
    }

    @Override
    protected E doInBackground(B... params) {

        if(isCancelled()) {
            return null;
        }
            String response = performNetworkCall(params);
            if (response.equals("")) {
                webResponce = ResponseType.EMPTY;
            } else {
                try {
                    JSONObject json = new JSONObject(response);
                    if (json.getBoolean(JSON_KEY_STATUS)) {

                        if(isCancelled()) {
                            return null;
                        }
                        successData = parseToSuccessData(response);

                        webResponce = ResponseType.SUCCESS;

                    } else {
                        webResponce = ResponseType.FAILURE;
                        message = json.getString(JSON_KEY_MESSAGE);
                    }
                } catch (JSONException e) {
                    webResponce = ResponseType.ERROR;
                    e.printStackTrace();
                }
            }


        return null;
    }

    @Override
    protected void onPostExecute(E result) {
        super.onPostExecute(result);

        if(finishListener!=null){
            finishListener.onTaskFinished(this);
        }

        if(webResponce==ResponseType.SUCCESS){
            successListener.onCallback(successData);
        }
        else {
            failureListener.onCallback(new ErrorResponse(webResponce,message));
        }
    }


    protected abstract String performNetworkCall(B... params);
    protected abstract R parseToSuccessData(String responseString) throws JSONException;

    public interface FinishTaskCallback {

        public void onTaskFinished(AsyncTask asyncTask);

    }


}

