import org.json.JSONObject;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
        String trainFile = "C://Users/saygintufan/Desktop/SayginTUFAN/javaProject/train.arff"; // sınıflandırılma yapıldıktan sonra içine yazılan arff dosyası
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
        /*System.out.println("J48 modeli oluşturuluyor...");
        J48 j48=new J48();
        bayes.buildClassifier(train);
        System.out.println(" j48 oluşturuldu");*/


        //String testFile = "Files\\WithoutStopWordOriginal.arff";
        //String testFile = "C://Users/saygintufan/Desktop/SayginTUFAN/javaProject/test.arff";
        String originalData ="C://Users/saygintufan/Desktop/SayginTUFAN/javaProject/orijinal.txt";
        String lock = "lockjava.txt";


        //region UrlParse

        helpers.executePost("http://saygintufan.com/chatbot/que.json","");
        JSONObject json = helpers.readJsonFromUrl("http://saygintufan.com/chatbot/que.json");
        String question = (String) json.get("question");
        System.out.println(question);
        System.out.println(json);
        FileWriter arff = new FileWriter("test.arff");

        arff.append("@relation set-sample\n\n");
        arff.append("@attribute question string\n");
        arff.append(
                "@attribute class { rsp1, rsp2, rsp3, rsp4, rsp5, rsp6, rsp7, rsp8, rsp9, rsp10, rsp11, rsp12, rsp13 ,rsp14, rsp15, rsp16, rsp17, rsp18, rsp19, rsp20, rsp21, rsp22, rsp23, rsp24, rsp25, rsp26, rsp27, rsp28 ,rsp29, rsp30, rsp31, rsp32, rsp33, rsp34, rsp35, rsp36, rsp37, rsp38, rsp39, rsp40, rsp41, rsp42, rsp43, rsp44, rsp45, rsp46, rsp47, rsp48, rsp49, rsp50, rsp51, rsp52, rsp53, rsp54, rsp55, rsp56, rsp57, rsp58, rsp59, rsp60, rsp61, rsp62, rsp63, rsp64, rsp65, rsp66, rsp67, rsp68, rsp69, rsp70, rsp71, rsp72, rsp73, rsp74, rsp75, rsp76, rsp77, rsp78, rsp79, rsp80, rsp81, rsp82, rsp83, rsp84, rsp85, rsp86, rsp87, rsp88, rsp89, rsp90, rsp91, rsp92, rsp93, rsp94, rsp95, rsp96, rsp97, rsp98, rsp99, rsp100 ,rsp101, rsp103, rsp104, rsp105, rsp106, rsp107, rsp108, rsp109, rsp110, rsp111, rsp112, rsp113, rsp115, rsp116, rsp117, rsp118}\n");
        arff.append("@data\n");

        arff.append("'" + question + "'" + ",");
        arff.append("?");

        arff.flush();
        arff.close();


        //

        String testFile = "C://Users/saygintufan/Desktop/SayginTUFAN/javaProject/test.arff";



        while(true)	{

            if(new File(testFile).exists() && !new File("lock.txt").exists())	{
                new File(lock).createNewFile();
                Path path = Paths.get(lock);
                Files.setAttribute(path, "dos:hidden", Boolean.TRUE, LinkOption.NOFOLLOW_LINKS);

                List<String> allLines = Files.readAllLines(Paths.get(testFile));
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
