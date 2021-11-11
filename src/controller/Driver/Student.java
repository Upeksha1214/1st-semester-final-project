package controller.Driver;

public class Student {
    public void learn() {

    }

    public void learn(String subject) {

    }

    public void learn(String subject1, String subject2) {

    }
}
class Demo{
    public static void main(String args []){
        Student student =new Student();
        student.learn();
        student.learn("Maths");
        student.learn("Maths","English");
    }
}

