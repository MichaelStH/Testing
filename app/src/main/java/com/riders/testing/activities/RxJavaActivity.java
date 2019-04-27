package com.riders.testing.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxJavaActivity extends AppCompatActivity {

    private static final String TAG = RxJavaActivity.class.getSimpleName();

    Subscription mySubscription1;
    Subscription mySubscription2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable<String> myObservable = Observable.just("Hello"); // Emits "Hello"

        Observer<String> myObserver = new Observer<String>() {

            @Override
            public void onCompleted() {
                // Called when the observable has no more data to emit
                Log.i(TAG, "onCompleted() : No more data to emit");
            }

            @Override
            public void onError(Throwable e) {
                // Called when the observable encounters an error
                Log.e(TAG, "Error RxJava : ", e);
            }


            @Override
            public void onNext(String s) {
                // Called each time the observable emits data
                Log.d(TAG, "onNext() - Observer result : " + s);
            }
        };

        mySubscription1 = myObservable.subscribe(myObserver);


        Action1<String> myAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG, "call() - Inner class result : " + s);
            }
        };

        mySubscription2 = myObservable.subscribe(myAction);


        Observable<Integer> myArrayObservable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6}); // Emits each item of the array, one at a time
        myArrayObservable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                Log.i(TAG, "Prints the number received : ");
                Log.d(TAG, String.valueOf(i)); // Prints the number received
            }
        });


        myArrayObservable
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return integer * integer; // Square the number
                    }
                })
                .skip(2) // Skip the first two items
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) { // Ignores any item that returns false
                        return integer % 2 == 0;
                    }
                }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "ArrayObservable result : " + integer);
            }
        });


        //String content = fetchData("http://www.google.com");
        // fetches the contents of google.com as a String

        Observable<String> fetchFromGoogle = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String data = fetchData("https://www.google.com");
                    subscriber.onNext(data); // Emit the contents of the URL
                    subscriber.onCompleted(); // Nothing more to emit
                } catch (Exception e) {
                    subscriber.onError(e); // In case there are network errors
                }
            }
        });

        /*fetchFromGoogle
                .subscribeOn(Schedulers.newThread()) // Create a new Thread
                .observeOn(AndroidSchedulers.mainThread()) // Use the UI thread
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i( TAG, "fetchFromGoogle : " + s );
                        //view.setText(view.getText() + "\n" + s); // Change a View
                    }
                });*/

        /*
        Observable<String> fetchFromYahoo = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String data = fetchData("https://fr.yahoo.com/?p=us");
                    subscriber.onNext(data); // Emit the contents of the URL
                    subscriber.onCompleted(); // Nothing more to emit
                }catch(Exception e){
                    subscriber.onError(e); // In case there are network errors
                }
            }
        });
       */

//        fetchFromGoogle = fetchFromGoogle.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        //fetchFromYahoo = fetchFromYahoo.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());

        // Fetch from both simultaneously
        /*Observable<String> zipped = Observable.zip(fetchFromGoogle, fetchFromYahoo, new Func2<String, String, String>() {
            @Override
            public String call(String google, String yahoo) {
                // Do something with the results of both threads
                return google + "\n" + yahoo;
            }
        });

        zipped.subscribeOn(Schedulers.newThread()) // Create a new Thread
                .observeOn(AndroidSchedulers.mainThread()) // Use the UI thread
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i( TAG, "Result zipped actions : " + s );
                        //view.setText(view.getText() + "\n" + s); // Change a View
                    }
                });
*/
    }

    public String fetchData(String url) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        String inputLine;
        String result = "";

        while ((inputLine = in.readLine()) != null) {
            result += inputLine;
        }

        return result.length() + "";
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        mySubscription1.unsubscribe();
        mySubscription2.unsubscribe();
    }
}
