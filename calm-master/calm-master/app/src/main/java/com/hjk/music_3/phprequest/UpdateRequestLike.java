package com.hjk.music_3.phprequest;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateRequestLike extends StringRequest {

    final static String url="https://nowglobalhealing.com/wp-admin/UpdateLike.php";
    private Map<String,String> map;

    public UpdateRequestLike(String userId, String like_music, Response.Listener<String> listener) {
        super(Request.Method.POST, url, listener, null);
        map=new HashMap<>();
        map.put("userId",userId);
        map.put("like_music",like_music);

    }

    @Override
    protected Map<String,String> getParams() throws AuthFailureError {
        return map;
    }

}
