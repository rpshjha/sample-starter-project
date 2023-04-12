package org.example.mobile;

import com.testvagrant.commons.entities.DeviceDetails;
import com.testvagrant.mdb.android.Android;
import org.example.core.AndroidCapability;
import org.example.core.AppiumDriverInstance;
import org.example.utilities.EmulatorControls;
import org.example.utilities.PropertyReader;
import org.example.utilities.RandomUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
class ParallelMobileTest {
    PropertyReader properties;
    List<DeviceDetails> list;

    @BeforeAll
    void setup() throws Exception {
        properties = PropertyReader.instance();
//        EmulatorControls.launchEmulator("Device_1");
        EmulatorControls.launchEmulator("Device_2");

        list = new Android().getDevices();
    }

    @Test
    void o() throws InterruptedException {
        System.out.println("FirstParallelUnitTest first() start => " + Thread.currentThread().getName());
        Thread.sleep(1000);
        System.out.println("FirstParallelUnitTest first() end => " + Thread.currentThread().getName());
    }

    @Test
    void b() throws InterruptedException {
        System.out.println("SecondParallelUnitTest first() start => " + Thread.currentThread().getName());
        Thread.sleep(1000);
        System.out.println("SecondParallelUnitTest first() end => " + Thread.currentThread().getName());
    }

//    @Test
//    void test1() {
//        System.out.println("FirstParallelUnitTest first() start => " + Thread.currentThread().getName());
//        AndroidCapability androidCapability = new AndroidCapability();
//        androidCapability.setAppPackage(properties.getValue("appPackage"));
//        androidCapability.setAppActivity(properties.getValue("appActivity"));
//        androidCapability.setSystemPort(RandomUtils.randomSystemPort());
//        androidCapability.setPort("4723");
//        AppiumDriverInstance.setUpAppiumDriver(list.get(0), androidCapability);
//        System.out.println("FirstParallelUnitTest first() end => " + Thread.currentThread().getName());
//    }
//
//    @Test
//    void test2() {
//        System.out.println("SecondParallelUnitTest first() start => " + Thread.currentThread().getName());
//        AndroidCapability androidCapability = new AndroidCapability();
//        androidCapability.setAppPackage(properties.getValue("appPackage"));
//        androidCapability.setAppActivity(properties.getValue("appActivity"));
//        androidCapability.setSystemPort(RandomUtils.randomSystemPort());
//        androidCapability.setPort("4792");
//        AppiumDriverInstance.setUpAppiumDriver(list.get(1), androidCapability);
//        System.out.println("SecondParallelUnitTest first() end => " + Thread.currentThread().getName());
//    }
}
