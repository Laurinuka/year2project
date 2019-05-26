package com.project.xlore.user_list;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.project.xlore.R;
import com.project.xlore.data.User;

import androidx.fragment.app.Fragment;

public class UserDetailFragment extends Fragment
{
    public static final String ARG_ITEM_ID = "item_id";
    private User mItem;

    public UserDetailFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID))
        {
            mItem = UserListActivity.USER_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null)
            {
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.user_detail, container, false);

        String detail = "Contact Details:\n" + mItem.getEmail() + "\n" +
                        mItem.getPhoneNumber() + "\n\n" +
                        "Bio:\n" + mItem.getBio() + "\n\n" +
                        "Skills:\n" + mItem.getSkills();

        if (mItem != null)
        {
            ((TextView) rootView.findViewById(R.id.user_detail)).setText(detail);
        }

        return rootView;
    }
}
