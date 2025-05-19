package config;

import java.io.File;

public final class Constants {
    public static final File RUN_DIR = new File("runs");

    public static final String KAFKA_SERVER = "http://localhost:9092";
//    public static final String TRUSTSTORE_LOCATION = "";
//    public static final String KEYSTORE_LOCATION = "";
//    public static final String TRUSTSTORE_PASSWORD = "";
//    public static final String KEYSTORE_PASSWORD = "";
//    public static final String KEY_PASSWORD = "";
    public static final String TOPIC_NAME = "test-topic";
    public static final String GROUP_ID = "1";

    public static final String CONTAINER_APP_DIR = "/app";
    public static final long CONTAINER_MEMORY = 512L * 1024 * 1024;
}
