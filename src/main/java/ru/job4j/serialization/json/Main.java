package ru.job4j.serialization.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        /* JSONObject из json-строки строки */
        JSONObject jsonContact = new JSONObject("{\"phone\":\"+7(924)111-111-11-11\"}");

        /* JSONArray из ArrayList */
        List<String> list = new ArrayList<>();
        list.add("Student");
        list.add("Free");
        JSONArray jsonStatuses = new JSONArray(list);

        /* JSONObject напрямую методом put */
        final Person person = new Person(false, 30, new Contact("11-111"), new String[]{"Worker", "Married"});
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sex", person.getSex());
        jsonObject.put("age", person.getAge());
        jsonObject.put("contact", jsonContact);
        jsonObject.put("statuses", jsonStatuses);

        /* Выведем результат в консоль */
        System.out.println(jsonObject.toString());

        /* Преобразуем объект person в json-строку */
        System.out.println(new JSONObject(person).toString());

        JSONObject jsonAddress = new JSONObject("""
                {
                    "city":"Волгоград",
                    "street":"Пушкина",
                    "houseNumber":21
                }
                """.stripIndent());
        JSONArray jsonSubjects = new JSONArray(List.of("Философия", "Литература"));
        final Student student = new Student(true, 19, "Viktor",
                new Address("Москва", "Пушкина", 10),
                new String[]{"Математика", "Физика"});
        JSONObject jsonStudent = new JSONObject();
        jsonStudent.put("isFullTime", student.getFullTime());
        jsonStudent.put("age", student.getAge());
        jsonStudent.put("name", student.getName());
        jsonStudent.put("address", jsonAddress);
        jsonStudent.put("subjects", jsonSubjects);

        System.out.println(jsonStudent);
        System.out.println(new JSONObject(student));
    }
}
