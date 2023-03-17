package org.example.utilities;

import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.time.Duration;

import static org.example.utilities.PropertyReader.get;

public class AppiumServer {

    private static final Logger log = LoggerFactory.getLogger(AppiumServer.class);
    private static final ThreadLocal<AppiumDriverLocalService> appiumService = new ThreadLocal<>();

    private AppiumServer() {
    }

    public static AppiumDriverLocalService startServer(int port) {

        AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
        appiumServiceBuilder.withIPAddress(get("ip"));
        appiumServiceBuilder.usingPort(port);
        appiumServiceBuilder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        appiumServiceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
        appiumServiceBuilder.usingDriverExecutable(new File("/usr/local/bin/node"));
        appiumServiceBuilder.withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"));
        appiumServiceBuilder.withArgument(GeneralServerFlag.BASEPATH, "/wd/hub/");

        if (get("platformName").equalsIgnoreCase(MobilePlatform.ANDROID))
            appiumServiceBuilder.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER,
                    String.valueOf(aRandomOpenPortOnAllLocalInterfaces()));

        appiumService.set(AppiumDriverLocalService.buildService(appiumServiceBuilder));

        log.info("starting appium server");
        appiumService.get().clearOutPutStreams();
        appiumService.get().start();

        if (appiumService.get() == null || !appiumService.get().isRunning())
            throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
        else
            log.info("appium server started at : {}", appiumService.get().getUrl());
        return appiumService.get();
    }

    private boolean isAppiumServiceRunning(int port) {
        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.close();
        } catch (IOException e) {
            // If control comes here, then it means that the port is in use
            isServerRunning = true;
        }
        return isServerRunning;
    }

    public static void stopServer() {
        log.info("stopping appium server");
        if (appiumService.get() != null) {
            appiumService.get().stop();
            appiumService.remove();
        }
    }

    private static int aRandomOpenPortOnAllLocalInterfaces() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException("no open ports found for bootstrap");
        }
    }

}