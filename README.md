💻 Interrupt Controller Simulation
🎯 Project Objective
This project simulates the core functionality of a Programmable Interrupt Controller (PIC), demonstrating how a computer system handles concurrent interrupts from multiple I/O devices based on priority and masking.

🧪 Assignment Requirements
The simulation handles three distinct I/O devices, each with a predetermined priority level:
Keyboard: High Priority
Mouse: Medium Priority
Printer: Low Priority

Key Features:
1 Priority-Based Handling: The Interrupt Controller always serves the highest-priority interrupt that is currently pending.
2 Interrupt Masking: Users can dynamically enable or disable (mask) specific device interrupts during runtime.
3 Asynchronous Simulation: Uses [Mechanism, e.g., Java Multithreading or C++ std::thread] to simulate devices triggering   interrupts at different times.
4 Clear Output: Console output shows whether an interrupt is triggered, handled by the Interrupt Service Routine (ISR), or ignored due to masking

🚀 Interaction
The simulation runs continuously. You will typically use simple console commands to:
Mask (disable) interrupts for a specific device.
Unmask (enable) interrupts for a specific device.
