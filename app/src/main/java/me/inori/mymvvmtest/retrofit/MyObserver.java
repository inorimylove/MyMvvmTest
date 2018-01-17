package me.inori.mymvvmtest.retrofit;

import rx.Observable;

/**
 * Created by hjx on 2018/1/17.
 */

public class MyObserver extends Observable {
    /**
     * Creates an Observable with a Function to execute when it is subscribed to.
     * <p>
     * <em>Note:</em> Use {@link #create(OnSubscribe)} to create an Observable, instead of this constructor,
     * unless you specifically have a need for inheritance.
     *
     * @param f {@link OnSubscribe} to be executed when {@link #subscribe(Subscriber)} is called
     */
    protected MyObserver(OnSubscribe f) {
        super(f);
    }

}
