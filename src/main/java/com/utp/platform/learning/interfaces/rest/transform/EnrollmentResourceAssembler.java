package com.utp.platform.learning.interfaces.rest.transform;

import com.utp.platform.learning.domain.aggregates.Enrollment;
import com.utp.platform.learning.interfaces.rest.resources.*;

public final class EnrollmentResourceAssembler {
    private EnrollmentResourceAssembler() {}
    public static EnrollmentResource toResource(Enrollment s) {
        return new EnrollmentResource(s.getId(), s.getStudentId(), s.getCourseId(), s.getStatus().name(), s.getRequestedAt(), s.getConfirmedAt(), s.getCancelledAt(), s.progressPercentage(), s.getProgressItems().stream().map(i -> new ProgressItemResource(i.getId(), i.getTutorialId().tutorialId(), i.getStatus().name(), i.getStartedAt(), i.getCompletedAt())).toList());
    }
}
