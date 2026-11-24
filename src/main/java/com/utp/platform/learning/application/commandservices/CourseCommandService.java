package com.utp.platform.learning.application.commandservices;
import com.utp.platform.learning.domain.exceptions.*;
import com.utp.platform.learning.application.commands.*;
import com.utp.platform.learning.domain.aggregates.Course;
import com.utp.platform.learning.domain.events.CourseCreatedEvent;
import com.utp.platform.learning.domain.repositories.CourseRepository;
import com.utp.platform.shared.application.events.DomainEventPublisher;
import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;
@Service
public class CourseCommandService {
 private final CourseRepository repo; private final DomainEventPublisher publisher;
 public CourseCommandService(CourseRepository repo,DomainEventPublisher publisher){this.repo=repo;this.publisher=publisher;}
 @Transactional public Course handle(CreateCourseCommand c){
  if(repo.existsByCode(c.code()))throw new IllegalStateException("Course code already exists");
  Course course=new Course(c.code(),c.title(),c.description(),c.credits(),c.capacity());
  Course saved=repo.save(course);
  for(var event:course.pullDomainEvents()){
   if(event instanceof CourseCreatedEvent e)publisher.publish(new CourseCreatedEvent(saved.getId(),e.code(),e.occurredAt()));
   else publisher.publish(event);
  }
  return saved;
 }
 @Transactional public Course handle(UpdateCourseCommand c){
  Course course=repo.findById(c.courseId()).orElseThrow(()->new CourseNotFoundException(c.courseId()));
  course.updateDetails(c.title(),c.description(),c.credits(),c.capacity());
  Course saved=repo.update(course);course.pullDomainEvents().forEach(publisher::publish);return saved;
 }
}
