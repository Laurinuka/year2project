package com.project.xlore.user_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.xlore.MainActivity;
import com.project.xlore.ProfileActivity;
import com.project.xlore.R;
import com.project.xlore.data.User;
import com.project.xlore.data.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

public class UserListActivity extends AppCompatActivity
{
    public final static List<User> USERS = new ArrayList<>();
    public final static Map<String, User> USER_MAP = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.user_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView)
    {
        retrieveUserDatabase();
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(USERS));
    }

    private void retrieveUserDatabase()
    {
        USERS.clear();
        USER_MAP.clear();
        UserRepository repo = new UserRepository(this);
        repo.getEmployeeUsers().observe(this, new Observer<List<User>>()
        {
            @Override
            public void onChanged(@Nullable List<User> users)
            {
                for(User user : users)
                {
                    USERS.add(user);
                    USER_MAP.put(Integer.toString(user.getId()), user);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_profile)
        {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_applications)
        {
            return true;
        }
        else if (id == R.id.action_addjob)
        {
            return true;
        }
        else if (id == R.id.action_logout)
        {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>
    {
        private final List<User> mValues;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                User item = (User) view.getTag();
                Context context = view.getContext();
                Intent intent = new Intent(context, UserDetailActivity.class);
                intent.putExtra(UserDetailFragment.ARG_ITEM_ID, Integer.toString(item.getId()));
                context.startActivity(intent);
            }
        };

        SimpleItemRecyclerViewAdapter(List<User> items)
        {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position)
        {
            holder.mContentView.setText(mValues.get(position).getName());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount()
        {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            final TextView mContentView;

            ViewHolder(View view)
            {
                super(view);
                mContentView = view.findViewById(R.id.userlist_text_content);
            }
        }
    }
}
