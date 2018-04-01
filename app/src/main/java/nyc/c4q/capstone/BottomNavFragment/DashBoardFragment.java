package nyc.c4q.capstone.BottomNavFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import nyc.c4q.capstone.MainActivity;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.database.TenantDataBaseHelper;
import nyc.c4q.capstone.datamodels.Tickets;
import nyc.c4q.capstone.datamodels.UserApartmentInfo;
import nyc.c4q.capstone.signupactivites.SignInActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends Fragment {

    View rootView;
    TenantDataBaseHelper db;
    UserApartmentInfo user;


    public DashBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dash_board, container, false);
        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = TenantDataBaseHelper.getInstance(FirebaseDatabase.getInstance());
        db.getTicketsList().size();


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dash_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                FirebaseAuth.getInstance().signOut();
                Auth.GoogleSignInApi.signOut(MainActivity.googleApiClient);
                MainActivity.mUsername = MainActivity.ANONYMOUS;
                startActivity(new Intent(getActivity(), SignInActivity.class));
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void giveStuff(List<Tickets> messages, UserApartmentInfo user) {
        Log.e("User",user.getAPT());
        Log.e("mess",messages.size()+"");
    }
}
