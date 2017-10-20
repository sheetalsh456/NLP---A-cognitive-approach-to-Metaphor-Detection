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
public class DFS
{
	final String ENG_BI_MODEL = "stanford-postagger-2015-12-09/models/english-bidirectional-distsim.tagger";
	final String PCG_MODEL = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
	final MaxentTagger mxt = new MaxentTagger(ENG_BI_MODEL);
	final LexicalizedParser parser = LexicalizedParser.loadModel(PCG_MODEL);

	public static void main(String args[])
	{
		String sentence = "It seemed as if whole town was mourning his death.";
		Tree tree = null;
		DFS dfs = new DFS();
		tree = dfs.parse(sentence);
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
	public static void dfs_traversal(Tree t) 
	{
      		if (t == null || t.isLeaf()) 
      		{
         		return;
      		}
			System.out.println(t.label().value());
     		for(Tree child : t.children()) 
     		{
         		dfs_traversal(child);
    		}
	}
}