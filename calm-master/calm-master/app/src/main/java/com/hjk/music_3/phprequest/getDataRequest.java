package com.hjk.music_3.phprequest;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class getDataRequest extends StringRequest {

    final static private String url="https://nowglobalhealing.com/wp-admin/getData.php";
    private Map<String,String> map;

    public getDataRequest(String userId, Response.Listener<String> listener){
        super(Method.POST,url,listener,null);

        map=new HashMap<>();
        map.put("userId",userId);

    }

    @Override
    public Map<String,String> getParams() throws AuthFailureError{
        return map;
    }
}
