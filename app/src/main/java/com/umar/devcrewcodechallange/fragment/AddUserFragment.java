package com.umar.devcrewcodechallange.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.umar.devcrewcodechallange.R;
import com.umar.devcrewcodechallange.interfaces.GenericCallback;
import com.umar.devcrewcodechallange.utility.InjectView;
import com.umar.devcrewcodechallange.utility.InputFiltersUtil;

/**
 * Created by UmarQasim on 9/20/2016.
 */
public class AddUserFragment extends BaseDialogFragment {

    @InjectView(R.id.frag_add_edt_firstname)
    EditText edtFirstName;

    @InjectView(R.id.frag_add_edt_lastname)
    EditText edtLastName;

    @InjectView(R.id.frag_add_txt_save)
    TextView txtSave;

    @InjectView(R.id.frag_add_txt_cancel)
    TextView txtCancel;

    GenericCallback<String[]> addUserCallback;

    public void setAddUserCallback(GenericCallback<String[]> addUserCallback) {
        this.addUserCallback = addUserCallback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View rootView = setFragmentContentViews(inflater,container,false,R.layout.fragment_add_user);
        getDialog().setTitle(getResources().getString(R.string.title_add_user));
        initializeFragmentComponents();
        return rootView;
    }

    private void initializeFragmentComponents()
    {
        edtFirstName.setFilters(new InputFilter[]{InputFiltersUtil.getDisplayNameFilter()});
        edtLastName.setFilters(new InputFilter[]{InputFiltersUtil.getDisplayNameFilter()});
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateInput()){
                    if(addUserCallback!=null){
                        String[] name = new String [2];
                        name[0] = edtFirstName.getText().toString();
                        name[1] = edtLastName.getText().toString();
                        addUserCallback.onCallback(name);
                    }
                    dismiss();
                }
            }
        });

    }

    private boolean validateInput()
    {
        if(edtFirstName.getText().toString().isEmpty()){
            edtFirstName.requestFocus();
            edtFirstName.setError(getResources().getString(R.string.error_field_empty));
            return false;
        }

        if(edtLastName.getText().toString().isEmpty()){
            edtLastName.requestFocus();
            edtLastName.setError(getResources().getString(R.string.error_field_empty));
            return false;
        }

        return true;
    }
}
