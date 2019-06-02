package ru.gavrilov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gavrilov.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
