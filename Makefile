bayes:
	@javac Bayes.java

knn:
	@javac NearestNeighbours.java

runBayes:
	@java Bayes training.csv

usageBayes:
	@java Bayes

runKnn:
	@java NearestNeighbours training.csv

usageKnn:
	@java NearestNeighbours

clean:
	@rm -rf *.class