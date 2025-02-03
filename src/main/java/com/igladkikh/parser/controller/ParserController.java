package com.igladkikh.parser.controller;

import com.igladkikh.parser.service.ParserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@Tag(name = "Parser-controller")
@Validated
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ParserController {
    private final ParserService service;

    @Operation(summary = "Поиск n-го максимального значения в файле .xlsx",
            description = "Принимает 2 аргумента: путь к файлу и уровень искомого максимального значения.<br/>" +
                    "Пример файла - <b>tmp/sample.xlsx</b>"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Возвращается n-е максимальное значение",
                    responseCode = "200",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Long.class))
                    }
            )
    })
    @GetMapping(value = "/parser")
    public ResponseEntity<Long> parseMaxValueFromXlsxFile(@RequestParam String fileLocation,
                                                          @RequestParam(required = false, defaultValue = "1")
                                                          @Positive int position) throws IOException {
        log.info("Find max value in file {} at position {} ", fileLocation, position);
        return ResponseEntity.ok()
                .body(service.parseMaxLongWithPosition(fileLocation, position));
    }
}
