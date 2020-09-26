package com.hjk.music_3.phprequest;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateRequestMusic  extends StringRequest {

    final static String url="https://nowglobalhealing.com/wp-admin/UpdateMusic.php";


    private Map<String,String> map;


    public UpdateRequestMusic(String userId,String save_day,String save_history,String save_time, String last_login, Response.Listener<String>listener) {
        super(Method.POST, url, listener, null);
        map=new HashMap<>();
        map.put("userId",userId);
        map.put("save_day",save_day);
        map.put("save_history",save_history);
        map.put("save_time",save_time);
        map.put("last_login",last_login);

    }



    @Override
    protected Map<String,String> getParams() throws AuthFailureError {
        return map;
    }


}