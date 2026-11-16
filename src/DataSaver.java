import  javax.swing.JFileChooser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class DataSaver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> records = new ArrayList<>();
        boolean another = true;

        while (another) {
            String firstName = SafeInput.getNonZeroLenString(in, "Enter first name");
            String lastName = SafeInput.getNonZeroLenString(in, "Enter last name");
            int idNum = SafeInput.getRangedInt(in, "Enter ID number", 0, 999999);
            String email = SafeInput. getNonZeroLenString(in, "enter email");
            int yearOfBirth = SafeInput.getRangedInt(in, "Enter your year of birth", 1926, 2026);

            String line = String.format("%s,%s,%6d,%s,%d", firstName, lastName, idNum, email, yearOfBirth);
            records.add(line);
            System.out.println("Record added: " + line);

            another = SafeInput.getYNConfirm(in, "Add another record?");
        }

        JFileChooser chooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);
        chooser.setDialogTitle("Save File");

        if (chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
            System.out.println("No save location specified. Exiting.");
            System.exit(0);
        }

        Path filePath = chooser.getSelectedFile().toPath();

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (String record : records) {
                writer.write(record);
                writer.newLine();
            }
            System.out.println("File saved successfully: " + filePath.getFileName());
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
