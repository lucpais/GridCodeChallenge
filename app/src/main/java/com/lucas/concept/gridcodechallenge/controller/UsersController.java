package com.lucas.concept.gridcodechallenge.controller;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lucas.concept.gridcodechallenge.model.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UsersController {

    private static final String TAG = UsersController.class.getCanonicalName();
    public static final String USER_INDEX = "contact_index";
    private static UsersController mInstance;
    private final INewInformationAvailableListener mIInformationAvailableListener;
    private Context mContext;
    private ArrayList<UserInfo> mData;
    private int mLastPage = 1;
    private String mUsersUrl = "https://randomuser.me/api/?results=50&seed=1dfbc44b856ac08d&page=";

    /**
     * Obtains instance of UsersController
     *
     * @param context making the request of the instance
     * @return
     */
    public static UsersController getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new UsersController(context);
        }
        return mInstance;
    }

    private UsersController(Context context) {
        mContext = context;
        mData = new ArrayList<>();
        mIInformationAvailableListener = (INewInformationAvailableListener) context;
        pupulateInfo();
    }

    private void pupulateInfo() {
        String requestURL = mUsersUrl + String.valueOf(mLastPage);
        JsonObjectRequest usersInfoRequest = new JsonObjectRequest(requestURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray resultArray = response.getJSONArray("results");
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject currentUser = resultArray.getJSONObject(i);
                        UserInfo userInfo = new UserInfo();

                        JSONObject nameObject = currentUser.getJSONObject("name");
                        userInfo.setFirstName(nameObject.getString("first"));
                        userInfo.setLastName(nameObject.getString("last"));

                        userInfo.setEmailAddress(currentUser.getString("email"));

                        JSONObject loginObject = currentUser.getJSONObject("login");
                        userInfo.setUsername(loginObject.getString("username"));

                        JSONObject pictureObject = currentUser.getJSONObject("picture");
                        userInfo.setThumbUrl(pictureObject.getString("thumbnail"));
                        userInfo.setImageUrl(pictureObject.getString("large"));

                        mData.add(userInfo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mIInformationAvailableListener.refreshViews();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(usersInfoRequest);
    }

    /**
     * Retrieves a list with users, populated from the API request
     * @return List with populated info so far.
     */
    public ArrayList<UserInfo> getData() {
        return mData;
    }

    /**
     * Populates mData with new information retrieved from subsequent pages.
     */
    public void loadMoreInfo() {
        mLastPage++;
        pupulateInfo();
    }
}
