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

    // Declare a static final field for the base path to avoid creating it every time
    private static final Path BASE_PATH = Paths.get("src", "main", "resources"); // Use varargs constructor instead of multiple resolve calls

    /*
    @dev: Gets the absolute path in this computer of a given image (in the resources folder)
    @param: String with th name of the subfolder where the image is located
            String with th name of the desired image
    @returns: String of the absolute path in this computer of the given image
    @author: Andres Masis
     */
    public String getSpecificImagePath(String folder, String imageName) {
        // Adds the folder and image name to the base path
        Path imagePath = BASE_PATH.resolve(folder).resolve(imageName + ".png"); // Use resolve method instead of concatenation

        // Returns the absolute path in this computer as a String
        return imagePath.toAbsolutePath().toString();
    }
}
