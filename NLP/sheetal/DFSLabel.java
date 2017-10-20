import java.util.List;
import java.io.StringReader;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.process.AbstractTokenizer;
import java.util.*;
import java.lang.*;
import java.io.*;
//sheetal
public class DFSLabel {

    private static final String ENG_BI_MODEL = "stanford-postagger-2015-12-09/models/english-bidirectional-distsim.tagger";
    private static final String PCG_MODEL = "englishPCFG.ser.gz";
    private static final MaxentTagger mxt = new MaxentTagger(ENG_BI_MODEL);
    private static final LexicalizedParser parser = LexicalizedParser.loadModel(PCG_MODEL);
    private static final Map<String, Integer> hm = new HashMap<String, Integer> ();

    public static void main(String[] args) 
    {
        hm.put("S",0);
        hm.put("NP",1);
        hm.put("VP",2);
        hm.put("PP",3);
        hm.put("RP",4);
        String sentence = "It seemed as if whole town was mourning his death.";
        DFSLabel dfs = new DFSLabel();
        Tree tree=null;
        tree=dfs.parse(sentence);
        dfs_traversal(tree);
	}
	public Tree parse(String s) 
    {
        TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
        List<CoreLabel> tokens = tokenizerFactory.getTokenizer(new StringReader(s)).tokenize();
        mxt.tagCoreLabels(tokens);
        Tree tree = parser.apply(tokens);
        return tree;
    }

    private static  int i=1000;
    private static final int start=100;
    private static final int end=200;
    public static void dfs_traversal(Tree t)
    {
        int k;
        if(t==null)
        {
            return;
        }
        if(t.isLeaf())
        {
            System.out.println(t+" ");
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
            System.out.println((++i)+"\n"+(start+hm.get(t.label().value())));
            k=i;
            for(Tree child : t.children())
            {
                dfs_traversal(child);
            }
            System.out.println((end+hm.get(t.label().value()))+"\n"+(k+1000));
        }
    }
}
