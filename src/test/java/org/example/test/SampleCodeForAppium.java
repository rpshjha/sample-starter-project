package org.example.test;

import com.testvagrant.commons.entities.DeviceDetails;
import com.testvagrant.mdb.android.Android;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;
import java.util.List;

import static io.appium.java_client.remote.AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS;
import static io.appium.java_client.remote.MobileCapabilityType.*;
import static org.openqa.selenium.remote.CapabilityType.BROWSER_NAME;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

public class SampleCodeForAppium {

    private AppiumDriver appiumDriver;

    @BeforeTest
    void setup() throws Exception {

        List<DeviceDetails> deviceDetails = new Android().getDevices();

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
        capabilities.setCapability(UDID, deviceDetails.get(0).getUdid());
        capabilities.setCapability(PLATFORM_NAME, "Android");
        capabilities.setCapability(PLATFORM_VERSION, deviceDetails.get(0).getOsVersion());
//        capabilities.setCapability(APP_PACKAGE, "com.android.camera2");
//        capabilities.setCapability(APP_ACTIVITY, "com.android.camera.CameraLauncher");
        capabilities.setCapability(BROWSER_NAME, "chrome");
        capabilities.setCapability(AUTO_GRANT_PERMISSIONS, true);
        capabilities.setCapability(NO_RESET, false);
        capabilities.setCapability(NEW_COMMAND_TIMEOUT, 100);

        URL url = startServer();
        System.out.println("URL is " + url.toString());
        appiumDriver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
//        appiumDriver = new AppiumDriver(new URL("http://127.0.0.0:4723/wd/hub"), capabilities);
//        appiumDriver = new AndroidDriver(url, capabilities);

    }

    @Test
    void test() {

        System.out.println("Launching app.. !!");

//        appiumDriver.get("https://www.google.com/");
    }

    @Test
    void tearDown() {

        appiumDriver.quit();
    }

    public static URL startServer() {
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
