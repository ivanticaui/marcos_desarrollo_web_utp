package com.utp.platform.learning.domain.exceptions;

import com.utp.platform.learning.domain.valueobjects.AcmeStudentRecordId;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) { super("Student with ID " + id + " not found"); }
    public StudentNotFoundException(AcmeStudentRecordId studentRecordId) {
        super("Student with ACME record id %s not found".formatted(studentRecordId.studentRecordId()));
    }
}
