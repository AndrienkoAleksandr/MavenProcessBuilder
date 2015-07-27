package MavenProcessBuilder;

import java.io.File;
import java.io.IOException;

public class MavenProcessBuilder {

    private static final PropertiesReader propertiesReader = new PropertiesReader();

    //build this project with help maven
    public static void main(String[] args) throws IOException, InterruptedException {
        String mavenPath = propertiesReader.getPropertiesByName("mavenLocation");

        System.out.println(mavenPath);

        String[] commands = new String[]{mavenPath, "clean", "package"};
        //String[] commands = new String[]{"/bin/bash", "-cl", mavenPath, "clean", "package"};
        //String[] commands = new String[]{"CMD", "/C", mavenPath, "clean", "package"};//todo should works for windows

        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        configureLog(processBuilder);

        Process process = processBuilder.start();
        processStatus(process);
    }

    public static void configureLog(ProcessBuilder processBuilder) {
        if (propertiesReader.getPropertiesByName("logSource").equals("console")) {
            processBuilder.inheritIO();
        } else {
            processBuilder.redirectOutput(new File("src/main/resources/log.log"));
            processBuilder.redirectError(new File("src/main/resources/error.log"));
        }
    }

    public static void processStatus(Process process) throws InterruptedException {
        int returnVal = process.waitFor();
        System.out.println("Exit code: " + returnVal);
        switch (returnVal) {
            case 0:
                System.out.printf("Success!");
                break;
            default:
                System.out.println("Failed!");
                break;
        }
    }
}
