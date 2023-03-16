package org.example.mobile;

import com.testvagrant.commons.entities.DeviceDetails;
import com.testvagrant.mdb.android.Android;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;

import static io.appium.java_client.remote.AndroidMobileCapabilityType.BROWSER_NAME;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.PLATFORM_NAME;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.*;
import static io.appium.java_client.remote.AutomationName.ANDROID_UIAUTOMATOR2;
import static io.appium.java_client.remote.MobileBrowserType.CHROME;
import static io.appium.java_client.remote.MobileCapabilityType.*;
import static io.appium.java_client.remote.MobilePlatform.ANDROID;

@Log
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCodeForAppium {

    private AppiumDriver appiumDriver;
    private boolean onMobileBrowser = false;

    @BeforeAll
    void setup() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("avd", "Pixel_4_API_28");

        DeviceDetails deviceDetails = new Android().getDevices().get(0);
        capabilities.setCapability(UDID, deviceDetails.getUdid());
        capabilities.setCapability(PLATFORM_VERSION, deviceDetails.getOsVersion());
        capabilities.setCapability(PLATFORM_NAME, ANDROID);
        capabilities.setCapability(NEW_COMMAND_TIMEOUT, 100);
        capabilities.setCapability(AUTOMATION_NAME, ANDROID_UIAUTOMATOR2);
        capabilities.setCapability(NO_RESET, false);
        capabilities.setCapability(AUTO_GRANT_PERMISSIONS, true);

        if (onMobileBrowser) {
            capabilities.setCapability(APP_PACKAGE, "com.android.camera2");
            capabilities.setCapability(APP_ACTIVITY, "com.android.camera.CameraLauncher");
        } else {
            capabilities.setCapability(BROWSER_NAME, CHROME);
        }

        appiumDriver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
    }

    @Test
    void test() {
        System.out.println("Launching app.. !!");
        appiumDriver.get("https://www.google.com/");
    }

    @Test
    void tearDown() {
        appiumDriver.quit();
    }

    private static URL startServer() {
        AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder()
                .withIPAddress("127.0.0.1")
                .usingAnyFreePort()
                .withAppiumJS(new File("/usr/local/bin/appium"))
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.BASEPATH, "/wd/hub/");

        AppiumDriverLocalService appiumDriverLocalService = AppiumDriverLocalService.buildService(appiumServiceBuilder);
        appiumDriverLocalService.start();

        return appiumDriverLocalService.getUrl();
    }

}
