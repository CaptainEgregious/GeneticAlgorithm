
import java.util.*;
import java.io.*;
/**
 * Write a description of class main here.
 * 
 * @author Robin Taylor and Matthew Odinga
 * @version 2.0
 * 
 * This is a Genetic algorithm which will generate 5 chromosomes which for use in a 5th order equation
 * these chromosomes will be subject to fitness comparisons against randomly generated chromosomes
 * with a survival of the fittest ensuring positive evolution of the gene.
 * 
 * There are several 'Print to check' tests within the code which print values to the screen for 
 * visual inspection, as the code was developed on a tight time frame, they remained until the last and 
 * were not cleaned out.
 */
public class main
{
    public ArrayList<Double> calculated = new ArrayList<Double>();
    public ArrayList<Double> xval = new ArrayList<Double>();
    public ArrayList<Double> yval = new ArrayList<Double>();
    public ArrayList<ArrayList<Double>> chromosomes = new ArrayList<ArrayList<Double>>();
    public ArrayList<ArrayList<Double>> newchromosomes = new ArrayList<ArrayList<Double>>();
    public ArrayList<Double> fitnessList = new ArrayList<Double>();
    public ArrayList<Double> prob = new ArrayList<Double>();
 
    public double sumFitness;
    /**
     * Constructor for objects of class main
     */
    public main()
    {
     sumFitness = 0.0;
     readInput();
     generateInitialChromosomes();
     systemLoop();
    }
    
    /**
     * Loops the program over a set number of iterations
     */
    public void systemLoop()
    {
           calculate_y();
           generateProbabilities();
    }
    
    /**
     * Method inputs the data from the provided file into array lists for use in the method calculate.
     */
    private void readInput()
    {
        try{
        BufferedReader br = new BufferedReader(new FileReader("InputFileAI.txt"));//Reads in file line by line
        String line;     
        while((line= br.readLine())!=null){
            //Splits entry into at the space and puts components into array
            String[] entry = line.split("\\s+");
            // converts the two items in the array into strings
            String x = entry[0];
            String y = entry[1];
            // converts the strings to the corresponding x or y values for use.
            double tempx = Double.parseDouble(x);
            double tempy = Double.parseDouble(y);
            // adds values to the appropriate array lists
            xval.add(tempx);
            yval.add(tempy);
            
        }
        br.close();
        System.out.println("Input read");
       }catch(IOException e){System.err.println("Goofed");} 
    }
      
    public void generateInitialChromosomes()
    {
        double rangeMax = 9999;
        double rangeMin = -9999;
        Random r = new Random();
        for(int i = 0 ; i<6 ; i++){   
        ArrayList<Double> chromosome = new ArrayList<Double>();   
            for(int j = 0; j<6; j++){       
         double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
         chromosome.add(j, randomValue); //this will populate an array with six 1 chromosomes, six two chomosomes...etc only distinguishable by order.
        
        }
        
        chromosomes.add(i,chromosome);
        
       }
       
       System.out.println("Generated Chromosomes");
    }
    
    public void calculate_y()
    {
		// this will initialise necessary values for calculation of y value
         double x= 0.0;
         double y= 0.0;
         double a= 0.0;
         double b= 0.0;
         double c= 0.0;
         double d= 0.0;
         double e= 0.0;
         double f= 0.0;
        
         for(int i=0;i<6;i++){
         ArrayList<Double> chromosome = new ArrayList<Double>();
         chromosome=chromosomes.get(i); //get chromosomes, place in array
         a = chromosome.get(0); //set values
         b = chromosome.get(1);
         c = chromosome.get(2);
         d = chromosome.get(3);
         e = chromosome.get(4);
         f = chromosome.get(5);
         System.out.print(a+" "+b+" "+c+" "+d+" "+e+" "+f); //print chromosomes
         System.out.println();
         for(int j = 0 ; j<xval.size(); j++){
            x =  xval.get(j);
            y = a+(b*x)+(c*(x*x))+(d*(x*x*x))+(e*(x*x*x*x))+(f*(x*x*x*x*x)); //calculate y
            calculated.add(y);
         }
         fitness();
         calculated.clear();
        }
    }
    
    public void fitness()
    {
        double totalfitness;
        double fitness;
        totalfitness = 0;
        fitness = 0;
        for(int i = 0 ; i<xval.size(); i++){
            fitness = (yval.get(i) - calculated.get(i))*(yval.get(i) - calculated.get(i)); //calculate the fitness
           totalfitness = totalfitness + fitness ; //update the fitness value
        }
        
        fitnessList.add(totalfitness);
        sumFitness= sumFitness+ totalfitness;
       
    }
    
     public void generateProbabilities() 
     //this method generates random numbers for the random selection of which chromosomes within the gene will mutate
     {
      double cumulativeProb = 0.0;
        for(int i = 0; i<6 ; i++)
      {
         double p = (fitnessList.get(i)/sumFitness); 
         
         cumulativeProb = cumulativeProb + p; // create a cumulative probability
        
         prob.add(cumulativeProb); //add cumulative prob to an array
        
      }
      System.out.println(); 
      System.out.println("Probabilities calculated");
      System.out.println(); 
      selection();
    }
    
    public void selection()
    {
        double p1 = prob.get(0); // retrieve probabilities from array
        double p2 = prob.get(1);
        double p3 = prob.get(2);
        double p4 = prob.get(3);
        double p5 = prob.get(4);
        double p6 = prob.get(5);
        double rangeMin = 0.0; //define range values
        double rangeMax = 1;
        ArrayList<Double> cval = new ArrayList<Double>();
        Random r = new Random();
        
        System.out.println(p1); //print out probabilities for visual inspection
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);
        System.out.println(p5);
        System.out.println(p6);
        System.out.println();
        
        //Generate random values.
        for(int i = 0; i<6 ; i++)
        {
          double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
          cval.add(randomValue);
        }
        System.out.println("Random numbers generated"); 
       
        
        for(int i =0; i<6 ; i++)
        {
           double c = cval.get(i);
           System.out.println(c+" "+(i+1));
          
           if(0<=c && c<=p1)// newc = c1
           {
             newchromosomes.add((chromosomes.get(0)));System.out.println("newchromosome"+(i+1)+" = chromosome1");
            }
           else if(p1<c && c<=p2)// newc = c2
           {
             newchromosomes.add((chromosomes.get(1)));System.out.println("newchromosome"+(i+1)+" = chromosome2");
            }
           else if(p2<c && c<=p3)// newc = c3
           {
             newchromosomes.add((chromosomes.get(2)));System.out.println("newchromosome"+(i+1)+" = chromosome3");
            }
           else if(p3<c && c<=p4)// newc = c4
           {
             newchromosomes.add((chromosomes.get(3)));System.out.println("newchromosome"+(i+1)+" = chromosome4");
            }
           else if(p4<c && c<=p5)//newc = c5
           {
             newchromosomes.add((chromosomes.get(4)));System.out.println("newchromosome"+(i+1)+" = chromosome5");
            }
           else if(p5<c && c<=p6)//newc = c6
           {
             newchromosomes.add((chromosomes.get(5)));System.out.println("newchromosome"+(i+1)+" = chromosome6");
            }
           else
           {
            System.err.println("Probability does not rest between desired bounds.");
            }
        }
        System.out.println(); 
        
        for(int i = 0; i<chromosomes.size(); i++){
          chromosomes.set(i,newchromosomes.get(i));
         }
        newchromosomes.clear();
        for(int i=0 ; i<6 ;i++){
         System.out.println(chromosomes.get(i));
        }
        
        crossover();
    }
    
    private void crossover()
    {
        int k=0; //chromosome number       
        double crossoverRate=0.7;
        
        ArrayList<Integer> parentList = new ArrayList<Integer>();
        
        while(k<=5){
            Random r=new Random(); 
            double rK = r.nextDouble();
            if(rK<=crossoverRate){
                parentList.add(k); //list of chromosomes for swapping
            }
            k = k+1;
        }
        
        
        //System.out.println(parentList+" parent list");
        int parentListLength = parentList.size();
        //System.out.println("Parent List Length ="+parentListLength);
        
        final ArrayList<ArrayList<Double>> tempChromo = new ArrayList<ArrayList<Double>>();
        
        if(parentListLength>=2){
            //arraylist to contain all points of swapping for each node parent
            
            
            List<Integer> swaps = new ArrayList<Integer>(); //create swaps list
            for(int i=1; i<=parentListLength;i++){
            Random r=new Random();
            double swapValue = 0.5+ (5.0) * r.nextDouble(); //create random value between 1 and 6
            int p = (int) Math.round(swapValue); // round the value, giving an int of 1 to 6
            swaps.add(p); //add value
           }         
           
            for(int i=0; i<parentListLength;i++){
            tempChromo.add(chromosomes.get(parentList.get(i)));// add parent list to temp chromosome
           }
        
           
           
            for(int i=0;i<tempChromo.size();i++){
                
                //System.out.println(tempChromo.get(i)+" Temporary chromosomes for swapping");
            }   
            System.out.println(swaps+" Swaps At");
            //starting the process of chromosome swapping

            
            for (int i=0; (i<parentListLength); i++){
                System.out.println("I is"+i);
             
                ArrayList<Double> tempParent = new ArrayList<Double>();
                ArrayList<Double> tempChild = new ArrayList<Double>();
                  if(i<(parentListLength-1)){
                          
                                
                      tempParent.addAll(tempChromo.get(i));
                     
                    
                    
                    
                     for(int z=0;z<tempChromo.size();z++){
                      //System.out.println(tempChromo.get(z)+" TEMP ");
                                } 
                    //System.out.println(tempParent+" Temp Parent");
                    tempChild.addAll(tempChromo.get(i+1));
                   // System.out.println(tempChild+ "Temp Child");
                    
                    
                    for(int j=0; j<swaps.get(i); j++){
                       
                        tempChild.set(j,tempParent.get(j)); 
                        //System.out.println(tempChild+ "Temp Child");
                    }
                    
                    
                    newchromosomes.add(tempChild);
                    }
                 
                else if(i==(parentListLength-1)){
                   
                    
                    
                    tempParent.addAll(tempChromo.get(i));
                    
                    
                    
                    
                   // System.out.println(tempParent+" Temp Parent");
                    
                    
                    
                    tempChild.addAll(tempChromo.get(0));
                    
                    
                    
                    //System.out.println(tempChild+ "Temp Child");
                    
                    
                   for(int q=0; q<swaps.get(i); q++){
                    tempChild.set(q,tempParent.get(q));               
                   }
                   
                   
                    newchromosomes.add(tempChild);  
                }
                else{
                    
                    
                    System.out.println("whackajack");
                }
               
            }
           }
      
           else{
               System.out.print("goofed at if statement checking array size");
            }
            System.out.println("now printing final chromosomes");
            for(int i=0; i<newchromosomes.size();i++){
                    System.out.println(newchromosomes.get(i));
                }
        tempChromo.clear();
        for(int i = 0; i<newchromosomes.size(); i++){
          
            int position = parentList.get(i);
            chromosomes.set(position,newchromosomes.get(i));
          
         }
         newchromosomes.clear();
         System.out.println("Final list") ;  
         for(int i=0; i<chromosomes.size();i++){
                 
             System.out.println(chromosomes.get(i));
                }
         
         mutation();
    }
    
      public void mutation()
    {
        
        double mutation_rate = 0.2;
        double rangeMin = -9999;
        double rangeMax = 9999;

        Random r=new Random();
        int mutations = (int) Math.round(36*mutation_rate);
        
        // array to store chromosomes before we change
        Hashtable<Integer, Double> change = new Hashtable<Integer, Double>();
                
        
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Number of  mutations = "+ mutations);
        for(int i=0;i<mutations;i++)
        {
            System.out.println();
            System.out.println("Mutation = "+(i));
            
            int changeChromo =  r.nextInt(5);
            
            int changeVal = r.nextInt(5); //picks the value of the gene
            System.out.println("changeChromo  = "+(changeChromo)) ;  
            System.out.println("changeVal = "+(changeVal)) ;  
            double newValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            System.out.println("newValue = "+newValue) ;
            
            System.out.println("Change Should be " +chromosomes.get(changeChromo)); // printing chromosome that will be changed
            
            //copy array to change
            
            for(int j=0; j<6;j++){
                change.put(j,(chromosomes.get(changeChromo)).get(j));//adds entire chromosome from proper list to change list
            }
            System.out.println("Change Is " +change); //prints chromosome before change
            
            //sets value
            
            change.put(changeVal,newValue); //changes the one value in chromosome before 
            
            System.out.println("Change Is Now " +change); // changed array after being changed
            
            //puts change back
            ArrayList <Double> temp = new ArrayList<Double>();
            Double p = change.get(0);
            System.out.println("at key 0 we get "+p);
            for(int j=0; j<6; j++){
                
                temp.add(j, change.get(j)); //putting changed array back into array or proper chromosomes
                
            }
            chromosomes.set(changeChromo, temp); //putting changed array back into array or proper chromosomes
            
            
            System.out.println("temp should be 0 but is "+temp); //check statements to check swap is progressing, omitt as necessary
            System.out.println("Chromosomes is "+chromosomes.get(changeChromo)); //more check statements
            
            
            System.out.println();
            System.out.println("Full List"); //check statements
            System.out.println();
            
            
            //prints aftermath of change
             for(int u=0; u<chromosomes.size();u++){
               System.out.println(chromosomes.get(u));
                }
            
            
               change.clear();
             
             System.out.println("change should be blank but is "+change); //check print statements
            
        }    
    }
    
    public void print()
    {
        try{
        FileOutputStream fos = new FileOutputStream("y_output.txt"); //output file
        PrintStream ps = new PrintStream(fos);
        
        }catch(IOException e){System.err.println("Error in outputting to file: line 457 of code");}
    }
}
