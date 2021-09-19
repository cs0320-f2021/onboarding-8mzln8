package edu.brown.cs.student.main;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;

import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

  // use port 4567 by default when running server
  private static final int DEFAULT_PORT = 4567;

  //Store Global variable field for the list of stars for naive_neighbours
  private List<Star> starList;
  private List<Star> sortedKList;



  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) throws Exception {
    new Main(args).run();
  }



  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() throws Exception {
    // set up parsing of command line flags
    OptionParser parser = new OptionParser();

    // "./run --gui" will start a web server
    parser.accepts("gui");

    // use "--port <n>" to specify what port on which the server runs
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
        .defaultsTo(DEFAULT_PORT);

    OptionSet options = parser.parse(args);
    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }


    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      String input;
      while ((input = br.readLine()) != null) {
        try {
            input = input.trim();
            String[] arguments = input.split(" ");


            MathBot mathbot = new MathBot(); //initialize mathbot

            //make sure it is part of inputs
            if ( !(arguments[0].equals("add")) && !(arguments[0].equals("subtract")) &&
                    !(arguments[0].equals("stars")) && !(arguments[0].equals("naive_neighbors"))
            ) { //not add, stars, subtract or naive_neighbors
                System.out.println("ERROR: command" + " '" + arguments[0] + "' "
                        + "not identifiable, please try again!");
            }
            for (int i = 0; i < arguments.length; i++) { //loop through query

                if (arguments[i].equals("add")) { //add operand
                    try {
                        Double ansAdd = mathbot.add(Double.parseDouble(arguments[i + 1]),
                                Double.parseDouble(arguments[i + 2]));
                        System.out.println(ansAdd);
                    } catch (Exception e) { //throw exception if incorrect type/number of arguments
                        System.out.println("ERROR: Incorrect type/number of arguments!");
                    }

                }
                else if (arguments[i].equals("subtract")) { //subtract operand
                    try {
                        Double ansSub = mathbot.subtract(Double.parseDouble(arguments[i + 1]),
                                Double.parseDouble(arguments[i + 2]));
                        System.out.println(ansSub);
                    } catch (Exception e) { //throw exception if incorrect type/number of arguments
                        System.out.println("ERROR: Incorrect type/number of arguments!");
                    }

                }
                else if (arguments[i].equals("stars")) {
                    try {

                        //load in star position information
                        String starPath = arguments[i + 1];
                        BufferedReader br2 = new BufferedReader(new FileReader(starPath));
                        String row = "";


                        //initalize list of stars
                        starList = new ArrayList<Star>();

                        // looping through rows
                        br2.readLine(); //read through category row
                        while ((row = br2.readLine()) != null) {
                            String[] columns = row.split(","); //split row into string

                            Star rowStar = new Star(  //create star with each field
                                    Integer.parseInt(columns[0]),
                                    columns[1],
                                    Double.parseDouble(columns[2]),
                                    Double.parseDouble(columns[3]),
                                    Double.parseDouble(columns[4])
                            );
                            starList.add(rowStar); //add star to list
                        }

//                        System.out.println("Search results for" + " '" + starPath + "' " + ":");
//                          System.out.println(starList);
                        System.out.println("Read" +
                                " " + Integer.toString(starList.size()) + " "
                                + "stars from" + " " + starPath);

                    }
                    catch (FileNotFoundException e) { //throw exception when file not found
                        System.out.println("ERROR: File not found!");
                    }
                    catch (IOException e) {
                        System.out.println("ERROR: Incorrect Input!");
                    }
                    catch (NumberFormatException e) {
                        System.out.println("ERROR: Incorrect number of Columns!");
                    }

                }

                else if (arguments[i].equals("naive_neighbors")) {
                    //first check if starsList has been loaded, if it hasn't print a complaint
                    if (starList == null) {
                        System.out.println("ERROR: Empty starList! Please run 'stars csvpath command' and try again!");
                    }

                    if(Integer.parseInt(arguments[i + 1]) > starList.size()) {
                        System.out.println("ERROR: k val greater than starlist!");
                    }



                        //naive_neighbors <k> <x> <y> <z> is 5 args
                        if (arguments.length == 5) {
                            try {

                                //calculate the relative distance for each star in list
                                for (Star s : starList) {
                                    s.calcReldistance(Double.parseDouble(arguments[2]),
                                            Double.parseDouble(arguments[3]),
                                            Double.parseDouble(arguments[4])
                                    );
                                }

                                //sort the list
                                List<Star> sortedList = starList.stream().
                                        sorted(Comparator.comparingDouble(Star::getRelDistance)).
                                        collect(Collectors.toList());



                                if ( (Integer.parseInt(arguments[1]) == 0)) { //o
                                    // if k is zero just return nothing
                                    break;

                                }


                                //obtain sublist
                                    sortedKList = sortedList.subList(0,
                                            Integer.parseInt(arguments[i + 1]));


                                    //print out first k distances
                                    for (int ss = 0; ss < Integer.parseInt(arguments[i + 1]) ; ss++) {
                                        System.out.println(sortedKList.get(ss).getID());
                                    }
                                // if k is zero just return nothing
                            } catch (NumberFormatException e) { //exception thrown if k val messed up
//                                    e.printStackTrace();
                                System.out.println("ERROR: please check parameters!");
                            }

                        }

                    //naive_neighbors <k> <“name”> stars with ""
                    //third and last element start and end with dashes
                    if ( (arguments[i + 2].startsWith("\"")) &&
                       (arguments[arguments.length - 1].endsWith("\"")) ) {

                nname:
                    try {
                        //build name argument
                        StringBuffer StarPNBuff=new StringBuffer();
                        for(int z = i + 2; z < arguments.length; z++) {
                            StarPNBuff.append(arguments[z]);
//
                        }

                        String starPNQuery = StarPNBuff.toString();

                        //remove quotes
                        String noQuotesStarPN = starPNQuery.substring(1, starPNQuery.length() - 1);

                        //store initial length for later
                        int initial = starList.size();
                        //loop through stars to find the star with name in star list
                        for (Star s : starList) {

                            String pnNoWhiteSpace = s.getPN().replaceAll("\\s", "");
                            if (pnNoWhiteSpace.equals(noQuotesStarPN)) {

                                //calculate distance relative to s for each star q in starList (excluding s)
                                for (Star q : starList) {
                                    q.calcReldistance(s.getX(), s.getY(), s.getZ());

                                }

                                //if the size of starList is 1 (0 since we removed)
                                // , then we just return that lonley stars ID:
                                if(starList.size() == 0) {
                                    System.out.println(s.getID());
                                    break nname;
                                }

                                //get rid of the same element
                                starList.remove(s);
                                    break  ;





                            }
                        }

                        //store final length for later
                        int fin = starList.size();
                        //if the length is unchanged than
                        if(fin == initial) {
                            System.out.println("ERROR: star does not exist in csv");
                            break nname;
//                            System.out.println("ERROR: star" +
//                                    " " + noQuotesStarPN + " "
//                                    + "does not exist in csv" );
                        }



                        //sort the list
                        List<Star> sortedList = starList.stream().
                                sorted(Comparator.comparingDouble(Star::getRelDistance)).
                                collect(Collectors.toList());


                        if (Integer.parseInt(arguments[i + 1]) != 0) { //k is not zero
                            //obtain sublist
                            sortedKList = sortedList.subList(0,
                                    Integer.parseInt(arguments[i + 1]));


                            //print out first k distances
                            for (int ss = 0; ss < Integer.parseInt(arguments[i + 1]); ss++) {
                                System.out.println(sortedKList.get(ss).getID());
                            }


                        }



                    } catch (Exception e) {
//                            e.printStackTrace();
                        System.out.println("ERROR: Please check star name and k value!");
                    }

                    }

                }
            }
        } catch (Exception e) {
          // e.printStackTrace();
          System.out.println("ERROR: We couldn't process your input");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("ERROR: Invalid input for REPL");
    }

  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration(Configuration.VERSION_2_3_0);

    // this is the directory where FreeMarker templates are placed
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    // set port to run the server on
    Spark.port(port);

    // specify location of static resources (HTML, CSS, JS, images, etc.)
    Spark.externalStaticFileLocation("src/main/resources/static");

    // when there's a server error, use ExceptionPrinter to display error on GUI
    Spark.exception(Exception.class, new ExceptionPrinter());

    // initialize FreeMarker template engine (converts .ftl templates to HTML)
    FreeMarkerEngine freeMarker = createEngine();

    // setup Spark Routes
    Spark.get("/", new MainHandler(), freeMarker);
  }

  /**
   * Display an error page when an exception occurs in the server.
   */
  private static class ExceptionPrinter implements ExceptionHandler<Exception> {
    @Override
    public void handle(Exception e, Request req, Response res) {
      // status 500 generally means there was an internal server error
      res.status(500);

      // write stack trace to GUI
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }

  /**
   * A handler to serve the site's main page.
   *
   * @return ModelAndView to render.
   * (main.ftl).
   */
  private static class MainHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      // this is a map of variables that are used in the FreeMarker template
      Map<String, Object> variables = ImmutableMap.of("title",
          "Go go GUI");

      return new ModelAndView(variables, "main.ftl");
    }
  }
}
