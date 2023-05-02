import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        // Найти количество несовершеннолетних (т.е. людей младше 18 лет).
        long underageCount = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println("Количество несовершеннолетних: " + underageCount);

        // Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет).
        List<String> conscripts = persons.stream()
                .filter(person -> person.getSex() == Sex.MAN && person.getAge() >= 18 && person.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println("Список фамилий призывников: " + conscripts);

        // Получить отсортированный по фамилии список потенциально работоспособных людей с высшим образованием
        // (т.е. людей с высшим образованием от 18 до 60 лет для женщин и до 65 лет для мужчин).
        List<Person> potentialWorkers = persons.stream()
                .filter(person -> person.getEducation() == Education.HIGHER &&
                        ((person.getSex() == Sex.MAN && person.getAge() <= 65) ||
                                (person.getSex() == Sex.WOMAN && person.getAge() <= 60)))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        System.out.println("Отсортированный список потенциально работоспособных людей: " + potentialWorkers);
    }
}