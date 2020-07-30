import java.io.File;
import java.io.FileWriter;

public class SeatingArrangement {

    public Person persons[];
    public int instanceNum;
    public int score;

    SeatingArrangement(int instanceNumber) {
        try {
            instanceNum = instanceNumber;
            String filename = "/hw1-inst" + instanceNum + ".txt";
            String path = System.getProperty("user.dir");

            //load persons and preferences from instance file
            Instance instance = new Instance(path + filename);
            persons = instance.persons;

        } catch(Exception e) {
            System.out.println(e + " SeatingArrangement");
        }

    }

    //returns the role of the person, True if host, False if guest
    public boolean r(Person p) { return p.host; }

    //returns the preference value p1 has for p2
    public int h(Person p1, Person p2) { return p1.prefs[p2.id]; }

    //swaps the seats of two people
    public void swapSeats(int p1, int p2)
    {
        Person tempPerson = persons[p1];
        persons[p1] = persons[p2];
        persons[p2] = tempPerson;

        int tempSeat = persons[p1].seat;
        persons[p1].seat = persons[p2].seat;
        persons[p2].seat = tempPerson.seat;
    }

    //finds the total score for current guest layout
    public int Score()
    {
        //int score = 0;
        score = 0;
        int half = persons.length/2;

        //find score of adjacent persons
        for(int i = 0; i < (half - 1); i++)
        {
            //+1 if different roles for first half of table
            if(persons[i].host != persons[i+1].host)
                score++;

            //+1 if different roles for second half of table
            if(persons[i+half].host != persons[i+half+1].host)
                score++;

            //add adjacent preference values for first half
            score += h(persons[i], persons[i+1]);
            score += h(persons[i+1], persons[i]);

            //add adjacent preference values for second half
            score += h(persons[i+half], persons[i+half+1]);
            score += h(persons[i+half+1], persons[i+half]);
        }

        //find score of opposite pairs
        for(int i = 0; i < half; i++)
        {
            //+2 if different roles
            if(persons[i].host != persons[i+half].host)
                score += 2;

            //add opposite pair preference values
            score += h(persons[i], persons[i+half]);
            score += h(persons[i+half], persons[i]);
        }

        return score;
    }

    //prints current seating arrangement to solution file
    //public void printTable(int instanceNum, int score)
    public void printTable()
    {
        try
        {
            //calculate final score
            Score();

            //find current directory
            String path = System.getProperty("user.dir");
            path += "/hw1-soln" + instanceNum + ".txt";
            File solution = new File(path);

            //create solution file if it doesn't already exist
            if (!solution.exists())
                solution.createNewFile();

            //create FireWriter for printing to txt file
            FileWriter fw = new FileWriter(path);
            fw.write("Seating arrangement for instance " + instanceNum + ":\n");

            //print first row of people
            for (int i = 0; i < persons.length / 2; i++)
            {
                fw.write(persons[i].id+1 + "  ");
                if(persons[i].id+1 < 10)
                    fw.write(" ");
            }
            fw.write("\n");

            //print second row of people
            for (int i = persons.length / 2; i < persons.length; i++)
            {
                fw.write(persons[i].id+1 + "  ");
                if(persons[i].id+1 < 10)
                    fw.write(" ");
            }

            //print out final score and close file
            fw.write("\n\nScore = " + score);
            fw.close();
        }
        catch(Exception e)
        {
            System.out.println(e + "solution output");
        }
    }
}

