import java.util.Scanner;

class Bayes
{
	Dataset training;

	public Bayes()
	{
		training = new Dataset();
	}

	public void train(String filename)
	{
		training.train(filename);
	}

	public void query(Feature f1, Feature f2, Feature f3)
	{
		System.out.println();
		System.out.println("\t    Query");
		System.out.println("------------------------------");
		System.out.print("|" + f1 + "|" + "\n" + "|" +  f2 + "|" + "\n" + "|" + f3 + "|" + "\n" + "|");

		float cp = training.classProbability(Class.Give);
		float fp = training.probability(0, f1.getValue(), Class.Give) * training.probability(1, f2.getValue(), Class.Give) * training.probability(2, f3.getValue(), Class.Give);

		float icp = training.classProbability(Class.DoNotGive);
		float ifp = training.probability(0, f1.getValue(), Class.DoNotGive) * training.probability(1, f2.getValue(), Class.DoNotGive) * training.probability(2, f3.getValue(), Class.DoNotGive);

		if(cp * fp > icp * ifp)
		{
			System.out.print("Outcome\t-->\t");
			System.out.println("True " +"|");
		}
		else
		{	
			System.out.print("Outcome\t-->\t");
			System.out.println("False" + "|");
		}
		System.out.println("------------------------------");
		System.out.println();
	}

	public static void authors()
	{
		System.out.println("$whoami");
		System.out.println("Nikolaos Aliferopoulos");
		System.out.println("\t[EMAIL]\t\t aliferopoulos@icloud.com");
		System.out.println("\t[GITHUB]\t http://www.github.com/naliferopoulos");
		System.out.println("\t[LINKEDIN]\t http://www.linkedin.com/in/naliferopoulos");
		
		System.out.println("$whoami");
		System.out.println("Georgina Karakasilioti");
		System.out.println("\t[EMAIL]\t\t georginakr2105@gmail.com");
		System.out.println("\t[LINKEDIN]\t http://www.linkedin.com/in/georgina-karakasilioti");
	}

	public static void usage()
	{
		System.out.println("This is a Naive Bayes Classifier implementation by Nikolaos Aliferopoulos and Georgina Karakasilioti.");
		System.out.println("It solves the problem of a bank that needs to decide wether or not to give a loan to a client based on the client having a high enough income, being married and previously having taken a loan.");
		System.out.println("It was implemented in January 2017 for research and completing an assignment for AUEB Informatics, in the Artificial Intelligence course.");
		System.out.println();
		System.out.println("Usage:");
		System.out.println("\tjava Bayes [train.csv]");
		System.out.println();
		System.out.println("Enter a query by typing 'true' or 'false' for 'Income', 'Married' 'HasLoan'");
		System.out.println("Example:");
		System.out.println("\t[Query] >>> true true false");
		System.out.println("Type 'exit' to exit.");
		System.out.println("Type 'authors' for contact information.");
		System.out.println("Type 'usage' to see this message.");
	}

	public static void main(String[] args)
	{
		Bayes b = new Bayes();
		
		if(args.length == 0)
		{
			usage();
			return;
		}

		b.train(args[0]);

		b.query(new Feature("Income", true), new Feature("Married", false), new Feature("HasLoan", false));
		b.query(new Feature("Income", false), new Feature("Married", true), new Feature("HasLoan", true));
		
		String input;
		Scanner sc = new Scanner(System.in);

		System.out.print("[Query] >>> ");
		input = sc.nextLine();
System.out.println();
		while(!input.equals("exit"))
		{
			if(input.equals("authors"))
			{
				authors();
			}
			else if(input.equals("usage"))
			{
				usage();
			}
			else
			{
				String[] broken = input.split(" ");

				if(broken.length < 3)
				{
					System.out.println("Wrong input! Run the program without any arguments for help!");
					break;
				}

				boolean income = broken[0].equals("true") ? true : false;
				boolean married = broken[1].equals("true") ? true : false;
				boolean hasLoan = broken[2].equals("true") ? true : false;
				
				b.query(new Feature("Income", income), new Feature("Married", married), new Feature("HasLoan", hasLoan));
			}

			System.out.print("[Query] >>> ");
			input = sc.nextLine();
			System.out.println();
		}
	}
}