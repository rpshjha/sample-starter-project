package org.example.core;

import com.testvagrant.commons.entities.DeviceDetails;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import lombok.extern.java.Log;
import org.example.core.utilities.AppiumServer;
import org.example.core.utilities.PropertyReader;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static io.appium.java_client.remote.AndroidMobileCapabilityType.PLATFORM_NAME;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.*;
import static io.appium.java_client.remote.MobileCapabilityType.*;

import static org.example.core.utilities.PropertyReader.get;

@Log
public class AndroidManager {

    private AndroidManager() {
    }

    /**
     * creates android driver
     *
     * @param deviceDetails
     * @param systemPort
     * @param appiumPort
     * @return AndroidDriver
     */
    public static AndroidDriver createAndroidDriver(DeviceDetails deviceDetails, String systemPort,
                                                    String appiumPort) {
        AndroidDriver androidDriver = null;
        try {
            DesiredCapabilities cap = setAndroidCapability(deviceDetails, systemPort);
            URL appiumServiceUrl;

            if (appiumPort == null || appiumPort.isEmpty()) {
                System.err.println("appium port NOT received, using default port provided in config property");
                appiumServiceUrl = AppiumServer.startServer(Integer.parseInt(get("appiumPort")))
                        .getUrl();
            } else {
                appiumServiceUrl = AppiumServer.startServer(Integer.parseInt(appiumPort)).getUrl();
            }

            log.info("initializing android driver");
            androidDriver = new AndroidDriver(appiumServiceUrl, cap);
            log.info("android driver initialized");

        } catch (AppiumServerHasNotBeenStartedLocallyException | MalformedURLException | WebDriverException e) {
            e.printStackTrace();
            log.severe(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return androidDriver;
    }

    /**
     * set desired capability
     *
     * @param deviceDetails
     * @param systemPort
     * @return DesiredCapabilities
     */
    private static DesiredCapabilities setAndroidCapability(DeviceDetails deviceDetails, String systemPort) {

        DesiredCapabilities cap = new DesiredCapabilities();
        int newCommandTimeout = Integer.parseInt(get("newCommandTimeout"));

        if (systemPort == null)
            log.info("system port NOT received, ignoring system port capability");
        else if (!systemPort.isEmpty())
            cap.setCapability(SYSTEM_PORT, Integer.valueOf(systemPort));


        if (deviceDetails == null) {
            cap.setCapability(AVD, get("nameOfAVD"));
            cap.setCapability(DEVICE_NAME, get("nameOfAVD"));
        } else {
            cap.setCapability(UDID, deviceDetails.getUdid());
            cap.setCapability(DEVICE_NAME, deviceDetails.getDeviceName());
            cap.setCapability(PLATFORM_VERSION, deviceDetails.getOsVersion());
        }

        cap.setCapability(PLATFORM_NAME, MobilePlatform.ANDROID);
        cap.setCapability(APP, new File(get("apk")).getPath());
        cap.setCapability(APP_PACKAGE, get("appPackage"));
        cap.setCapability(APP_ACTIVITY, get("appActivity"));
        cap.setCapability(NEW_COMMAND_TIMEOUT, newCommandTimeout);
        cap.setCapability(AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
        cap.setCapability(AUTO_GRANT_PERMISSIONS, true);
        cap.setCapability(NO_RESET, false);

        return cap;
    }
}
