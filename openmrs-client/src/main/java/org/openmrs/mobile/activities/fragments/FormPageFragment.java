package org.openmrs.mobile.activities.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.openmrs.mobile.R;
import org.openmrs.mobile.models.retrofit.form.Page;

import java.io.Serializable;

public class FormPageFragment extends Fragment {

    private static final String ARG_PAGE_NUMBER = "page_number";
    private static final String ARG_PAGE_OBJECT = "page_object";


    public FormPageFragment() {
    }

    public static FormPageFragment newInstance(int sectionNumber, Page page) {
        FormPageFragment fragment = new FormPageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, sectionNumber);
        args.putSerializable(ARG_PAGE_OBJECT, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.form_page_fragment, container, false);
        TextView tv=(TextView)rootView.findViewById(R.id.test);
        tv.setText(getPageObject().getSections().get(0).getLabel());

        return rootView;
    }

    Page getPageObject()
    {
        return (Page) getArguments().getSerializable(ARG_PAGE_OBJECT);
    }
}
