package com.wyban.community.HelloController;
import com.wyban.community.Provider.GithubProvider;
import com.wyban.community.dto.AccessTokenDTO;
import com.wyban.community.dto.GithubUser;
import com.wyban.community.mapper.UserMapper;
import com.wyban.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String ClientId;
    @Value("${github.client.secret}")
    private String ClientSecret;
    @Value("${github.redirect.uri}")
    private String redirecturi;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(ClientId);
        accessTokenDTO.setClient_secret(ClientSecret);
        accessTokenDTO.setRedirect_uri(redirecturi);
        accessTokenDTO.setState(state);
        String accesstoken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accesstoken);
        if (githubUser !=null){
            //登陆成功，写cookie 和session
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            //手动存入存储cookie
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
            //登陆失败，重新登陆
            return "redirect:/";
        }
    }
}
