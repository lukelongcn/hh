package com.h9.api.util;

import org.jboss.logging.Logger;
import org.slf4j.MDC;

/**
 * Created by itservice on 2017/10/28.
 */
public class UserUtil {
    public static Long getCurrentUserId(){
        Logger logger = Logger.getLogger(UserUtil.class);
        String userIdStr = MDC.get("userId");
        try {
            return Long.valueOf(userIdStr);
        } catch (NumberFormatException e) {
            logger.info(e.getMessage(),e);
            return null;
        }

    }
}
