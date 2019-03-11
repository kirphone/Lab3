package control;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import game.Person;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandHandler {
    private CollectionManager manager;
    private boolean isStopped;

    public CommandHandler(CollectionManager collectionManager) {
        manager = new CollectionManager();
        if (collectionManager != null) manager = collectionManager;
        isStopped = false;
    }

    private String[] readCommand() {
        Scanner scanner = new Scanner(System.in);
        String command;

        try {
            System.out.println("Введите команду");
            command = scanner.nextLine();
        } catch (NoSuchElementException ex) {
            command = "stop";
        }

        /*if (fullCommand.length > 1) {
            while (fullCommand[1].contains(" ")) {
                fullCommand[1] = fullCommand[1].replaceAll(" ", " ");
            }
        }*/

        return command.trim().split(" ", 2);

    }

    private Person getElementFromJSON(Gson gson, String elementInString) throws ReadElementFromJsonException, JsonSyntaxException {
        Person element = gson.fromJson(elementInString, Person.class);
        if (element == null || element.getName() == null || element.getMutableSpeed() == null
                || element.getCurrentLocation() == null) {
            throw new ReadElementFromJsonException();
        }
        return element;
    }

    private String getKeyFromJSON(Gson gson, String stringInJson) throws ReadKeyFromJsonException, JsonSyntaxException {
        String key = gson.fromJson(stringInJson, String.class);
        if (key == null) {
            throw new ReadKeyFromJsonException();
        }
        return key;
    }

    private class ReadElementFromJsonException extends Exception {
        public String toString() {
            return "Ошибка, элемент задан неверно, возможно вы указали не все значения.\n" +
                    "Требуется указать следующие поля: name, speed, currentLoc.\n";
        }
    }

    private class ReadKeyFromJsonException extends Exception {
        public String toString() {
            return "Ошибка, задан пустой ключ. Коллекция не работает с пустыми ключами.\n";
        }
    }

    public void control() {
        while (!isStopped) {
            String[] fullCommand = readCommand();
            switch (fullCommand[0]) {
                case "insert":
                case "add_if_min":
                case "remove":
                case "remove_greater":
                case "remove_greater_key":
                    if (fullCommand.length == 1) {
                        System.out.println("Ошибка, команда " + fullCommand[0] + " должна иметь аргумент.");
                        continue;
                    }
                    Gson gson = new Gson();

                    switch (fullCommand[0]) {
                        case "remove":
                        case "remove_greater_key":
                            try {
                                String key = getKeyFromJSON(gson, fullCommand[0]);
                                switch (fullCommand[0]) {
                                    case "remove":
                                        manager.remove(key);
                                        break;
                                    case "remove_greater_key":
                                        manager.removeGreaterKey(key);
                                        break;
                                }
                            } catch (JsonSyntaxException ex) {
                                System.out.println("Ошибка, ключ задан неверно. Используйте формат JSON.");
                            } catch (ReadKeyFromJsonException ex) {
                                System.out.print(ex.toString());
                            }
                            break;
                        case "remove_greater":
                        case "add_if_min":
                            try {
                                Person element = getElementFromJSON(gson, fullCommand[1]);
                                switch (fullCommand[0]) {
                                    case "remove_greater":
                                        manager.removeGreater(element);
                                        break;
                                    case "add_if_min":
                                        manager.addIfMin(element);
                                        break;
                                }
                            } catch (JsonSyntaxException ex) {
                                System.out.println("Ошибка, элемент задан неверно. Используйте формат JSON.");
                            } catch (ReadElementFromJsonException ex) {
                                System.out.print(ex.toString());
                            }
                            break;
                        case "insert":
                            String[] args = fullCommand[1].split(" ", 2);
                            if (args.length == 1) {
                                System.out.println("Ошибка, команда " + fullCommand[0] + " должна иметь 2 аргумента.");
                                continue;
                            }
                            try {
                                String key = getKeyFromJSON(gson, args[0]);
                                Person element = getElementFromJSON(gson, args[1]);
                                manager.insert(key, element);
                            } catch (JsonSyntaxException ex) {
                                System.out.println("Ошибка, элемент задан неверно. Используйте формат JSON.");
                            } catch (ReadElementFromJsonException ex) {
                                System.out.print(ex.toString());
                            } catch (ReadKeyFromJsonException ex) {
                                System.out.print(ex.toString());
                            }
                            break;
                    }
                    break;
                case "info":
                    manager.info();
                    break;
                case "show":
                    manager.show();
                    break;
                case "stop":
                    manager.show();
                    isStopped = true;
                    manager.finishWork();
                    break;
                default:
                    System.out.println("Ошибка, Неизвестная команда.");
                    break;
            }
        }

    }
}
