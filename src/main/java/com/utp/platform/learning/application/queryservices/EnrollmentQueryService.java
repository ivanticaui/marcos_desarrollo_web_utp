package com.utp.platform.learning.application.queryservices;

import com.utp.platform.learning.domain.exceptions.*;
import com.utp.platform.learning.domain.aggregates.Enrollment;
import com.utp.platform.learning.domain.repositories.EnrollmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EnrollmentQueryService {
    private final EnrollmentRepository repo;

    public EnrollmentQueryService(EnrollmentRepository repo) {
        this.repo = repo;
    }

    public Enrollment handle(com.utp.platform.learning.application.queries.GetEnrollmentByIdQuery q) {
        return repo.findById(q.id()).orElseThrow(() -> new EnrollmentNotFoundException(q.id()));
    }

    public java.util.List<Enrollment> handle(com.utp.platform.learning.application.queries.GetEnrollmentsByStudentQuery q) {
        return repo.findByStudentId(q.studentId());
    }

    public java.util.List<Enrollment> handle(com.utp.platform.learning.application.queries.GetEnrollmentsByCourseQuery q) {
        return repo.findByCourseId(q.courseId());
    }
}
