import java.io.*;
import java.time.*;
import java.util.*;
public class InterruptController {
private static final int KEYBOARD_PRIORITY = 3;
private static final int MOUSE_PRIORITY = 2;
private static final int PRINTER_PRIORITY = 1;
private static boolean keyboardInterrupt = false;
private static boolean mouseInterrupt = false;
private static boolean printerInterrupt = false;
private static boolean maskKeyboard = false;
private static boolean maskMouse = false;
private static boolean maskPrinter = false;
private static final Object lock = new Object();
private static void logEvent(String device) {
try (FileWriter fw = new FileWriter("interrupt_log.txt", true)) {
LocalTime now = LocalTime.now();
fw.write(String.format("[%02d:%02d:%02d] %s Interrupt handled successfully\n",
now.getHour(), now.getMinute(), now.getSecond(), device));
} catch (IOException e) {
e.printStackTrace();
}
}
private static void keyboardISR() {
if (maskKeyboard) {
System.out.println("Keyboard Interrupt Ignored (Masked)");
return;
}
System.out.print("Keyboard Interrupt Triggered → Handling ISR");
try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
System.out.println(" → Completed");
logEvent("Keyboard");
}
private static void mouseISR() {
if (maskMouse) {
System.out.println("Mouse Interrupt Ignored (Masked)");
return;
}
System.out.print("Mouse Interrupt Triggered → Handling ISR");
try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
System.out.println(" → Completed");
logEvent("Mouse");
}
private static void printerISR() {
if (maskPrinter) {
System.out.println("Printer Interrupt Ignored (Masked)");
return;
}
System.out.print("Printer Interrupt Triggered → Handling ISR");
try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
System.out.println(" → Completed");
logEvent("Printer");
}
private static class DeviceThread extends Thread {
private final String device;
DeviceThread(String device) {
this.device = device;
}
@Override
public void run() {
Random rand = new Random();
while (true) {
try { Thread.sleep((rand.nextInt(5) + 1) * 1000L); } catch (InterruptedException ignored) {}
synchronized (lock) {
switch (device) {
case "Keyboard" -> keyboardInterrupt = true;
case "Mouse" -> mouseInterrupt = true;
case "Printer" -> printerInterrupt = true;
}
}
}
}
}
private static void handleInterrupts() {
synchronized (lock) {
if (keyboardInterrupt) {
keyboardInterrupt = false;
keyboardISR();
return;
}
if (mouseInterrupt) {
mouseInterrupt = false;
mouseISR();
return;
}
if (printerInterrupt) {
printerInterrupt = false;
printerISR();
return;
}
}
System.out.println("No pending interrupts.");
}
private static void showMaskStatus() {
System.out.println("\n--- Current Mask Status ---");
System.out.println("Keyboard: " + (maskKeyboard ? "Masked (Disabled)" : "Enabled"));
System.out.println("Mouse: " + (maskMouse ? "Masked (Disabled)" : "Enabled"));
System.out.println("Printer: " + (maskPrinter ? "Masked (Disabled)" : "Enabled"));
System.out.println("---------------------------");
}
public static void main(String[] args) {
new DeviceThread("Keyboard").start();
new DeviceThread("Mouse").start();
new DeviceThread("Printer").start();
Scanner sc = new Scanner(System.in);
System.out.println("==== Multithreaded Interrupt Controller Simulation (Java) ====");
while (true) {
System.out.println("\n1. Poll for Interrupts");
System.out.println("2. Toggle Mask (Enable/Disable Device)");
System.out.println("3. View Mask Status");
System.out.println("4. Exit");
System.out.print("Enter your choice: ");
int choice = sc.nextInt();
switch (choice) {
case 1 -> handleInterrupts();
case 2 -> {
System.out.println("Select device to toggle mask:");
System.out.println("1. Keyboard\n2. Mouse\n3. Printer");
int dev = sc.nextInt();
switch (dev) {
case 1 -> maskKeyboard = !maskKeyboard;
case 2 -> maskMouse = !maskMouse;
case 3 -> maskPrinter = !maskPrinter;
default -> System.out.println("Invalid device!");
}
showMaskStatus();
}
case 3 -> showMaskStatus();
case 4 -> {
System.out.println("Exiting simulation... Goodbye!");
sc.close();
System.exit(0);
}
default -> System.out.println("Invalid choice! Try again.");
}
}
}
}