package Test.demo.controllers;

import Test.demo.models.Note;
import Test.demo.repositories.NoteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @GetMapping
    public String getNotes(
        Model model,
        @AuthenticationPrincipal OidcUser principal
    ) {
        if (principal != null) {
            String userId = principal.getSubject();
            // Use name or email instead of sub claim
            String userName = principal.getClaim("name");
            if (userName == null || userName.isEmpty()) {
                userName = principal.getClaim("email");
            }
            if (userName == null || userName.isEmpty()) {
                userName = userId; // Fallback to sub if name and email are not available
            }
            model.addAttribute("username", userName);
            model.addAttribute("notes", noteRepository.findByUserId(userId));
        }
        return "notes";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Note> addNote(
        @RequestBody Note newNote,
        @AuthenticationPrincipal OidcUser principal
    ) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        newNote.setUserId(principal.getSubject());
        Note savedNote = noteRepository.save(newNote);
        return ResponseEntity.ok(savedNote);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Note> updateNote(
        @PathVariable Long id,
        @RequestBody Note updatedNote,
        @AuthenticationPrincipal OidcUser principal
    ) {
        if (principal != null) {
            return noteRepository
                .findByIdAndUserId(id, principal.getSubject())
                .map(note -> {
                    note.setContent(updatedNote.getContent());
                    return ResponseEntity.ok(noteRepository.save(note));
                })
                .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteNote(
        @PathVariable Long id,
        @AuthenticationPrincipal OidcUser principal
    ) {
        if (principal != null) {
            if (
                noteRepository.existsByIdAndUserId(id, principal.getSubject())
            ) {
                noteRepository.deleteById(id);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<Note>> getNotesApi(
        @AuthenticationPrincipal OidcUser principal
    ) {
        if (principal != null) {
            List<Note> notes = noteRepository.findByUserId(
                principal.getSubject()
            );
            return ResponseEntity.ok(notes);
        }
        return ResponseEntity.badRequest().build();
    }
}
