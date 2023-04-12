package org.example.core;

import lombok.Builder;
import lombok.Data;

@Data
public class AndroidCapability {

    private String ip;
    private String port;
    private String systemPort;
    private String apkPath = "";
    private String appPackage;
    private String appActivity;

}
