package by.epam.riverport.util;

import by.epam.riverport.exception.RiverPortException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class Waiting {

    private static final Logger logger = LogManager.getLogger();

    public static void wait(int milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.log(Level.WARN, "InterruptedException in method Waiting.wait.");
            e.printStackTrace();
        }
    }

}
