package org.example.utilities;

import com.testvagrant.mdb.android.Android;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.remote.MobilePlatform.ANDROID;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

public class EmulatorControls {

    private static final Logger log = LoggerFactory.getLogger(EmulatorControls.class);
    private static String emulatorPath;
    private static String adbPath;

    static {
        if (PropertyReader.instance().getValue(PLATFORM_NAME).equals(ANDROID)) {
            if (System.getProperty("os.name").contains("Mac")) {
                String homeDirectory = System.getProperty("user.home");
                String sdkPath = "Library" + File.separator + "Android" + File.separator + "sdk";
                emulatorPath = homeDirectory + File.separator + sdkPath + File.separator + "emulator" + File.separator + "emulator";
                adbPath = homeDirectory + File.separator + sdkPath + File.separator + "platform-tools" + File.separator + "adb";
            } else {
                String homeDirectory = System.getProperty("user.home");
                String sdkPath = "AppData" + File.separator + "Local" + File.separator + "Android" + File.separator + "Sdk";
                emulatorPath = homeDirectory + File.separator + sdkPath + File.separator + "emulator" + File.separator + "emulator.exe";
                adbPath = homeDirectory + File.separator + sdkPath + File.separator + "platform-tools" + File.separator + "adb.exe";
            }
        }
    }

    private EmulatorControls() {
    }

    public static void launchEmulator(String nameOfAVD) {

        if (isEmulatorOrDeviceRunning(nameOfAVD)) {
            log.info("Emulator/Device {} is already running ..!!", nameOfAVD);

        } else {
            log.info("Starting emulator ->> {}", nameOfAVD);
            String[] aCommand = new String[]{emulatorPath, "-avd", nameOfAVD};

            Process process;
            try {
                process = new ProcessBuilder(aCommand).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                process.waitFor(40, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (isEmulatorOrDeviceRunning(nameOfAVD))
                log.info("Emulator ->> {} launched successfully!", nameOfAVD);
        }
    }

//    public static void closeEmulator() throws InterruptedException, IOException {
//        Process process;
//        if (isEmulatorOrDeviceRunning()) {
//            log.info("Killing emulator...");
//            String[] aCommand = new String[]{adbPath, "emu", "kill"};
//            process = new ProcessBuilder(aCommand).start();
//            process.waitFor(1, TimeUnit.SECONDS);
//            if (!isEmulatorOrDeviceRunning())
//                log.info("Emulator closed successfully .. !!");
//            else if (isEmulatorOrDeviceRunning())
//                log.error("Emulator could not be closed .. !!");
//        } else
//            log.info("Currently there are no Emulators running");
//    }

    private static boolean isEmulatorOrDeviceRunning(String nameOfAVD) {
        Process process;
        String[] commandDevices;
        boolean isRunning = false;
        StringBuilder output = new StringBuilder();
        String line;
        try {
            commandDevices = new String[]{adbPath, "devices"};
            process = new ProcessBuilder(commandDevices).start();
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while ((line = inputStream.readLine()) != null) {
                output.append(line);
            }
            if (!output.toString().replace("List of devices attached", "").trim().equals("")) {

                boolean isDeviceRunning = new Android().getDevices().stream().anyMatch(d -> d.getDeviceName().contains(nameOfAVD));
                if (isDeviceRunning)
                    isRunning = true;
                log.info("\nList of devices attached\n{}", output.toString().replace("List of devices attached", ""));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isRunning;
    }
}