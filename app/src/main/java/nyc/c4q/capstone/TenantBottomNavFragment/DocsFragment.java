package nyc.c4q.capstone.TenantBottomNavFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nyc.c4q.capstone.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DocsFragment extends Fragment {

    View rootview;


    public DocsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_docs, container, false);


        return rootview;
    }


}
