package org.example.core;

import com.testvagrant.commons.entities.DeviceDetails;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;

import static io.appium.java_client.remote.AndroidMobileCapabilityType.PLATFORM_NAME;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.*;
import static io.appium.java_client.remote.MobileBrowserType.CHROME;
import static io.appium.java_client.remote.MobileCapabilityType.*;
import static org.example.utilities.AppiumServer.startServer;
import static org.example.utilities.PropertyReader.get;

public class AndroidManager {

    private static final Logger log = LoggerFactory.getLogger(AndroidManager.class);

    private AndroidManager() {
    }

    /**
     * creates android driver
     */
    public static AndroidDriver createAndroidDriver(DeviceDetails deviceDetails, String systemPort,
                                                    String appiumPort) {
        AndroidDriver androidDriver = null;
        DesiredCapabilities cap = setAndroidCapability(deviceDetails, systemPort);
        try {
            URL appiumServiceUrl;

            if (appiumPort == null || appiumPort.isEmpty()) {
                log.info("appium port NOT received, using default port provided");
                appiumServiceUrl = startServer(Integer.parseInt(get("appiumPort")))
                        .getUrl();
            } else {
                appiumServiceUrl = startServer(Integer.parseInt(appiumPort)).getUrl();
            }

            log.info("initializing android driver");
            androidDriver = new AndroidDriver(appiumServiceUrl, cap);
            log.info("android driver initialized");

        } catch (AppiumServerHasNotBeenStartedLocallyException | WebDriverException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return androidDriver;
    }


    /**
     * set desired capability
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
        if (get("onMobileBrowser").equals("true")) {
            cap.setCapability(BROWSER_NAME, CHROME);
        } else {
            if (!get("apk").isEmpty())
                cap.setCapability(APP, new File(get("apk")).getPath());
            cap.setCapability(APP_PACKAGE, get("appPackage"));
            cap.setCapability(APP_ACTIVITY, get("appActivity"));
        }
        cap.setCapability(ADB_EXEC_TIMEOUT, 40000);
        cap.setCapability(NEW_COMMAND_TIMEOUT, newCommandTimeout);
        cap.setCapability(AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
        cap.setCapability(AUTO_GRANT_PERMISSIONS, true);
        cap.setCapability(NO_RESET, false);

        return cap;
    }
}
