package com.utp.platform.learning.application.commandservices;
import com.utp.platform.learning.domain.exceptions.*;
import com.utp.platform.learning.application.commands.*;
import com.utp.platform.learning.domain.aggregates.*;
import com.utp.platform.learning.domain.events.*;
import com.utp.platform.learning.domain.repositories.*;
import com.utp.platform.learning.domain.valueobjects.EnrollmentStatus;
import com.utp.platform.shared.application.events.DomainEventPublisher;
import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;
@Service
public class EnrollmentCommandService {
 private final EnrollmentRepository enrollments;private final StudentRepository students;private final CourseRepository courses;private final DomainEventPublisher publisher;
 public EnrollmentCommandService(EnrollmentRepository enrollments,StudentRepository students,CourseRepository courses,DomainEventPublisher publisher){this.enrollments=enrollments;this.students=students;this.courses=courses;this.publisher=publisher;}
 @Transactional public Enrollment handle(RequestEnrollmentCommand c){
  Student student=students.findById(c.studentId()).orElseThrow(()->new StudentNotFoundException(c.studentId()));
  Course course=courses.findById(c.courseId()).orElseThrow(()->new CourseNotFoundException(c.courseId()));
  if(!student.isActive()||!course.isActive())throw new IllegalStateException("Student and course must be active");
  if(enrollments.existsActiveEnrollment(c.studentId(),c.courseId()))throw new IllegalStateException("Active enrollment already exists");
  Enrollment enrollment=new Enrollment(c.studentId(),c.courseId());Enrollment saved=enrollments.save(enrollment);
  for(var event:enrollment.pullDomainEvents()){
   if(event instanceof EnrollmentRequestedEvent e)publisher.publish(new EnrollmentRequestedEvent(saved.getId(),e.studentId(),e.courseId(),e.occurredAt()));
   else publisher.publish(event);
  }
  return saved;
 }
 @Transactional public Enrollment handle(ConfirmEnrollmentCommand c){
  Enrollment enrollment=find(c.enrollmentId());
  Course course=courses.findById(enrollment.getCourseId()).orElseThrow(()->new CourseNotFoundException(enrollment.getCourseId()));
  if(!course.isActive()||!course.hasCapacity((int)enrollments.countByCourseIdAndStatus(course.getId(),EnrollmentStatus.CONFIRMED)))throw new IllegalStateException("Course has no available capacity");
  enrollment.confirm();Enrollment saved=enrollments.update(enrollment);
  for(var event:enrollment.pullDomainEvents()){
   if(event instanceof EnrollmentConfirmedEvent e)publisher.publish(new EnrollmentConfirmedEvent(saved.getId(),e.studentId(),e.courseId(),e.occurredAt()));
   else publisher.publish(event);
  }
  return saved;
 }
 @Transactional public Enrollment handle(CancelEnrollmentCommand c){Enrollment e=find(c.enrollmentId());e.cancel();Enrollment saved=enrollments.update(e);e.pullDomainEvents().forEach(publisher::publish);return saved;}
 @Transactional public Enrollment handle(RejectEnrollmentCommand c){Enrollment e=find(c.enrollmentId());e.reject();Enrollment saved=enrollments.update(e);e.pullDomainEvents().forEach(publisher::publish);return saved;}
 private Enrollment find(Long id){return enrollments.findById(id).orElseThrow(()->new EnrollmentNotFoundException(id));}
}
