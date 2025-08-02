package com.example.team8be.domain.material.service;

import com.example.team8be.domain.material.domain.Material;
import com.example.team8be.domain.material.domain.repository.MaterialRepository;
import com.example.team8be.domain.material.presentation.dto.MaterialRequest;
import com.example.team8be.domain.script.domain.Script;
import com.example.team8be.domain.script.domain.repository.ScriptRepository;
import com.example.team8be.infrastructure.openai.service.MaterialGPTService;
import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadMaterialService {

    private final MaterialGPTService gptService;
    private final MaterialRepository materialRepository;
    private final ScriptRepository scriptRepository;

    public Material execute(MultipartFile file, MaterialRequest request) {
        try {
            String originalName = file.getOriginalFilename();
            String extension = originalName.substring(originalName.lastIndexOf(".")).toLowerCase();
            if (!extension.equals(".pdf") && !extension.equals(".pptx")) {
                throw new IllegalArgumentException("지원하지 않는 파일 형식입니다: " + extension);
            }

            File tempFile = File.createTempFile("material-", extension);
            file.transferTo(tempFile);

            Material material = Material.builder()
                    .title(request.getTitle())
                    .fileUrl(tempFile.getAbsolutePath())
                    .build();
            materialRepository.save(material);

            String content = extractText(tempFile);

            //GPT → 슬라이드별 대본 생성
            List<String> slideScripts = gptService.generateSlideScript(content);

            int slideNumber = 1;
            for (String scriptText : slideScripts) {
                Script script = Script.builder()
                        .slideNumber(slideNumber++)
                        .slideText(scriptText)
                        .build();
                scriptRepository.save(script);
            }

            return material;
        } catch (IOException e) {
            throw new RuntimeException("발표자료 처리 중 오류 발생", e);
        }
    }

    private String extractText(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            AutoDetectParser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler(-1); // 길이 제한 없음
            Metadata metadata = new Metadata();
            parser.parse(fis, handler, metadata, new ParseContext());
            return handler.toString();
        } catch (TikaException | SAXException e) {
            throw new IOException("파일 파싱 실패", e);
        }
    }


}
