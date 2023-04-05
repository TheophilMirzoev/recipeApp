package me.theo.recipeapp.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesService {
    @Value("${path.to.data.file}")
    private String dataFilePath;

    @Value("${name.of.data.file}")
    private String dataFileName;

    public boolean saveToFile(String json) {
        cleanDataFile();
        try {
            Files.writeString(Path.of(dataFilePath, dataFileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String readFromFile() {
           try {
               return Files.readString(Path.of(dataFilePath, dataFileName));
           } catch (IOException e) {
               e.printStackTrace();
               return null;
           }
    }
    private boolean cleanDataFile() {
        try {
            Path path = Path.of(dataFilePath, dataFileName);     //создаем переменную path которая равна Path.of(dataFilePath) для того
                                                   //что-бы сделать код ниже более коротким и аккуратным
            Files.deleteIfExists(path);
            Files.createFile(path);
//            Files.deleteIfExists(Path.of(dataFilePath));
//            Files.createFile(Path.of(dataFilePath));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}
