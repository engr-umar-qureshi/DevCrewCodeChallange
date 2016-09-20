package com.umar.devcrewcodechallange.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.umar.devcrewcodechallange.BuildConfig;
import com.umar.devcrewcodechallange.R;
import com.umar.devcrewcodechallange.interfaces.GenericCallback;
import com.umar.devcrewcodechallange.interfaces.ResponseType;
import com.umar.devcrewcodechallange.tasks.BaseAsyncTask;
import com.umar.devcrewcodechallange.utility.ErrorResponse;
import com.umar.devcrewcodechallange.utility.InjectView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by UmarQasim on 9/20/2016.
 */
public class BaseActivity extends AppCompatActivity implements GenericCallback<ErrorResponse>,BaseAsyncTask.FinishTaskCallback{

    private final String TAG = this.getClass().getSimpleName();

    protected List<AsyncTask> backgroundTaskList =new ArrayList<AsyncTask>();

    ProgressDialog dialogNetworkProgress;
    AlertDialog alertDialog;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        injectViews();

    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        injectViews();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        injectViews();
    }

    public void performAsyncTask(BaseAsyncTask asyncTask, String... params){
        if(isOnline(this)){
            asyncTask.setFinishListener(this);
            backgroundTaskList.add(asyncTask);
            showDialogNetworkProgress();
            asyncTask.execute(params);
        }
        else {
            showAlertDialog(getResources().getString(R.string.txt_error_title),getResources().getString(R.string.error_msg_offline));
        }
    }

    private void injectViews()
    {
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            InjectView annos = field.getAnnotation(InjectView.class);
            if (annos != null) {
                try {
                    field.setAccessible(true);
                    View v = findViewById(annos.value());
                    if(v!=null)
                    {
                        field.set(this,(field.getType().cast(v)));
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception in injectView()", e);
                }
            }
        }
    }

    @Override
    public void onTaskFinished(AsyncTask asyncTask) {
        for(int i=0; i<backgroundTaskList.size();i++){
            if(backgroundTaskList.get(i).equals(asyncTask)){
                backgroundTaskList.remove(i);
                break;
            }
        }
        hideDialogNetworkProgress();
    }

    @Override
    public void onCallback(ErrorResponse errorResponse) {

        String errorMessage;
        if (BuildConfig.DEBUG) {
            switch (errorResponse.getResponseType()) {
                case EMPTY:
                    errorMessage = getResources().getString(R.string.error_msg_empty);
                    break;
                case ERROR:
                    errorMessage = getResources().getString(R.string.error_msg_error);
                    break;
                case FAILURE:
                    errorMessage = errorResponse.getResponseMessage();
                    break;
                default:
                    errorMessage = getResources().getString(R.string.error_msg_unkown);
                    break;
            }
        }
        else {
            if(errorResponse.getResponseType()==ResponseType.FAILURE){
                errorMessage = errorResponse.getResponseMessage();
            }
            else {
                errorMessage = getResources().getString(R.string.error_msg_prod);
            }
        }
        showAlertDialog(getResources().getString(R.string.txt_error_title),errorMessage);
    }

    public void showAlertDialog(String title,String alertMessage) {
        if(alertDialog==null){
            alertDialog = new AlertDialog.Builder(this).setPositiveButton(getResources().getString(R.string.txt_ok), null).create();

        }
        alertDialog.setTitle(title);
        alertDialog.setMessage(alertMessage);
        alertDialog.show();
    }

    public void showDialogNetworkProgress() {
        if(dialogNetworkProgress==null){
            dialogNetworkProgress = new ProgressDialog(this);
            dialogNetworkProgress.setMessage(this.getResources().getString(R.string.msg_network_loading));
            dialogNetworkProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialogNetworkProgress.setCanceledOnTouchOutside(false);
            dialogNetworkProgress.setIndeterminate(true);
            dialogNetworkProgress.setProgress(0);
        }
        dialogNetworkProgress.show();
    }

    public void hideDialogNetworkProgress(){
        if(dialogNetworkProgress!=null && dialogNetworkProgress.isShowing()){
            dialogNetworkProgress.dismiss();
        }
    }

    @Override
    protected void onDestroy() {

        if(dialogNetworkProgress!=null&&dialogNetworkProgress.isShowing())
        {
            dialogNetworkProgress.dismiss();
        }


        if(alertDialog!=null&& alertDialog.isShowing())
        {
            alertDialog.dismiss();
        }


        for(int i = 0; i< backgroundTaskList.size(); i++)
        {
            if(backgroundTaskList.get(i)!=null&&!backgroundTaskList.get(i).isCancelled()&& backgroundTaskList.get(i).getStatus()!=AsyncTask.Status.FINISHED)
            {
                backgroundTaskList.get(i).cancel(true);
            }
        }
        super.onDestroy();

    }

    public static boolean isOnline(Context con) {
        ConnectivityManager cm =
                (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
