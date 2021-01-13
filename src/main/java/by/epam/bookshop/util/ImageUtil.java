package by.epam.bookshop.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public class ImageUtil {

    private static final String EXTENSION = "jpg";

    public static void main(String[] args) {
        File file = new File("src/main/resources/library2.jpg");
        try {
            System.out.println(resizeImage(file, 200, 170).getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {

        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    public static File resizeImage(File file, int targetWidth, int targetHeight) throws IOException {
        BufferedImage image = ImageIO.read(file);
        String filename = file.getAbsolutePath()
                .substring(0, file.getAbsolutePath().lastIndexOf(".") - 1)
                .concat("_resized.").concat(EXTENSION);
        File newFile = new File(filename);
        if (newFile.exists()) {
            newFile.delete();
        }
        ImageIO.write(resizeImage(image, targetWidth,  targetHeight), EXTENSION, newFile);
        return newFile;
    }

    public static Function<File, File> getFunction(int targetWidth, int targetHeight) {
         Function<File, File> function = new Function<File, File>() {
             @Override
             public File apply(File s) {
                 try {
                     return resizeImage(s, targetWidth,  targetHeight);
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }
             }
         };
         return function;
    }
}
