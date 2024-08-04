package Test.demo.repositories;

import Test.demo.models.Note;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserId(String userId);
    Optional<Note> findByIdAndUserId(Long id, String userId);
    boolean existsByIdAndUserId(Long id, String userId);
}
