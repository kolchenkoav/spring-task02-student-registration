package com.example.studentregistration.generaror;

import com.example.studentregistration.model.Student;
import com.example.studentregistration.config.AppProperties;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GeneratorStudentImpl implements Generator {
    private final AppProperties appProperties;
    private final int minAge;
    private final int maxAge;
    private final String delimiter;

    public GeneratorStudentImpl(AppProperties appProperties1, AppProperties appProperties) {
        this.appProperties = appProperties1;
        minAge = Integer.parseInt(appProperties.getMinAge());
        maxAge = Integer.parseInt(appProperties.getMaxAge());
        delimiter = appProperties.getDelimiter();
    }

    private final String[] firstName = {"Александр", "Максим", "Иван", "Артём", "Никита", "Дмитрий", "Егор", "Даниил",
            "Михаил", "Андрей", "Алексей", "Илья", "Кирилл", "Сергей", "Владислав", "Роман", "Владимир", "Тимофей",
            "Матвей", "Георгий", "Николай", "Павел", "Арсений", "Денис", "Степан", "Фёдор", "Данила", "Антон",
            "Константин", "Глеб", "Ярослав", "Григорий", "Игорь", "Евгений", "Тимур", "Руслан", "Пётр", "Олег",
            "Вадим", "Василий", "Вячеслав", "Виктор", "Юрий", "Артемий", "Леонид", "Давид", "Марк", "Лев", "Семён",
            "Артур"};
    private final String[] lastName = {"Иванов", "Васильев", "Петров",
            "Смирнов", "Михайлов", "Фёдоров", "Соколов", "Яковлев", "Попов", "Андреев", "Алексеев", "Александров",
            "Лебедев", "Григорьев", "Степанов", "Семёнов", "Павлов", "Богданов", "Николаев", "Дмитриев", "Егоров",
            "Волков", "Кузнецов", "Никитин", "Соловьёв", "Тимофеев", "Орлов", "Афанасьев", "Филиппов", "Сергеев",
            "Захаров", "Матвеев", "Виноградов", "Кузьмин", "Максимов", "Козлов", "Ильин", "Герасимов", "Марков",
            "Новиков", "Морозов"};
    private final String[] secondName = {"Александрович", "Адамович", ",Анатольевич", "Аркадьевич", "Алексеевич",
            "Андреевич", "Артемович", "Альбертович", "Антонович", "Богданович", "Богуславович", "Борисович",
            "Вадимович", "Васильевич", "Владимирович", "Валентинович", "Вениаминович", "Вячеславович", "Валерьевич",
            "Викторович", "Геннадиевич", "Георгиевич", "Геннадьевич", "Григорьевич", "Давидович", "Денисович",
            "Данилович", "Дмитриевич", "Евгеньевич", "Егорович", "Ефимович", "Иванович", "Ильич", "Игоревич",
            "Иосифович", "Кириллович", "Константинович", "Леонидович", "Львович", "Макарович", "Максович",
            "Миронович", "Максимович", "Матвеевич", "Михайлович", "Натанович", "Наумович", "Николаевич", "Олегович",
            "Оскарович", "Павлович", "Петрович", "Платонович", "Робертович", "Ростиславович", "Рудольфович",
            "Романович", "Рубенович", "Русланович", "Святославович", "Сергеевич", "Степанович", "Семенович",
            "Станиславович", "Тарасович", "Тимофеевич", "Тимурович", "Федорович", "Феликсович", "Филиппович",
            "Харитонович", "Эдуардович", "Эмануилович", "Эльдарович", "Юрьевич", "Юхимович", "Яковлевич",
            "Ярославович"};

    public String generateFirstName() {
        return firstName[(int) (Math.random() * firstName.length)];
    }

    public String generateLastName() {
        return lastName[(int) (Math.random() * lastName.length)];
    }

    public String generateSecondName() {
        return secondName[(int) (Math.random() * secondName.length)];
    }

    public int generateAge(int minimum, int maximum) {
        Random rn = new Random();
        return rn.nextInt(maximum - minimum + 1) + minimum;
    }

    @Override
    public String getGeneratedLine() {
        return generateFirstName() + delimiter +
                generateLastName() + delimiter +
                generateAge(minAge, maxAge);
    }

    @Override
    public Student getGeneratedEntity() {
        Student student = new Student();
        student.setFirstName(generateFirstName());
        student.setLastName(generateLastName());
        student.setAge(generateAge(minAge, maxAge));
        return student;
    }
}
