package com.dev.holker.wholesale;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.dev.holker.wholesale.model.ResourcesW;
import com.dev.holker.wholesale.model.User;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<User> {
    private Context mContext;
    private int mResources;
    private ArrayList<User> mUsers;

    public UserAdapter(@NonNull Context context, int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);

        mContext = context;
        mResources = resource;
        mUsers = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.card_user, null);
        User user = mUsers.get(position);
        //CircleImageView circleImageView = view.findViewById(R.id.profile_image);
        TextView nameOfUser = (TextView) view.findViewById(R.id.tv_profile_name);


        //circleImageView.setImageBitmap(user.getAvatar());
        nameOfUser.setText(user.getUsername());
        ImageView background = (ImageView) view.findViewById(R.id.card_background);
        background.setImageBitmap(ResourcesW.Companion.getBackground(user.getBack(),mContext));
        return view;

    }
}

