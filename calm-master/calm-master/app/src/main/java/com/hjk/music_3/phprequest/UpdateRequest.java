package com.hjk.music_3.phprequest;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateRequest  extends StringRequest {

    final static String url="https://nowglobalhealing.com/wp-admin/Update.php";

    final static String url2="https://nowglobalhealing.com/wp-admin/UpdatePass.php";

    final static String url3="https://nowglobalhealing.com/wp-admin/UpdateLike.php";
    private Map<String,String> map;


    public UpdateRequest(String userId,Integer save_back, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        map=new HashMap<>();
        map.put("userId",userId);
        map.put("save_back",save_back.toString());

    }

    public UpdateRequest(String userId,String pwd, Response.Listener<String> listener) {
        super(Method.POST, url2, listener, null);
        map=new HashMap<>();
        map.put("userId",userId);
        map.put("userPass",pwd);

    }




    @Override
    protected Map<String,String> getParams() throws AuthFailureError {
        return map;
    }


}
