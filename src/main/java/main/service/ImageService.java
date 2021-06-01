package main.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.imageio.ImageIO;
import main.data.response.ResultResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

  @Value("${upload.path}")
  public String uploadPath;

  @Value("${upload.url}")
  public String uploadUrl;

  public Object uploadImage(MultipartFile image) throws IOException {

    if (image.getSize() > 1048576) {

      ResultResponse response = new ResultResponse();
      Map<String, String> errors = new HashMap<>();
      response.setErrors(errors);
      errors.put("image", "Размер файла превышает допустимый размер");
      return response;
    }



    String originalFilename = image.getOriginalFilename();
    String storeName = UUID.randomUUID().toString() + "_" + originalFilename;

    String folderName1 = (RandomStringUtils.randomAlphabetic(4)).toLowerCase();
    String folderName2 = (RandomStringUtils.randomAlphabetic(4)).toLowerCase();
    String folderName3 = (RandomStringUtils.randomAlphabetic(4)).toLowerCase();

    File f1 = new File(uploadPath + "/" + folderName1);
    f1.mkdir();
    File f2 = new File(uploadPath + "/" + folderName1+ "/" + folderName2);
    f2.mkdir();
    File f3 = new File(uploadPath + "/" + folderName1 + "/" + folderName2 + "/" + folderName3);
    f3.mkdir();

    // где картирка хранится на сервере

    File storeDestination = new File(f3 + "/" + storeName);
    int originalFileLastDot = (originalFilename != null) ? originalFilename.lastIndexOf(".") : -1;
    String type = (originalFilename != null) ? originalFilename.substring(originalFileLastDot + 1) : "";

    resizeAndSaveImage(image, type,  storeDestination, 100, 100);

    // как фронт будет обращаться к картинке

    String responseUrl = uploadUrl  + folderName1 + "/" + folderName2 + "/" + folderName3 + "/" + storeName ;

    return responseUrl;
  }

  protected void resizeAndSaveImage(MultipartFile image, String type, File storeDestination, int width, int height)
      throws IOException {

    byte[] photoBytes = image.getBytes();

    BufferedImage bufferedImageOriginal = ImageIO.read(new ByteArrayInputStream(photoBytes));
    BufferedImage bufferedImageResized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics2D = bufferedImageResized.createGraphics();
    graphics2D.drawImage(bufferedImageOriginal, 0, 0, width, height, null);
    graphics2D.dispose();

    ImageIO.write(bufferedImageResized, type, storeDestination);

  }

}
