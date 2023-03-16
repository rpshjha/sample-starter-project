package org.example.mobile;

import com.testvagrant.commons.entities.DeviceDetails;
import com.testvagrant.mdb.android.Android;
import org.example.core.AppiumDriverInstance;
import org.example.utilities.EmulatorControls;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCodeForAppium_1 {

    @BeforeAll
    void setup() throws Exception {

        System.out.println("in setup");

        EmulatorControls.launchEmulator("Pixel_4_API_28");

        DeviceDetails deviceDetails = new Android().getDevices().get(0);

        AppiumDriverInstance.setUpAppiumDriver(deviceDetails, null, null);
    }

    @Test
    void test() {
        System.out.println("Launching app.. !!");
    }

    @Test
    void tearDown() {

    }

}
