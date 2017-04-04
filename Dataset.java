import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

class Dataset
{
	int values = 0;

	Feature[][] data;
	Class[] classes;

	public Class getNearest(Feature f1, Feature f2, Feature f3)
	{
		int id = 0;
		int lowestScore = 3;

		for(int i = 0; i < values; i++)
		{
			int score = 0;
			for(int j = 0; j < 3; j++)
			{
				score += f1.getDifference(data[i][j]);
				score += f2.getDifference(data[i][j]);
				score += f2.getDifference(data[i][j]);
				if(score < lowestScore)
				{
					lowestScore = score;
					id = i;
				}
			}
		}

		Class one = classes[id];

		int idFirstExclusion = 0;
		int lowestScoreFirstExclusion = 3;

		for(int i = 0; i < values; i++)
		{
			int score = 0;
			for(int j = 0; j < 3; j++)
			{
				if(i != id)
				{
					score += f1.getDifference(data[i][j]);
					score += f2.getDifference(data[i][j]);
					score += f2.getDifference(data[i][j]);
					if(score < lowestScoreFirstExclusion)
					{
						lowestScoreFirstExclusion = score;
						idFirstExclusion = i;
					}
				}
			}
		}

		Class two = classes[idFirstExclusion];

		int idSecondExclusion = 0;
		int lowestScoreSecondExclusion = 3;

		for(int i = 0; i < values; i++)
		{
			int score = 0;
			for(int j = 0; j < 3; j++)
			{
				if(i != id)
				{
					score += f1.getDifference(data[i][j]);
					score += f2.getDifference(data[i][j]);
					score += f2.getDifference(data[i][j]);
					if(score < lowestScoreSecondExclusion)
					{
						lowestScoreSecondExclusion = score;
						idSecondExclusion = i;
					}
				}
			}
		}

		Class three = classes[idSecondExclusion];

		if(one == Class.Give)
		{
			if(two == Class.Give || three == Class.Give)
				return Class.Give;
		}
		else if(two == Class.Give)
		{
			if(one == Class.Give || three == Class.Give)
				return Class.Give;
		}
		else if(three == Class.Give)
		{
			if(one == Class.Give || two == Class.Give)
				return Class.Give;
		}
		else
			return Class.DoNotGive;

		return null;
	}

	public float classProbability(Class c)
	{
		int count = 0;

		for(int i = 0; i < values; i++)
		{
			if(classes[i] == c)
			{
				count++;
			}
		}

		return (float)((float)count / (float)values);
	}

	public float probability(int fID, boolean value, Class c)
	{
		int count = 0;
		
		for(int i = 0; i < values; i++)
		{
			if(data[i][fID].getValue() == value && classes[i] == c)
				count++;
		}

		return (float)((float)count / (float)values);
	}

	public void printData()
	{
		System.out.println("Training Data");
		System.out.println("-------------");

		for(int i = 0; i < values; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				System.out.print(data[i][j]);
				System.out.print("\t");
			}
			System.out.println(classes[i] == Class.Give ? "Class --> True" : "Class --> False");
		}

		System.out.println("-------------");
		System.out.println();
	}

	public void train(String dataFile)
	{
		int count = 0;

		try(BufferedReader br = new BufferedReader(new FileReader(dataFile)))
		{
			String line;
			while((line = br.readLine()) != null)
			{
				count++;
			}
		}
		catch(FileNotFoundException e)
		{
			System.err.println("Unable to open Training File '" + dataFile + "'. File does not exist or in use!");
			System.exit(1);
		}
		catch(Exception e)
		{
			System.err.println("Unknown Error!");
			System.exit(1);
		}

		values = count - 1; // Ignore first line, it is feature names

		data = new Feature[values][3];
		classes = new Class[values];

		try(BufferedReader br = new BufferedReader(new FileReader(dataFile)))
		{
			String line;
			line = br.readLine();

			String[] names = line.split(", ");

			count = 0;
			while((line = br.readLine()) != null)
			{
				String[] values = line.split(",");
				data[count][0] = new Feature(names[0], (values[0].replace(" ", "").equals("true") ? true : false));
				data[count][1] = new Feature(names[1], (values[1].replace(" ", "").equals("true") ? true : false));
				data[count][2] = new Feature(names[2], (values[2].replace(" ", "").equals("true") ? true : false));
				classes[count] = (values[3].endsWith("true") ? Class.Give : Class.DoNotGive);
				count++;				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		

		printData(); // Debug is love, debug is life <3
	}
}