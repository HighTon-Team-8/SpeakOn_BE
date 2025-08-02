package com.example.team8be.domain.material.service;

import com.example.team8be.domain.material.domain.Material;
import com.example.team8be.domain.material.domain.repository.MaterialRepository;
import com.example.team8be.domain.material.presentation.dto.request.MaterialRequest;
import com.example.team8be.domain.script.domain.Script;
import com.example.team8be.domain.script.domain.repository.ScriptRepository;
import com.example.team8be.domain.user.domain.User;
import com.example.team8be.domain.user.service.facade.UserFacade;
import com.example.team8be.infrastructure.openai.service.MaterialGPTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadMaterialService {

    private final MaterialGPTService gptService;
    private final MaterialRepository materialRepository;
    private final ScriptRepository scriptRepository;
    private final UserFacade userFacade;

    public Material execute(MultipartFile file, MaterialRequest request) {
        User user = userFacade.currentUser();

        try {
            String originalName = file.getOriginalFilename();
            String extension = originalName.substring(originalName.lastIndexOf(".")).toLowerCase();
            File tempFile = File.createTempFile("material-", extension);
            file.transferTo(tempFile);

            Material material = materialRepository.save(Material.builder()
                    .title(request.getTitle())
                    .fileUrl(tempFile.getAbsolutePath())
                    .build());

            List<String> slides = extractSlides(tempFile);
            int slideNum = 1;

            for (String slide : slides) {
                if (slide.isBlank()) continue;

                try {
                    String scriptText = gptService.generateSlideScript(slide);

                    scriptRepository.save(Script.builder()
                            .material(material)
                            .slideNumber(slideNum++)
                            .slideText(scriptText)
                            .build());

                } catch (IOException e) {
                    log.error("GPT 처리 중 오류 발생 - Material ID: {}, Slide {}",
                            material.getId(), slideNum, e);
                    slideNum++;
                }
            }

            return material;
        } catch (IOException e) {
            log.error("발표자료 처리 중 전체 오류 발생", e);
            throw new RuntimeException("발표자료 처리 중 오류 발생", e);
        }
    }

    private List<String> extractSlides(File file) throws IOException {
        String name = file.getName().toLowerCase();
        if (name.endsWith(".pptx")) {
            return extractSlidesFromPptx(file);
        } else if (name.endsWith(".pdf")) {
            return extractSlidesFromPdf(file);
        } else {
            throw new IllegalArgumentException("지원하지 않는 파일 형식: " + name);
        }
    }

    private List<String> extractSlidesFromPptx(File file) throws IOException {
        List<String> slides = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
             XMLSlideShow ppt = new XMLSlideShow(fis)) {

            for (XSLFSlide slide : ppt.getSlides()) {
                StringBuilder sb = new StringBuilder();
                for (XSLFShape shape : slide.getShapes()) {
                    if (shape instanceof XSLFTextShape textShape) {
                        sb.append(textShape.getText()).append("\n");
                    }
                }
                slides.add(sb.toString().trim());
            }
        }
        return slides;
    }

    private List<String> extractSlidesFromPdf(File file) throws IOException {
        List<String> slides = new ArrayList<>();
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            int pageCount = document.getNumberOfPages();
            for (int i = 1; i <= pageCount; i++) {
                stripper.setStartPage(i);
                stripper.setEndPage(i);
                String text = stripper.getText(document).trim();
                slides.add(text);
            }
        }
        return slides;
    }


}
