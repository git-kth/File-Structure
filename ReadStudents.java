import java.io.*;

public class ReadStudents {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("USAGE: java ReadStudents   입력파일명   [Option1]  [Option2]");
            System.exit(0);
        }
        String input_file = args[0];
        String command = args[1];
        String data = args[2];
        File in_f = new File(input_file);
        if (!in_f.exists()) {
            System.out.println(input_file + " does not exist");
            System.exit(0);
        }
        if (!"p s n a".contains(command) || command.length() != 1) {
            System.out.println("[Option1] : p OR s OR n OR a");
            System.exit(0);
        }
        String value = null;
        try {
            RandomAccessFile din = new RandomAccessFile(in_f, "r");
            Student s = new Student();
            Boolean found = false;
            switch (command) {
                case "s":
                case "n":
                    while (true) {
                        if (s.readStudent(din) < 0) break;        // if EOF
                        if (command.equals("s")) value = s.number;
                        else if (command.equals("n")) value = s.name;

                        if (value.equals(data)) {
                            s.printStudent();
                            found = true;
                            if (command.equals("n")) {
                                continue;
                            }
                            System.exit(0);
                        }
                    }
                    break;
                case "a":
                    while (true) {
                        if (s.readStudent(din) < 0) break;        // if EOF
                        if (s.address.contains(data)) {
                            found = true;
                            s.printStudent();
                        }
                    }
                    break;
                case "p":
                    int count = 0;
                    try {
                        int tmp = Integer.parseInt(data);
                    } catch (NumberFormatException e) {
                        System.out.println("p [Option2] : Option2 is not number");
                        System.exit(0);
                    }
                    int N = Integer.parseInt(data);
                    while (true) {
                        if (s.readStudent(din) < 0) break;        // if EOF
                        count++;
                        if (N == 0) {
                            s.printStudent();
                            found = true;
                            continue;
                        }
                        if (N == count) {
                            s.printStudent();
                            System.exit(0);
                        }
                    }
                    if (!found) {
                        System.out.println("p [Option2] : Option2 ranges from 0 to " + count + "(Number of students)");
                        System.exit(0);
                    }
                    break;
            }
            if (data != null && !found)
                System.out.println("\"" + data + "\"" + (command.equals("a") ? " address" : (command.equals("s") ? " number" : " name")) + "'s student no exists");
            din.close();
        } catch (IOException err) {
            System.out.println("file I/O error..");
        }
    }
}