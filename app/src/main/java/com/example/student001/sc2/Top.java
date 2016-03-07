package com.example.student001.sc2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * ドロワーメニューのクラスをひとまとめにする。
 */

public class Top extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    // webアドレスの文字を格納
    String webAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    // クリック処理
    public void OpenCampus(View v){
        //webAddress = getString(R.string.img_iconOC);
        Intent intent = new Intent(Top.this, WebActivity.class);
        startActivity(intent);
        Toast.makeText(Top.this,"オープンキャンパス",Toast.LENGTH_SHORT).show();
    }
    /*
    public void EntranceExamination(View v){

        Intent intent = new Intent(Top.this, EntranceExaminationActivity.class);
        startActivity(intent);
        Toast.makeText(Top.this,"入試概要",Toast.LENGTH_SHORT).show();

    }
    public void IntroducedClass(){

        Intent intent = new Intent(Top.this, IntroduceClassActivity.class);
        startActivity(intent);
        Toast.makeText(Top.this,"学科紹介",Toast.LENGTH_SHORT).show();
    }
    public void GraduatedGuess(){

        Intent intent = new Intent(Top.this, GraduateGuessActivity.class);
        startActivity(intent);
        Toast.makeText(Top.this,"卒業生の言葉",Toast.LENGTH_SHORT).show();
    }
    public void PolytechnicVision(){

        Intent intent = new Intent(Top.this, PolytechnicVisionActivity.class);
        startActivity(intent);
        Toast.makeText(Top.this,"ポリテクニックビジョン",Toast.LENGTH_SHORT).show();
    }
    */

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(position==1) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ImageFragment.newInstance(position + 1, R.layout.toptest))
                    .commit();
        }else if(position==2){
            Intent news = new Intent(this, UserAttributesActivity.class);
            startActivityForResult(news, 0);
        }else if(position==3){
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ImageFragment.newInstance(position + 1, R.layout.access))
                    .commit();
        }else if(position==4){
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ImageFragment.newInstance(position + 1, R.layout.schedule))
                    .commit();
        }else if(position==5){
            Intent mail = new Intent(this, UserBucketActivity.class);
            startActivityForResult(mail, 0);
        }else if(position==6){
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ImageFragment.newInstance(position + 1, R.layout.map))
                    .commit();
        }
        else{
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                    .commit();
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {

            case 2:
                mTitle = getString(R.string.title_section1);
                break;
            case 3:
                mTitle = getString(R.string.title_section2);
                break;
            case 4:
                mTitle = getString(R.string.title_section3);
                break;
            case 5:
                mTitle = getString(R.string.title_section4);
                break;
            case 6:
                mTitle = getString(R.string.title_section5);
                break;
            case 7:
                mTitle = getString(R.string.title_section6);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.toptest, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Top) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
