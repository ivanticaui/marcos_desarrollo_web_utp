package com.utp.platform.learning.application.commandservices;

import com.utp.platform.learning.domain.exceptions.*;
import com.utp.platform.learning.application.commands.*;
import com.utp.platform.learning.domain.aggregates.Student;
import com.utp.platform.learning.domain.events.StudentCreatedEvent;
import com.utp.platform.learning.domain.repositories.StudentRepository;
import com.utp.platform.shared.application.events.DomainEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentCommandService {
    private final StudentRepository repo;
    private final DomainEventPublisher publisher;

    public StudentCommandService(StudentRepository repo, DomainEventPublisher publisher) {
        this.repo = repo;
        this.publisher = publisher;
    }

    @Transactional
    public Student handle(CreateStudentCommand c) {
        Student student = new Student(c.firstName(), c.lastName(), c.email());
        Student saved = repo.save(student);
        for (var event : student.pullDomainEvents()) {
            if (event instanceof StudentCreatedEvent e)
                publisher.publish(new StudentCreatedEvent(saved.getId(), e.studentRecordId(), e.email(), e.occurredOn()));
            else publisher.publish(event);
        }
        return saved;
    }

    @Transactional
    public Student handle(ChangeStudentActiveCommand c) {
        Student student = repo.findById(c.studentId()).orElseThrow(() -> new StudentNotFoundException(c.studentId()));
        if (c.active()) student.activate();
        else student.deactivate();
        Student saved = repo.update(student);
        student.pullDomainEvents().forEach(publisher::publish);
        return saved;
    }
}
