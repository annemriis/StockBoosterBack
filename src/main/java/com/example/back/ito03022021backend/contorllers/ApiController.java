package com.example.back.ito03022021backend.contorllers;


import com.fasterxml.jackson.databind.JsonSerializer;
import netscape.javascript.JSObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/externalApi")
@RestController
public class ApiController {

    // https://gitlab.cs.ttu.ee/petarv/iti0302-2021-heroes-back/-/tree/feature/api-w-db/src/main

    //Olegi naide, kuidas controller paks tootama
    @GetMapping("{Symbol}")
    public JSObject getStock(@PathVariable String Symbol) {
            return null;
    }
}
