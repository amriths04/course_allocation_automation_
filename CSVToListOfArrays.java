import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVToListOfArrays {
    public static void main(String[] args) {
        String studentsFile = "cgpa.csv";
        String choicesFile = "xx.csv";
        String combinedDataFile = "combined_data.csv";
        String classAssignmentsFile = "class_assignments.csv";
        String line;
        String delimiter = ",";

        Map<String, String[]> studentCgpaMap = new HashMap<>();
        List<String[]> combinedDataList = new ArrayList<>();
        Map<String, Integer> choice1CountMap = new HashMap<>();

        int totalStudents = 10;
        int tsg ;
        int numberOfClasses = 3;
        int classStrength = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(studentsFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = parseCSVLine(line);
                String rollNo = values[1].trim();
                String name = values[0].trim();
                String cgpa = values[2].trim();
                studentCgpaMap.put(rollNo, new String[]{name, cgpa});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(choicesFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = parseCSVLine(line);
                String rollNo = values[4].trim();
                String name = values[3].trim();
                String[] priorities = values[5].trim().split(",");
                if (studentCgpaMap.containsKey(rollNo)) {
                    String[] studentDetails = studentCgpaMap.get(rollNo);
                    String cgpa = studentDetails[1];
                    String[] combinedData = {rollNo, name, cgpa, priorities[0].trim(), priorities[1].trim(), priorities[2].trim()};
                    combinedDataList.add(combinedData);
                    choice1CountMap.put(priorities[0].trim(), choice1CountMap.getOrDefault(priorities[0].trim(), 0) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        tsg = combinedDataList.size();
        if (numberOfClasses != 0) {
            classStrength = totalStudents / numberOfClasses;
        }


        List<String> subjects = new ArrayList<>();
        subjects.add("WEB");
        subjects.add("SPM");
        subjects.add("DBMS");

        int threshold = classStrength / 2;
        for (String subject : new ArrayList<>(subjects)) {
            if (choice1CountMap.getOrDefault(subject, 0) < threshold) {
                subjects.remove(subject);
            }
        }

// Sort
        subjects.sort(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return Integer.compare(choice1CountMap.getOrDefault(s2, 0), choice1CountMap.getOrDefault(s1, 0));
            }
        });

        Collections.sort(combinedDataList, new Comparator<String[]>() {
            @Override
            public int compare(String[] a, String[] b) {
                double cgpaA = Double.parseDouble(a[2]);
                double cgpaB = Double.parseDouble(b[2]);
                return Double.compare(cgpaB, cgpaA);
            }
        });

        // Write the combined data to a new CSV file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(combinedDataFile))) {
            bw.write("RollNo,Name,CGPA,Choice1,Choice2,Choice3");
            bw.newLine();
            for (String[] array : combinedDataList) {
                bw.write(String.join(delimiter, array));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

// Output combined data list for debugging
        for (String[] array : combinedDataList) {
            for (String value : array) {
                System.out.print(value + " ");
            }
            System.out.println();
        }

        System.out.println("Total number of students combined: " + tsg);
        System.out.println("Total number of students: " + totalStudents);
        System.out.println("Class strength (students per class): " + classStrength);
        System.out.println("Number of classes: " + numberOfClasses);

        System.out.println("\nCount of each subject in Choice1:");
        for (Map.Entry<String, Integer> entry : choice1CountMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\nSubjects remaining after filtering and sorting:");
        for (String subject : subjects) {
            System.out.println(subject);
        }

        List<String> class1 = new ArrayList<>();
        List<String> class2 = new ArrayList<>();
        List<String> class3 = new ArrayList<>();

        if (subjects.size() == 3) {
            class1.add(subjects.get(0));
            class2.add(subjects.get(1));
            class3.add(subjects.get(2));
        } else if (subjects.size() == 2) {
            class1.add(subjects.get(0));
            class2.add(subjects.get(0));
            class3.add(subjects.get(1));
        } else if (subjects.size() == 1) {
            class1.add(subjects.get(0));
            class2.add(subjects.get(0));
            class3.add(subjects.get(0));
        }


            for (String[] student : combinedDataList) {
                String rollNo = student[0];
                String choice1 = student[3];
                String choice2 = student[4];
                String choice3 = student[5];

                if(subjects.contains(choice1))
                {
                    if(class1.getFirst().equals(choice1) && (class1.size()<=classStrength))
                        {
                            class1.add(rollNo);
                        }
                    else if(class2.getFirst().equals(choice1) && (class2.size()<=classStrength))
                        {
                            class2.add(rollNo);
                        }
                    else
                    {
                        class3.add(rollNo);
                    }
                }
                else if(subjects.contains(choice2))
                {
                    if(class1.getFirst().equals(choice2) && (class1.size()<=classStrength))
                    {
                        class1.add(rollNo);
                    } else if (class2.getFirst().equals(choice2) && (class2.size()<=classStrength))
                    {
                        class2.add(rollNo);
                    }
//                    else if(class3.get(0).equals(choice2) && (class3.size()<classStrength))
                    else{

                        class3.add(rollNo);
                    }
                }
                else {
                    if(class1.getFirst().equals(choice3) && (class1.size()<classStrength))
                    {
                        class1.add(rollNo);
                    } else if (class2.getFirst().equals(choice3) && (class2.size()<classStrength)) {
                        class2.add(rollNo);
                    }
                    else {
                        class3.add(rollNo);
                    }
                }
            }
        System.out.println("\nClass 1 Subjects:");
        for (String subject : class1) {
            System.out.println(subject);
        }

        System.out.println("\nClass 2 Subjects:");
        for (String subject : class2) {
            System.out.println(subject);
        }

        System.out.println("\nClass 3 Subjects:");
        for (String subject : class3) {
            System.out.println(subject);
        }


        try (BufferedWriter bw = new BufferedWriter(new FileWriter(classAssignmentsFile))) {
            bw.write("Class1,Class2,Class3");
            bw.newLine();
            int maxSize = Math.max(class1.size(), Math.max(class2.size(), class3.size()));
            for (int i = 0; i < maxSize; i++) {
                String class1Subject = i < class1.size() ? class1.get(i) : "";
                String class2Subject = i < class2.size() ? class2.get(i) : "";
                String class3Subject = i < class3.size() ? class3.get(i) : "";
                bw.write(String.join(delimiter, class1Subject, class2Subject, class3Subject));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean insideQuote = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
                insideQuote = !insideQuote;
            } else if (c == ',' && !insideQuote) {
                result.add(field.toString());
                field.setLength(0);
            } else {
                field.append(c);
            }
        }
        result.add(field.toString());

        return result.toArray(new String[0]);
    }
}
