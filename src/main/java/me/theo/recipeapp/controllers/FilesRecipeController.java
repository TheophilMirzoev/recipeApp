package me.theo.recipeapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "файл рецептов",description = "скачать файл со всеми рецептами")
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
    @DeleteMapping()
    public ResponseEntity<Void> deleteRecipe() {
        try {
            filesRecipeServiceImpl.cleanDataFile();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "загрузка из файла",description = "загрузка рецепта из файла")
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
    @PostMapping(value = "/import/txt", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "загрузка из файла",description = "загрузка рецепта из файла txt")
    public ResponseEntity<Void> uploadFileTxt(@RequestParam MultipartFile file) {
        filesRecipeServiceImpl.cleanDataFile();
        try {
            filesRecipeServiceImpl.addFileToServer(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }


    //И создать новый эндпоинт, который позволит загрузить файл с расширением .txt.
    //
    //
    //Внутри эндпоинта тебе нужно вызвать метод сервиса (который ты напишешь) который вернёт файл.
    //
    //В этот файл тебе нужно записать рецепты в удобном для чтения виде.
    //
    //
    //Подправь эти неточности и присылай работу еще раз!
}
