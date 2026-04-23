package com.example.llmdamo;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class LlmDamoApplicationTests {

    @Resource
    private EmbeddingModel embeddingModel;

    @Test
    void contextLoads() {
    }

    @Test
    void testPDF() {
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader("classpath:/深入MySQL实战（阿里）.pdf",
                PdfDocumentReaderConfig.builder()
                        .withPageTopMargin(0)
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                .withNumberOfTopTextLinesToDelete(0)
                                .build())
                        .withPagesPerDocument(1)
                        .build());


        TokenTextSplitter splitter = new TokenTextSplitter(1000, 400, 10, 100000, true);
        splitter.apply(pdfReader.read()).forEach(document -> {
            System.out.println(Arrays.toString(embeddingModel.embed(document)));
        });
    }

}
