package org.openmrs.mobile.activities.fragments;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.openmrs.mobile.R;
import org.openmrs.mobile.models.retrofit.form.Page;
import org.openmrs.mobile.models.retrofit.form.Question;
import org.openmrs.mobile.models.retrofit.form.Section;

import java.io.Serializable;
import java.util.List;

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
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        LinearLayout parent=(LinearLayout) rootView.findViewById(R.id.viewholder);
        parent.setGravity(Gravity.CENTER);
        Page page=getPageObject();
        List<Section> sectionList=page.getSections();
        for (Section section:sectionList)
            addSection(section,parent);

        return rootView;
    }

    void addSection(Section section,LinearLayout parent)
    {
        LinearLayout sectionLL=new LinearLayout(getActivity());
        sectionLL.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //get resources

        Resources r = getActivity().getResources();
        float pxLeftMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());
        float pxTopMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());
        float pxRightMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());
        float pxBottomMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());

        layoutParams.setMargins(Math.round(pxLeftMargin), Math.round(pxTopMargin), Math.round(pxRightMargin), Math.round(pxBottomMargin));

        parent.addView(sectionLL);
        TextView tv=new TextView(getActivity());
        tv.setText(section.getLabel());
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
        tv.setTextColor(ContextCompat.getColor(getActivity(),R.color.primary));
        sectionLL.addView(tv,layoutParams);

        for (Question question:section.getQuestions())
        {
            addQuestion(question,sectionLL);
        }

    }

    void addQuestion(Question question,LinearLayout parent)
    {
        if (question.getQuestionOptions().getRendering().equals("group"))
        {
            LinearLayout questionLL=new LinearLayout(getActivity());
            questionLL.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            questionLL.setGravity(Gravity.CENTER);
            parent.addView(questionLL);

            Resources r = getActivity().getResources();
            float pxLeftMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());
            float pxTopMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());
            float pxRightMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());
            float pxBottomMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());

            layoutParams.setMargins(Math.round(pxLeftMargin), Math.round(pxTopMargin), Math.round(pxRightMargin), Math.round(pxBottomMargin));


            TextView tv=new TextView(getActivity());
            tv.setText(question.getLabel());
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            tv.setTextColor(ContextCompat.getColor(getActivity(),R.color.primary));
            questionLL.addView(tv,layoutParams);
            for(Question subquestion:question.getQuestions())
            {
                addQuestion(subquestion,questionLL);
            }
        }

        if(question.getQuestionOptions().getRendering().equals("number"))
        {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            Resources r = getActivity().getResources();
            float pxLeftMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());
            float pxTopMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());
            float pxRightMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());
            float pxBottomMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());

            layoutParams.setMargins(Math.round(pxLeftMargin), Math.round(pxTopMargin), Math.round(pxRightMargin), Math.round(pxBottomMargin));

            EditText ed=new EditText(getActivity());
            ed.setHint(question.getLabel());
            ed.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            parent.addView(ed,layoutParams);
        }
    }

    Page getPageObject()
    {
        return (Page) getArguments().getSerializable(ARG_PAGE_OBJECT);
    }
}
