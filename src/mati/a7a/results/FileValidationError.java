package mati.a7a.results;

import mati.a7a.files.IFile;

import java.util.Optional;

public class FileValidationError {
	
	public FileValidationError(IFile file, String message, Optional<Integer> line)
	{
		this.file = file; this.message = message; this.line = line;
	}
	public IFile file; public String message; public Optional<Integer> line;
}
