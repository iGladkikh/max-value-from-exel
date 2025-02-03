package com.igladkikh.parser.service;

import com.igladkikh.parser.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

@Slf4j
@Service
public class ParserServiceImpl implements ParserService {

    @Override
    public Long parseMaxLongWithPosition(String fileLocation, int position) throws IOException {
        // используем Set для исключения дубликатов значений
        SortedSet<Long> data = new TreeSet<>(Comparator.reverseOrder());

        try (FileInputStream inputStream = new FileInputStream(fileLocation);
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                int cellNum = row.getFirstCellNum();
                CellType cellType = row.getCell(cellNum).getCellType();
                if (cellType == CellType.NUMERIC) {
                    long cellValue = (long) row.getCell(cellNum).getNumericCellValue();
                    data.add(cellValue);

                    log.debug("Строка {}, значение {}", i, cellValue);

                    // удаляем меньший по значению элемент
                    if (data.size() > position) {
                        data.removeLast();
                    }
                } else {
                    throw new BadRequestException("Invalid cell type.");
                }
            }
        }

        if (data.isEmpty()) {
            throw new BadRequestException("Invalid file data.");
        }
        if (data.size() < position) {
            throw new BadRequestException("Invalid place position.");
        }

        // искомый элемент - последний
        return data.last();
    }
}
