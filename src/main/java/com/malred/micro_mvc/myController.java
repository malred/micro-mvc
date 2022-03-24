package com.malred.micro_mvc;

import org.springframework.stereotype.Controller;

/**
 * @author malguy-wang sir
 * @create ---
 */
@Controller
public class myController {
    @RequestMapping("/test")
    public String test(@RequestParam("name")String name){
        System.out.println("tttt"+name);
        return "test";
    }
    @RequestMapping("/test1")
    public String test1(){
        System.out.println("tttt");
        return "test1";
    }
}
