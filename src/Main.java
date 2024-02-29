import java.io.*;
import java.util.*;

class Goods implements Serializable {
    private String name;
    private List<String> categories;
    private double price;
    private int quantity;
    private String description;
    private String brand;

    public Goods(String name, List<String> categories, double price, int quantity, String description, String brand) {
        this.name = name;
        this.categories = categories;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public List<String> getCategories() {
        return categories;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}

class Store {
    private List<Goods> goodsList;

    public Store() {
        goodsList = new ArrayList<>();
    }

    public void addGoods(String name, List<String> categories, double price, int quantity, String description, String brand) {
        if (price <= 0 || quantity <= 0) {
            System.out.println("Ошибка: Цена и количество товара должны быть положительными числами.");
            return;
        }
        Goods goods = new Goods(name, categories, price, quantity, description, brand);
        goodsList.add(goods);
    }

    public List<Goods> findGoodsByCategory(String category) {
        List<Goods> foundGoods = new ArrayList<>();
        for (Goods goods : goodsList) {
            if (goods.getCategories().contains(category)) {
                foundGoods.add(goods);
            }
        }
        return foundGoods;
    }

    public List<Goods> displayAllGoods() {
        return goodsList;
    }

    public void editGoods(int index, String name, List<String> categories, double price, int quantity, String description, String brand) {
        if (index < 0 || index >= goodsList.size()) {
            System.out.println("Ошибка: Недопустимый индекс товара.");
            return;
        }
        Goods goods = goodsList.get(index);
        goods.setName(name);
        goods.setCategories(categories);
        goods.setPrice(price);
        goods.setQuantity(quantity);
        goods.setDescription(description);
        goods.setBrand(brand);
    }

    public void removeGoods(int index) {
        if (index < 0 || index >= goodsList.size()) {
            System.out.println("Ошибка: Недопустимый индекс товара.");
            return;
        }
        goodsList.remove(index);
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(goodsList);
            System.out.println("Данные успешно сохранены в файл " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных в файл.");
            e.printStackTrace();
        }
    }
    public List<Goods> filterGoodsByCategory(String category) {
        List<Goods> filteredGoods = new ArrayList<>();
        for (Goods goods : goodsList) {
            if (goods.getCategories().contains(category)) {
                filteredGoods.add(goods);
            }
        }
        return filteredGoods;
    }
    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            goodsList = (List<Goods>) ois.readObject();
            System.out.println("Данные успешно загружены из файла " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при загрузке данных из файла.");
            e.printStackTrace();
        }
    }
}


public class Main {
    public static void main(String[] args) {
        Store store = new Store();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Добавить товар");
            System.out.println("2. Поиск товара по категории");
            System.out.println("3. Показать все товары");
            System.out.println("4. Редактировать товар");
            System.out.println("5. Удалить товар");
            System.out.println("6. Сохранить данные в файл");
            System.out.println("7. Загрузить данные из файла");
            System.out.println("8. Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            switch (choice) {
                case 1:
                    // Добавление товара
                    System.out.print("Введите название товара: ");
                    String name = scanner.nextLine();
                    System.out.print("Введите категории товара через запятую: ");
                    String[] categoriesArray = scanner.nextLine().split(",");
                    List<String> categories = Arrays.asList(categoriesArray);
                    System.out.print("Введите цену товара: ");
                    double price = scanner.nextDouble();
                    System.out.print("Введите количество товара: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Очистка буфера
                    System.out.print("Введите описание товара: ");
                    String description = scanner.nextLine();
                    System.out.print("Введите бренд товара: ");
                    String brand = scanner.nextLine();
                    store.addGoods(name, categories, price, quantity, description, brand);
                    break;
                case 2:
                    // Поиск товара по категории
                    System.out.print("Введите категорию товара для поиска: ");
                    String searchCategory = scanner.nextLine();
                    List<Goods> foundGoods = store.findGoodsByCategory(searchCategory);
                    if (!foundGoods.isEmpty()) {
                        System.out.println("Найденные товары:");
                        for (Goods goods : foundGoods) {
                            System.out.println(goods);
                        }
                    } else {
                        System.out.println("Товары в данной категории не найдены.");
                    }
                    break;
                case 3:
                    // Показать все товары
                    System.out.println("Все товары:");
                    List<Goods> allGoods = store.displayAllGoods();
                    for (Goods goods : allGoods) {
                        System.out.println(goods);
                    }
                    break;
                case 4:
                    // Редактировать товар
                    System.out.print("Введите индекс товара для редактирования: ");
                    int editIndex = scanner.nextInt();
                    if (editIndex >= 0 && editIndex < store.displayAllGoods().size()) {
                        scanner.nextLine(); // Очистка буфера
                        System.out.print("Введите новое название товара: ");
                        String newName = scanner.nextLine();
                        System.out.print("Введите новые категории товара через запятую: ");
                        String[] newCategoriesArray = scanner.nextLine().split(",");
                        List<String> newCategories = Arrays.asList(newCategoriesArray);
                        System.out.print("Введите новую цену товара: ");
                        double newPrice = scanner.nextDouble();
                        System.out.print("Введите новое количество товара: ");
                        int newQuantity = scanner.nextInt();
                        scanner.nextLine(); // Очистка буфера
                        System.out.print("Введите новое описание товара: ");
                        String newDescription = scanner.nextLine();
                        System.out.print("Введите новый бренд товара: ");
                        String newBrand = scanner.nextLine();
                        store.editGoods(editIndex, newName, newCategories, newPrice, newQuantity, newDescription, newBrand);
                        System.out.println("Товар успешно отредактирован.");
                    } else {
                        System.out.println("Недопустимый индекс товара.");
                    }
                    break;
                case 5:
                    // Удалить товар
                    System.out.print("Введите индекс товара для удаления: ");
                    int deleteIndex = scanner.nextInt();
                    if (deleteIndex >= 0 && deleteIndex < store.displayAllGoods().size()) {
                        store.removeGoods(deleteIndex);
                        System.out.println("Товар успешно удален.");
                    } else {
                        System.out.println("Недопустимый индекс товара.");
                    }
                    break;
                case 6:
                    // Сохранить данные в файл
                    System.out.print("Введите имя файла для сохранения данных: ");
                    String saveFileName = scanner.nextLine();
                    store.saveToFile(saveFileName);
                    break;
                case 7:
                    // Загрузить данные из файла
                    System.out.print("Введите имя файла для загрузки данных: ");
                    String loadFileName = scanner.nextLine();
                    store.loadFromFile(loadFileName);
                    break;
                case 8:
                    // Выход из программы
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Некорректный ввод. Пожалуйста, выберите действие от 1 до 8.");
            }
        }
    }
}