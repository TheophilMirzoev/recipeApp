package me.theo.recipeapp.controllers;

import me.theo.recipeapp.services.impl.FilesRecipeServiceImpl;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files")
public class FilesRecipeController {
    private  final FilesRecipeServiceImpl filesRecipeServiceImpl;

    public FilesRecipeController(FilesRecipeServiceImpl filesRecipeServiceImpl) {
        this.filesRecipeServiceImpl = filesRecipeServiceImpl;
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> downloadDataFile() throws FileNotFoundException {
        File file = filesRecipeServiceImpl.getDataFile();
        if (file.exists()) {
           InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Recipe.json\"")
                    .contentLength(file.length())
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file) {
       filesRecipeServiceImpl.cleanDataFile();
       File dataFile = filesRecipeServiceImpl.getDataFile();
       try (FileOutputStream fos = new FileOutputStream(dataFile)){
           IOUtils.copy(file.getInputStream(), fos);
           return ResponseEntity.ok().build();
       } catch (IOException e) {
           e.printStackTrace();
       }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
