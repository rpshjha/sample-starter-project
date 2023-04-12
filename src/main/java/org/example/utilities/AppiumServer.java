package org.example.utilities;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static io.appium.java_client.remote.MobilePlatform.ANDROID;
import static io.appium.java_client.service.local.flags.AndroidServerFlag.BOOTSTRAP_PORT_NUMBER;
import static org.example.utilities.RandomUtils.aRandomOpenPortOnAllLocalInterfaces;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;


public class AppiumServer {

    private static final Logger log = LoggerFactory.getLogger(AppiumServer.class);
    private static final ThreadLocal<AppiumDriverLocalService> appiumService = new ThreadLocal<>();
    private static final String NODE_PATH = "/usr/local/bin/node";
    private static final String APPIUM_JS_PATH = "/usr/local/lib/node_modules/appium/build/lib/main.js";

    private AppiumServer() {
    }

    public static AppiumDriverLocalService startServer(String ip, String port) {

        if (ip == null || ip.isEmpty()) {
            ip = "0.0.0.0";
            log.info("appium ip NOT received, using default ip {}", ip);
        }
        if (port == null || port.isEmpty()) {
            port = "4723";
            log.info("appium port NOT received, using default port {}", port);
        }

        AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
        appiumServiceBuilder.withIPAddress(ip);
        appiumServiceBuilder.usingPort(Integer.parseInt(port));
        appiumServiceBuilder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        appiumServiceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
        appiumServiceBuilder.usingDriverExecutable(new File(NODE_PATH));
        appiumServiceBuilder.withAppiumJS(new File(APPIUM_JS_PATH));
        appiumServiceBuilder.withArgument(GeneralServerFlag.BASEPATH, "/wd/hub/");

        if (PropertyReader.instance().getValue(PLATFORM_NAME).equalsIgnoreCase(ANDROID))
            appiumServiceBuilder.withArgument(BOOTSTRAP_PORT_NUMBER, aRandomOpenPortOnAllLocalInterfaces());

        appiumService.set(AppiumDriverLocalService.buildService(appiumServiceBuilder));

        log.info("starting appium server ..");
        appiumService.get().clearOutPutStreams();
        appiumService.get().start();

        if (appiumService.get() == null || !appiumService.get().isRunning()) {
            log.error("appium server could not be started..!!");
            throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
        } else {
            log.info("appium server started at : {}", appiumService.get().getUrl());
            return appiumService.get();
        }
    }

    public static void stopServer() {
        log.info("stopping appium server");
        if (appiumService.get() != null) {
            appiumService.get().stop();
            appiumService.remove();
        }
    }

}