package com.project.xlore.job_list;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.project.xlore.R;
import com.project.xlore.data.Job;

import androidx.fragment.app.Fragment;

public class JobDetailFragment extends Fragment
{
    public static final String ARG_ITEM_ID = "item_id";
    private Job mItem;

    public JobDetailFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID))
        {
            mItem = JobListActivity.JOB_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null)
            {
                appBarLayout.setTitle(mItem.getJobName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.job_detail, container, false);

        String detail = "Closing Date:\n" + mItem.getClosingDate() + "\n\n" +
                        "Employment Type:\n" + mItem.getEmploymentType() + "\n\n" +
                        "Salary:\n" + mItem.getSalary() + "\n\n" +
                        "Location:\n" + mItem.getLocation() + "\n\n" +
                        "Description:\n" + mItem.getDescription() + "\n\n" +
                        "Required Skills:\n" + mItem.getRequiredSkills() + "\n\n";

        if (mItem != null)
        {
            ((TextView) rootView.findViewById(R.id.job_detail)).setText(detail);
        }

        return rootView;
    }
}
