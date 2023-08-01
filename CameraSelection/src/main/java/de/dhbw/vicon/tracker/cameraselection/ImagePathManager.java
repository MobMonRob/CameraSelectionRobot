/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.dhbw.vicon.tracker.cameraselection;

/**
 *
 * @author rahm-
 */
import java.nio.file.Path;
import java.nio.file.Paths;


/*
This module gets the path of the resource(image) folder of the project
Declaring into the code a String and write the absolute path there is not an option
If so, the path will work only in that computer
Relative paths are necessary to dinamically export it to other computers
However, the SikuliX library was having problems with the relatives path
This code was necessary to "calculate" the absolute path but depending on the computer

The class is abstract because each folder is handled by other classes
The other classes will be concrete
This class just provides the basic services
The concrete classes know how to handle each folder
*/
public abstract class ImagePathManager {
    
    /*
    @dev: Gets the absolute path in this computer of a given image (in the resources folder)
    @param: String with th name of the subfolder where the image is located
            String with th name of the desired image
    @returns: String of the absolute path in this computer of the given image
    @author: Andres Masis
    */
    
    public String getSpecificImagePath(String folder, String imageName){
        // Gets the resources folder absolute path for this computer
        String basePath = getImagesFolderPath();
        
        // Adds the folder to the absolute path until resources
        String folderPath = basePath+"\\"+folder;
        
        // Adds the image name to the absolute path until the given folder
        String imagePath = folderPath+"\\"+imageName+".PNG";  // Do not forget to add the .PNG extension
        
        // Returns the absolute path in this computer for the given image
        return imagePath;
    }
    
    /*
    @dev: Gets the absolute path in this computer of the resources folder (where the images are located)
    @param: None
    @returns: String of the absolute path of the resources folder
    @author: Andres Masis
    */
    private String getImagesFolderPath() {
        // Gets the absolute path of the working directory
        String currentPath = getCurrentFilePath();
        
        // This is necessary to reach where the images are stored
        String imagesFolderPath = currentPath+"src\\main\\resources";
        
        // Returns the direct absolute path to the resources folder in this computer
        return imagesFolderPath;
    }

    /*
    @dev: Gets the absolute path of the directory of the current file depending on the machine that is running this
    @param: None
    @returns: String of the absolute path of this directory
    @author: Andres Masis
    */
    private String getCurrentFilePath() {
        // Get the absolute path of the current working directory for the computer running this
        String currentWorkingDir = System.getProperty("user.dir");

        // Get the class name with the package and its path
        String classPath = ImagePathManager.class.getName();

        // Replace dots with double backslashes to convert package name to file path
        String packagePath = classPath.replace(".", "\\");

        // Get the path of the current file, merges both paths to get what is needed
        String filePath = currentWorkingDir + "\\" + packagePath;

        // Convert the file path string to a Path object, necessary for Path specific actions
        Path path = Paths.get(filePath);

        // Return the canonical path to handle any symbolic links, etc with .normalize()
        String normalizedPath = path.normalize().toString();
        
        // The normalized path has some extra data not needed, calls the removeExtra() to solve that
        String fixedPath = removeExtra(normalizedPath);
        
        // Returns the path of the current directory in the format needed
        return fixedPath;
    }
    
    /*
    @dev: This receives a path (very specific to this project) and removes whatever is after a certain delimeter
    @param: The string(path) that we want to cut
    @returns: String of the shorter path
    @author: Andres Masis
    */
    private String removeExtra(String longPath){
        // Gets the index of the point we want to cut
        int index = longPath.indexOf("de");  // "de" is the best delimeter, after that point we don´t need more
        
        // Cuts with the given index
        String resultString = longPath.substring(0, index);
        
        // Return the first part of the path, before the delimeter
        return resultString;
    }
    
}
