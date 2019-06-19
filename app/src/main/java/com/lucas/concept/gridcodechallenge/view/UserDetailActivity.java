package com.lucas.concept.gridcodechallenge.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucas.concept.gridcodechallenge.R;
import com.lucas.concept.gridcodechallenge.controller.UsersController;
import com.lucas.concept.gridcodechallenge.model.UserInfo;
import com.squareup.picasso.Picasso;

public class UserDetailActivity extends Activity {
    public static final String TAG = UserDetailActivity.class.getCanonicalName();
    private UserInfo mCurrentUser;
    private TextView mFirstName;
    private TextView mLastName;
    private TextView mEmailAddress;
    private TextView mUsername;
    private ImageView mUserPicture;
    private UsersController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail);

        mFirstName = findViewById(R.id.user_first_name);
        mLastName = findViewById(R.id.user_last_name);
        mEmailAddress = findViewById(R.id.email_address);
        mUsername = findViewById(R.id.username);
        mUserPicture = findViewById(R.id.user_picture);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int deviceIndex = extras.getInt(UsersController.USER_INDEX);
            mController = UsersController.getInstance(this);
            mCurrentUser = mController.getData().get(deviceIndex);
            updateUIFromContact();
        }
    }

    synchronized private void updateUIFromContact() {
        if (mCurrentUser != null) {
            mFirstName.setText(mCurrentUser.getFirstName() != null ? mCurrentUser.getFirstName() : "-");
            mLastName.setText(mCurrentUser.getLastName() != null ? mCurrentUser.getLastName() : "-");
            mUsername.setText(mCurrentUser.getUsername() != null ? mCurrentUser.getUsername() : "-");
            mEmailAddress.setText(mCurrentUser.getEmailAddress() != null ? mCurrentUser.getEmailAddress() : "-");
            Picasso.with(this).load(mCurrentUser.getImageUrl())
                    .resize((int) this.getResources().getDimension(R.dimen.width_image_large), (int) this.getResources().getDimension(R.dimen.height_image_large))
                    .error(android.R.drawable.ic_menu_camera)
                    .placeholder(android.R.drawable.ic_menu_camera)
                    .into(mUserPicture);
        }
    }

}
