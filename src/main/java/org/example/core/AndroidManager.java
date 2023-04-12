package org.example.core;

import com.testvagrant.commons.entities.DeviceDetails;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import org.example.utilities.PropertyReader;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static io.appium.java_client.remote.AndroidMobileCapabilityType.BROWSER_NAME;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.PLATFORM_NAME;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.*;
import static io.appium.java_client.remote.MobileCapabilityType.*;
import static org.example.utilities.AppiumServer.startServer;
import static org.example.utilities.RandomUtils.randomSystemPort;
import static org.openqa.selenium.remote.Browser.CHROME;

public class AndroidManager {

    private static final Logger log = LoggerFactory.getLogger(AndroidManager.class);

    private AndroidManager() {
    }

    /**
     * creates android driver
     */
    public static AndroidDriver createAndroidDriver(DeviceDetails deviceDetails, AndroidCapability androidCapability) {
        try {
            log.info("initializing android driver..");
            return new AndroidDriver(startServer(androidCapability.getIp(), androidCapability.getPort()).getUrl(), setAndroidCapability(deviceDetails, androidCapability));
        } catch (AppiumServerHasNotBeenStartedLocallyException | WebDriverException e) {
            log.error("android driver could not be initialized.. !!");
            log.error(e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * set desired capability
     */
    private static DesiredCapabilities setAndroidCapability(DeviceDetails deviceDetails, AndroidCapability androidCapability) {

        DesiredCapabilities cap = new DesiredCapabilities();

        if (androidCapability.getSystemPort() == null || androidCapability.getSystemPort().isEmpty())
            log.info("system port NOT received, ignoring system port capability");
        else
            cap.setCapability(SYSTEM_PORT, randomSystemPort());

        cap.setCapability(UDID, deviceDetails.getUdid());
        cap.setCapability(DEVICE_NAME, deviceDetails.getDeviceName());
        cap.setCapability(PLATFORM_VERSION, deviceDetails.getOsVersion());

        cap.setCapability(PLATFORM_NAME, MobilePlatform.ANDROID);

        if (PropertyReader.instance().getValue("onMobileBrowser").equals("true")) {
            cap.setCapability(BROWSER_NAME, CHROME);
        } else {
            if (!androidCapability.getApkPath().isEmpty())
                cap.setCapability(APP, new File(androidCapability.getApkPath()).getPath());
            cap.setCapability(APP_PACKAGE, androidCapability.getAppPackage());
            cap.setCapability(APP_ACTIVITY, androidCapability.getAppActivity());
        }

        cap.setCapability(ADB_EXEC_TIMEOUT, 40000);
        cap.setCapability(NEW_COMMAND_TIMEOUT, 60);
        cap.setCapability(AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
        cap.setCapability(AUTO_GRANT_PERMISSIONS, true);
        cap.setCapability(NO_RESET, true);

        return cap;
    }
}
