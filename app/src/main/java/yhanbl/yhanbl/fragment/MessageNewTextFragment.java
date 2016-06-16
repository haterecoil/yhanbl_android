package yhanbl.yhanbl.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yhanbl.yhanbl.R;

/**
 * Created by mrgn on 15/06/2016.
 */
public class MessageNewTextFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.message_new_write, container, false);
    }
}
