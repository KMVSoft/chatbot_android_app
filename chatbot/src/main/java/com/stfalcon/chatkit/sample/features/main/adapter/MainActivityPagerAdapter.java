package com.stfalcon.chatkit.sample.features.main.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.stfalcon.chatkit.sample.R;

/*
 * Created by troy379 on 11.04.17.
 */
public class MainActivityPagerAdapter extends FragmentStatePagerAdapter {

    public static final int ID_ANONYMOUS = 0;
    public static final int ID_SIGNIN = 1;
    public static final int ID_SIGNUP = 2;


    private Context context;

    public MainActivityPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        String title = null;
        String description = null;
        boolean input = true;
        switch (position) {
            case ID_ANONYMOUS:
                title = context.getString(R.string.login_anonymous);
                description = context.getString(R.string.btn_anonymous_login);
                input = false;
                break;
            case ID_SIGNIN:
                title = context.getString(R.string.login_with_name);
                description = context.getString(R.string.btn_login_by_name);
                break;
            case ID_SIGNUP:
                title = context.getString(R.string.singup);
                description = context.getString(R.string.btn_singup);
                break;
        }
        return DemoCardFragment.newInstance(position, title, description, input);
    }

    @Override
    public int getCount() {
        return 3;
    }
}