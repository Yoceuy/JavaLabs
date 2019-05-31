package com.company;
import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Manip {


        public static String tName;
        public static Connection conn;
        public static Statement statmt;
        public static ResultSet resSet;

        public static void Conn() throws ClassNotFoundException, SQLException
        {
            conn = null;
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\sqlite\\users.db");
            System.out.println("База Подключена!");
        }

        public static void ShowTables  () throws ClassNotFoundException{
            try {
                conn = null;
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:C:\\sqlite\\users.db");
                String query = "SELECT NAME FROM sqlite_master WHERE type = 'table'";
                statmt = conn.createStatement();
                resSet=statmt.executeQuery(query);
                while (resSet.next()) {
                    System.out.println(resSet.getString(1));
                }

            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }


        }
        public static void ChooseTab() throws ClassNotFoundException{
            try {
                conn = null;
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:C:\\sqlite\\users.db");
                String query = "SELECT NAME FROM sqlite_master WHERE type = 'table'";
                statmt = conn.createStatement();
                resSet=statmt.executeQuery(query);
                int o=0;

                ArrayList<String> listS = new ArrayList<String>();
                while (resSet.next()) {
                    o++;
                    if ( !(resSet.getString(1)).equals("sqlite_sequence") ) {
                        listS.add(resSet.getString(1));
                    }
                }
                for (int i = 0; i <listS.size(); i++) {
                    System.out.println(Integer.toString(i+1) + ". " + listS.get(i));
                }
                System.out.println("Выберите таблицу:");
                int vvod;
                while (true) {
                   // Scanner choose = new Scanner(System.in);
                    vvod=get_int();
                    if (vvod > 0 && vvod <= listS.size())
                        break;
                    System.out.println("Введен неверный индекс. Попробуйте снова.");
                }
                tName=listS.get(vvod-1);

                String str = "INSERT INTO"+" "+tName+" "+"( ";

                query = "SELECT * FROM "+tName;
                statmt = conn.createStatement();
                resSet=statmt.executeQuery(query);
                ResultSetMetaData rsmd = resSet.getMetaData();
                int columnCount = rsmd.getColumnCount();
                int b;

                Scanner in= new Scanner(System.in);
                for (b=0;b<columnCount;b++){
                    String str2=rsmd.getColumnName(b+1);
                   // columns.add(str2);
                    String strData= rsmd.getColumnTypeName(b+1);
                    //dataType.add(strData);
                    if (b==columnCount-1){
                        str+=str2;
                    }else

                    str+=str2+",";

                }
                str=str+") VALUES (";
                int t;
                for (t=0;t<columnCount;t++){
                    System.out.println("Введите значения для столбца"+" "+rsmd.getColumnName(t+1)+":");

                        String val = in.next();

                        if (t==columnCount-1){
                            str+="'"+val+"'";
                        }else
                        str+="'"+val+"'"+", ";

                    }
                str=str+");";
                System.out.println(str);
               statmt.executeUpdate(str);

            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
    }
        public static void DelValue(){

            System.out.println("Введите id удаляемого кортежа");
            int delVal=get_int();
            try{

                statmt = conn.createStatement();
                statmt.execute("DELETE FROM"+" "+tName+" "+"WHERE id = "+delVal+";");
                System.out.println("Кортеж удален успешно");
            }

            catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }

        public static void menu() {
            System.out.println(
                    "1. Просмотреть таблицу\n" +
                    "2. Добавить данные в таблицу\n" +
                    "3. Удалить данные из таблицы\n" +
                    "4.Показать таблицы\n" +
                    "5.Удалить таблицу\n" +
                    "6.Создать таблицу\n" +
                    "7.Изменить таблицу (Добавить столбец)\n" +
                            "0.Выход");
        }

        public static int get_int(){
            Scanner input = new Scanner(System.in);
            int choice;
            while(true) {
                try {
                    choice = input.nextInt();
                    return choice;
                } catch (InputMismatchException e) {
                    System.out.println("Ошибка ввода");
                    input.nextLine();
                    continue;
                }
            }
        }


        public static void updTab(){
            Scanner in = new Scanner(System.in);
            System.out.println("Введите название таблицы:");
            String tabName= in.nextLine();
            System.out.println("Название столбца");

            String colName = in.nextLine();
            String query = "ALTER TABLE "+tabName+" ADD COLUMN "+colName+" VARCHAR";
            try{
                statmt = conn.createStatement();
                statmt.executeUpdate(query);
                System.out.println(colName +" столбец успешно добавлен");
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }

        public static void ReadDB() throws ClassNotFoundException, SQLException {
            try {
                String query = "SELECT NAME FROM sqlite_master WHERE type = 'table'";
                statmt = conn.createStatement();
                resSet=statmt.executeQuery(query);
                int o=0;

                ArrayList<String> listS = new ArrayList<String>();
                while (resSet.next()) {
                    o++;
                    if ( !(resSet.getString(1)).equals("sqlite_sequence") ) {
                        listS.add(resSet.getString(1));
                    }
                }
                for (int i = 0; i <listS.size(); i++) {
                    System.out.println(Integer.toString(i+1) + ". " + listS.get(i));
                }
                System.out.println("Выберите таблицу:");
                int vvod;
                while (true) {

                    vvod=get_int();
                    if (vvod > 0 && vvod <= listS.size())
                        break;
                    System.out.println("Введен неверный индекс. Попробуйте снова.");
                }
                tName=listS.get(vvod-1);
                String quer = "SELECT * FROM "+tName;
                statmt=conn.createStatement();
                resSet = statmt.executeQuery(quer);
                ResultSetMetaData rsmd = resSet.getMetaData();
                int columnCount = rsmd.getColumnCount();

                System.out.println("Название таблицы : " + rsmd.getTableName(2));
                System.out.println("Столбцы  \tТип данных");

                for (int i = 0; i < columnCount; i++) {
                    System.out.print(rsmd.getColumnName(i + 1) + "  \t");
                    System.out.println(rsmd.getColumnTypeName(i + 1));
                }
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
                //continue;
            }
            try{
                while (resSet.next()) {
                    int i = 1;
                    while(true){
                        try {
                            System.out.print(resSet.getString(i)+" ");
                        }
                        catch (SQLException e){break;}
                        i++;
                    }
                    System.out.println();
                }
            }
                        catch (SQLException e){}
        }
        public static void Delete() {
            try{
                String query="DROP TABLE IF EXISTS"+" "+tName;
                statmt=conn.createStatement();

                statmt.execute(query);
                    System.out.println("Таблица" +" "+ tName +" успешно удалена");
                // System.out.println("Такой таблицы нет");
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }

        }
        public static void Add() {
            try{
                Scanner in = new Scanner (System.in);
                System.out.println("Введите имя таблицы для создания:");
                String tabName = in.nextLine();
                System.out.println("Первый столбец id по дефолту. Введите название второго столбца:");
                String firstCol=in.nextLine();
                System.out.println("Введите тип данных для столбца:");
                String data1 =in.nextLine();

                System.out.println("Введите название для третьего столбца:");
                String scndCol=in.nextLine();
                System.out.println("Введите тип данных для столбца:");
                String data2 =in.nextLine();
                String query = "CREATE TABLE if not exists"+" "+tabName+" "+" ('id' INTEGER PRIMARY KEY AUTOINCREMENT,"+" "+ firstCol+" "+data1+"," +scndCol+" "+data2+");";
                statmt=conn.createStatement();
                statmt.executeUpdate(query);
                System.out.println("Таблица" +" " +tabName +" успешно создана");
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }


        public static void CloseDB() throws ClassNotFoundException, SQLException, NullPointerException
        {
            try {
                conn.close();
                System.out.println("Соединения закрыты");
            }
            catch (NullPointerException e){
                System.out.println(e.getMessage());
            }
        }

    }

