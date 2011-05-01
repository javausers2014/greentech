package com.gtech.iarc.ischedule.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
public class ArcJobStatusManager {
    private static Log log = LogFactory.getLog(ArcJobStatusManager.class);
    private static HashMap<Long, String> statusMap = new HashMap<Long, String>();
    private static String COMPLETED = "COMPLETED";
    private static String COMPLETED_WITH_ERROR = "COMPLETED_WITH_ERROR";
    private static String ERROR = "ERROR";

    /**
     * DOCUMENT ME!
     *
     * @param jobId DOCUMENT ME!
     */
    public synchronized static void updateCompletedStatus(Long jobId) {
        log.debug("Updating COMPLETED status for Job " + jobId);

        String status = (String) statusMap.get(jobId);

        if (null != status) {
            if (ERROR.equals(status)) {
                log.debug("Updating COMPLETED_WITH_ERROR status for Job " +
                    jobId);
                statusMap.put(jobId, COMPLETED_WITH_ERROR);

                return;
            }
        }

        statusMap.put(jobId, COMPLETED);
    }

    /**
     * DOCUMENT ME!
     *
     * @param jobId DOCUMENT ME!
     */
    public synchronized static void updateErrorStatus(Long jobId) {
        log.debug("Updating ERROR status for Job " + jobId);
        statusMap.put(jobId, ERROR);
    }

    /**
     * DOCUMENT ME!
     *
     * @param jobId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static boolean isJobCompletedWithError(Long jobId) {
        String status = (String) statusMap.get(jobId);

        if (null != status) {
            return COMPLETED_WITH_ERROR.equals(status);
        }

        return false;
    }
}
