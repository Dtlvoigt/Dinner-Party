import java.io.File;
import java.util.Scanner;

public class Instance {
    public int size;
    public Person persons[];

    Instance(String filename) {
        try
        {
            //scanner to read instance file
            Scanner s = new Scanner(new File(filename));
            size = s.nextInt();
            persons = new Person[size];

            //move through file creating people with their preferences
            for(int i = 0; i < size; i++)
            {
                Person newPerson = new Person();
                newPerson.id = i;
                newPerson.seat = i;
                newPerson.prefs = new int[size];
                if(i < size/2)
                    newPerson.host = true;

                for(int j = 0; j < size; j++)
                {
                    newPerson.prefs[j] = s.nextInt();
                }
                persons[i] = newPerson;
            }
        } catch(Exception e)
        {
            System.out.println(e + " Instance");
        }
    }
}

