package org.example.utilities;

import io.appium.java_client.remote.MobilePlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class EmulatorControls {

    private static final Logger log = LoggerFactory.getLogger(EmulatorControls.class);
    private static final String PLATFORM_NAME = "platformName";
    private static String emulatorPath;
    private static String adbPath;

    static {
        if (PropertyReader.get(PLATFORM_NAME).equals(MobilePlatform.ANDROID)) {
            if (System.getProperty("os.name").contains("Mac")) {
                // "/Users/rupeshkumar/Library/Android/sdk"
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

    public static void launchEmulator(String nameOfAVD) throws Exception {

        Process process;

        if (isEmulatorOrDeviceRunning()) {
            log.info("Emulator/Device is already running ..!!");

        } else {
            log.info("Starting emulator ->> {}", nameOfAVD);

            if (PropertyReader.get(PLATFORM_NAME).equals(MobilePlatform.ANDROID)) {
                String[] aCommand = new String[]{emulatorPath, "-avd", nameOfAVD};

                process = new ProcessBuilder(aCommand).start();
                process.waitFor(40, TimeUnit.SECONDS);
                if (isEmulatorOrDeviceRunning())
                    log.info("Emulator ->> {} launched successfully!", nameOfAVD);
                else
                    throw new Exception("Emulator ->> " + nameOfAVD + " could not be launched");

            } else if (PropertyReader.get(PLATFORM_NAME).equals(MobilePlatform.IOS)) {

                String commandName = "/Users/rupeshkumar/Library/Android/sdk/emulator/emulator -avd " + nameOfAVD + " -netdelay none -netspeed full";

                ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", commandName);
                processBuilder.redirectErrorStream(true);
                Process p;
                try {
                    p = processBuilder.start();
                    BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;
                    line = r.readLine();
                    log.info(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void closeEmulator() throws InterruptedException, IOException {
        Process process;
        if (isEmulatorOrDeviceRunning()) {
            log.info("Killing emulator...");
            String[] aCommand = new String[]{adbPath, "emu", "kill"};
            process = new ProcessBuilder(aCommand).start();
            process.waitFor(1, TimeUnit.SECONDS);
            if (!isEmulatorOrDeviceRunning())
                log.info("Emulator closed successfully .. !!");
            else if (isEmulatorOrDeviceRunning())
                log.error("Emulator could not be closed .. !!");
        } else
            log.info("Currently there are no Emulators running");
    }

    private static boolean isEmulatorOrDeviceRunning() {
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
                isRunning = true;
                log.info("List of devices attached\n{}", output.toString().replace("List of devices attached", ""));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isRunning;
    }
}