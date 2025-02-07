package com.akul.taskslist.repository;

import com.akul.taskslist.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = """
            SELECT * FROM taskslist.tasks t
            JOIN taskslist.users_tasks ut
            ON ut.task_id = t.id
            WHERE ut.user_id = :userId
             """, nativeQuery = true)
    List<Task> findAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = """
            INSERT INTO taskslist.users_tasks (user_id, task_id)
            VALUES (:userId, :taskId)
            """, nativeQuery = true)
    void assignTask(@Param("userId") Long userId, @Param("taskId") Long taskId);

    @Modifying
    @Query(value = """
            INSERT INTO taskslist.tasks_images (task_id, image)
            VALUES (:id, :fileName)
            """, nativeQuery = true)
    void addImage(@Param("id") Long id, @Param("fileName") String fileName);

}
