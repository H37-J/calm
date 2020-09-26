package com.hjk.music_3.phprequest;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static String url="https://nowglobalhealing.com/wp-admin/Register.php";
    private Map<String,String> map;



    public RegisterRequest(String userId,String userPassword,String userName, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        map=new HashMap<>();
        map.put("userId",userId);
        map.put("userPassword",userPassword);
        map.put("userName",userName);
    }


    @Override
    protected Map<String,String> getParams() throws AuthFailureError{
        return map;
    }



}
