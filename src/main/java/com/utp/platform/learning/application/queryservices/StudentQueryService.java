package com.utp.platform.learning.application.queryservices;

import com.utp.platform.learning.domain.exceptions.*;
import com.utp.platform.learning.domain.aggregates.Student;
import com.utp.platform.learning.domain.repositories.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StudentQueryService {
    private final StudentRepository repo;

    public StudentQueryService(StudentRepository repo) {
        this.repo = repo;
    }

    public Student handle(com.utp.platform.learning.application.queries.GetStudentByIdQuery q) {
        return repo.findById(q.id()).orElseThrow(() -> new StudentNotFoundException(q.id()));
    }

    public java.util.List<Student> findAll() {
        return repo.findAll();
    }
}
