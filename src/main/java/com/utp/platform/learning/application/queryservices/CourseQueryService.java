package com.utp.platform.learning.application.queryservices;

import com.utp.platform.learning.domain.exceptions.*;
import com.utp.platform.learning.domain.aggregates.Course;
import com.utp.platform.learning.domain.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CourseQueryService {
    private final CourseRepository repo;

    public CourseQueryService(CourseRepository repo) {
        this.repo = repo;
    }

    public Course handle(com.utp.platform.learning.application.queries.GetCourseByIdQuery q) {
        return repo.findById(q.id()).orElseThrow(() -> new CourseNotFoundException(q.id()));
    }

    public java.util.List<Course> findAll() {
        return repo.findAll();
    }
}
