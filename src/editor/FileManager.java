package editor;

import javax.swing.*;
import java.io.*;

public class FileManager {
    public static void openFile(TextEditor textEditor, JTextArea textArea) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(textEditor) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            textEditor.currentFile = file;

            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null){
                    content.append(line).append("\n");
                }
                textArea.setText(content.toString());
            } catch (IOException e){
                JOptionPane.showMessageDialog(textEditor, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void saveFile(TextEditor textEditor, JTextArea textArea) {
        if (textEditor.currentFile == null){
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(textEditor) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                textEditor.currentFile = file;
                writeFile(file, textArea.getText(), textEditor);
            }
        }
        else{
            writeFile(textEditor.currentFile, textArea.getText(), textEditor);
        }
    }

    public static void newFile(TextEditor textEditor, JTextArea textArea) {
        textEditor.currentFile = null;
        textArea.setText("");
    }

    private static void writeFile(File file, String content, TextEditor textEditor){
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(content);
        } catch (IOException e){
            JOptionPane.showMessageDialog(textEditor,"Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
