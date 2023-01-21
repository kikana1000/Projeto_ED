package resources;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static resources.ConsoleColors.*;

/**
 * Classe com métodos  para ler informações a partir do {@link System#in teclado}
 */
public class Input {

    /**
     * Lê um {@link Integer Integer} a partir do {@link System#in teclado};
     * @param str {@link String String}  a ser mostrada antes da inserção do número;
     * @return {@link Integer Integer} lido a partir do {@link System#in teclado}.
     */
    public static int readInt(String str) {
        int i = 0;
        boolean a = false;
        Scanner sc = new Scanner(System.in);
        while (!a) {
            System.out.println(str);
            if (a = sc.hasNextInt()) {
                i = sc.nextInt();
            } else {
                System.out.println(RED+"Valor inválido!"+RESET);
            }
            sc.nextLine();
        }
        return i;
    }
    /**
     * Lê um {@link Integer Integer} a partir do {@link System#in teclado};
     * @param str {@link String String}  a ser mostrada antes da inserção do número;
     * @return {@link Integer Integer} lido a partir do {@link System#in teclado}.
     */
    public static int readInt(String str,int min,int max, String err_min_max) {
        int i = 0;
        boolean a = false;
        Scanner sc = new Scanner(System.in);
        while (!a) {
            System.out.println(str);
            if (a = sc.hasNextInt()) {
                i = sc.nextInt();
                if(i<min&&i>max){
                    a=false;
                    System.out.println(err_min_max);
                }
            } else {
                System.out.println(RED+"Valor inválido!"+RESET);
            }
            sc.nextLine();
        }
        return i;
    }


    /**
     * Lê um {@link Integer Integer} a partir do {@link System#in teclado};
     * @return {@link Integer Integer} lido a partir do {@link System#in teclado}.
     */
    public static int readInt() {
        int i = 0;
        boolean a = false;
        Scanner sc = new Scanner(System.in);
        while (!a) {
            if (a = sc.hasNextInt()) i = sc.nextInt();
            else {
                System.out.println(RED+"Valor inválido!"+RESET);
            }
            sc.nextLine();
        }
        return i;
    }

    /**
     * Lê um {@link Double Double} a partir do {@link System#in teclado};
     * @param str {@link String String}  a ser mostrada antes da inserção do número;
     * @return {@link Double Double} lido a partir do {@link System#in teclado}.
     */
    public static double readDouble(String str) {
        double i = 0;
        boolean a = false;
        Scanner sc = new Scanner(System.in);
        while (!a) {
            System.out.println(str);
            if (a = sc.hasNextDouble()) {
                i = sc.nextDouble();
            } else {
                System.out.println(RED+"Valor inválido!"+RESET);
            }
            sc.nextLine();
        }
        return i;
    }

    /**
     * Lê um {@link String String} a partir do {@link System#in teclado};
     * @param str {@link String String}  a ser mostrada antes da inserção da {@link String String};
     * @return {@link String String} lido a partir do {@link System#in teclado}.
     */
    public static String readString(String str) {
        String i = "";
        boolean a = false;
        Scanner sc = new Scanner(System.in);
        while (!a) {
            System.out.println(str);
            if (a = sc.hasNextLine()) {
                i = sc.nextLine();
                i.replace("\n","");
            } else {
                System.out.println(RED+"Valor inválido!"+RESET);
            }
        }
        return i;
    }

    /**
     * Lê um {@link LocalDateTime Data} de uma {@link String String} a partir do {@link System#in teclado};
     * @param str {@link String String}  a ser mostrada antes da inserção da {@link LocalDateTime Data};
     * @return {@link LocalDateTime Data} de uma {@link String String} a partir do {@link System#in teclado};
     */
    public static LocalDateTime readDate(String str) {
        String i;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime data;
        while (true) {
            i = Input.readString(str);
            try {
                data = LocalDateTime.parse(i, formatter);
            } catch (DateTimeParseException ex) {
                System.out.println(RED+"Data invalida!"+RESET);
                continue;
            }
            break;
        }

        return data;
    }
}

