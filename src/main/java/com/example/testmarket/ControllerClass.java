package com.example.testmarket;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Scanner;

@RestController
@RequestMapping("/hello")
public class ControllerClass {
    public String stringOne = "hello one";
    public String stringTwo ="hello two";

    @GetMapping("/one")
    public String method1(){
        Scanner in = new Scanner(System.in);
        stringOne = in.nextLine();
        return stringOne;
    }

    @GetMapping("/two")
    public String method2(){
        return stringTwo;
    }


}
