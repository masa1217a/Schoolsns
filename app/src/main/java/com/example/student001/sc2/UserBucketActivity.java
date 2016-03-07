package com.example.student001.sc2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kii.cloud.storage.KiiBucket;
import com.kii.cloud.storage.KiiObject;
import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.callback.KiiObjectCallBack;
import com.kii.cloud.storage.callback.KiiQueryCallBack;
import com.kii.cloud.storage.query.KiiQuery;
import com.kii.cloud.storage.query.KiiQueryResult;

/**
 * Created by Nyamura on 2016/01/25.
 *
 * ユーザーのバケットを作ってオブジェクトを作成する
 */
public class UserBucketActivity extends ActionBarActivity implements OnItemClickListener {

    private static final String TAG = "UserBucketActivity";

    private EditText comment;

    // define the UI elements
    private ProgressDialog mProgress;

    // define the list
    private ListView mListView;

    // define the list manager

    // define some strings used for creating objects
    private static final String OBJECT_KEY = "myObjectValue";
    private static final String BUCKET_NAME = "myBucket";

    private ObjectAdapter mListAdapter;

    // define the object count
    // used to easily see object names incrementing
    private int mObjectCount = 0;

    // define a custom list adapter to handle KiiObjects
    public class ObjectAdapter extends ArrayAdapter<KiiObject> {

        // define some vars
        int resource;
        String response;
        Context context;

        // initialize the adapter
        public ObjectAdapter(Context context, int resource,
                             List<KiiObject> items) {
            super(context, resource, items);

            // save the resource for later
            this.resource = resource;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // create the view
            LinearLayout rowView;

            // get a reference to the object
            KiiObject obj = getItem(position);

            KiiUser acuser = KiiUser.getCurrentUser();

            // if it's not already created
            if (convertView == null) {

                // create the view by inflating the xml resource
                // (res/layout/row.xml)
                rowView = new LinearLayout(getContext());
                String inflater = Context.LAYOUT_INFLATER_SERVICE;
                LayoutInflater vi;
                vi = (LayoutInflater) getContext().getSystemService(inflater);
                vi.inflate(resource, rowView, true);

            }

            // it's already created, reuse it
            else {
                rowView = (LinearLayout) convertView;
            }

            // get the text fields for the row
            TextView titleText = (TextView) rowView
                    .findViewById(R.id.rowTextTitle);
            TextView subtitleText = (TextView) rowView
                    .findViewById(R.id.rowTextSubtitle);

            // set the content of the row texts
            titleText.setText(obj.getString(OBJECT_KEY));
            subtitleText.setText("to: "+acuser.getUsername());

            // return the row view
            return rowView;
        }

    }

    // the user can add items from the options menu.
    // create that menu here - from the res/menu/menu.xml file
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.return_botton, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_return) {
            Intent intent = new Intent(this, Top.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void CreateChat(View v){

        // show a progress dialog to the user
        mProgress = ProgressDialog.show(UserBucketActivity.this, "",
                "Sending...", true);

        // create an incremented title for the object
        String value = comment.getText().toString();

        // get a reference to a KiiBucket
        KiiBucket bucket = KiiUser.getCurrentUser().bucket(BUCKET_NAME);

        // create a new KiiObject and set a key/value
        KiiObject obj = bucket.object();
        obj.set(OBJECT_KEY, value);

        // save the object asynchronously
        obj.save(new KiiObjectCallBack() {

            // catch the callback's "done" request
            public void onSaveCompleted(int token, KiiObject o, Exception e) {

                // hide our progress UI element
                mProgress.dismiss();

                // check for an exception (successful request if e==null)
                if (e == null) {

                    // tell the console and the user it was a success!
                    Toast.makeText(UserBucketActivity.this, "Created object",
                            Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Created object: " + o.toString());

                    // insert this object into the beginning of the list adapter
                    UserBucketActivity.this.mListAdapter.insert(o, 0);

                }

                // otherwise, something bad happened in the request
                else {

                    // tell the console and the user there was a failure
                    Toast.makeText(UserBucketActivity.this, "Error creating object",
                            Toast.LENGTH_SHORT).show();
                    Log.d(TAG,
                            "Error creating object: " + e.getLocalizedMessage());
                }
            }
        });

    }

    // load any existing objects associated with this user from the server.
    // this is done on view creation
    private void loadObjects() {

        // default to an empty adapter
        mListAdapter.clear();

        // show a progress dialog to the user
        mProgress = ProgressDialog.show(UserBucketActivity.this, "", "Loading...",
                true);

        // create an empty KiiQuery (will retrieve all results, sorted by
        // creation date)
        KiiQuery query = new KiiQuery(null);
        query.sortByAsc("_created");

        // define the bucket to query
        KiiBucket bucket = KiiUser.getCurrentUser().bucket(BUCKET_NAME);

        // perform the query
        bucket.query(new KiiQueryCallBack<KiiObject>() {

            // catch the callback's "done" request
            public void onQueryCompleted(int token,
                                         KiiQueryResult<KiiObject> result, Exception e) {

                // hide our progress UI element
                mProgress.dismiss();

                // check for an exception (successful request if e==null)
                if (e == null) {

                    // add the objects to the adapter (adding to the listview)
                    List<KiiObject> objLists = result.getResult();
                    for (KiiObject obj : objLists) {
                        mListAdapter.add(obj);
                    }

                    // tell the console and the user it was a success!
                    Log.v(TAG, "Objects loaded: "
                            + result.getResult().toString());
                    Toast.makeText(UserBucketActivity.this, "Objects loaded",
                            Toast.LENGTH_SHORT).show();

                }

                // otherwise, something bad happened in the request
                else {

                    // tell the console and the user there was a failure
                    Log.v(TAG,
                            "Error loading objects: " + e.getLocalizedMessage());
                    Toast.makeText(
                            UserBucketActivity.this,
                            "Error loading objects: " + e.getLocalizedMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }, query);

    }

    // the user has chosen to delete an object
    // perform that action here...
    void performDelete(int position) {

        // show a progress dialog to the user
        mProgress = ProgressDialog.show(UserBucketActivity.this, "",
                "Deleting object...", true);

        // get the object to delete based on the index of the row that was
        // tapped
        final KiiObject o = UserBucketActivity.this.mListAdapter.getItem(position);

        // delete the object asynchronously
        o.delete(new KiiObjectCallBack() {

            // catch the callback's "done" request
            public void onDeleteCompleted(int token, Exception e) {

                // hide our progress UI element
                mProgress.dismiss();

                // check for an exception (successful request if e==null)
                if (e == null) {

                    // tell the console and the user it was a success!
                    Toast.makeText(UserBucketActivity.this, "Deleted object",
                            Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Deleted object: " + o.toString());

                    // remove the object from the list adapter
                    UserBucketActivity.this.mListAdapter.remove(o);

                }

                // otherwise, something bad happened in the request
                else {

                    // tell the console and the user there was a failure
                    Toast.makeText(UserBucketActivity.this, "Error deleting object",
                            Toast.LENGTH_SHORT).show();
                    Log.d(TAG,
                            "Error deleting object: " + e.getLocalizedMessage());
                }

            }
        });
    }

    // the user has clicked an item on the list.
    // we use this action to possibly delete the tapped object.
    // to confirm, we prompt the user with a dialog:
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
                            long arg3) {
        // build the alert
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Would you like to remove this item?")
                .setCancelable(true)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            // if the user chooses 'yes',
                            public void onClick(DialogInterface dialog, int id) {

                                // perform the delete action on the tapped
                                // object
                                UserBucketActivity.this.performDelete(arg2);
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // if the user chooses 'no'
                    public void onClick(DialogInterface dialog, int id) {

                        // simply dismiss the dialog
                        dialog.cancel();
                    }
                });

        // show the dialog
        builder.create().show();

    }

    public void setViews(){

        comment = (EditText) findViewById(R.id.editComment);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // set our view to the xml in res/layout/main.xml
        setContentView(R.layout.activity_userbucket);

        // create an empty object adapter
        mListAdapter = new ObjectAdapter(this, R.layout.row,
                new ArrayList<KiiObject>());

        mListView = (ListView) this.findViewById(R.id.listMe);
        mListView.setOnItemClickListener(this);
        // set it to our view's list
        mListView.setAdapter(mListAdapter);

        // query for any previously-created objects
        this.loadObjects();

        setViews();
    }
}
