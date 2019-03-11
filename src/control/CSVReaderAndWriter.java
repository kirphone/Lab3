package control;

import game.Animal;
import game.FIO;
import game.Location;
import game.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CSVReaderAndWriter {    //порядок в csv файле: name, speed, currentLoc

    String write(HashMap<String, Person> collection){
        StringBuilder csvFormat = new StringBuilder();

        collection.forEach((key, value) -> csvFormat.append(value.getName().toString().replace(" ", "_"))
                .append(",").append(value.getSpeed()).append(",").append(value.getCurrentLocation()).append("\n"));

        return csvFormat.toString();
    }

    class CSVReadException extends Exception{

        @Override
        public String toString() {
            return "Обнаружена ошибка в CSV формате. Либо вы используете другой формат, либо неправильно задали элемент.";
        }
    }

    boolean read(List<String> lines, HashMap<String, Person> collection){
        for (String str : lines) {
            try {
                String[] fields = str.split(",", 3);
                if (fields.length != 3) {
                    throw new CSVReadException();
                } else {
                    FIO name;
                    var splitter = fields[0].split("_", 2);
                    if (splitter.length == 2)
                        name = new FIO(splitter[0], splitter[1]);
                    else if (splitter.length == 1)
                        name = new FIO(splitter[0], null);
                    else
                        throw new CSVReadException();
                    Person p = new Animal(name, Location.values()[Integer.parseInt(fields[2])]);
                    p.setSpeed(Integer.parseInt(fields[1]));
                    collection.put(name.toString().replace(" ", "_"), p);
                }
            } catch (CSVReadException e) {
                System.out.println(e.toString());
                return true;
            } catch (NumberFormatException e){
                System.out.println("Числовые значения полей заданы неправильно.");
                return true;
            } catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Локации с таким номером не существует");
                return true;
            }
        }
        return false;
    }
}
