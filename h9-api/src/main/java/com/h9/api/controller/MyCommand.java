package com.h9.api.controller;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

/**
 * Created by itservice on 2018/2/24.
 */
@ShellCommandGroup("myCommand")
@ShellComponent
public class MyCommand {

    private boolean connected;

    @ShellMethod("connect to the server")
    public void connect(String user,String pwd){
        connected = true;
    }

    @ShellMethod("download the codes")
    public String  download(){

        return "download success";
    }

    @ShellMethod("download the codes")
    public String  download2(){

        return "download success";
    }

    @ShellMethodAvailability({"download","download2"})
    public Availability checkDownload(){
        return connected ? Availability.available() : Availability.unavailable("u not connect");
    }
}
