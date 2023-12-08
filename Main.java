 /*
Program Assignment : # 10
Student ID : 65160325
Student Name : Thanipha Damrongsaktrakool
Date : 04/11/2023
Description :  Final Reccommend age , Gender , Type , keyword
 */
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Date;
import java.text.DecimalFormat;
public class Main {

    static File fileMachine = new File("src/Machine.txt");
    static File fileLogin = new File("src/Login.txt");
    static File fileMenu = new File("src/Menu.txt");
    static File fileOrder = new File("src/Order.txt");
    static ArrayList<Object[]> machineData = new ArrayList<>();
    static ArrayList<Object[]> loginData = new ArrayList<>();
    static ArrayList<Object[]> menuData = new ArrayList<>();
    static ArrayList<Object[]> orderData = new ArrayList<>();
    /*
    MACHINE
    data[0] => ID
    data[1] => CITY
    data[2] => LATITUDE
    data[3] => LONGITUDE
    data[4] => ACCOUNT
    data[5] => BALANCE
    */
    public static void readMachineData() throws IOException {
        Scanner inputFile = new Scanner(fileMachine);
        while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine();
            String[] info = line.split("\t");
            String id = info[0];
            String city = info[1];
            String account = info[3];
            String[] position = info[2].split(", ");
            Object[] data = {
                    id,
                    city,
                    position[0],//latitude
                    position[1],//longitude
                    account,
                    Double.parseDouble(info[4].toString().replace("$", "").replace(",","")),//balance
                    info[5]
            };
            machineData.add(data);
        }
        inputFile.close();
    }
    /*
    LOGIN
    data[0] => ID
    data[1] => FIRSTNAME
    data[2] => LASTNAME
    data[3] => EMAIL
    data[4] => PASSWORD
    data[5] => TELEPHONE
    */
    public static void readLoginData() throws IOException {
        Scanner inputFile = new Scanner(fileLogin);
        while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine();
            String[] info = line.split("\t");
            String id = info[0];
            String email = info[2];
            String password = info[3];
            String tel = info[4];
            Object[] data = {
                    id,
                    (info[1].toString().split(" "))[0],
                    (info[1].toString().split(" "))[1],
                    email,
                    password,
                    tel
            };
            loginData.add(data);
        }
        inputFile.close();
    }
    /*
    MENU
    data[0] => MENU ID
    data[1] => MENU NAME
    data[2] => PRICE
    data[3] => RECOMMENDED AGES
    data[4] => RECOMMENDED GENDER
    data[5] => TYPE - SD = SOFT DRINK, HD = HARD DRINK
    data[6] => DESCRIPTION
    */
    public static void readMenuData() throws IOException {
        Scanner inputFile = new Scanner(fileMenu);
        while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine();
            String[] info = line.split("\t");
            Object[] data = {
                    info[0],//ID
                    info[1],//name
                    info[2],//price
                    info[3],//rec.ages
                    info[4],//rec.gender
                    info[5],//type
                    info[6]//description
            };
            menuData.add(data);
        }
        inputFile.close();
    }
    /*
    ORDER
    data[0] => ID
    data[1] => MENU ID
    data[2] => MACHINE ID
    data[3] => TELEPHONE
    data[4] => PIN
    data[5] => STATUS
    data[6] => USE DATE
    */
    public static void readOrderData() throws IOException {
        Scanner inputFile = new Scanner(fileOrder);
        while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine();
            String[] info = line.split("\t");
            Object[] data = {
                    info[0],//ID
                    info[1],//menu id
                    info[2],//machine id
                    info[3],//telephone
                    info[4],//PIN
                    info[5],//status
                    info[6]//use date
            };
            orderData.add(data);
        }
        inputFile.close();
    }

    // use in showDetail method
    public static void showMachine(ArrayList<Object[]> machineData) {
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf("%-12s\t%-15s\t%-15s\t%-15s\t%-15s\t\t%-18s\n", "ID", "City", "Position", "ShippingType", "AccountNumber", "Balance($)");
        System.out.println("-------------------------------------------------------------------------------------------------");
        DecimalFormat df = new DecimalFormat("#,###.00");
        System.out.println();
        for (Object[] info : machineData) {
            System.out.printf(
                    "%-12s\t%-15s\t%s,\t%s\t%-15s\t%-15s\t%-18s\n",
                    info[0],//id
                    info[1],//city
                    String.format("%.2f", Float.parseFloat(info[2].toString())),// Latitude
                    String.format("%.2f",Float.parseFloat(info[3].toString())),// Longitude
                    checkShippingType((String) info[2], (String) info[3]),//ShippingType
                    info[4].toString().substring(0, 12) + "xxxx",// acc No.
                    df.format(Double.parseDouble(info[5].toString())).substring(0,df.format(Double.parseDouble(info[5].toString())).indexOf(","))
                    + ",xxx.xx"// balance
            );
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
    }
    // use in showMachine method
    public static String checkShippingType(String latitude, String longitude) {
        if (latitude.charAt(0) == '-') {
            if (longitude.charAt(0) == '-') {
                return "Ships";
            } else {
                return "Trucks";
            }
        } else if (latitude.charAt(0) != '-') {
            if (longitude.charAt(0) == '-') {
                return "Trucks";
            } else {
                return "Planes";
            }
        }
        return "Planes";
    }

    public static void showMenu(ArrayList<Object[]> menuData) {
        System.out.println("--------------------------------------");
        System.out.printf("ID\tMenu\t\t\tPrice\n");
        System.out.println("--------------------------------------");
        for (Object[] info : menuData) {
            System.out.printf("%-3s\t%-20s\t%s\n",
                    info[0],//ID
                    info[1],//menu
                    info[2]//price
            );
        }
        System.out.println("--------------------------------------");
    }
    public static void showMenuType(ArrayList<Object[]> menuData,String Type) {
        System.out.println("--------------------------------------");
        System.out.printf("ID\t\t\tMenu\n");
        System.out.println("--------------------------------------");
        for (Object[] info : menuData) {
            System.out.printf("%-3s\t%-20s\n",
                    info[0],//ID
                    info[1]//menu
            );
        }
        System.out.println("--------------------------------------");
    }
    public static void showOrderMachine(ArrayList<Object[]> machineData){
        System.out.println("-------------------------");
        System.out.printf("ID\tCity\n");
        System.out.println("-------------------------");
        for (Object[] info : machineData){
            System.out.printf("%-3s\t%s\n",
                    info[0].toString().substring(3,5) + info[0].toString().substring(8,9),
                    info[1]
            );
        }
        System.out.println("-------------------------");
    }

    /*
    sorting data value
    true => min to max
    false => max to min
     */
    public static void sortValue(ArrayList<Object[]> dataSet, int index, boolean isSort) {
        for (int i = 0; i < dataSet.size() - 1; i++) {
            for (int j = 0; j < dataSet.size() - i - 1; j++) {
                Object[] i_data = dataSet.get(j);
                Object[] j_data = dataSet.get(j + 1);
                if (isSort && Double.parseDouble(i_data[index].toString()) > Double.parseDouble(j_data[index].toString())) {
                    Collections.swap(dataSet, j, j + 1);
                }
                if (!isSort && Double.parseDouble(i_data[index].toString()) < Double.parseDouble(j_data[index].toString())) {
                    Collections.swap(dataSet, j, j + 1);
                }
            }
        }
    }

    /*
    sorting data alphabet
    true => A to Z
    false => Z to A
    */
    public static void sortAlphabet(ArrayList<Object[]> dataSet, int index, boolean isSort) {
        for (int i = 0; i < dataSet.size() - 1; i++) {
            for (int j = 0; j < dataSet.size() - i - 1; j++) {
                Object[] i_data = dataSet.get(j);
                Object[] j_data = dataSet.get(j + 1);
                if (isSort && i_data[index].toString().compareTo(j_data[index].toString()) > 0) {
                    Collections.swap(dataSet, j, j + 1);
                }
                if (!isSort && i_data[index].toString().compareTo(j_data[index].toString()) < 0) {
                    Collections.swap(dataSet, j, j + 1);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        readMachineData();
        readLoginData();
        readMenuData();
        readOrderData();
        String choice;
        do {
            System.out.print(
                    "-------------------\n" +
                            "  SE BUU Drink\n" +
                            "-- -----------------\n" +
                            "1. Ordering your drink\n" +
                            "2. PIN Check\n" +
                            "3. Most popular drink\n" +
                            "4. Virtual machine\n"+
                            "5. Login\n" +
                            "6. Exit\n" +
                            "-------------------\n" +
                            "Enter Number : "
            );
            choice = input.next();
            System.out.println("-------------------");
            switch (choice) {
                case "1":
                    addOrder();
                    break;
                case "2":
                    checkPIN();
                    break;
                case "3":
                    mostPopular();
                    break;
                case "4":
                    virtual();
                    break;
                case "5":
                    loginMenu();
                    break;
                case "6":
                    break;
            }
        } while (!choice.equals("6"));
        System.out.println("Thank you my bro");
    }
    public static void checkPIN()throws IOException{
        Scanner inputpin = new Scanner(System.in);
        System.out.println(
                "-------------------\n" +
                        "     PIN Check\n" +
                        "-------------------"
        );
        System.out.print("Enter PIN : ");
        String pinCheck = inputpin.next();
        System.out.println("-------------------");
        boolean havePIN = false;
        Object[] dataPIN = null;
        for (int i = 0; i < orderData.size(); i++) {
            dataPIN = orderData.get(i);
            if (dataPIN[4].equals(pinCheck)) {
                havePIN = true;
                break;
            }
        }
        if (!havePIN) {
            System.out.println("Invalid PIN.");
            System.out.println("-------------------");
            return;
        }
        String menu = "";
        for (int i = 0; i < menuData.size(); i++) {
            Object[] dataMenu = menuData.get(i);
            if (dataMenu[0].equals(dataPIN[1])) {
                menu = dataMenu[1].toString();
                break;
            }
        }
        System.out.println("Menu : " + menu);
        System.out.println("Status : " + (dataPIN[5].toString().equals("1") ? "Used" : "Not yet used"));
        System.out.println("-------------------");
    }
    public static void addOrder() throws IOException {
        Scanner inputOrder = new Scanner(System.in);
        sortValue(menuData, 2, true);
        showMenu(menuData);
        System.out.print("Enter menu ID : ");
        String menuID = inputOrder.next();
        System.out.println("-------------------");
        boolean findID = false;
        for (int i = 0; i < menuData.size(); i++) {
            Object[] info = menuData.get(i);
            if (info[0].equals(menuID)) {
                findID = true;
                break;
            }
        }

        if (!findID) {
            System.out.println("menu ID incorrect!!!");
            return;
        }
        System.out.print("Enter Tel. : ");
        String telephone = inputOrder.next();
        System.out.println("-------------------");
        if (telephone.length() != 10) {
            System.out.println("Your telephone is error!!!");
            return;
        }
        Random random = new Random();
        String PIN = String.valueOf(String.format("%04d", random.nextInt(9999))) +
                (char) (random.nextInt(26) + 'A') + (char) (random.nextInt(26) + 'A');

        System.out.println("Your PIN is : " + PIN);
        System.out.println("-------------------");
        Object[] infoOrder = {
                Integer.parseInt(orderData.get(orderData.size() - 1)[0].toString()) + 1,
                menuID,
                "-",
                telephone.substring(0, 3) + "-" + telephone.substring(3, 6) + "-" + telephone.substring(6),
                PIN,
                "0",
                "-"
        };
        orderData.add(infoOrder);
        FileWriter writer_order = new FileWriter("src/Order.txt", true);
        PrintWriter add_order = new PrintWriter(writer_order);
        add_order.println();
        for (Object data : infoOrder) {
            add_order.print(data + "\t");

        }
        add_order.close();
        System.out.println("Your order has been successfully ordered.");
    }
    // case 3 in main
    public static void mostPopular() {
        Scanner inputmost = new Scanner(System.in);
        System.out.print("-------------------\n" +
                "Most popular drink\n" +
                "-------------------\n" +
                "1. For-Men\n" +
                "2. For-Women\n" +
                "3. For-All\n" +
                "4. Exit\n" +
                "--------------------\n" +
                "Enter Number : ");
        String choice = inputmost.next();
        System.out.println("-------------------");
        switch (choice) {
            case "1":
                findTop("M");
                break;
            case "2":
                findTop("F");
                break;
            case "3":
                findTop("A");
                break;
            case "4":
                break;
        }
    }
    // use in findTop method
    private static String getMenu(String menuID) {
        for (int i = 0; i < menuData.size(); i++) {
            Object[] data = menuData.get(i);
            if (data[0].toString().equals(menuID)) {
                return data[1].toString();
            }
        }
        return "";
    }
    private static String getPrice(String nameMenu){
        for (int i = 0 ; i < menuData.size() ; i++){
            Object[] data = menuData.get(i);
            if(nameMenu.equals(data[1])){
                return data[2].toString();
            }
        }
        return "";
    }
    // use in findTop method
    private static String getMenuID(String menuName){
        for (int i = 0; i < menuData.size(); i++) {
            Object[] data = menuData.get(i);
            if(data[1].equals(menuName)){
                return data[0].toString();
            }
        }
        return "";
    }
    // use in findTop method
    private static String getMenuGender(String menuID) {
        for (Object[] data : menuData) {
            if (data[0].toString().equals(menuID)) {
                return data[4].toString();
            }
        }
        return "";
    }
    /*
    data[4] = gender
    orderInfo[1] = menuID
    use in mostPopular method
    */
    public static void findTop(String gender) {
        // keep order and count of order.
        Map<String, Integer> menuItemOrderCounts = new HashMap<>();
        /* loop All_order and keep order to  menuItemOrderCounts if order without,
        menuItemOrderCounts make new order. */
        for (Object[] orderInfo : orderData) { // orderInfo[1] => menuID
            String checkGender = getMenuGender(orderInfo[1].toString());
            if (checkGender != null && checkGender.equalsIgnoreCase(gender)) {
                String menu = getMenu(orderInfo[1].toString());
                /*Helps prevent errors and check (String menu) Has it ever existed?
                If it has ever existed, it will add the value to 1.
                If it has never existed before,
                it will create and set the value to 0.*/
                menuItemOrderCounts.putIfAbsent(menu, 0);
                /* add order count 0f (String menu) to menuItemOrderCounts.
                by adding original value by 1 */
                menuItemOrderCounts.put(menu, menuItemOrderCounts.get(menu) + 1);
            }
        }

        // Create a list of the top 3 menu items, sorted by order count.
        List<String> topMenuItems = menuItemOrderCounts.entrySet()
                .stream() // Transform into a menuItemOrderCounts.entrySet() to be able to use sorted() and limit().
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue())) // sort order max -> min
                .limit(3)// Limiting the number of returned items to 3.
                .map(Map.Entry::getKey)// convert to new
                .collect(Collectors.toList());// keep to new List<String>

        // Print the top 3 menu items.
        System.out.println("-------------------");
        System.out.println("Top 3 " + (gender.equals("M") ? "For-Men" : (gender.equals("F") ? "For-Women" : "For-All")));
        System.out.println("-------------------");
        int count = 1;
        for (String menuItem : topMenuItems) {
            if (count > 3) {
                break;
            }
            System.out.printf("# %d : %s %s%n", count, getMenuID(menuItem), menuItem);
            count++;
        }
        System.out.println("-------------------");
    }

    public static void virtual() throws IOException {
        Scanner inputvirtual = new Scanner(System.in);
        showOrderMachine(machineData);
        System.out.print("Enter Machine ID : ");
        String MachineID = inputvirtual.next();
        System.out.println("-------------------------");
        Object[] dataMachine;
        boolean haveID = false;
        for (int i = 0; i < machineData.size();i++){
            dataMachine = machineData.get(i);
            String id = dataMachine[0].toString().substring(3,5) + dataMachine[0].toString().substring(8,9);
            if(id.equals(MachineID)) {
                haveID = true;
                System.out.println("Machine ID : " + id + " (" + dataMachine[1] + ")");
            }
        }
        if(!haveID){
            System.out.println("Machine ID is incorrect.");
            return;
        }
        System.out.println("-------------------------");
        System.out.print(
                "1. Use your PIN to get a drink.\n"+
                "2. Recommend the drink menu.\n"+
                "3. Exit\n"+
                "-------------------------\n"+
                "Enter Number : "
        );
        String choice = inputvirtual.next();
        System.out.println("-------------------------");
        switch (choice){
            case "1":
                boolean havePIN = false;
                System.out.print("Enter your PIN : ");
                String PIN = inputvirtual.next();
                System.out.println("-------------------------");
                for (int i = 0; i < orderData.size(); i++) {
                    Object[] data = orderData.get(i);
                    if (data[4].toString().equals(PIN)) {
                        if(!data[5].toString().equals("1") && data[2].toString().equals("-")) {
                            havePIN = true;
                            updateOrder(PIN, MachineID);
                            updataMachinBalance(PIN, MachineID);
                        }
                    }
                }
                if(!havePIN){
                    System.out.println("Invalid PIN.");
                }
            case "2":
                reccommend();
                break;
            case "3":
                break;
        }
    }
    public static void reccommend(){
        Scanner inputvirtual = new Scanner(System.in);
        String reccommend;
        do {
            System.out.print("-------------------------\n" +
                    "Recommend the drink menu\n" +
                    "-------------------------\n" +
                    "1. By Age\n" +
                    "2. By Gender\n" +
                    "3. By Type\n" +
                    "4. By Keywords\n" +
                    "5. Exit\n" +
                    "-------------------------\n" +
                    "Enter Number : "
            );
           reccommend = inputvirtual.next();
            System.out.println("-------------------------");
            switch (reccommend) {
                case "1":
                    System.out.print("Enter your Age : ");
                    String age = inputvirtual.next();
                    System.out.println("-------------------------");
                    System.out.printf("ID\tMenu\n");
                    System.out.println("-------------------------");
                    for (int i = 0; i < menuData.size(); i++) {
                        Object[] data = menuData.get(i);
                        if ((Integer.parseInt(age ) >= Integer.parseInt(data[3].toString()))) {
                            System.out.printf("%-3s\t%-20s\n", data[0].toString(), data[1].toString());
                        }
                    }
                    System.out.println("-------------------------");
                    break;
                case "2":
                    System.out.print("Enter your gender (F/M) : ");
                    String gender = inputvirtual.next();
                    System.out.println("-------------------------");
                    System.out.printf("ID\tMenu\n");
                    System.out.println("-------------------------");
                    for (int i = 0; i < menuData.size(); i++) {
                        Object[] data = menuData.get(i);
                        if (gender.equals(data[4])) {
                            System.out.printf("%-3s\t%-20s\n", data[0].toString(), data[1].toString());
                        }
                    }
                    System.out.println("-------------------------");
                    for (int i = 0; i < menuData.size(); i++) {
                        Object[] data = menuData.get(i);
                        if (!gender.equals(data[4])) {
                            System.out.printf("%-3s\t%-20s\n", data[0].toString(), data[1].toString());
                        }
                    }
                    break;
                case "3":
                    System.out.print("Enter type (SD/HD) : ");
                    String type = inputvirtual.next();
                    System.out.println("-------------------------");
                    System.out.printf("ID\tMenu\n");
                    System.out.println("-------------------------");
                    for (int i = 0; i < menuData.size(); i++) {
                        Object[] data = menuData.get(i);
                        if (data[5].equals(type)) {
                            System.out.printf("%-3s\t%s\n", data[0].toString(), data[1].toString());
                        }
                    }
                    System.out.println("-------------------------");
                    break;
                case "4":
                    System.out.print("Enter keyword : ");
                    String key = inputvirtual.next();
                    System.out.println("-------------------------");
                    System.out.printf("ID\tMenu\n");
                    System.out.println("-------------------------");
                    for (int i = 0; i < menuData.size(); i++) {
                        Object[] data = menuData.get(i);
                        if(data[6].toString().toLowerCase().contains(key.toLowerCase())) {
                            System.out.printf("%-3s\t%-20s\n", data[0].toString(), data[1].toString());
                        }
                    }
                    System.out.println("-------------------------");
                    break;
                case "5":
                    break;
            }
        }while (!reccommend.equals("5"));
    }
    private static void  updateOrder(String PIN, String id)throws IOException{
        ArrayList keep = new ArrayList<>();
        Object[] dataMachine;
        for (int i = 0; i < orderData.size(); i++) {
            Object[] data = orderData.get(i);
            String update = "";
            if(data[4].toString().equals(PIN)){
                if(!data[5].toString().equals("1") && data[2].toString().equals("-")){
                    data[5] = "1";
                    Date dateUse = new Date();
                    SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd");
                    String today = date.format(dateUse);
                    String here = null;
                    for (int j = 0 ; j < machineData.size();j++){
                        dataMachine = machineData.get(j);
                        String machineId = dataMachine[0].toString().substring(3, 5) + dataMachine[0].toString().substring(8, 9);
                        if (machineId.equals(id)) {
                            here = dataMachine[0].toString();
                        }
                    }
                    data[2] = here;
                    data[6] = today;
                    update = data[0].toString()+"\t"+data[1].toString()+"\t"+data[2]+"\t"
                            +data[3]+"\t"+data[4]+"\t"+data[5]+"\t"+data[6];
                } else if (data[5].toString().equals("1")) {
                    System.out.println("Invalid PIN.");
                }
            }else {
                update = data[0].toString() + "\t" + data[1].toString() + "\t" + data[2] + "\t"
                        + data[3] + "\t" + data[4] + "\t" + data[5] + "\t" + data[6];
            }
            keep.add(update);
        }
        FileWriter writerOrder = new FileWriter("src/Order.txt");
        PrintWriter updateOrder = new PrintWriter(writerOrder) ;
        for (int i = 0; i < keep.size(); i++) {
            updateOrder.print(String.valueOf(keep.get(i)));
            updateOrder.println();
        }
        updateOrder.close();
        System.out.println("Your "+getMenuOrder(PIN)+" is currently in the process of being prepared, please wait a moment.");
    }
    private static void updataMachinBalance(String PIN, String id)throws IOException{
        ArrayList<String> pay = new ArrayList<>();
        String getMoney = "";
        DecimalFormat df = new DecimalFormat("#,###.00");
        for (int i = 0; i < machineData.size(); i++) {
            Object[] data = machineData.get(i);
            String machineId = data[0].toString().substring(3, 5) + data[0].toString().substring(8, 9);
            double balanceUSD = Double.parseDouble(data[5].toString().replace(",",""));
            data[5] = df.format(balanceUSD);
            if(machineId.equals(id)) {
                double balanceBaht = (Double.parseDouble(getPrice(getMenuOrder(PIN)))) / (36.96);
                double balanceTotal = balanceBaht + balanceUSD;
                data[5] = df.format(balanceTotal);
                getMoney = data[0].toString() + "\t" + data[1].toString() + "\t" + data[2] + ", "
                        + data[3] + "\t" + data[4] + "\t" +"$"+ data[5]+"\t"+data[6];
            }else {
                getMoney = data[0].toString() + "\t" + data[1].toString() + "\t" + data[2] + ", "
                        + data[3] + "\t" + data[4] + "\t" +"$"+ data[5]+"\t"+data[6];
            }
            pay.add(getMoney);
        }
        FileWriter writerMachine = new FileWriter("src/Machine.txt");
        PrintWriter updateBalacne = new PrintWriter(writerMachine) ;
        for (int i = 0; i < pay.size(); i++) {
            updateBalacne.print(String.valueOf(pay.get(i)));
            
        }
        updateBalacne.close();
    }
    private static String getMenuOrder(String PIN){
        String keep = "";
        for (int i = 0; i < orderData.size(); i++) {
            Object[] data = orderData.get(i);
            if(PIN.equals(data[4].toString())){
                keep = data[1].toString();
            }
        }
        for (int i = 0; i < menuData.size(); i++) {
            Object[] info = menuData.get(i);
            if (info[0].equals(keep)) {
                return info[1].toString();
            }
        }
        return "";
    }

    public static void loginMenu() throws IOException{
        Scanner inputLogin = new Scanner(System.in);
        String User;
        String Pass;
        int count = 0;
        String choice;
        do {
            System.out.println("-------------------");
            System.out.println("        Login");
            System.out.println("-------------------");
            System.out.print("Username : ");
            User = inputLogin.next();
            System.out.print("Password : ");
            Pass = inputLogin.next();
            System.out.println("-------------------");
            Scanner fileLogin = new Scanner(new File("src/Login.txt"));
            while (fileLogin.hasNextLine()) {
                String line = fileLogin.nextLine();
                String[] infoLogin = line.split("\t");
                String name = infoLogin[1];
                String email = infoLogin[2];
                String fullPassword = infoLogin[3];
                String telaphone = infoLogin[4];
                String[] partname = name.split(" ");
                String firstname = partname[0].charAt(0) + ". ";
                String lastname = partname[1];
                name = firstname + lastname;
                String password = fullPassword.substring(3, 5) + fullPassword.substring(11, 15);
                String checkExpire = fullPassword.substring(8, 9);
                //Show Login interface when requirement is true
                if (User.equals(email) && Pass.equals(password)) {
                    if (checkExpire.equals("1")) {
                        do {
                            System.out.println("Welcome : " + name);
                            System.out.println("Email : " + email);
                            System.out.println("Tel : " + telaphone.substring(0, 8) + "xxxx");
                            System.out.println("-------------------");
                            System.out.println("        Menu");
                            System.out.println("-------------------");
                            System.out.println("1. Machine details");
                            System.out.println("2. Add user");
                            System.out.println("3. Edit user");
                            System.out.println("4. Exit");
                            System.out.println("-------------------");
                            System.out.print("Enter Number (1-4) : ");
                            choice = inputLogin.next();
                            System.out.println("-------------------");
                            switch (choice) {
                                case "1":
                                     String menu;
                                    System.out.println("-------------------");
                                    System.out.println("        Menu");
                                    System.out.println("-------------------");
                                    System.out.println("1. Sorting by balance (DESC)");
                                    System.out.println("2. Sorting by by city (ASC)");
                                    System.out.println("3. Return to main menu");
                                    System.out.println("-------------------");
                                    System.out.print("Enter Number (1-2) : ");
                                    menu = inputLogin.next();
                                    System.out.println("-------------------");
                                    // sort balance from max to min
                                    switch (menu){
                                        case "1":
                                            sortValue(machineData,5,false);
                                            showMachine(machineData);
                                            break;
                                        case "2":
                                            sortAlphabet(machineData,1,true);
                                            showMachine(machineData);
                                            break;
                                        case "3":
                                            break;
                                    }
                                    break;
                                case "2":
                                    addUser();
                                    break;
                                case"3":
                                    editUser();
                                    break;
                            }
                        } while (!choice.equals("4"));
                        System.out.println("Thank you for using the service.");
                        return;
                    } else {
                        System.out.println("The users account has expired");
                        return;
                    }
                }
            }
            fileLogin.close();
            count++;// นับจำนวนรอบที่เข้าใช้งาน
            System.out.println("The username or password is incorrect. (" + count + ")");
        } while (count != 3);
        System.out.println("Thank you for using the service.");
    }

    // choice case: 3 : add user in Login.text, use in loginMenu method
    public static void addUser() throws IOException {
        Scanner inputUser = new Scanner(System.in);
        System.out.print("Enter First name : ");
        String firstname = inputUser.next();
        System.out.print("Enter Last name : ");
        String lastname = inputUser.next();
        System.out.print("Enter Email : ");
        String email = inputUser.next();
        System.out.print("Enter Password : ");
        String password = inputUser.next();
        System.out.print("Confirm Password : ");
        String cfpassword = inputUser.next();
        System.out.print("Enter Telaphone : ");
        String tel = inputUser.next();
        boolean isCheck = true;

        // firstname >= 2
        if (firstname.length() < 2) {
            isCheck = false;
        }
        // lastname >= 2
        if (lastname.length() < 2) {
            isCheck = false;
        }
        //password = 6
        if (password.length() != 6) {
            isCheck = false;
        }
        // confirm = password = 6
        if (cfpassword.length() != 6 || !cfpassword.equals(password)) {
            isCheck = false;
        }
        // telaphone = 10
        if (tel.length() != 10) {
            isCheck = false;
        }
        // email have @
        if (!email.contains("@")) {
            isCheck = false;
        }

        // All of isCheck = true
        if (isCheck) {
            // format of telaphone
            String telaphone = tel.substring(0, 3) + "-" + tel.substring(3, 6)
            + "-" + tel.substring(6);
            FileWriter writer_user = new FileWriter("src/Login.txt", true);
            PrintWriter add_user = new PrintWriter(writer_user);
            add_user.print("\n" +( Integer.parseInt(loginData.get(loginData.size() - 1)[0].toString()) + 1) +
                    "\t" + firstname + " " + lastname + "\t" + email + "\t" + fullPassword(password) + "\t" + telaphone);
            add_user.close();
            System.out.println("The user added sucessfully");
        } else {
            System.out.println("The user addition failed");
        }
    }
    // random Full.password : use in addUser method
    public static String fullPassword(String password) {
        String full = "";
        if (password.length() == 6) {
            for (int i = 0; i < 18; i++) {
                Random random = new Random();
                char alphabet = (char) (random.nextInt(26) + 'A');
                switch (i) {
                    case 3:
                        full += password.charAt(0);
                        break;
                    case 4:
                        full += password.charAt(1);
                        break;
                    case 11:
                        full += password.charAt(2);
                        break;
                    case 12:
                        full += password.charAt(3);
                        break;
                    case 13:
                        full += password.charAt(4);
                        break;
                    case 14:
                        full += password.charAt(5);
                        break;
                    case 8:
                        full += '1';
                        break;
                    default:
                        full += alphabet;
                        break;
                }
            }
        }
        return full;
    }

    // choice case: 2 : use in loginMenu method
    public static void editUser() throws IOException {
        Scanner inputedit = new Scanner(System.in);
        System.out.println("-------------------");
        System.out.print("Enter member ID : ");
        String id_edit = inputedit.next();
        if (findID(id_edit)) {
            System.out.println("Valid Member ID.");
            System.out.println("-------------------");
            System.out.print("1. Edit user status\n" +
                    "2. Reset password\n" +
                    "3. Return to main menu\n" +
                    "-------------------\n" +
                    "Enter Number (1-3) : ");
            String choiceEdit = inputedit.next();
            System.out.println("-------------------");
            switch (choiceEdit) {
                case "1":
                    System.out.print("3.1 Edit user status\n" +
                            "-------------------\n" +
                            "1. Active\n" +
                            "2. Non-active\n" +
                            "-------------------\n" +
                            "Enter Number (1-2) : ");
                    String edit_status = inputedit.next();
                    if (edit_status.equals("1")) {
                        isActive(id_edit, true);
                    } else if (edit_status.equals("2")) {
                        isActive(id_edit, false);
                    } else {
                        break;
                    }
                    System.out.println("This user status has been successfully edit.");
                    break;
                case "2":
                    System.out.print("3.2 Reset password\n" +
                            "-------------------\n" +
                            "Are you sure about resetting this password? \n" +
                            "-------------------\n" +
                            "Enter Number (Y/N) : ");
                    String reset_password = inputedit.next();
                    if (reset_password.equals("Y")) {
                        System.out.println("This password has been successfully reset. =>  New password is "
                         + resetPassword(id_edit));
                    } else if (reset_password.equals("N")) {
                        break;
                    }
                case "3":
                    break;
            }
        } else {
            System.out.println("Invalid Member ID.!!!!");
        }
    }
    // use in edit method
    public static boolean findID(String id_edit) throws IOException {
        Scanner findID = new Scanner(new File("src/Login.txt"));
        boolean check = false;
        while (findID.hasNextLine()) {
            String line = findID.nextLine();
            String[] info = line.split("\t");
            String id = info[0];
            if (id.equals(id_edit)) {
                check = true;
            }
        }
        return check;
    }
    // change active of password,use in editUser method
    public static void isActive(String ID_edit, boolean status) throws IOException {
        Scanner is_active = new Scanner(new File("src/Login.txt"));
        String keep = "";
        while (is_active.hasNextLine()) {
            String line = is_active.nextLine();
            String[] info = line.split("\t");
            String id = info[0];
            String name = info[1];
            String email = info[2];
            String password = info[3];
            String tel = info[4];
            String output = "";
            if (ID_edit.equals(id)) {
                output = id + "\t" + name + "\t" + email + "\t" + password.substring(0, 8) +
                (status ? "1" : "0") + password.substring(9) + "\t" + tel + (is_active.hasNextLine() ? "\n" : "");
            } else {
                output = line + (is_active.hasNextLine() ? "\n" : "");
            }
            keep += output;
        }
        FileWriter file_active = new FileWriter("src/Login.txt", false);
        PrintWriter edit_active = new PrintWriter(file_active);
        edit_active.print(keep);
        edit_active.close();
    }
    /*
    Reset.password 6 digit
    keep = give a StringAll
    use in editUser method*/
    public static String resetPassword(String ID_edit) throws IOException {
        Scanner reset_pass = new Scanner(new File("src/Login.txt"));
        String newPassword = "";
        String keep = ""; // keep output
        while (reset_pass.hasNextLine()) {
            String line = reset_pass.nextLine();
            String[] info = line.split("\t");
            String id = info[0];
            String name = info[1];
            String email = info[2];
            String password = info[3];
            String tel = info[4];
            String output = ""; // format String
            if (ID_edit.equals(id)) {
                Random pass = new Random();
                int random = pass.nextInt(999999);
                String newRandom = String.format("%06d", random);
                newPassword = password.substring(0, 3) + newRandom.substring(0, 2) +
                password.substring(5, 11) + newRandom.substring(2) + password.substring(15);
                output = id + "\t" + name + "\t" + email + "\t" + newPassword + "\t" + tel + (reset_pass.hasNextLine() ? "\n" : "");
            } else {
                output = line + (reset_pass.hasNextLine() ? "\n" : "");
            }
            keep += output;
        }
        FileWriter file_reset = new FileWriter("src/Login.txt", false);
        PrintWriter edit_reset = new PrintWriter(file_reset);
        edit_reset.print(keep);
        edit_reset.close();
        return newPassword.substring(3, 5) + newPassword.substring(11, 15);
    }
}