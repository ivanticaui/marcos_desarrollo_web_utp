package com.utp.platform.learning.domain;

import com.utp.platform.learning.domain.aggregates.Course;
import com.utp.platform.learning.domain.aggregates.Enrollment;
import com.utp.platform.learning.domain.aggregates.Student;
import com.utp.platform.learning.domain.events.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DomainEventsTest {
    @Test void studentCreationRegistersAndDrainsEvent() {
        Student student = new Student("Ana", "Perez", "ana@example.com");
        assertInstanceOf(StudentCreatedEvent.class, student.pullDomainEvents().getFirst());
        assertTrue(student.pullDomainEvents().isEmpty());
    }
    @Test void courseCreationRegistersEvent() {
        Course course = new Course("JAVA101", "Java", "Basics", 3, 30);
        assertInstanceOf(CourseCreatedEvent.class, course.pullDomainEvents().getFirst());
    }
    @Test void enrollmentRecordsRequestAndConfirmation() {
        Enrollment enrollment = new Enrollment(1L, 2L);
        assertInstanceOf(EnrollmentRequestedEvent.class, enrollment.pullDomainEvents().getFirst());
        enrollment.confirm();
        assertInstanceOf(EnrollmentConfirmedEvent.class, enrollment.pullDomainEvents().getFirst());
    }
}
