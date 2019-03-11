package control;

import com.google.gson.Gson;
import game.Person;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

class CollectionManager {
    private HashMap<String, Person> collection;
    private Date initDate;
    private File fileForIO;

    CollectionManager(File collectionFile) {
        this();
        if (collectionFile != null) {
            importFile(collectionFile);
            fileForIO = collectionFile;
        }
    }

    CollectionManager() {
        fileForIO = null;
        initDate = new Date();
        collection = new HashMap<>();
    }


    /**
     * Выводит информацию о коллекции
     */

    void info() {
        System.out.println("Коллекция имеет тип HashSet и содержит объекты класса Person");
        System.out.println("Коллекция инициализировалась на основе следующих данных: " + initDate);
        System.out.printf("Коллекция содержит %d элементов\n", collection.size());
    }

    /**
     * Удаляет элемент по ключу
     *
     * @param key : (Things) - Remove key
     */

    void remove(String key) {
        if (collection.remove(key) != null)
            System.out.println("Элемент удалён");
        else
            System.out.println("Коллекция не содержит данный элемент");
    }

    /**
     * Выводит все элементы коллекции.
     */

    public void show() {
        collection.forEach((key, value) -> System.out.println(value.toString()));
    }

    /**
     * Method removes collection's items which greater than argument thingsForCompare.
     *
     * @param element (Person) - Object of class Person
     */

    public void removeGreater(Person element) {
        int beginSize = collection.size();
        collection.values().removeIf(i -> i.compareTo(element) > 0);
        System.out.printf("Удалено %d элементов\n", beginSize - collection.size());
    }

    public void removeGreaterKey(String key) {
        int beginSize = collection.size();
        collection.keySet().removeIf(i -> i.compareTo(key) > 0);
        /*collection.forEach((k, obj) -> {
            if (k.compareTo(key) > 0) collection.remove(k);
        });*/
        //collection.values().removeIf(i -> i.compareTo(element) > 0);
        System.out.printf("Удалено %d элементов\n", beginSize - collection.size());
    }

    public void addIfMin(Person element){
        if(element.compareTo(Collections.min(collection.values())) < 0)
            insert(element.getName().toString(), element);
    }

    /**
     * Method adds item for storing collection.
     *
     * @param key     : (String) - Insert key
     * @param element : (Person) - Object of class Person
     */

    public void insert(String key, Person element) {
        Person previous = collection.put(key, element);
        System.out.println("Элемент добавлен");
        if (previous == null)
            System.out.println("Прежний элемент по данному ключу потерян");
    }
    /**
     * Method imports items for storing collection from file.
     * @param importFile:(java.io.File) - file for reading
     */

    public void importFile(File importFile) {
        try{
            if ((!(importFile.isFile()))) throw new FileNotFoundException("Ошибка. Указаный путь не ведёт к файлу.");
            if (!(importFile.exists()))
                throw new FileNotFoundException("Фаил коллекцией не найден. Добавьте элементы вручную или импортируйте из другого файла");
            if (!importFile.canRead()) throw new SecurityException("Доступ запрещён. Файл защищен от чтения");

            boolean res = readCSVFromFile(importFile);
            if (!res){
                System.out.println("Добавлены все элементы из файла");
            }else System.out.println("Ничего не добавлено, возможно импортируемая коллекция пуста, или элементы заданы неверно");
        }catch (FileNotFoundException | SecurityException ex){
            System.out.println(ex.getMessage());
        } catch (IOException ex){
            System.out.println("Непредвиденная ошибка ввода: " + ex.toString());
        }
    }

    /**
     * @param fileForRead:(java.io.File) - file for reading
     * @return string in format json (serialized object)
     * @throws IOException - throws Input-Output Exceptions
     */

    private boolean readCSVFromFile(File fileForRead) throws IOException {
        try(InputStreamReader reader = new InputStreamReader(new FileInputStream(fileForRead))) {
            List<String> allLines = new BufferedReader(reader).lines().collect(Collectors.toList());
            return new CSVReaderAndWriter().read(allLines, collection);
        }
    }

    /**
     * Завершает работу с коллекцией элементов, сохраняя ее в фаил из которого она была считана.
     * Если сохранение в исходный фаил не удалось, то сохранение происходит в фаил с уникальным названием.
     */

    void finishWork() {              //порядок в csv файле: name, speed, currentLoc
        File saveFile = (fileForIO != null) ? fileForIO : new File("");
        CSVReaderAndWriter csv = new CSVReaderAndWriter();
        try(OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(saveFile))){
            writer.write(csv.write(collection));
            writer.flush();
            System.out.println("Коллекция сохранена в файл " + saveFile.getAbsolutePath());
        }catch (IOException | NullPointerException e){
            saveFile = new File("saveFile" + new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss").format(new Date()) + ".txt");
            try(OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(saveFile))) {
                if (saveFile.createNewFile()) throw new IOException();
                writer.write(csv.write(collection));
                writer.flush();
                System.out.println("Коллекция сохранена в файл " + saveFile.getAbsolutePath());

            } catch (IOException ex){
                System.out.println("Сохранение коллекции не удалось");
            }
        }
    }
}
