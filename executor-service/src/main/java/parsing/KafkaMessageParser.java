package parsing;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import model.RunRequest;

import java.io.IOException;

public class KafkaMessageParser {
    JsonAdapter<RunRequest> runRequestJsonAdapter;

    public KafkaMessageParser() {
        Moshi moshi = new Moshi.Builder().build();
        runRequestJsonAdapter = moshi.adapter(RunRequest.class);
    }

    public RunRequest parse(String jsonMessage) throws ParserException {
        try {
            return runRequestJsonAdapter.fromJson(jsonMessage);
        }
        catch (IOException ex) {
            throw new ParserException("Failed to decode run request from kafka message", ex);
        }
    }
}
