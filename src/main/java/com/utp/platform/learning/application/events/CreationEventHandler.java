package com.utp.platform.learning.application.events;

import com.utp.platform.learning.domain.events.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Component
public class CreationEventHandler {
    private static final Logger log = LoggerFactory.getLogger(CreationEventHandler.class);

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void studentCreated(StudentCreatedEvent e) {
        log.info("Student created: id={}, recordId={}", e.studentId(), e.studentRecordId());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void courseCreated(CourseCreatedEvent e) {
        log.info("Course created: id={}, code={}", e.courseId(), e.code());
    }
}
