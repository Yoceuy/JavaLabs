package com.company;

import java.sql.Connection;
import java.sql.SQLException;

import static javafx.application.Platform.exit;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException,NullPointerException {
        while (true){
            Manip.menu();
            int menu=Manip.get_int();
            switch (menu) {

                case 1:
                    Manip.Conn();
                    //Manip.CreateDB();
                    Manip.ReadDB();
                    break;
                case 2:
                    Manip.Conn();
                    Manip.ChooseTab();
                   // Manip.ReadDB();
                    //Manip.WriteDB();
                    break;
                case 3:
                    Manip.Conn();
                   // Manip.CreateDB();
                    Manip.ReadDB();
                    Manip.DelValue();
                    break;
                case 4:
                    Manip.ShowTables();
                    break;
                case 5:
                    Manip.Conn();
                    Manip.ReadDB();
                    Manip.Delete();
                    break;
                case 6:
                    Manip.Conn();
                    Manip.Add();
                    break;
                case 7:
                    Manip.Conn();
                    Manip.updTab();
                    break;
                //case 8:
                //    Manip.Conn();
                //    Manip.ChooseTab();
                //    break;
                case 0:
                    System.exit(1);
                    default:
                        System.out.println("Такого пункта меню не существует!");


            }
            Manip.CloseDB();
        }
    }
}
