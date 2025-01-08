package ru.job4j.serialization.xml;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.StringReader;
import java.io.StringWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        Person person = new Person(false, 30, new Contact("11-111"), "Worker", "Married");
        serializeAndDeserialize(person, Person.class);

        Student student = new Student(true, 19, "Viktor",
                new Address("Москва", "Пушкина", 10),
                new String[]{"Математика", "Физика"});
        serializeAndDeserialize(student, Student.class);
    }

    private static <T> void serializeAndDeserialize(T obj, Class<T> clazz) throws Exception {
        JAXBContext context = JAXBContext.newInstance(clazz);
        /* Создаем сериализатор */
        Marshaller marshaller = context.createMarshaller();
        /* Указываем, что нам нужно форматирование */
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        String xml = "";
        try (StringWriter writer = new StringWriter()) {
            /* Сериализуем */
            marshaller.marshal(obj, writer);
            xml = writer.getBuffer().toString();
            System.out.println(xml);
        }
        /* Для десериализации нам нужно создать десериализатор */
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (StringReader reader = new StringReader(xml)) {
            /* десериализуем */
            T result = clazz.cast(unmarshaller.unmarshal(reader));
            System.out.println(result);
        }
    }
}
