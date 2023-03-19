package me.theo.recipeapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @GetMapping("/")
    public String applicationIsRunning(){
        return "Приложение запущено";
    }

    @GetMapping("/info")
    public String info() {
        return "name = Theo " + "//" + " projectName = recipe App " + "//" + "dateOfCreate = 19/03/23"+ "//" + "description = recipe app ";
    }
}

