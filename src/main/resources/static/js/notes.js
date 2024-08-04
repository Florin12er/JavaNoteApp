// Function to get CSRF token from cookie (for when you re-enable CSRF)
function getCookie(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(";").shift();
}

// Function to add a new note
function addNote(content) {
  fetch("/notes", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      // 'X-XSRF-TOKEN': getCookie('XSRF-TOKEN')  // Uncomment when CSRF is enabled
    },
    body: JSON.stringify({ content: content }),
    credentials: "include",
  })
    .then((response) => {
      if (!response.ok) {
        console.error("Response status:", response.status);
        console.error("Response headers:", response.headers);
        return response.text().then((text) => {
          throw new Error(`Failed to add note: ${text}`);
        });
      }
      return response.json();
    })
    .then((note) => {
      console.log("Note added:", note);
      addNoteToUI(note);
      document.getElementById("addNoteForm").reset();
    })
    .catch((error) => {
      console.error("Error:", error);
      showNotification("Failed to add note: " + error.message, "is-danger");
    });
}

// Function to edit a note
function editNote(id) {
  const noteElement = document.getElementById(`note-${id}`);
  const content = noteElement.querySelector(".content").textContent;
  document.getElementById("editNoteContent").value = content;
  currentEditId = id;
  document.getElementById("editModal").classList.add("is-active");
}

// Function to update a note
function updateNote() {
  const content = document.getElementById("editNoteContent").value;
  fetch(`/notes/${currentEditId}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      // 'X-XSRF-TOKEN': getCookie('XSRF-TOKEN')  // Uncomment when CSRF is enabled
    },
    body: JSON.stringify({ content: content }),
    credentials: "include",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Failed to update note");
      }
      return response.json();
    })
    .then((updatedNote) => {
      console.log("Note updated:", updatedNote);
      updateNoteInUI(updatedNote);
      closeEditModal();
    })
    .catch((error) => {
      console.error("Error:", error);
      showNotification("Failed to update note", "is-danger");
    });
}

// Function to delete a note
function deleteNote(id) {
  fetch(`/notes/${id}`, {
    method: "DELETE",
    headers: {
      // 'X-XSRF-TOKEN': getCookie('XSRF-TOKEN')  // Uncomment when CSRF is enabled
    },
    credentials: "include",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Failed to delete note");
      }
      return response.text();
    })
    .then(() => {
      console.log("Note deleted:", id);
      removeNoteFromUI(id);
    })
    .catch((error) => {
      console.error("Error:", error);
      showNotification("Failed to delete note", "is-danger");
    });
}

// Function to close the edit modal
function closeEditModal() {
  document.getElementById("editModal").classList.remove("is-active");
  currentEditId = null;
}

// Function to add a note to the UI
function addNoteToUI(note) {
  const notesList = document.getElementById("notesList");
  const noteElement = document.createElement("div");
  noteElement.className = "column is-one-third";
  noteElement.id = `note-${note.id}`;
  noteElement.innerHTML = `
        <div class="card">
            <div class="card-content">
                <p class="content">${note.content}</p>
            </div>
            <footer class="card-footer">
                <a href="#" class="card-footer-item" onclick="editNote(${note.id})">
                    <span class="icon"><i class="fas fa-edit"></i></span>
                    <span>Edit</span>
                </a>
                <a href="#" class="card-footer-item" onclick="deleteNote(${note.id})">
                    <span class="icon"><i class="fas fa-trash-alt"></i></span>
                    <span>Delete</span>
                </a>
            </footer>
        </div>
    `;
  notesList.appendChild(noteElement);
}

// Function to update a note in the UI
function updateNoteInUI(note) {
  const noteElement = document.getElementById(`note-${note.id}`);
  noteElement.querySelector(".content").textContent = note.content;
}

// Function to remove a note from the UI
function removeNoteFromUI(id) {
  const noteElement = document.getElementById(`note-${id}`);
  noteElement.remove();
}

// Function to show a notification
function showNotification(message, type) {
  const notification = document.createElement("div");
  notification.className = `notification ${type}`;
  notification.innerHTML = `
        <button class="delete"></button>
        ${message}
    `;
  document.body.appendChild(notification);
  notification.querySelector(".delete").addEventListener("click", () => {
    notification.remove();
  });
  setTimeout(() => {
    notification.remove();
  }, 3000);
}

// Event listeners
document.addEventListener("DOMContentLoaded", function () {
  const addNoteForm = document.getElementById("addNoteForm");
  if (addNoteForm) {
    addNoteForm.addEventListener("submit", function (e) {
      e.preventDefault();
      const content = this.elements["content"].value;
      addNote(content);
    });
  }

  const updateNoteButton = document.getElementById("updateNoteButton");
  if (updateNoteButton) {
    updateNoteButton.addEventListener("click", updateNote);
  }

  document.querySelectorAll(".close-modal").forEach((element) => {
    element.addEventListener("click", closeEditModal);
  });
});
