package nyc.c4q.capstone.BottomNavFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.capstone.MainActivity;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.dash_controller.DashAdapter;
import nyc.c4q.capstone.dash_controller.Dash_Rv_Model;
import nyc.c4q.capstone.database.TenantDataBaseHelper;
import nyc.c4q.capstone.datamodels.Tickets;
import nyc.c4q.capstone.datamodels.UserApartmentInfo;
import nyc.c4q.capstone.maintenance.NewRequestFragment;
import nyc.c4q.capstone.maintenance.SubmittedAdapter;
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

        RecyclerView recyclerView = rootView.findViewById(R.id.my_recycler_view);
        List<Dash_Rv_Model> models = new ArrayList<>();
        models.add(new Dash_Rv_Model(R.drawable.recycle2,"Recycling Info"));
        models.add(new Dash_Rv_Model(R.drawable.no_smoking,"NYC Smoking Resource"));
        models.add(new Dash_Rv_Model(R.drawable.housingcourtanswerslogo,"Housing Court Questions"));
        models.add(new Dash_Rv_Model(R.drawable.nyc_logo,"NYC Rent Increase Info"));

        DashAdapter dashAdapter = new DashAdapter(models);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setAdapter(dashAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);





        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = TenantDataBaseHelper.getInstance(FirebaseDatabase.getInstance());


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
