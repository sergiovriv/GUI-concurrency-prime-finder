# GUI-concurrency-prime-finder
This project provides a user-friendly Java application equipped with a graphical user interface (GUI) for efficiently discovering prime numbers within a collection of text files. The program employs a robust producer-consumer pattern, leveraging multiple threads for concurrent file processing.

## Features:

### Graphical User Interface:
- Uses Swing to provide a user-friendly interface.
- Allows users to dynamically adjust the number of consumer threads via buttons.
- Displays real-time information about file processing progress and found prime numbers.

### Producer and Consumer Threads:
- A producer thread generates file names and places them in a queue for processing.
- Consumer threads process files to find prime numbers, updating the GUI with relevant information.
  
### Dynamic Thread Handling:
- Users can increase or decrease the number of consumer threads as needed.
- The application dynamically adjusts the thread count in real-time.
  
### GUI Statistics:
- Shows the total number of processed files.
- Reports the smallest and largest prime numbers found during processing.

## Usage Instructions:
1. Clone the repository locally.
2. Open the project in a compatible Java development environment.
3. Run the VentanaPrincipal class to launch the application.
4. Use the "+" and "-" buttons to adjust the number of consumer threads.
5. Observe how the GUI displays real-time information about file processing progress and results.
