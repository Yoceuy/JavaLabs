import javafx.stage.DirectoryChooser;

import javax.naming.spi.DirectoryManager;
import javax.sound.midi.Soundbank;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class FMan {
    public static void main(String[] args) throws IOException {
        File currentDirectory = new File("D:\\test\\test2");

        while (true){
            Scanner in = new Scanner(System.in);
            int t;
            System.out.println("1.Создать каталог\n" +
                    "2.Вернуться назад" + "\n" +
                    "3.Перейти вперед\n" +
                    "4.Перейти по указанному пути\n" +
                    "5.Открыть содержимое файла\n" +
                    "6.Создать файл\n" +
                    "7.Показать все элементы\n" +
                    "8.Удалить элемент\n" +
                    "9.Переместить файл\n" +
                    "10.Переименовать файл\n" +
                    "11.Выход\n");

            t = in.nextInt();

            switch (t) {
                case 1:
                    String text;
                    in.nextLine();
                    System.out.println("Введите название для папки:");
                    text = in.nextLine();
                    try {
                        File file = new File(currentDirectory, text);
                        if(!file.mkdir()){
                            throw new IOException ("Отказано в доступе");
                        }


                    }catch (IOException x) {
                        System.out.println(x.getMessage());
                    }


                    break;

                case 2:
                    //currentDirectory = new File("D:\\test\\test2");
                    try {
                        File parent = currentDirectory.getParentFile();
                        System.out.println(parent.getPath());
                        currentDirectory = parent;
                    } catch (NullPointerException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;

                case 3:
                    File[] childDirectories = currentDirectory.listFiles((FileFilter) null);
                    ArrayList<String> listS = new ArrayList<String>();
                   try {
                        if (childDirectories.length==0){
                            throw new NullPointerException ("Папка пуста");
                        }
                    for (int q = 0; q < childDirectories.length; q++)
                    {
                        if(childDirectories[q].isDirectory())
                        {
                            listS.add(childDirectories[q].toString());

                        }
                    }

                    for (int i = 0; i <listS.size(); i++) {
                        System.out.println(Integer.toString(i+1) + ". " + listS.get(i));
                    }

                    int index;
                    if (listS.size() == 0) {
                        System.out.println("В папке отсутствует вложенность.");
                        break;
                    }

                    while (true) {
                        System.out.println("Выберите папку для перехода:");
                        try{
                            Scanner choose = new Scanner(System.in);
                            index = choose.nextInt();
                            if (index > 0 && index <= listS.size())
                                break;
                            System.out.println("Введен неверный индекс. Попробуйте снова.");
                        }catch (InputMismatchException e){
                            System.out.println("Введите число!");
                        }
                    }

                    currentDirectory = new File (listS.get(index-1));
                    //File up = new File ("D:\\test\\test2");
                    //up.getParentFile();
                    System.out.println(listS.get(index-1));
                    }
                    catch (NullPointerException b){
                        System.out.println("В этой папке нет папок");
                    }
                    break;

                case 4:
                    Scanner input = new Scanner(System.in);
                    System.out.println("Укажите путь к папке.");
                    String path = input.nextLine();
                    File newFile = new File(path);
                    if (!newFile.isDirectory()) {
                        System.out.println("Папка не существует или путь указан неверно.");
                        break;
                    }

                    currentDirectory = newFile;
                    System.out.println("Директория успешно установлена.");
                    break;

                case 5:
                    input = new Scanner(System.in);
                    System.out.println("Введите имя файла");

                    String pathname = input.nextLine();

                    try (Scanner sc = new Scanner(new FileReader(pathname))) {
                        String _text;
                        while (sc.hasNextLine()) {
                            _text = sc.nextLine();
                            System.out.println(_text);
                        }
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 6:
                    String fileName;
                    System.out.println("Введите название файла:");
                    in.nextLine();
                    fileName = in.nextLine();
                    try {
                        File writeR = new File(currentDirectory, fileName);
                        writeR.createNewFile();
                    }
                    catch (IOException ex){
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 7:
                    File[] childD = currentDirectory.listFiles((FileFilter) null);

                   try{
                       for (int q = 0; q < childD.length; q++)
                        {
                            File check = new File(childD[q].toString());
                            if(check.isDirectory())
                            {
                                System.out.println(Integer.toString(q+1) + ". "+"Папка-" + check.getName());
                            }else if(check.isFile()) {
                                System.out.println(Integer.toString(q+1) + ". "+"Файл-" +check.getName()+ " size:" +check.length() +"byte");
                            }
                            if (childD.length==0){
                                throw new NullPointerException ("Папка пуста");
                            }
                        }
                   }catch (NullPointerException n){
                       System.out.println(n.getMessage());
                   }
                    break;
                case 8:
                    File[] childDir = currentDirectory.listFiles((FileFilter) null);
                    ArrayList<String> listDel = new ArrayList<String>();
                    try {


                        for (int q = 0; q < childDir.length; q++) {

                            listDel.add(childDir[q].toString());

                        }

                        for (int i = 0; i < listDel.size(); i++) {
                            System.out.println(Integer.toString(i + 1) + ". " + listDel.get(i));
                        }

                        int ind;
                        if (childDir.length == 0) {
                            System.out.println("В папке отсутствует вложенность.");
                            break;
                        }

                        while (true) {
                            System.out.println("Выберите папку для удаления:");
                            Scanner choose = new Scanner(System.in);
                            ind = choose.nextInt();
                            if (ind > 0 && ind <= childDir.length)
                                break;
                            System.out.println("Введен неверный индекс. Попробуйте снова!");
                        }

                        //currentDirectory = childDir[index - 1];
                        //File up = new File ("D:\\test\\test2");
                        //up.getParentFile();
                        File currentDirectory2 = new File(childDir[ind - 1].toString());
                        deleteDir(currentDirectory2);
                    } catch (NullPointerException x){
                        System.out.println("Нечего удалять!");
                    }

                    break;
                case 9:
                    File[] move = currentDirectory.listFiles((FileFilter) null);
                    ArrayList<String> listMov = new ArrayList<String>();
                    for (int q = 0; q < move.length; q++)
                    {
                        if(move[q].isFile())
                        {
                            listMov.add(move[q].toString());

                        }
                    }

                    for (int i = 0; i <listMov.size(); i++) {
                        System.out.println(Integer.toString(i+1) + ". " + listMov.get(i));
                    }

                    //
                    int cnt;
                    if (move.length == 0) {
                        System.out.println("Нет файлов!");
                        break;
                    }

                    while (true) {
                        try {
                            System.out.println("Выберите файл для перемещения:");
                            Scanner choose = new Scanner(System.in);
                            cnt = choose.nextInt();
                            if (cnt > 0 && cnt <= listMov.size())
                                break;
                            System.out.println("Введен неверный индекс. Попробуйте снова!");
                        }catch (InputMismatchException d) {
                            System.out.println("Введите число");
                        }
                    }
                    currentDirectory= new File (listMov.get(cnt-1));
                    //


                    //Path target_dir = FileSystems.getDefault().getPath("C:/tutorial/photos");
                    System.out.println(currentDirectory);
                    System.out.println("Куда переместить?");
                    String whereto;
                    in.nextLine();
                    whereto=in.nextLine();

                    File destFolder = new File(whereto); // это папка, в которую будем перемещать
                    currentDirectory.renameTo(new File(destFolder, currentDirectory.getName()));
                    //}
                    break;
                case 10:
                    File[] rnm = currentDirectory.listFiles((FileFilter) null);
                    ArrayList<String> listRnm = new ArrayList<String>();
                    for (int q = 0; q < rnm.length; q++)
                    {
                        if(rnm[q].isFile())
                        {
                            listRnm.add(rnm[q].toString());

                        }
                    }

                    for (int i = 0; i <listRnm.size(); i++) {
                        System.out.println(Integer.toString(i+1) + ". " + listRnm.get(i));
                    }

                    //
                    int count;
                    if (rnm.length == 0) {
                        System.out.println("Нет файлов!");
                        break;
                    }

                    while (true) {
                        System.out.println("Выберите файл для переименования:");
                        Scanner choose = new Scanner(System.in);
                        count = choose.nextInt();
                        if (count > 0 && count <= listRnm.size())
                            break;
                        System.out.println("Введен неверный индекс. Попробуйте снова!");
                    }
                    currentDirectory= new File (listRnm.get(count-1));
                    System.out.println(currentDirectory);
                    System.out.println("Как переименовать?");
                    String name;
                    in.nextLine();
                    name=in.nextLine();
                    File Rnmd= new File(currentDirectory.getParent(),name);
                    currentDirectory.renameTo(Rnmd);
                    break;
                case 11:System.exit(0);

            }
        }

    }

    public static void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        file.delete();
    }

}


