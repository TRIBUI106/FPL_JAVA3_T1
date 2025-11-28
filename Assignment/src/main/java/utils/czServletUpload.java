package utils;

import java.io.*;
import java.nio.file.*;
import java.util.UUID;
import jakarta.servlet.http.Part;

public class czServletUpload {

	public static String saveFile(Part part, String uploadDir) throws IOException {
        if (part == null || part.getSize() == 0) return null;
        String submitted = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        String ext = "";
        int i = submitted.lastIndexOf('.');
        if (i > 0) ext = submitted.substring(i);
        String fileName = UUID.randomUUID().toString() + ext;
        File upload = new File(uploadDir);
        if (!upload.exists()) upload.mkdirs();
        part.write(uploadDir + File.separator + fileName);
        return fileName;
    }

    public static boolean deleteFile(String uploadDir, String fileName) {
        if (fileName == null) return false;
        File f = new File(uploadDir, fileName);
        if (f.exists()) return f.delete();
        return false;
    }
	
}
