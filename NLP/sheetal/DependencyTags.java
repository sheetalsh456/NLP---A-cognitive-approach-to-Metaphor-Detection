import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import java.io.File;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;
import java.lang.Object;
import edu.stanford.nlp.ling.IndexedWord;


import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;

import java.io.StringReader;
import java.io.*;
import java.lang.*;
import java.util.List;
import java.util.*;

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

class DependencyTags {
   public static void main(String []args) throws Exception {
       String text = "It seemed as if whole town was mourning his death.";
       int i;

       String[] words = text.split("\\s+");
       for (i = 0; i < words.length; i++) 
       {
            words[i] = words[i].replaceAll("[^\\w]", "");
        }
        TreebankLanguagePack tlp = new PennTreebankLanguagePack();
        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
        LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
        lp.setOptionFlags(new String[]{"-maxLength", "500", "-retainTmpSubcategories"});
        TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
        List<CoreLabel> wordList = tokenizerFactory.getTokenizer(new StringReader(text)).tokenize();
        Tree tree = lp.apply(wordList);    
        GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
        Collection<TypedDependency> tdl = gs.typedDependenciesCCprocessed(true);
        //Main.writeImage(tree,tdl, "image.png",3);
        System.out.println(tdl);

        Map<String, Integer> hm = new HashMap<String, Integer> ();
    hm.put("acomp",1);
    hm.put("advcl",2);
    hm.put("advmod",3);
    hm.put("agent",4);
    hm.put("amod",5);
    hm.put("appos",6);
    hm.put("aux",7);
    hm.put("auxpass",8);
    hm.put("cc",9);
    hm.put("ccomp",10);
    hm.put("conj",11);
    hm.put("cop",12);
    hm.put("csubj",13);
    hm.put("csubjpass",14);
    hm.put("dep",15);
    hm.put("det",16);
    hm.put("discourse",17);
    hm.put("dobj",18);
    hm.put("expl",19);
    hm.put("goeswith",20);
    hm.put("iobj",21);
    hm.put("mark",22);
    hm.put("mwe",23);
    hm.put("neg",24);
    hm.put("nn",25);
    hm.put("npadvmod",26);
    hm.put("nsubj",27);
    hm.put("nsubjpass",28);
    hm.put("num",29);
    hm.put("number",30);
    hm.put("parataxis",31);
    hm.put("pcomp",32);
    hm.put("pobj",33);
    hm.put("poss",34);
    hm.put("possessive",35);
    hm.put("preconj",36);
    hm.put("predet",37);
    hm.put("prep",38);
    hm.put("prepc",39);
    hm.put("prt",40);
    hm.put("punct",41);
    hm.put("quantmod",42);
    hm.put("rcmod",43);
    hm.put("ref",44);
    hm.put("root",45);
    hm.put("tmod",46);
    hm.put("vmod",47);
    hm.put("xcomp",48);
    hm.put("xsubj",49);
  
    int ans;
        Object[] list = tdl.toArray();
        System.out.println(list.length);
        TypedDependency typedDependency;
        System.out.println("\n\nType\tGov\tDep");
        for (Object object : list) {
        typedDependency = (TypedDependency) object;
        if(hm.get(typedDependency.reln().toString())==null)
            ans=0;
        else
            ans=hm.get(typedDependency.reln().toString());
        System.out.println((ans+500)+"\t"+typedDependency.gov().index()+"\t"+typedDependency.dep().index()); 
  }
  System.out.println("\n");

  }
}
