package com.example.student001.sc2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kii.cloud.storage.Kii;
import com.kii.cloud.storage.KiiBucket;
import com.kii.cloud.storage.KiiObject;
import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.UserFields;
import com.kii.cloud.storage.callback.KiiUserCallBack;
import com.kii.cloud.storage.exception.app.AppException;

import java.io.IOException;

/**
 * Created by Nyamura on 2016/02/01.
 *
 * 個人設定
 */
public class UserAttributesActivity extends Activity {
/*
    private static final String TAG = UserAttributesActivity.class.getSimpleName();
    private final UserAttributesActivity self = this;

    private EditText editUsername;
    private EditText editPassword;

    private String gender = "男性";                   //性別
    private int    Grade = 2;                         //学年
    private String FamilyName = "電子情報技術科";      //科名
    private String Course = "専門";                   //課程
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userattribute);

        //setupViews();
    }

    /*private void setupViews() {

        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);

    }*/

    public void CreateChat(View v)  {
        Toast.makeText( this, "saveされました", Toast.LENGTH_SHORT ).show();
/*
        KiiBucket appBucket = Kii.bucket("my_user");
        KiiObject object = appBucket.object();
        try {
            object.save();
            Toast.makeText( this, "saveされました", Toast.LENGTH_SHORT ).show();

        } catch (IOException e) {

        } catch (AppException e) {

        }*/
    }
}
