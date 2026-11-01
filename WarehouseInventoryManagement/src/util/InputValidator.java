package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Utility class for console input validation.
 *
 * @author Group4_SE2023
 */
public class InputValidator {

    private static Scanner scanner = new Scanner(System.in);

    private static final DateTimeFormatter[] DATE_FORMATTERS = new DateTimeFormatter[]{
        DateTimeFormatter.ofPattern("dd/MM/yyyy"),
        DateTimeFormatter.ofPattern("d/M/yyyy"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd")
    };

    public static void setScanner(Scanner customScanner) {
        if (customScanner != null) {
            scanner = customScanner;
        }
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public static String readNonEmptyString(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine();
                if (line != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        return line;
                    }
                }
                System.out.println(">> Error: Input cannot be empty! Please try again.");
            } catch (NoSuchElementException e) {
                return "";
            }
        }
    }

    public static String readString(String prompt) {
        try {
            System.out.print(prompt);
            String line = scanner.nextLine();
            return (line != null) ? line.trim() : "";
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public static int readPositiveInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine();
                if (line != null) {
                    int value = Integer.parseInt(line.trim());
                    if (value > 0) {
                        return value;
                    }
                    System.out.println(">> Error: Value must be a positive integer (> 0)! Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println(">> Error: Invalid format, please enter an integer!");
            } catch (NoSuchElementException e) {
                return 0;
            }
        }
    }

    public static int readNonNegativeInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine();
                if (line != null) {
                    int value = Integer.parseInt(line.trim());
                    if (value >= 0) {
                        return value;
                    }
                    System.out.println(">> Error: Value cannot be negative (>= 0)! Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println(">> Error: Invalid format, please enter an integer!");
            } catch (NoSuchElementException e) {
                return 0;
            }
        }
    }

    public static int readIntInRange(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine();
                if (line != null) {
                    int value = Integer.parseInt(line.trim());
                    if (value >= min && value <= max) {
                        return value;
                    }
                    System.out.printf(">> Error: Please choose a number between [%d - %d]!%n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println(">> Error: Invalid format, please enter an integer!");
            } catch (NoSuchElementException e) {
                return min;
            }
        }
    }

    public static double readPositiveDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine();
                if (line != null) {
                    double value = Double.parseDouble(line.trim());
                    if (value > 0.0) {
                        return value;
                    }
                    System.out.println(">> Error: Value must be greater than 0 (> 0)! Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println(">> Error: Invalid format, please enter a valid decimal number!");
            } catch (NoSuchElementException e) {
                return 0.0;
            }
        }
    }

    public static double readNonNegativeDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine();
                if (line != null) {
                    double value = Double.parseDouble(line.trim());
                    if (value >= 0.0) {
                        return value;
                    }
                    System.out.println(">> Error: Value cannot be negative (>= 0)! Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println(">> Error: Invalid format, please enter a valid decimal number!");
            } catch (NoSuchElementException e) {
                return 0.0;
            }
        }
    }

    public static LocalDate readValidDate(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine();
                if (line != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
                            try {
                                LocalDate date = LocalDate.parse(line, formatter);
                                // Ensure strict calendar date validation (e.g., prevent 31/02 being resolved to 29/02)
                                String reformatted = date.format(formatter);
                                String[] inputParts = line.split("[/-]");
                                String[] reformattedParts = reformatted.split("[/-]");
                                if (inputParts.length == 3 && reformattedParts.length == 3) {
                                    // Compare day, month, year numbers
                                    int inputDay = Integer.parseInt(inputParts[0].length() == 4 ? inputParts[2] : inputParts[0]);
                                    int inputMonth = Integer.parseInt(inputParts[1]);
                                    int inputYear = Integer.parseInt(inputParts[0].length() == 4 ? inputParts[0] : inputParts[2]);

                                    if (date.getDayOfMonth() == inputDay && date.getMonthValue() == inputMonth && date.getYear() == inputYear) {
                                        return date;
                                    }
                                }
                            } catch (NumberFormatException e) {
                            }
                        }
                    }
                }
                System.out.println(">> Error: Invalid date! Please enter in dd/MM/yyyy (e.g. 25/12/2024) or yyyy-MM-dd format.");
            } catch (NoSuchElementException e) {
                return LocalDate.now();
            }
        }
    }

    public static LocalDate readExpiryDate(String prompt, LocalDate manufacturingDate) {
        while (true) {
            LocalDate expDate = readValidDate(prompt);
            if (manufacturingDate == null || !expDate.isBefore(manufacturingDate)) {
                return expDate;
            }
            System.out.printf(">> Error: Expiry date (%s) cannot be before manufacturing date (%s)! Please try again.%n",
                    expDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    manufacturingDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
    }

    public static boolean readConfirmation(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine();
                if (line != null) {
                    line = line.trim().toLowerCase();
                    if (line.equals("y") || line.equals("yes")) {
                        return true;
                    }
                    if (line.equals("n") || line.equals("no")) {
                        return false;
                    }
                }
                System.out.println(">> Please enter 'Y' (Yes) or 'N' (No)!");
            } catch (NoSuchElementException e) {
                return false;
            }
        }
    }
}
