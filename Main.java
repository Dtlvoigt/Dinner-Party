import java.util.Date;

public class Main
{
    public static int size;

    public static void main(String[] args)
    {
        try
        {
            //change instanceNumber to load different instances
            int instanceNumber = 3;

            //SA contains an array of person objects and all the required methods
            SeatingArrangement SA = new SeatingArrangement(instanceNumber);
            size = SA.persons.length;

            //I'll start by swapping every other opposite pair so that there are no same role pairs
            //this gives the max score disregarding all preferences
            firstSwap(SA);

            //run local search algorithm
            localSearch(SA);

            //produce solution file
            SA.printTable();

        } catch(Exception e)
        {
            System.out.println(e + " main");
        }
    }

    //swap every other opposite pair
    public static void firstSwap(SeatingArrangement SA)
    {
        for(int i = 0; i < size/2; i++)
            if(i % 2 == 0)
                SA.swapSeats(i, i+(size/2));
    }

    //swap seats for a higher score until time has ran out
    public static void localSearch(SeatingArrangement SA)
    {
        double startTime = System.currentTimeMillis();
        double currentTime = 0;
        int currScore = SA.Score();
        int prevScore = 0;
        int endTime = 60000;

        //seat to start swapping first
        int startSeat = 0;

        //run loop until time runs out
        while(currentTime < endTime)
        {
            //update score of last iteration
            prevScore = currScore;

            //loop through all seats
            for(int i = 0; i < size; i++)
            {
                //try a seat swap, if score isn't better swap back
                if(i != startSeat)
                {
                    SA.swapSeats(startSeat, i);
                    int newScore = SA.Score();
                    if(newScore < currScore)
                        SA.swapSeats(startSeat, i);
                    else {
                        currScore = newScore;
                        startSeat = i;
                    }
                }

                //check if time has run over
                currentTime = new Date().getTime() - startTime;
                if(currentTime >= endTime)
                    break;
            }

            //if score hasn't changed since last loop change the swapping seat
            if(prevScore == currScore)
            {
                startSeat++;
                if(startSeat >= size)
                    startSeat = 0;
            }

            //update time
            currentTime = new Date().getTime() - startTime;
        }
    }
}
