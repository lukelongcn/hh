//package com.h9.common.utils;
//
//import net.sf.uadetector.*;
//import net.sf.uadetector.service.UADetectorServiceFactory;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Created by itservice on 2018/2/1.
// */
//public class MiceUtils {
//
//    public static UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();
//
//    public static void main(String... args){
//        String googleBot21 = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
//        String macChrome = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36";
//        String ie8 = "Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 5.0; Trident/4.0; InfoPath.1; SV1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 3.0.04506.30)";
//        String ua = "Dalvik/2.1.0 (Linux; U; Android 7.0; Mi-4c MIUI/V9.1.3.0.NXKCNEI)";
//        String ua2 = " hj_ios/4.1 (com.huijiu.huijiu; build:1; iOS 11.2.2) Alamofire/4.6.0 ";
//        printUa(parser.parse(ua2));
////        printUa(parser.parse(macChrome));
////        printUa(parser.parse(ie8));
//    }
//
//    public static void printUa(ReadableUserAgent agent){
//        System.out.println("- - - - - - - - - - - - - - - - -");
//        // type
//        System.out.println("Browser type: " + agent.getType().getName());
//        System.out.println("Browser name: " + agent.getName());
//        VersionNumber browserVersion = agent.getVersionNumber();
//        System.out.println("Browser version: " + browserVersion.toVersionString());
//        System.out.println("Browser version major: " + browserVersion.getMajor());
//        System.out.println("Browser version minor: " + browserVersion.getMinor());
//        System.out.println("Browser version bug fix: " + browserVersion.getBugfix());
//        System.out.println("Browser version extension: " + browserVersion.getExtension());
//        System.out.println("Browser producer: " + agent.getProducer());
//
//        // operating system
//        OperatingSystem os = agent.getOperatingSystem();
//        System.out.println("\nOS Name: " + os.getName());
//        System.out.println("OS Producer: " + os.getProducer());
//        VersionNumber osVersion = os.getVersionNumber();
//        System.out.println("OS version: " + osVersion.toVersionString());
//        System.out.println("OS version major: " + osVersion.getMajor());
//        System.out.println("OS version minor: " + osVersion.getMinor());
//        System.out.println("OS version bug fix: " + osVersion.getBugfix());
//        System.out.println("OS version extension: " + osVersion.getExtension());
//
//        // device category
//        ReadableDeviceCategory device = agent.getDeviceCategory();
//        System.out.println("\nDevice: " + device.getName());
//    }
//}