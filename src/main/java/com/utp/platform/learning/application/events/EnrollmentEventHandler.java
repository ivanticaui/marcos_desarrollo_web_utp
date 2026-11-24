package com.utp.platform.learning.application.events;

import com.utp.platform.learning.domain.events.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Component
public class EnrollmentEventHandler {
    private static final Logger log = LoggerFactory.getLogger(EnrollmentEventHandler.class);

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void requested(EnrollmentRequestedEvent e) {
        log.info("Enrollment requested: id={}, student={}, course={}", e.enrollmentId(), e.studentId(), e.courseId());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void confirmed(EnrollmentConfirmedEvent e) {
        log.info("Enrollment confirmed: id={}", e.enrollmentId());
    }
}
