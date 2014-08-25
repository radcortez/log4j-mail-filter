package com.radcortez.samples.log4j2.mail.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Roberto Cortez
 */
public class Log4j2Filter {
    private static final Logger logger = LogManager.getLogger(Log4j2Filter.class);
    private boolean cycle;

    public void executeBusinessLogic() {
        String errorCode;

        if (cycle) {
            errorCode = "ERROR01";
            cycle = false;
        } else {
            errorCode = "ERROR02";
            cycle = true;
        }

        logger.error("We got the following error: " + errorCode);
    }
}
