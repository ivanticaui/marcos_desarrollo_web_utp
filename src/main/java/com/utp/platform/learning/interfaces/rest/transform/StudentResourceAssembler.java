package com.utp.platform.learning.interfaces.rest.transform;

import com.utp.platform.learning.domain.aggregates.Student;
import com.utp.platform.learning.interfaces.rest.resources.*;

public final class StudentResourceAssembler {
    private StudentResourceAssembler() {}
    public static StudentResource toResource(Student s) {
        return new StudentResource(s.getId(), s.getRecordId().studentRecordId(), s.getFirstName(), s.getLastName(), s.getEmail(), s.isActive(), s.getCreatedAt());
    }
}
