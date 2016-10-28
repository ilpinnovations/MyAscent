package ilp.innovations.tcs.com.myascent.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ilp.innovations.tcs.com.myascent.R;

/**
 * Created by maverick on 12/6/2015.
 */
public class InformationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.information_fragment,container,false);
    }
}
