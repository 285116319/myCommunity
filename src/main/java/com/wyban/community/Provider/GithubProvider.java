package com.wyban.community.Provider;


import com.alibaba.fastjson.JSON;
import com.wyban.community.dto.AccessTokenDTO;
import com.wyban.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * created by wyban on 2019/12/26
 * mama's birthday
 */

@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){

        System.out.println("##########");
        MediaType  mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token?client_id="+accessTokenDTO.getClient_id()+"&client_secret="+accessTokenDTO.getClient_secret()+"&code="+accessTokenDTO.getCode()+"&redirect_uri="+accessTokenDTO.getRedirect_uri()+"&state="+accessTokenDTO.getState())
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                System.out.println("*********");
                System.out.println(string);
                System.out.println("*********");
                return string;

        } catch (IOException e) {
            System.out.println("有异常："+ e);
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string,GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

