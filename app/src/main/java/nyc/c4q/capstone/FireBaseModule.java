package nyc.c4q.capstone;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jervon.arnoldd on 7/2/18.
 */

@Module
class Stuff {

    @Singleton
    @Provides
    FirebaseDatabase provideFireBaseDataBase() {
        return FirebaseDatabase.getInstance();
    }

    @Singleton
    @Provides
    FirebaseAuth provideFireBaseAuth() {
        return FirebaseAuth.getInstance();
    }
}
