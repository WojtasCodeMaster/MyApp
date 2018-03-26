package com.test.demo.profile;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.nio.ch.IOUtil;

import java.io.*;

@Controller
public class PictureUploadController {
    public static final Resource PICTURES_DIR = new FileSystemResource("./pictures");

    @RequestMapping("upload")
    public String uploadPage() {
        return "profile/uploadPage";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String onUpload(MultipartFile file, RedirectAttributes redirectAttr) throws IOException {
        if (file.isEmpty() || !isImage(file)){
            redirectAttr.addFlashAttribute("error","Niewłaściwy Plik. Załaduj plik z obrazem.");
                    return "redirect:/upload";
        }
        copyFileToPictures(file);
        return "profile/uploadPage";
        }

        private Resource copyFileToPictures(MultipartFile file) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        File tempFile = File.createTempFile("pic",fileExtension,PICTURES_DIR.getFile());
        try (InputStream in =file.getInputStream();
             OutputStream out = new FileOutputStream(tempFile)){
            IOUtils.copy(in,out);
        }
        return new FileSystemResource(tempFile);
    }
    private boolean isImage(MultipartFile file){
        return file.getContentType().startsWith("image");
    }

    private static String getFileExtension(String name) {
        return name.substring(name.lastIndexOf("."));
    }
}
