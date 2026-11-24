package com.utp.platform.learning.interfaces.rest.transform;

import com.utp.platform.learning.domain.aggregates.Course;
import com.utp.platform.learning.interfaces.rest.resources.*;

public final class CourseResourceAssembler {
    private CourseResourceAssembler() {}
    public static CourseResource toResource(Course s) {
        return new CourseResource(s.getId(), s.getCode(), s.getTitle(), s.getDescription(), s.getCredits(), s.getCapacity(), s.isActive(), s.getCreatedAt(), s.getPrerequisiteCourseIds(), s.getTutorialIds().stream().map(t -> t.tutorialId()).toList());
    }
}
