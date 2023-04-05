package me.theo.recipeapp.services;

import java.io.File;

public interface FilesService {
     boolean saveToFile(String json);
     String readFromFile();
     boolean cleanDataFile();
     File getDataFile();

}
