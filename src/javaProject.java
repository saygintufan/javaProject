import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class javaProject {
    public static void main(String[] args) throws Exception {
        System.out.println("SaygınTufan Java Project \n");

        System.out.println("Model oluşturuluyor...");
        String trainFile = "C://Users/saygintufan/Desktop/SayginTUFAN/javaProject/test.arff"; // sınıflandırılma yapıldıktan sonra içine yazılan arff dosyası
        Instances train = getInstance(trainFile);
        int lastIndex = train.numAttributes() - 1;
        train.setClassIndex(lastIndex);
        System.out.println("Eğitim seti okundu...");

        StringToWordVector stwv = new StringToWordVector();
        stwv.setInputFormat(train);
        train = weka.filters.Filter.useFilter(train, stwv);
        System.out.println("String to word Vektor işlemi yapıldı...");

        System.out.println("Naive Bayes modeli oluşturuluyor...");
        NaiveBayes bayes = new NaiveBayes();
        bayes.buildClassifier(train);
        System.out.println("Naive Bayes oluşturuldu");

        // j48 algoritması
        System.out.println("J48 modeli oluşturuluyor...");
        J48 j48=new J48();
        bayes.buildClassifier(train);
        System.out.println(" j48 oluşturuldu");


        //String testFile = "Files\\WithoutStopWordOriginal.arff";
        String testFile = "C://Users/saygintufan/Desktop/SayginTUFAN/javaProject/train.arff"; // bizim ham olarak verdiğimiz veriseti
        String originalData ="C://Users/saygintufan/Desktop/SayginTUFAN/javaProject/orijinal.txt";
        String lock = "lockjava.txt";

        while(true)	{

            if(new File(testFile).exists() && !new File("lock.txt").exists())	{
                new File(lock).createNewFile();
                Path path = Paths.get(lock);
                Files.setAttribute(path, "dos:hidden", Boolean.TRUE, LinkOption.NOFOLLOW_LINKS);

                List<String> allLines = Files.readAllLines(Paths.get(originalData));
                System.out.println("Test verisi okuması yapılıyor..");

                Instances test = getInstance(testFile);
                test.setClassIndex(lastIndex);
                test = weka.filters.Filter.useFilter(test, stwv);
                System.out.println("Test verisi üzerinde String to word Vektor işlemi yapıldı...");

                System.out.println("Sınıflandırma işlemine başlanıyor");
                for (int i = 0; i < test.numInstances(); i++) {
                    double index = bayes.classifyInstance(test.instance(i));
                    String className = train.attribute(0).value((int) index);
                    System.out.println("Sınıflandırma Sonucu: " + className);

                    String[] parameters =allLines.get(i).split("_@_");
                    String urlParameters = "question=" + URLEncoder.encode(questions.getQuestion(parameters[0]), "UTF-8");

                    helpers.executePost(helpers.mainUrl, urlParameters);

                }
                System.out.println("Sınıflandırma işlemi tamamlandı...\n\n\n--------------------------------");
                new File(testFile).delete();
                new File(originalData).delete();
                new File(lock).delete();
            }
        }
    }

    private static Instances getInstance(String fileName) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        Instances instance = new Instances(reader);
        reader.close();

        return instance;
    }
}
