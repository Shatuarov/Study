// Класс телевизора
class TV {
    public void turnOn() {
        System.out.println("Телевизор включен.");
    }

    public void turnOff() {
        System.out.println("Телевизор выключен.");
    }

    public void setChannel(int channel) {
        System.out.println("Канал установлен на " + channel);
    }
}

// Класс аудиосистемы
class AudioSystem {
    public void turnOn() {
        System.out.println("Аудиосистема включена.");
    }

    public void turnOff() {
        System.out.println("Аудиосистема выключена.");
    }

    public void setVolume(int volume) {
        System.out.println("Громкость установлена на " + volume);
    }
}

// Класс DVD-проигрывателя
class DVDPlayer {
    public void play() {
        System.out.println("Проигрывание DVD начато.");
    }

    public void pause() {
        System.out.println("Проигрывание DVD приостановлено.");
    }

    public void stop() {
        System.out.println("Проигрывание DVD остановлено.");
    }
}

// Класс игровой консоли
class GameConsole {
    public void turnOn() {
        System.out.println("Игровая консоль включена.");
    }

    public void startGame(String game) {
        System.out.println("Запуск игры: " + game);
    }
}
//Реализация класса

class HomeTheaterFacade {
    private TV tv;
    private AudioSystem audioSystem;
    private DVDPlayer dvdPlayer;
    private GameConsole gameConsole;

    public HomeTheaterFacade(TV tv, AudioSystem audioSystem, DVDPlayer dvdPlayer, GameConsole gameConsole) {
        this.tv = tv;
        this.audioSystem = audioSystem;
        this.dvdPlayer = dvdPlayer;
        this.gameConsole = gameConsole;
    }

    public void watchMovie() {
        tv.turnOn();
        audioSystem.turnOn();
        dvdPlayer.play();
        System.out.println("Приятного просмотра фильма!");
    }

    public void stopMovie() {
        dvdPlayer.stop();
        audioSystem.turnOff();
        tv.turnOff();
        System.out.println("Просмотр фильма завершен.");
    }

    public void playGame(String game) {
        tv.turnOn();
        gameConsole.turnOn();
        gameConsole.startGame(game);
        System.out.println("Приятной игры!");
    }

    public void listenToMusic() {
        tv.turnOn();
        audioSystem.turnOn();
        audioSystem.setVolume(30);
        System.out.println("Воспроизведение музыки начато.");
    }
}
//Клиентсий код
public class Main {
    public static void main(String[] args) {
        TV tv = new TV();
        AudioSystem audioSystem = new AudioSystem();
        DVDPlayer dvdPlayer = new DVDPlayer();
        GameConsole gameConsole = new GameConsole();

        HomeTheaterFacade homeTheater = new HomeTheaterFacade(tv, audioSystem, dvdPlayer, gameConsole);

        // Сценарии
        homeTheater.watchMovie();  // просмотр фильма
        homeTheater.stopMovie();   // завершение просмотра
        homeTheater.playGame("Super Mario");  // запуск игры
        homeTheater.listenToMusic();  // прослушивание музыки
    }
}



//Задача 2: Паттерн "Компоновщик" для файловой системы

abstract class FileSystemComponent {
    protected String name;

    public FileSystemComponent(String name) {
        this.name = name;
    }

    public abstract void display();
    public abstract int getSize();
}
//Реализация классов File и Directory
// Класс для файла
class File extends FileSystemComponent {
    private int size;

    public File(String name, int size) {
        super(name);
        this.size = size;
    }

    @Override
    public void display() {
        System.out.println("Файл: " + name + ", Размер: " + size + "KB");
    }

    @Override
    public int getSize() {
        return size;
    }
}

// Класс для папки
import java.util.ArrayList;
import java.util.List;

class Directory extends FileSystemComponent {
    private List<FileSystemComponent> components = new ArrayList<>();

    public Directory(String name) {
        super(name);
    }

    public void addComponent(FileSystemComponent component) {
        if (!components.contains(component)) {
            components.add(component);
        } else {
            System.out.println("Компонент уже существует в папке " + name);
        }
    }

    public void removeComponent(FileSystemComponent component) {
        components.remove(component);
    }

    @Override
    public void display() {
        System.out.println("Папка: " + name);
        for (FileSystemComponent component : components) {
            component.display();
        }
    }

    @Override
    public int getSize() {
        int totalSize = 0;
        for (FileSystemComponent component : components) {
            totalSize += component.getSize();
        }
        return totalSize;
    }
}
//Клиентский код
public class FileSystemTest {
    public static void main(String[] args) {
        File file1 = new File("File1.txt", 100);
        File file2 = new File("File2.txt", 200);
        File file3 = new File("File3.txt", 300);

        Directory folder1 = new Directory("Folder1");
        Directory folder2 = new Directory("Folder2");
        Directory rootFolder = new Directory("Root");

        folder1.addComponent(file1);
        folder1.addComponent(file2);

        folder2.addComponent(file3);
        rootFolder.addComponent(folder1);
        rootFolder.addComponent(folder2);

        rootFolder.display();
        System.out.println("Общий размер: " + rootFolder.getSize() + "KB");
    }
}
