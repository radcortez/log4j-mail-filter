package com.radcortez.log.log4j.mail.filter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Roberto Cortez
 */
public class Log4jExpressionFilter {
    private static final Logger logger = LogManager.getLogger(Log4jExpressionFilter.class);
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
