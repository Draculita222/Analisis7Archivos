package mati.a7a.results;

import mati.a7a.files.IFile;

import java.util.Optional;

public record FileValidationError (IFile file, String message, Optional<Integer> line) {
}
