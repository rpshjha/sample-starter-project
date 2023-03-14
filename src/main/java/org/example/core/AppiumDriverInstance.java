package org.example.core;

import com.testvagrant.commons.entities.DeviceDetails;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobilePlatform;
import lombok.extern.java.Log;
import org.example.core.utilities.AppiumServer;

@Log
public class AppiumDriverInstance {

    private static final ThreadLocal<AppiumDriver> appiumDriver = new ThreadLocal<>();

    private AppiumDriverInstance() {
    }

    /**
     * set up appium driver based on platform provided in config file
     *
     * @param deviceDetails
     * @param systemPort
     * @param systemPort
     * @param appiumPort
     * @return appiumDriver {@link AppiumDriver}
     */

    public static AppiumDriver setUpAppiumDriver(DeviceDetails deviceDetails, String systemPort,
                                                 String appiumPort) {

        switch (System.getProperty("platformName")) {

            case MobilePlatform.ANDROID:
                return AndroidManager.createAndroidDriver(deviceDetails, systemPort, appiumPort);

            case MobilePlatform.IOS:
                return IOSManager.createIOSDriver();

            default:
                System.err.println("enter platformName as Android or iOS");
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
