package com.ixat.twitter.TwitterAnalytics;

import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class SentimentAnalyzer {

	public static void main(String args[]) {
		Properties props = new Properties();
		props.put("annotators",
				"tokenize, ssplit, parse, pos, lemma, sentiment");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		String tweet = "i admire you deeply for your kindness and love to everyone. you deserve the very best in life. follow me? je t'aime ";
	//	String tweet = "Maryland Cop Who Pointed Gun To Man's Head Sentenced To 5 Years In Prison. Says Victim Should Apologize To Him https://t.co/MMuLLgqgY9";
	//	String tweet = "It was a very bad day today";
		sentiTest(tweet, pipeline);
	}

	private static void sentiTest(String text, StanfordCoreNLP pipeline) {
		int mainSentiment = 0;
		if (text != null && text.length() > 0) {
			int longest = 0;
			Annotation document = pipeline.process(text);
			for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
				Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
				int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
				String partText = sentence.toString();
				if (partText.length() > longest) {
					mainSentiment = sentiment;
					longest = partText.length();
				}
			}
		}
		System.out.println("Senti score =====>" + mainSentiment);
	}
}
