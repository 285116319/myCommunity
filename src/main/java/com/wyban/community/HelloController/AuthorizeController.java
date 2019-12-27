package com.wyban.community.HelloController;
import com.wyban.community.Provider.GithubProvider;
import com.wyban.community.dto.AccessTokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(code);
        accessTokenDTO.setClient_id("47e97c8959a379883288");
        accessTokenDTO.setClient_secret("8e7cdf53c4c707f8d5ef8bbe39da7d89ec6c54cb");
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        accessTokenDTO.setCode(state);
        githubProvider.getAccessToken(accessTokenDTO);
        return "index";
    }
}
