package com.Student_Teacher.ManagementApplication.repository;

import com.Student_Teacher.ManagementApplication.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
