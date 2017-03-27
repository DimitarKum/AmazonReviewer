package servlet;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.io.File;

import java.util.*;

import model.Review;
import model.Product;
import model.StringProcessor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "MyServlet", 
        urlPatterns = {"/hello"}
    )
public class HelloServlet extends HttpServlet {
    public static final String DATASET_NAME = "Office_Products_5";
    public static final String METADATA_NAME = "meta_Office_Products";

    private final Map<String, List> reviewMap;
    private final Map<String, List> productMap;

    private String fileString;

    public HelloServlet() throws Exception{
        super();
        reviewMap = new HashMap<String, List>();
        productMap = new HashMap<String, List>();
        fileString = "";
        initReviewMap();
        initProductMap();

        // myReview = new Review();
    }

    public void initReviewMap() throws Exception{
        File root = new File(".");
        File dataFile = new File(root, "/resources/data/" + DATASET_NAME + ".json");
        FileReader fileReader = new FileReader(dataFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = null;
        JSONObject jsonObject;
        while((line = bufferedReader.readLine()) != null) {
            jsonObject = (JSONObject) new JSONParser().parse(line);

            Review current = new Review(jsonObject);
            // Review current = new Review((String) jsonObject.get("asin"),
            //                             (String) jsonObject.get("reviewText"),
            //                             (Double) jsonObject.get("overall"),
            //                             (String) jsonObject.get("summary"));

            if(!reviewMap.containsKey(current.getAsin())){
                reviewMap.put(current.getAsin(), new LinkedList());

            }
            reviewMap.get(current.getAsin()).add(current);
            // reviewMap.put(current.getAsin(), current);

            // fileString += (String) jsonObject.get("reviewerName") + '\n';
            // json.add(jsonObject);

        }


        // JSONParser jsonParser = new JSONParser();
        // JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

        // fileString = (String) jsonObject.get("reviewerName");
        // fileString = "fuynny string words";
    }

    public void initProductMap() throws Exception{
        File root = new File(".");
        File dataFile = new File(root, "/resources/data/" + METADATA_NAME + ".json");
        FileReader fileReader = new FileReader(dataFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = null;
        JSONObject jsonObject;

        while((line = bufferedReader.readLine()) != null) {
            jsonObject = (JSONObject) new JSONParser().parse(line);

            Product current = new Product(jsonObject);
            if(!productMap.containsKey(current.getAsin())){
                    productMap.put(current.getAsin(), new LinkedList());
                }
            productMap.get(current.getAsin()).add(current);

        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String asin = req.getParameter("asin");

        String data = "Review search result:<br>";

        if(productMap.containsKey(asin)){
            for(Product current: (List<Product>) productMap.get(asin)){
                data += current.getHtml() + "<br>";
            }
        }else{
            data += "No product found for asin: " + asin + "<br>";
        }

        if(reviewMap.containsKey(asin)){
            List<String> reviewTexts = new LinkedList<String>();

            for(Review current: (List<Review>) reviewMap.get(asin)){
                data += current.getHtml() + "<br>";
                reviewTexts.add(current.getReviewText());
            }

            StringProcessor spc = new StringProcessor(reviewTexts);
            data += spc.analyzeText();
        }else{
            data += "No review found for asin: " + asin + "<br>";
        }



        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(data);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // ServletOutputStream out = resp.getOutputStream();
        // out.write("hello heroku".getBytes());
        // out.flush();
        // out.close();

        // String data = "THIS CAME FROM A JAVA FUNCTION!";

        // Random generator = new Random();
        // Object[] reviews = reviewMap.values().toArray();
        // Review randomReview = (Review) reviews[generator.nextInt(reviews.length)];

        String data = "this came from the servlet";
        // String data = randomReview.getReviewText();
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(data);
    }
    


    private static File getRootFolder() {
        try {
            File root;
            String runningJarPath = HelloServlet.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().replaceAll("\\\\", "/");
            int lastIndexOf = runningJarPath.lastIndexOf("/target/");
            if (lastIndexOf < 0) {
                root = new File("");
            } else {
                root = new File(runningJarPath.substring(0, lastIndexOf));
            }
            System.out.println("application resolved root folder: " + root.getAbsolutePath());
            return root;
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }
}
