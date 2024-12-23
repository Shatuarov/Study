// Подсистема бронирования номеров
class RoomBookingSystem {
    public void bookRoom(String roomType) {
        System.out.println("Забронирован номер типа: " + roomType);
    }

    public void cancelBooking(String roomType) {
        System.out.println("Бронирование номера типа " + roomType + " отменено.");
    }
}

// Подсистема ресторана
class RestaurantSystem {
    public void bookTable(int guests) {
        System.out.println("Забронирован стол на " + guests + " гостей.");
    }

    public void orderFood(String dish) {
        System.out.println("Заказано блюдо: " + dish);
    }
}

// Подсистема управления мероприятиями
class EventManagementSystem {
    public void bookConferenceRoom(String roomType, int attendees) {
        System.out.println("Забронирован конференц-зал для " + attendees + " участников.");
    }

    public void orderEquipment(String equipment) {
        System.out.println("Заказано оборудование: " + equipment);
    }
}

// Подсистема службы уборки
class CleaningService {
    public void scheduleCleaning(String roomType) {
        System.out.println("Назначена уборка для номера типа: " + roomType);
    }

    public void cleanRoom(String roomType) {
        System.out.println("Выполнена уборка номера типа: " + roomType);
    }
}

// Подсистема вызова такси
class TaxiService {
    public void orderTaxi() {
        System.out.println("Такси заказано.");
    }
}


class HotelFacade {
    private RoomBookingSystem roomBookingSystem;
    private RestaurantSystem restaurantSystem;
    private EventManagementSystem eventManagementSystem;
    private CleaningService cleaningService;
    private TaxiService taxiService;

    public HotelFacade() {
        roomBookingSystem = new RoomBookingSystem();
        restaurantSystem = new RestaurantSystem();
        eventManagementSystem = new EventManagementSystem();
        cleaningService = new CleaningService();
        taxiService = new TaxiService();
    }

    public void bookRoomWithServices(String roomType, String dish) {
        roomBookingSystem.bookRoom(roomType);
        restaurantSystem.orderFood(dish);
        cleaningService.scheduleCleaning(roomType);
        System.out.println("Номер забронирован с услугами ресторана и уборки.");
    }

    public void organizeEventWithRoomsAndEquipment(String roomType, int attendees, String equipment) {
        eventManagementSystem.bookConferenceRoom(roomType, attendees);
        roomBookingSystem.bookRoom("Double");
        eventManagementSystem.orderEquipment(equipment);
        System.out.println("Мероприятие организовано с бронированием номеров и оборудованием.");
    }

    public void bookTableWithTaxi(int guests) {
        restaurantSystem.bookTable(guests);
        taxiService.orderTaxi();
        System.out.println("Стол забронирован, такси заказано.");
    }
}


public class Main {
    public static void main(String[] args) {
        HotelFacade hotelFacade = new HotelFacade();

        // Сценарии использования фасада
        hotelFacade.bookRoomWithServices("Single", "Паста");
        hotelFacade.organizeEventWithRoomsAndEquipment("Конференц-зал", 50, "Проектор");
        hotelFacade.bookTableWithTaxi(4);
    }
}


//Задача 2: Паттерн "Компоновщик" для корпоративной иерархии
import java.util.ArrayList;
import java.util.List;

abstract class OrganizationComponent {
    protected String name;

    public OrganizationComponent(String name) {
        this.name = name;
    }

    public abstract void displayInfo();
    public abstract int getBudget();
    public abstract int getEmployeeCount();
}


// Класс для сотрудника
class Employee extends OrganizationComponent {
    private String position;
    private int salary;

    public Employee(String name, String position, int salary) {
        super(name);
        this.position = position;
        this.salary = salary;
    }

    @Override
    public void displayInfo() {
        System.out.println("Сотрудник: " + name + ", Должность: " + position + ", Зарплата: " + salary);
    }

    @Override
    public int getBudget() {
        return salary;
    }

    @Override
    public int getEmployeeCount() {
        return 1;
    }
}

// Класс для отдела
class Department extends OrganizationComponent {
    private List<OrganizationComponent> components = new ArrayList<>();

    public Department(String name) {
        super(name);
    }

    public void addComponent(OrganizationComponent component) {
        components.add(component);
    }

    public void removeComponent(OrganizationComponent component) {
        components.remove(component);
    }

    @Override
    public void displayInfo() {
        System.out.println("Отдел: " + name);
        for (OrganizationComponent component : components) {
            component.displayInfo();
        }
    }

    @Override
    public int getBudget() {
        int totalBudget = 0;
        for (OrganizationComponent component : components) {
            totalBudget += component.getBudget();
        }
        return totalBudget;
    }

    @Override
    public int getEmployeeCount() {
        int totalEmployees = 0;
        for (OrganizationComponent component : components) {
            totalEmployees += component.getEmployeeCount();
        }
        return totalEmployees;
    }
}


public class OrganizationTest {
    public static void main(String[] args) {
        Employee emp1 = new Employee("Иван", "Разработчик", 3000);
        Employee emp2 = new Employee("Ольга", "Тестировщик", 2500);
        Employee emp3 = new Employee("Мария", "Менеджер", 4000);

        Department devDepartment = new Department("Отдел разработки");
        devDepartment.addComponent(emp1);
        devDepartment.addComponent(emp2);

        Department hrDepartment = new Department("Отдел HR");
        hrDepartment.addComponent(emp3);

        Department company = new Department("Компания");
        company.addComponent(devDepartment);
        company.addComponent(hrDepartment);

        System.out.println("Структура компании:");
        company.displayInfo();
        System.out.println("Общий бюджет компании: " + company.getBudget());
        System.out.println("Общее количество сотрудников: " + company.getEmployeeCount());
    }
}
