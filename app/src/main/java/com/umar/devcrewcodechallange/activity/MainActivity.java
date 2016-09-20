package com.umar.devcrewcodechallange.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.umar.devcrewcodechallange.R;
import com.umar.devcrewcodechallange.adapters.UserAdapter;
import com.umar.devcrewcodechallange.fragment.AddUserFragment;
import com.umar.devcrewcodechallange.interfaces.GenericCallback;
import com.umar.devcrewcodechallange.model.UserModel;
import com.umar.devcrewcodechallange.tasks.GetUsersTask;
import com.umar.devcrewcodechallange.utility.InjectView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {


    private final String PARCEL_KEY_LIST_USERS="listUsers";
    @InjectView(R.id.act_main_rv_names)
    RecyclerView rvNames;

    @InjectView(R.id.fab)
    FloatingActionButton fab;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    ArrayList<UserModel> listUser;

    UserAdapter adapterUser;

    LinearLayoutManager userListManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null || !savedInstanceState.containsKey(PARCEL_KEY_LIST_USERS)) {
            performAsyncTask( new GetUsersTask(getUserSuccessCallback,this));
        }
        else {
            ArrayList<UserModel> parcelListUser = savedInstanceState.getParcelableArrayList(PARCEL_KEY_LIST_USERS);
            initializeUserListWithComponents(parcelListUser);
        }

    }

    private void initializeActivityComponents()
    {

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUserFragment addUserFragment = new AddUserFragment();
                addUserFragment.setAddUserCallback(addUserCallback);
                addUserFragment.show(getFragmentManager(),getResources().getString(R.string.title_add_user));
            }
        });
    }

    private void initializeUserListWithComponents(ArrayList<UserModel> data)
    {
        listUser = data;
        adapterUser = new UserAdapter(listUser,MainActivity.this);
        userListManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        rvNames.setLayoutManager(userListManager);
        rvNames.setAdapter(adapterUser);

        initializeActivityComponents();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(PARCEL_KEY_LIST_USERS,listUser);
        super.onSaveInstanceState(outState);
    }

    private GenericCallback<List<UserModel>> getUserSuccessCallback = new GenericCallback<List<UserModel>>() {
        @Override
        public void onCallback(List<UserModel> data) {
            initializeUserListWithComponents(new ArrayList<UserModel>(data));
        }
    };

    private GenericCallback<String[]> addUserCallback = new GenericCallback<String[]>() {
        @Override
        public void onCallback(String[] data) {
        listUser.add(new UserModel(data[0],data[1]));
            adapterUser.notifyDataSetChanged();
            userListManager.scrollToPosition(listUser.size()-1);
        }
    };


}
