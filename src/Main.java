import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SubjectManager subjectManager;
        if (WriteReadFile.loadSubject() != null) {
            subjectManager = new SubjectManager(WriteReadFile.loadSubject());
        } else {
            subjectManager = new SubjectManager(new ArrayList<>());
        }
        BillManager billManager = new BillManager(subjectManager);
        if (WriteReadFile.loadBill(subjectManager) != null) {
            billManager.setBills(WriteReadFile.loadBill(subjectManager));
        }
        BillCalculator billCalculator = new BillCalculator(billManager);
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1.Add bill");
            System.out.println("2.Display all bills of list");
            System.out.println("3.Display all bills by date or month or year or time");
            System.out.println("4.Search bills by name,subject,date,time");
            System.out.println("5.Delete bills");
            System.out.println("6.Update bills");
            System.out.println("7.Menu to subject");
            System.out.println("8.Summary spent money display by date,month,year ");
            System.out.println("0.Exit");
            System.out.println("Enter your choice");
            choice = TryCatchAndRegex.tryCatchInt(scanner);
            switch (choice) {
                case 1:
                    billManager.addBills(scanner);
                    break;
                case 2:
                    billManager.displayBill(billManager.getBills());
                    break;
                case 3:
                    choiceDisplayBill(billManager, scanner);
                    break;
                case 4:
                    billManager.searchBill(scanner);
                    break;
                case 5:
                    billManager.deleteBills(scanner);
                    break;
                case 6:
                    billManager.updateBills(scanner);
                    break;
                case 7:
                    menuOfSubject(subjectManager, scanner, billManager);
                    break;
                case 8:
                    menuOfSummarySpentMoney(billCalculator, scanner);
                case 0:
                    WriteReadFile.saveBill(billManager.getBills(), "Bill");
                    WriteReadFile.saveBill(subjectManager.getSubjects(), "Subject");
            }

        } while (choice != 0);
    }

    private static void choiceDisplayBill(BillManager billManager, Scanner scanner) {
        int choice;
        do {
            System.out.println("MENU:");
            System.out.println("1.Display all bills by date");
            System.out.println("2.Display all bills by month");
            System.out.println("3.Display all bills by year");
            System.out.println("4.Display all bills by time");
            System.out.println("0. Exit");
            System.out.println("Enter your choice: ");
            choice = TryCatchAndRegex.tryCatchInt(scanner);
            switch (choice) {
                case 1:
                    LocalDate date = TryCatchAndRegex.tryCatchDate(scanner);
                    if (date != null) {
                        System.out.println("List of bills in day:" + date);
                        billManager.displayBill(billManager.displayBillByDate(date, billManager.getBills()));
                    }
                    break;
                case 2:
                    LocalDate date1 = TryCatchAndRegex.tryCatchMonth(scanner);
                    if (date1 != null) {
                        System.out.println("List of bills in month:" + date1.getMonthValue() + "/" + date1.getYear());
                        billManager.displayBill(billManager.displayBillByMonth(date1, billManager.getBills()));
                    }
                    break;
                case 3:
                    System.out.println("Enter the year of bills you want to display");
                    int year = TryCatchAndRegex.tryCatchInt(scanner);
                    System.out.println("List of bills in year:" + year);
                    billManager.displayBill(billManager.displayBillByYear(year, billManager.getBills()));
                    break;
                case 4:
                    LocalTime time = TryCatchAndRegex.tryCatchTime(scanner);
                    System.out.println("List of bills with time: " + time);
                    billManager.displayBill(billManager.displayBillByTime(time, billManager.getBills()));
                    break;
            }
        } while (choice != 0);
    }

    public static void menuOfSubject(SubjectManager subjectManager, Scanner scanner, BillManager billManager) {
        int choice;
        do {
            System.out.println("MENU:");
            System.out.println("1.Display all subject");
            System.out.println("2.Search subject by name");
            System.out.println("3.Add subject");
            System.out.println("4.Delete subject");
            System.out.println("5.Update subject");
            System.out.println("0. Exit");
            System.out.println("Enter your choice: ");
            choice = TryCatchAndRegex.tryCatchInt(scanner);
            switch (choice) {
                case 1:
                    subjectManager.displaySubject();
                    break;
                case 2:
                    System.out.println("Enter subject's name to search");
                    String name = TryCatchAndRegex.tryCatchString(scanner);
                    if (!(name.equals(""))) {
                        System.out.println(subjectManager.searchSubject(name).getName());
                    } else {
                        System.out.println("Nothing to search");
                    }
                    break;
                case 3:
                    subjectManager.addSubject(scanner);
                    break;
                case 4:
                    subjectManager.deleteCategory(scanner, billManager.getBills());
                    break;
                case 5:
                    subjectManager.editSubjectByName(scanner);
                    break;
            }
        } while (choice != 0);
    }

    public static void menuOfSummarySpentMoney(BillCalculator billCalculator, Scanner scanner) {
        int choice;
        do {
            System.out.println("MENU:");
            System.out.println("1.Display money spent by date");
            System.out.println("2.Display money spent by month");
            System.out.println("3.Display money spent by year");
            System.out.println("0. Exit");
            System.out.println("Enter your choice: ");
            choice = TryCatchAndRegex.tryCatchInt(scanner);
            switch (choice) {
                case 1:
                    billCalculator.calculateSpendingByDay(scanner);
                    break;
                case 2:
                    billCalculator.calculateSpendingByMonth(scanner);
                    break;
                case 3:
                    billCalculator.calculateSpendingByYear(scanner);
                    break;
            }
        } while (choice != 0);
    }
}
