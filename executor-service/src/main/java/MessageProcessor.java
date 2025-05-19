import execution.Executor;
import execution.ExecutorException;
import model.RunRequest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import parsing.KafkaMessageParser;
import parsing.ParserException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageProcessor {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final Executor executor;
    private final KafkaMessageParser parser;

    public MessageProcessor() {
        executor = new Executor();
        parser = new KafkaMessageParser();
    }

    public void processMsg(ConsumerRecord<String, String> msg) {
        logger.log(Level.INFO, "Received msg: " + msg.value());
        try {
            RunRequest runRequest = parser.parse(msg.value());
            executor.execute(runRequest);
            logger.log(Level.INFO, "Message processed successfully");
        }
        catch (ParserException ex) {
            logger.log(Level.SEVERE, "Message parsing failed");
            ex.printStackTrace();
        }
        catch (ExecutorException ex) {
            logger.log(Level.SEVERE, "Message processing failed");
            ex.printStackTrace();
            // retry, send to secondary topic, Dead Letter Queue etc
        }
    }
}
