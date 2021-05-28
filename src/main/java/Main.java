import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PDFConverter {
    private final File root;
    private final List<String> files;
    private final File[] listOfFiles;
    private final Document document;
    private Image image;

    PDFConverter(String pathname, String outputFile) throws Exception{
        this.root = new File(pathname);
        this.files = new ArrayList<>();
        this.listOfFiles  = root.listFiles();
        this.document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(new File(root, outputFile)));
    }

    protected void readfiles() {
        for (File file : listOfFiles) {
            if (file.isFile()) {
                files.add(file.getName());
            }
        }
    }

    protected void create() throws Exception{
        document.open();
        for (String f : files) {
            document.newPage();
            image = Image.getInstance(new File(root, f).getAbsolutePath());
            image.setAbsolutePosition(0, 0);
            image.setBorderWidth(0);
            image.scaleAbsolute(PageSize.A4);
            document.add(image);
        }
        document.close();
    }
}

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(System.in);

        System.out.println("Place all the images in a folder");
        System.out.print("Press ENTER key to proceed!");
        s.nextLine();

        System.out.print("\nEnter pathname of the images folder : ");
        String pathname = s.nextLine();

        System.out.println("Enter desired name of output file : ");
        String outputFile = s.nextLine() + ".pdf";

        PDFConverter pdf = new PDFConverter(pathname, outputFile);
        pdf.readfiles();
        pdf.create();

        System.out.println("\nPDF CREATED!\n\nLOCATION : " + pathname + "\n\nTHANK YOU!");
    }
}