package nyc.c4q.capstone.maintenance_tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindAnim;
import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.capstone.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubmittedFragment extends Fragment {

    public SubmittedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_submitted, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

}
