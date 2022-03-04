package client;

import client.scenes.MainScreenCtrl;
import client.scenes.NameScreenCtrl;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;


public class MainModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(MainScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(NameScreenCtrl.class).in(Scopes.SINGLETON);
    }
}