package ANSI2UTF8;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Converter {

    public static void main(String[] args) throws FileNotFoundException {
        try {
            UIManager.put("OptionPane.cancelButtonText", "İptal");
            UIManager.put("OptionPane.noButtonText", "Hayır");
            UIManager.put("OptionPane.okButtonText", "Tamam");
            UIManager.put("OptionPane.yesButtonText", "Evet");
            int sel = JOptionPane.showConfirmDialog(null, "Şu anki dizini mi kullanmak istiyorsunuz?", "Dizin seçimi", JOptionPane.YES_NO_CANCEL_OPTION);
            String dir = "";
            switch (sel) {
                case 0:
                    dir = System.getProperty("user.dir");
                    break;
                case 1:
                    dir = JOptionPane.showInputDialog(null, "Klasör yolunu giriniz.", "Dizin giriniz", JOptionPane.QUESTION_MESSAGE);
                    break;
                default:
                    System.exit(0);
            }
            File files[] = new File(dir).listFiles();
            int counter = 0;
            for (File f : files) {
                if (f.getName().endsWith(".srt")) {
                    counter++;
                    InputStream inputStream = new FileInputStream(dir + "/" + f.getName());
                    Reader reader = new InputStreamReader(inputStream, "windows-1254");
                    int data = reader.read();
                    (new File(dir + "/Output")).mkdir();
                    OutputStream outputStream = new FileOutputStream(dir + "/Output/" + f.getName());
                    OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
                    writer.append("1");
                    while (data != -1) {
                        data = reader.read();
                        if ((char) data == '￿') {
                            continue;
                        }
                        writer.append((char) data);
                    }
                    reader.close();
                    writer.close();
                }
            }
            if (counter < 1) {
                JOptionPane.showMessageDialog(null, ".srt uzantılı dosya bulunamadı!", "Hata!", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, counter + " dosya başarıyla dönüştürüldü!", "İşlem bitti!", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}