package finalproject.data;

import lombok.Data;

// Конструктор для объявления
@Data
public class Announcement {
    private String name; //имя
    private String description; //описание товара
    private int price; //стоимость
    private String photoPath; //путь к фото
}