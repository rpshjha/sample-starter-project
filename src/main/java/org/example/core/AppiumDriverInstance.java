package org.example.core;

import com.testvagrant.commons.entities.DeviceDetails;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobilePlatform;
import org.example.utilities.AppiumServer;
import org.example.utilities.PropertyReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppiumDriverInstance {
    private static final Logger log = LoggerFactory.getLogger(AppiumDriverInstance.class);
    private static final ThreadLocal<AppiumDriver> appiumDriver = new ThreadLocal<>();

    private AppiumDriverInstance() {
    }

    /**
     * set up appium driver based on platform provided in config file
     */
    public static AppiumDriver setUpAppiumDriver(DeviceDetails deviceDetails, String systemPort,
                                                 String appiumPort) {

        switch (PropertyReader.get("platformName")) {

            case MobilePlatform.ANDROID:
                return AndroidManager.createAndroidDriver(deviceDetails, systemPort, appiumPort);

            case MobilePlatform.IOS:
                return IOSManager.createIOSDriver();

            default:
                log.error("enter platformName as Android or iOS");
                return null;
        }
    }

    public static AppiumDriver setUpAppiumDriver(DeviceDetails deviceDetails) {

        switch (System.getProperty("platformName")) {

            case MobilePlatform.ANDROID:
                return AndroidManager.createAndroidDriver(deviceDetails, null, "");

            case MobilePlatform.IOS:
                return IOSManager.createIOSDriver();

            default:
                log.error("enter platformName as Android or iOS");
                return null;
        }
    }

    /**
     * @return {@link AppiumDriver}
     */
    public static AppiumDriver getAppiumDriver() {
        return appiumDriver.get();
    }

    /**
     * quit appium session
     */
    public static void quitAppiumDriver() {

        if (appiumDriver.get() != null) {
            log.info("quitting appium session");

            appiumDriver.get().quit();
            appiumDriver.remove();
            AppiumServer.stopServer();
        }
    }
}
