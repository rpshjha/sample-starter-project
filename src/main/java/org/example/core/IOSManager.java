package org.example.core;

import io.appium.java_client.ios.IOSDriver;
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

import static io.appium.java_client.remote.IOSMobileCapabilityType.BUNDLE_ID;
import static io.appium.java_client.remote.MobileCapabilityType.*;

import static org.example.core.utilities.PropertyReader.get;

@Log
public class IOSManager {

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
            URL appiumServiceUrl = AppiumServer.startServerOnAnyPort().getUrl();
            iosDriver = new IOSDriver(appiumServiceUrl, capabilities);

        } catch (AppiumServerHasNotBeenStartedLocallyException | MalformedURLException | WebDriverException e) {
            e.printStackTrace();
            log.severe(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return iosDriver;
    }

    private static DesiredCapabilities setIOSCapability() {

        DesiredCapabilities cap = new DesiredCapabilities();

        cap.setCapability(PLATFORM_NAME, MobilePlatform.IOS);
        cap.setCapability(DEVICE_NAME, get("deviceName"));
        cap.setCapability(UDID, get("udid"));
        cap.setCapability(AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        cap.setCapability(BUNDLE_ID, get("bundleId"));
        cap.setCapability(APP, new File(get("apk")).getPath());

        return cap;
    }
}