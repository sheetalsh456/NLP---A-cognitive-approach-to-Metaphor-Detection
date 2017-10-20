//package similarity1;

import java.util.*;
import java.lang.*;
import java.io.*;
import edu.smu.tspell.wordnet.*;

public class sim1
{
public static void main(String[] args)
{
	//Scanner in = new Scanner(System.in);
	try
	{
	String[] words;
	String line;
	FileReader file = new FileReader("nounslist");
    BufferedReader reader = new BufferedReader(file);
	Set<String> set = new HashSet<String>();
	Map<String, Integer> map = new HashMap<String, Integer>();
	Map<String, Integer> map1 = new HashMap<String, Integer>();
	while((line=reader.readLine())!=null)
	{
		words = line.split(" ");
		if(map.get(words[0])==null)
		map.put(words[0],Integer.parseInt(words[1]));
	}
	String wordForm;
	int j;
	for (Map.Entry<String, Integer> entry : map.entrySet())
	{
			//System.out.println("inside");
			//System.out.println("value "+entry.getValue());
			wordForm = entry.getKey();
			//System.out.println(wordForm);
			//  Get the synsets containing the wrod form
			WordNetDatabase database = WordNetDatabase.getFileInstance();
			Synset[] synsets = database.getSynsets(wordForm);
			//  Display the word forms and definitions for synsets retrieved
			if (synsets.length > 0)
			{
				//System.out.println("The following synsets contain '" +
				//		wordForm + "' or a possible base form " +
				//		"of that text:");
				for (int i = 0; i < synsets.length; i++)
				{
					//System.out.println("");
					//System.out.println(synsets[i].getWordForms());
					String[] wordForms = synsets[i].getWordForms();
					/*for(j=0;j<wordForms.length;j++)
					{
						System.out.println(wordForms[j]);
					}*/
					for (j = 0; j < wordForms.length; j++)
					{
						//System.out.print((j > 0 ? ", " : "") +
								//wordForms[j]);
						set.add(wordForms[j]);
						if(map1.get(wordForms[j])==null)
							map1.put(wordForms[j],entry.getValue());
						//System.out.println(map.get(wordForms[j]));
						//System.out.println(wordForms[j]);
					}
					//System.out.println("outside j for");
					//System.out.println(set);
					//System.out.println(": " + synsets[i].getDefinition());
				}
				//System.out.println("outside i for");
			}
			//System.out.println("after if");
	}
	//System.out.println("Outside");
	int cnt=0;
	for (Map.Entry<String, Integer> entry : map1.entrySet())
	{
		System.out.println(entry.getKey()+"/" +entry.getValue());
		cnt++;
	}
	//System.out.println(cnt);
				/*Iterator iter = set.iterator();
				while (iter.hasNext()) 
				{
    				System.out.println(iter.next());
				}*/
			/*else
			{
				System.out.println("No synsets exist that contain " +
						"the word form '" + wordForm + "'");
			}*/
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

}