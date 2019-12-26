package com.wyban.community.HelloController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Create by wyban on 2019/12/24
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";

    }
}
