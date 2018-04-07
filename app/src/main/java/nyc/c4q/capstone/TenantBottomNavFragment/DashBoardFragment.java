package nyc.c4q.capstone.TenantBottomNavFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.capstone.MainActivity;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.tenant_resource_controller.DashAdapter;
import nyc.c4q.capstone.tenant_resource_controller.DashRvModel;
import nyc.c4q.capstone.database.TenantDataBaseHelper;
import nyc.c4q.capstone.datamodels.Tickets;
import nyc.c4q.capstone.datamodels.UserInfo;
import nyc.c4q.capstone.signupactivites.SignInActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends Fragment {


    View rootView;
    TenantDataBaseHelper db;
    LinearLayout clickable;
    UserInfo user;
    int position = 0;



    public DashBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dash_board, container, false);
        setHasOptionsMenu(true);
        resources();
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
    public void resources() {
        RecyclerView recyclerView = rootView.findViewById(R.id.my_recycler_view);
        List<DashRvModel> models = new ArrayList<>();
        models.add(new DashRvModel(R.drawable.recycling, "Recycling Info", "http://www1.nyc.gov/site/hpd/renters/harassment.page"));
        models.add(new DashRvModel(R.drawable.smoke, "NYC Smoking Resource", "http://www1.nyc.gov/nyc-resources/service/2493/smoking"));
        models.add(new DashRvModel(R.drawable.judge, "Housing Court Questions", "http://housingcourtanswers.org/"));
        models.add(new DashRvModel(R.drawable.nyc_logo, "NYC Rent Increase Info", "http://www1.nyc.gov/nyc-resources/service/2069/new-york-city-rent-increase"));
        models.add(new DashRvModel(R.drawable.nyc_logo, "Are you Being Harassedd", "http://www1.nyc.gov/site/hpd/renters/harassment.page"));
        DashAdapter dashAdapter = new DashAdapter(models);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setAdapter(dashAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
