package me.theo.recipeapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import me.theo.recipeapp.services.impl.FilesRecipeServiceImpl;
import me.theo.recipeapp.services.impl.RecipeServiceImpl;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/files")
public class FilesRecipeController {
    private  final FilesRecipeServiceImpl filesRecipeServiceImpl;
    private final RecipeServiceImpl recipeService;

    public FilesRecipeController(FilesRecipeServiceImpl filesRecipeServiceImpl, RecipeServiceImpl recipeService) {
        this.filesRecipeServiceImpl = filesRecipeServiceImpl;
        this.recipeService = recipeService;
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
    @GetMapping( "/export/txt")
    @Operation(summary = "загрузка текстового файла",description = "загрузка рецепта в файл")
    public void downloadRecipe(HttpServletResponse response) throws IOException {
        ContentDisposition disposition = ContentDisposition.attachment()
                .name("data.txt")
                .build();
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, disposition.toString());
        response.setContentType("text/plain");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        recipeService.exportFileFromMemory(response.getWriter());
//
//
//        filesRecipeServiceImpl.cleanDataFile();
//        try {
//            filesRecipeServiceImpl.addFileToServer(file);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.ok().build();
    }

}
