package org.example.core;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import org.example.utilities.AppiumServer;
import org.example.utilities.PropertyReader;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;

import static io.appium.java_client.remote.IOSMobileCapabilityType.BUNDLE_ID;
import static io.appium.java_client.remote.MobileCapabilityType.*;

public class IOSManager {

    private static final Logger log = LoggerFactory.getLogger(IOSManager.class);

    private IOSManager() {
    }

    /**
     * creates iosDriver
     *
     * @return IOSDriver
     */
    public static IOSDriver createIOSDriver() {

        IOSDriver iosDriver = null;

        try {

            DesiredCapabilities capabilities = setIOSCapability();
            URL appiumServiceUrl = AppiumServer.startServer("", "4723").getUrl();
            iosDriver = new IOSDriver(appiumServiceUrl, capabilities);

        } catch (AppiumServerHasNotBeenStartedLocallyException | WebDriverException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return iosDriver;
    }

    private static DesiredCapabilities setIOSCapability() {

        DesiredCapabilities cap = new DesiredCapabilities();

        cap.setCapability(PLATFORM_NAME, MobilePlatform.IOS);
        cap.setCapability(DEVICE_NAME, PropertyReader.instance().getValue("deviceName"));
        cap.setCapability(UDID, PropertyReader.instance().getValue("udid"));
        cap.setCapability(AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        cap.setCapability(BUNDLE_ID, PropertyReader.instance().getValue("bundleId"));
        cap.setCapability(APP, new File(PropertyReader.instance().getValue("apk")).getPath());

        return cap;
    }
}