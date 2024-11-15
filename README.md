# Course-Allocation-Automation

This repository contains a Java program designed to automate course allocation based on students' CGPA and subject preferences. The program reads student data from CSV files, processes the data by sorting students, filtering subjects based on popularity, and assigning students to classes. The results are saved into CSV files for further review.
## NOTE
Currently works for 3 subject in 3 classes.
## Features

- Reads student CGPA and course preferences from CSV files/EXCEL SHEET.
- Assigns students to classes based on their CGPA, preferences and subject POPULARITY.
- Supports dynamic adjustment based on class size and subject availability.
- Outputs results to CSV files/EXCEL SHEET for easy data handling.

## File Structure

- **`CSVToListOfArrays.java`**: Main program file responsible for course allocation.
- **`cgpa.csv`**: Sample CSV file containing student CGPA data.
- **`xx.csv`**: Sample CSV file containing student subject preferences.
- **`combined_data.csv`**: Generated CSV file containing the combined and sorted student data.
- **`class_assignments.csv`**: Generated CSV file containing the final class assignments.

## How to Run

1. Compile the Java program:
   ```bash
   javac CSVToListOfArrays.java
2. Run the Java Program
   ```bash
   java CSVToListOfArrays

https://tally.so/dashboard
Feel free to explore, modify, and improve the automation! Contributions and suggestions are welcome.
