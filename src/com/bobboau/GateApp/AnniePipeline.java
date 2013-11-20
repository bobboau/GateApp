/**
 * 
 */
package com.bobboau.GateApp;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import gate.Corpus;
import gate.Factory;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.creole.SerialAnalyserController;

/**
 * @author bobboau
 *
 */
public class AnniePipeline implements Pipeline {
	
	/**
	 * the actual pipeline
	 */
	SerialAnalyserController pipeline = null;
	
	/**
	 * constructor
	 * @throws ResourceInstantiationException 
	 */
	public AnniePipeline() throws ResourceInstantiationException, MalformedURLException {
		this.pipeline = (SerialAnalyserController)Factory.createResource("gate.creole.SerialAnalyserController");
		
		this.pipeline.add((gate.LanguageAnalyser)Factory.createResource("gate.creole.annotdelete.AnnotationDeletePR"));
		this.pipeline.add((gate.LanguageAnalyser)Factory.createResource("gate.creole.tokeniser.DefaultTokeniser"));
		this.pipeline.add((gate.LanguageAnalyser)Factory.createResource("gate.creole.splitter.SentenceSplitter"));
		this.pipeline.add((gate.LanguageAnalyser)Factory.createResource("gate.creole.gazetteer.DefaultGazetteer"));
		this.pipeline.add((gate.LanguageAnalyser)Factory.createResource("gate.creole.Transducer",
							gate.Utils.featureMap("grammarURL", new File(System.getProperty("user.dir")+"/plugins/TFIDF/Term.jape").toURI().toURL(),"encoding", "UTF-8")));
	}

	/**
	 * runs the pipeline on the given corpus
	 * @param corpus
	 * @throws ExecutionException
	 */
	@Override
	public void execute(Corpus corpus) throws ExecutionException {
		this.pipeline.setCorpus(corpus);
		this.pipeline.execute();
	}

}