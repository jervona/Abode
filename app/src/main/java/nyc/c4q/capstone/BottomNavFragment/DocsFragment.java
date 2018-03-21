package nyc.c4q.capstone.BottomNavFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import butterknife.OnClick;
import nyc.c4q.capstone.MainActivity;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.SignInActivity;

import static nyc.c4q.capstone.MainActivity.ANONYMOUS;


/**
 * A simple {@link Fragment} subclass.
 */
public class DocsFragment extends Fragment {

    View rootview;
    FirebaseAuth firebaseAuth;

    public DocsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_docs, container, false);
        ButterKnife.bind(this, rootview);

        firebaseAuth = FirebaseAuth.getInstance();
        return rootview;
    }

    @OnClick(R.id.sign_out)
    public void SignOut() {
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(MainActivity.googleApiClient);
        MainActivity.mUsername = MainActivity.ANONYMOUS;
        startActivity(new Intent(getActivity(), SignInActivity.class));
        getActivity().finish();
    }
}
