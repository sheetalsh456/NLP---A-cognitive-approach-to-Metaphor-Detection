import java.util.List;
import java.io.StringReader;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.process.AbstractTokenizer;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths; 
//sheetal
public class DFSLabelFile {

    private static final String ENG_BI_MODEL = "stanford-postagger-2015-12-09/models/english-bidirectional-distsim.tagger";
    private static final String PCG_MODEL = "englishPCFG.ser.gz";
    private static final MaxentTagger mxt = new MaxentTagger(ENG_BI_MODEL);
    private static final LexicalizedParser parser = LexicalizedParser.loadModel(PCG_MODEL);
    private static final Map<String, Integer> hm = new HashMap<String, Integer> ();
    private static  int i;
    private static int start;
    private static int end;
    private static Map<String, Integer> words1map = new HashMap<String, Integer> ();

    public static void main(String[] args) 
    {
        String sentence="";
        BufferedReader br=null;
        try{
            br = new BufferedReader(new FileReader("Input.txt"));
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        DocumentPreprocessor dp = new DocumentPreprocessor(br);
        List<String> sentenceList = new ArrayList<String>();
        for (List<HasWord> sent : dp) 
        {
            String sentenceString = Sentence.listToString(sent);
            sentenceList.add(sentenceString.toString());
         }
        for (String sent : sentenceList) 
        {
            i=1000;
            start=100;
            end=200;
            sentence = sentence + sent;
            //Map<String, Integer> words1map = new HashMap<String, Integer> ();
            String[] words1 = sentence.split("(?<=[,.;:])|(?=[,.;:])|\\s+");
            for(i=0;i<words1.length;i++)
            {
                words1map.put(words1[i],i+1);
            }
            hm.put("S",0);
            hm.put("NP",1);
            hm.put("VP",2);
            hm.put("PP",3);
            hm.put("RP",4);
            hm.put("MD",5);
            DFSLabel dfs = new DFSLabel();
            Tree tree=null;
            tree=dfs.parse(sentence);
            //System.out.println("\n----------------------------------------------------------------------------------------------------------------");
            //System.out.println(sentence+"\n");
            System.out.println("1000");
            dfs_traversal(tree);
            sentence="";
	   }
    }
	public Tree parse(String s) 
    {
        TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
        List<CoreLabel> tokens = tokenizerFactory.getTokenizer(new StringReader(s)).tokenize();
        mxt.tagCoreLabels(tokens);
        Tree tree = parser.apply(tokens);
        return tree;
    }

    
    public static void dfs_traversal(Tree t)
    {
        int k;
        if(t==null)
        {
            return;
        }
        if(t.isLeaf())
        {
            System.out.println(words1map.get(t.toString())+"\t"+t.toString().length());
            return;
        }
        if(hm.get(t.label().value())==null)
        {
            for(Tree child : t.children())
            {
                dfs_traversal(child);
            }
        }
        else
        {
            System.out.println((++i)+"\t1"+"\n"+(start+hm.get(t.label().value()))+"\t2");
            k=i;
            for(Tree child : t.children())
            {
                dfs_traversal(child);
            }
            System.out.println((end+hm.get(t.label().value()))+"\t2"+"\n"+(k+1000)+"\t1");
        }
    }
}
