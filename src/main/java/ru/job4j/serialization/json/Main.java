package ru.job4j.serialization.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public static void main(String[] args) {
        final Person person = new Person(false, 30, new Contact("11-111"),
                new String[] {"Worker", "Married"});

        /* Преобразуем объект person в json-строку. */
        final Gson gson = new GsonBuilder().create();
        System.out.println(gson.toJson(person));

        /* Создаём новую json-строку с модифицированными данными*/
        final String personJson =
                "{"
                        + "\"sex\":false,"
                        + "\"age\":35,"
                        + "\"contact\":"
                        + "{"
                        + "\"phone\":\"+7(924)111-111-11-11\""
                        + "},"
                        + "\"statuses\":"
                        + "[\"Student\",\"Free\"]"
                        + "}";
        /* Превращаем json-строку обратно в объект */
        final Person personMod = gson.fromJson(personJson, Person.class);
        System.out.println(personMod);

        final Student student = new Student(true, 19, "Viktor",
                new Address("Москва", "Пушкина", 10),
                new String[]{"Математика", "Физика"});
        System.out.println(gson.toJson(student));

        final String studentJson = """
                {
                    "isFullTime":false,
                    "age":21,
                    "name":"Vlad",
                    "address":
                        {
                            "city":"Волгоград",
                            "street":"Пушкина",
                            "houseNumber":21
                        },
                    "subjects":["Философия","Литература"]
                }
                """.stripIndent();
        final Student studentMod = gson.fromJson(studentJson, Student.class);
        System.out.println(studentMod);
    }
}
