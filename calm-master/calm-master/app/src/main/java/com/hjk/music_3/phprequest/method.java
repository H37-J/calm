package com.hjk.music_3.phprequest;

public class method {

    //로그인
    //php
//        if(userViewModel.load_save_login()==1) {
//            Response.Listener<String> responseListener = new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        boolean success = jsonObject.getBoolean("success");
//
//                        if (success) {
//                            String userId = jsonObject.getString("userId");
//                            String userPassword = jsonObject.getString("userPassword");
//                            String name=jsonObject.getString("display_name");
//                            String save_back = jsonObject.getString("save_back");
//                            String save_day=jsonObject.getString("save_day");
//                            String save_history=jsonObject.getString("save_history");
//                            String save_time=jsonObject.getString("save_time");
//                            String like_music=jsonObject.getString("like_music");
//                            String last_login=jsonObject.getString("last_login");
//                            last_login= TimeUtils.getDayCom(); //현재 년도 월일 받아오기
//
//                            if(Integer.parseInt(last_login)==0){
//                                count=1;
//                            }
//                            if(Integer.parseInt(last_login)== Integer.parseInt(TimeUtils.getDayCom())-1){
//                                count=Integer.parseInt(save_day)+1;
//                            }
//                            else{
//                                count=1;
//                            }
//
//                            System.out.println(save_time+"받아온 시간");
//
//
//                            userViewModel.save_login(1);
//                            User user = new User();
//                            user.setId(userId);
//                            user.setPwd(userPassword);
//                            user.setName(name);
//                            user.setSave_day(Integer.toString(count));
//                            user.setSave_history(save_history);
//                            user.setSave_time(save_time);
//                            user.setLike_music(like_music);
//                            user.setLast_login(last_login);
//
//                            System.out.println("name:"+name);
//                            user.setSave_back(save_back);
//                            if (save_back != "null")
//                                backViewModel.set_current_back(backViewModel.getBack().getValue().get(Integer.parseInt(save_back)));
//
//
//                            userViewModel.setCurrent_user(user);
//
//                            Response.Listener<String> responseListener2= new Response.Listener<String>(){
//                                @Override
//                                public void onResponse(String response){
//
//                                }
//                            };
//
//                            UpdateRequestMusic updateRequestMusic=new UpdateRequestMusic(userId,Integer.toString(count),save_history,save_time,last_login, responseListener2);
//                            RequestQueue queue2= Volley.newRequestQueue(SplashActivity.this);
//                            queue2.add(updateRequestMusic);
//
//                        } else {
//
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            };
//
//            LoginRequest loginRequest = new LoginRequest(userViewModel.load_id(), userViewModel.load_pwd(), responseListener);
//            RequestQueue queue = Volley.newRequestQueue(SplashActivity.this);
//            queue.add(loginRequest);
//        }
}
