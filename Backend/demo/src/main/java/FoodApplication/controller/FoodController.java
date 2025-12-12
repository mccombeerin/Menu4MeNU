package FoodApplication.controller;

import FoodApplication.model.Food;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    /**
     * GET /api/foods
     * Reads foods.xlsx from the resources folder and returns all rows as a list of Food objects.
     */
    @GetMapping
    public ResponseEntity<List<Food>> getAllFoods() {
        List<Food> foods = new ArrayList<>();

        // Name of the Excel file inside src/main/resources
        String excelFileName = "recipes.xlsx";

        try (InputStream is = new ClassPathResource(excelFileName).getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0); // First sheet

            // Assume first row is header, so start from row 1
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);

                if (row == null) {
                    continue; // skip empty row
                }

                String name = getCellStringValue(row.getCell(0));
                String mainNutrition = getCellStringValue(row.getCell(1));
                List<String> ingredients = parseCsvToList(getCellStringValue(row.getCell(2)));
                List<String> recipes = parseCsvToList(getCellStringValue(row.getCell(3)));
                List<String> recommendations = parseCsvToList(getCellStringValue(row.getCell(4)));
                List<String> tags = parseCsvToList(getCellStringValue(row.getCell(5)));

                Food food = new Food(
                        name,
                        mainNutrition,
                        ingredients,
                        recipes,
                        recommendations,
                        tags
                );

                foods.add(food);
            }

            return ResponseEntity.ok(foods);

        } catch (IOException e) {
            // If something goes wrong reading the file, return 500
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Safely get a String from a cell (handles nulls and different cell types).
     */
    private String getCellStringValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            // Convert numeric to string (e.g., for IDs, simple numbers)
            return String.valueOf(cell.getNumericCellValue());
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.FORMULA) {
            // Evaluate formula result as string if possible
            try {
                return cell.getStringCellValue().trim();
            } catch (IllegalStateException e) {
                try {
                    return String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException ex) {
                    return "";
                }
            }
        }
        return "";
    }

    /**
     * Parse a comma-separated string into a List<String>.
     * Empty or null becomes an empty list.
     */
    private List<String> parseCsvToList(String csv) {
        if (csv == null || csv.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String[] parts = csv.split(",");
        List<String> list = new ArrayList<>();
        Arrays.stream(parts)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .forEach(list::add);
        return list;
    }
}
