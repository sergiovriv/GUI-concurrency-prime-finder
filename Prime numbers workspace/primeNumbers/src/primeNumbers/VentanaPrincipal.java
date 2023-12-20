package primeNumbers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VentanaPrincipal {

    private static final int BUFFER_SIZE = 10; // Buffer size
    private static final int NUM_PRODUCERS = 1; // Number of producers
    private static int[] CURRENT_NUM_CONSUMERS = {3}; // number of consumers now (changes)
    private static int[] WISHED_NUM_CONSUMERS = {3}; // desired consumers (changes)
    private static final String FOLDER_PATH = "./rand_files/";

    private static BlockingQueue<String> fileNamesQueue = new ArrayBlockingQueue<>(BUFFER_SIZE);

    private static BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(BUFFER_SIZE);

    private static JLabel numberThreadsLabel = new JLabel("Number of threads: "+ String.valueOf(CURRENT_NUM_CONSUMERS[0]));
    
    private static JLabel minimal_prime_found = new JLabel("Minimal prime found: ");
    private static JLabel maximal_prime_found = new JLabel("Maximal prime found: ");
    private static JLabel last_updating_file = new JLabel("Last updating file: ");
    private static JLabel files_processed = new JLabel("Files proccessed: ");
    
    private static int[] num_files_processed = {0};
    private static int[] num_minimal_prime_found = {1000000000};
    private static int[] num_maximal_prime_found = {0};

    public static void main(String[] args) throws InterruptedException {
        // New JFrame Object, window
        JFrame frame = new JFrame("Find Primes");

        // Config default operation on window closing
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Stablish background of the window
        frame.getContentPane().setBackground(Color.WHITE);

        // main pannel using GridBagLayout
        JPanel mainPanel = new JPanel();




        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setPreferredSize(new Dimension(670, 100));
        buttonsPanel.setMaximumSize(new Dimension(670, 100));
        buttonsPanel.setMinimumSize(new Dimension(670, 100));
        buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.white,6)); // Add border
        ;
        
        // Create two buttons
        JButton button1 = new JButton("-");
        button1.setPreferredSize(new Dimension(50, 50));
        JButton button2 = new JButton("+");
        button2.setPreferredSize(new Dimension(50, 50));
        
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(3, 3, 3, 3); // Margins
        gbc.gridwidth = 1; // two columns
        buttonsPanel.add(numberThreadsLabel,gbc);
        gbc.gridx = 7;
        gbc.gridy = 1;
        gbc.insets = new Insets(3, 3, 3, 3); // Margins
        gbc.gridwidth = 5; // two columns
        buttonsPanel.add(button1,gbc);
        gbc.gridx = 14;
        gbc.gridy = 1;
        gbc.insets = new Insets(3, 3, 3, 3); // margins
        gbc.gridwidth = 5; // two columns
        buttonsPanel.add(button2,gbc);

        // Add ActionListener for manage click events in buttons
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WISHED_NUM_CONSUMERS[0] = WISHED_NUM_CONSUMERS[0] - 1;
                //numberThreadsLabel.setText("Number of threads: "+ String.valueOf(CURRENT_NUM_CONSUMERS[0]));
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	WISHED_NUM_CONSUMERS[0] = WISHED_NUM_CONSUMERS[0] + 1;            	new Thread(new Consumer(
           			 fileNamesQueue, 
           			 mainPanel, 
           			 WISHED_NUM_CONSUMERS, 
           			 CURRENT_NUM_CONSUMERS,
           			 numberThreadsLabel,
           	    	 minimal_prime_found,
           	    	 maximal_prime_found,
           	         last_updating_file,
           	    	 files_processed,
           	    	num_files_processed,
           	    	num_minimal_prime_found,
    	    		num_maximal_prime_found)).start();
            	CURRENT_NUM_CONSUMERS[0] = CURRENT_NUM_CONSUMERS[0] + 1;
            	numberThreadsLabel.setText("Number of threads: "+ String.valueOf(CURRENT_NUM_CONSUMERS[0]));
            }
        });

        // Add buttons to the main pannel


        
        mainPanel.add(buttonsPanel);
        
        
        // create info pannel
        JPanel infoPanel = new JPanel(new GridLayout(4, 1));

        infoPanel.setBorder(BorderFactory.createLineBorder(Color.white,6)); // Add border


        
       ;
        
        infoPanel.add(minimal_prime_found);
        infoPanel.add(maximal_prime_found);
        infoPanel.add(last_updating_file);
        infoPanel.add(files_processed);
        infoPanel.setPreferredSize(new Dimension(200, 150));
        infoPanel.setMaximumSize(new Dimension(670, 100));
        infoPanel.setMinimumSize(new Dimension(670, 150));

        mainPanel.add(infoPanel);
        
        frame.getContentPane().add(mainPanel);
        
        // window size
        frame.setSize(670, 800);

        // turn window visible
        frame.setVisible(true);
        
        
        
      
        
        
        createThreadAndPaint
        		(mainPanel,
        		 fileNamesQueue, 
   			    numberThreadsLabel,
   	    		minimal_prime_found,
   	    		maximal_prime_found,
   	    		last_updating_file,
   	    		files_processed);
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    }

    private static void createThreadAndPaint
    		(JPanel mainPanel,
    		 BlockingQueue<String> fileNamesQueu,
    		JLabel numberThreadsLabel,
    		JLabel minimal_prime_found,
    		JLabel maximal_prime_found,
    		JLabel last_updating_file,
    		JLabel files_processede) {

            new Thread(new Producer(fileNamesQueue, FOLDER_PATH)).start();
	        		
            for (int i = 0; i < CURRENT_NUM_CONSUMERS[0]; i++) {
            	 new Thread(new Consumer
            			 (fileNamesQueue,
            			  mainPanel,
            			  WISHED_NUM_CONSUMERS,
            			  CURRENT_NUM_CONSUMERS,
            			 numberThreadsLabel,
            	    		minimal_prime_found,
            	    		maximal_prime_found,
            	    		last_updating_file,
            	    		files_processed,
            	    		num_files_processed,
            	    		num_minimal_prime_found,
            	    		num_maximal_prime_found)).start();
            }
	}




    public static int extractNumber(String fileName) {
        // pattern to find a number in the file
        Pattern pattern = Pattern.compile("\\d+");

        // matcher to pattern and filename
        Matcher matcher = pattern.matcher(fileName);

        // Verify if theres a coincidence
        if (matcher.find()) {
            // extract and return the obtained number
            return Integer.parseInt(matcher.group());
        } else {
            // No number found
            throw new IllegalArgumentException("No number found in the file: " + fileName);
        }
    }

}

class Producer implements Runnable {
    private BlockingQueue<String> fileQueue;
    private String folderPath;

    public Producer(BlockingQueue<String> fileQueue, String folderPath) {
        this.fileQueue = fileQueue;
        this.folderPath = folderPath;
    }

    @Override
    public void run() {
        // Add filenames to buffer
        for (int i = 1; i <= 1000; i++) {
            String fileName = folderPath + "file" + String.valueOf(i) + ".txt";
            try {
                // Wait until theres space in buffer
                while (fileQueue.size() >= 10) {
                    Thread.sleep(100); // Wait to evict active blocking
                }

                // Add the file to the buffer
                fileQueue.put(fileName);
                System.out.println("File produced: " + fileName);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Error in the file production process.");
            }
        }
    }
}


class Consumer implements Runnable {
    private BlockingQueue<String> fileQueue;
    private JPanel mainPanel;
    private int[] whished_num_consumer;
    private int[] current_num_consumer;
    private static JLabel numberThreadsLabel;
    
    private static JLabel minimal_prime_found;
    private static JLabel maximal_prime_found;
    private static JLabel last_updating_file;
    private static JLabel files_processed;

    private int[] num_files_processed;
    private int[] num_minimal_prime_found;
    private int[] num_maximal_prime_found;
    
    public Consumer
    		(BlockingQueue<String> fileQueue,
    		 JPanel mainPanel,
    		 int[] whished_num_consumer,
    		 int[] current_num_consumer,
    		JLabel numberThreadsLabel,
    		JLabel minimal_prime_found,
    		JLabel maximal_prime_found,
    		JLabel last_updating_file,
    		JLabel files_processed,
    		 int[] num_files_processed,
    		 int[] num_minimal_prime_found,
    		 int[] num_maximal_prime_found) {
        this.fileQueue = fileQueue;
        this.mainPanel = mainPanel;
        this.whished_num_consumer = whished_num_consumer;
        this.current_num_consumer = current_num_consumer;
        this.numberThreadsLabel = numberThreadsLabel;
        this.minimal_prime_found = minimal_prime_found;
        this.maximal_prime_found = maximal_prime_found;
        this.last_updating_file = last_updating_file;
        this.files_processed = files_processed;
        this.num_files_processed = num_files_processed;
        this.num_minimal_prime_found = num_minimal_prime_found;
        this.num_maximal_prime_found = num_maximal_prime_found;
    }

    @Override
    public synchronized void run() {
    	
        JPanel filePanel = new JPanel();
        filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.Y_AXIS));

        filePanel.add(Box.createRigidArea(new Dimension(10, 10))); // Height adjust 
        filePanel.setBorder(BorderFactory.createLineBorder(Color.white,6)); // Add border

        
        JLabel fileNameLabel = new JLabel("File: ");
        filePanel.add(fileNameLabel);

        JProgressBar progressBar = new JProgressBar(0, 100);
        filePanel.add(progressBar);
    	
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(filePanel);
        mainPanel.repaint();
        mainPanel.validate();
        
        while (true) {
            try {
                String fileName = fileQueue.take();
                synchronized (num_files_processed) {
                    num_files_processed[0] = num_files_processed[0] +1;
                    files_processed.setText("Files processed: " + String.valueOf(num_files_processed[0]));
                }
                
                
                last_updating_file.setText("Last updating file: "+fileName);
                fileNameLabel.setText("File taken: " + fileName);

                
                System.out.println("File taken: " + fileName);
                // Verify if it thesentinel that puts the end to the work
                if (fileName.equals("FIN")) {
                    break;
                }
                
                int totalNumbers = 0;
                try (BufferedReader readerCount = new BufferedReader(new FileReader(fileName))) {
                    while (readerCount.readLine() != null) {
                        totalNumbers++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //System.out.println("totalNumbers" + totalNumbers);
                int primeCount = 0;
                try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                    String line;
                    int numbersProcessed = 0;
                    while ((line = reader.readLine()) != null) {
                        int number = Integer.parseInt(line.trim());
                        if (isPrime(number)) {
                            primeCount++;
                            synchronized (num_minimal_prime_found) {
                            	if (number < num_minimal_prime_found[0]) {
                                	num_minimal_prime_found[0] = number;
                                	minimal_prime_found.setText("Minimal prime found: " + String.valueOf(num_minimal_prime_found[0]));
                                }
                            }
                            
                            synchronized (num_maximal_prime_found) {
                            	if (number > num_maximal_prime_found[0]) {
                                	num_maximal_prime_found[0] = number;
                                	maximal_prime_found.setText("Maximal prime found: " + String.valueOf(num_maximal_prime_found[0]));
                                }
                            }
                            
                        }
                        numbersProcessed++;
                        //System.out.println("numbersProcessed" + numbersProcessed);
                        int progress = (int) ((double) numbersProcessed / totalNumbers * 100);
                        SwingUtilities.invokeLater(() ->{    
                        	fileNameLabel.setText("File: " + fileName);
                        	progressBar.setValue(progress);
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            //ELIMINATE FROM FRAME IF LESS THREADS DESIRED
            synchronized (whished_num_consumer) {
            	
                if (whished_num_consumer[0] < current_num_consumer[0]) {
                	current_num_consumer[0] = current_num_consumer[0] -1;
                	numberThreadsLabel.setText("Number of threads: "+ String.valueOf(current_num_consumer[0]));
                    mainPanel.remove(filePanel);
                    mainPanel.repaint();
                    mainPanel.validate();
                    break;
                }
            }
            

            
        }
    }


    private boolean isPrime(int number) {
        if (number <= 1) {
            return false; // numbers less than one are not prime
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false; // if number is divisible, not prime
            }
        }
        return true; // if not divisor found, number is prime
    }
}