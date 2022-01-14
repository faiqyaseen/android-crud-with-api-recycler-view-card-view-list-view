package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListViewCustomAdapter extends BaseAdapter {
    List<UserList> userlist;
    Context context;

    public ListViewCustomAdapter(List<UserList> userlist, Context context) {
        this.userlist = userlist;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userlist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.user_list_view_data, null);

        TextView fname = view1.findViewById(R.id.tv_fname);
        TextView lname = view1.findViewById(R.id.tv_lname);
        TextView email = view1.findViewById(R.id.tv_email);

        UserList userList1 = userlist.get(i);

        fname.setText(userList1.getFname());
        lname.setText(userList1.getLname());
        email.setText(userList1.getEmail());

        return view1;
    }
}
