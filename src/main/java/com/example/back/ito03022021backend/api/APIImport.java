package com.example.back.ito03022021backend.api;

import java.io.ObjectInputFilter;

public class APIImport {


    public void initialize() {
        Config cfg = Config.builder()
                .key("#&ALPHA10100DEMOKEY")
                .timeOut(10)
                .build();
    }

}
