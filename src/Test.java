import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.*;


public class Test {
    public static void main(String[] args) {
            try {
                JSONObject jo = (JSONObject) new JSONParser().parse(new FileReader("catalog.json"));
                Catalog c = Catalog.getInstance();
                JSONArray ja = (JSONArray) jo.get("courses");
                Iterator itr = ja.iterator();
                int type = 0;
                while(itr.hasNext()){
                    Iterator<Map.Entry> aux = ((Map) itr.next()).entrySet().iterator();
                    PartialCourse.PartialCourseBuilder builder = new PartialCourse.PartialCourseBuilder();
                    FullCourse.FullCourseBuilder builder2 = new FullCourse.FullCourseBuilder();
                    builder.gradeTree(new TreeSet<>());
                    builder2.gradeTree(new TreeSet<>());
                    while(aux.hasNext()){
                        Map.Entry pair = aux.next();
                        if(pair.getKey().equals("name")){
                            builder.courseName(pair.getValue().toString());
                            builder2.courseName(pair.getValue().toString());
                        }
                        if(pair.getKey().equals("teacher")){
                            Map names = (Map) pair.getValue();
                            StringBuilder str = new StringBuilder();
                            for(Object ent: names.entrySet()){
                                str.append(((Map.Entry)ent).getValue().toString());
                                str.append(" ");
                            }
                            String[] nm = str.toString().split(" ");
                            builder.titular(new Teacher(nm[0], nm[1]));
                            builder2.titular(new Teacher(nm[0], nm[1]));
                        }
                        if(pair.getKey().equals("strategy")){
                            switch (pair.getValue().toString()) {
                                case "BestPartialScore" -> {
                                    builder.strategy(new BestPartialScore());
                                    builder2.strategy(new BestPartialScore());
                                }
                                case "BestExamScore" -> {
                                    builder.strategy(new BestExamScore());
                                    builder2.strategy(new BestExamScore());
                                }
                                case "BestTotalScore" -> {
                                    builder.strategy(new BestTotalScore());
                                    builder2.strategy(new BestTotalScore());
                                }
                            }
                        }
                        if(pair.getKey().equals("assistants")){
                            HashSet<Assistant> rez = new HashSet<>();
                            Iterator itrInner = ((JSONArray)pair.getValue()).iterator();
                            String fn, ln;
                            while(itrInner.hasNext()){
                               Object aux2 = itrInner.next();
                               if(aux2 instanceof JSONObject){
                                   fn = ((JSONObject) aux2).get("firstName").toString();
                                   ln = ((JSONObject) aux2).get("lastName").toString();
                                   rez.add(new Assistant(fn, ln));
                               }
                            }
                            builder.astTree(rez);
                            builder2.astTree(rez);
                        }
                        if(pair.getKey().equals("type")){
                            if(pair.getValue().equals("PartialCourse"))
                                type = 1;
                            else
                                type = 2;
                        }
                        HashMap<String, Group> gr = new HashMap<>();
                        if(pair.getKey().equals("groups")){
                            Group g = null;
                            Iterator itrInner = ((JSONArray)pair.getValue()).iterator();
                            while(itrInner.hasNext()){
                                Iterator<Map.Entry> aux2 = ((Map) itrInner.next()).entrySet().iterator();
                                while(aux2.hasNext()) {
                                    String ID = "";
                                    Assistant a = null;
                                    Map.Entry pair2 = aux2.next();
                                    if(pair2.getKey().equals("ID"))
                                    {
                                        ID = pair2.getValue().toString();
                                        g.setID(ID);
                                    }
                                    if(pair2.getKey().equals("assistant")){
                                        Map names = (Map) pair2.getValue();
                                        StringBuilder str = new StringBuilder();
                                        for(Object ent: names.entrySet()){
                                            str.append(((Map.Entry)ent).getValue().toString());
                                            str.append(" ");
                                        }
                                        String[] nm = str.toString().split(" ");
                                        a = new Assistant(nm[0], nm[1]);
                                        g = new Group(ID, a);
                                    }
                                    if(pair2.getKey().equals("students")){
                                        Iterator itr2 = ((JSONArray)pair2.getValue()).iterator();
                                        while(itr2.hasNext()){
                                            String fn, ln;
                                            JSONObject next = (JSONObject) itr2.next();
                                            fn = next.get("firstName").toString();
                                            ln = next.get("lastName").toString();
                                            g.add(new Student(fn, ln));
                                        }

                                    }
                                    if(!g.isEmpty())
                                        gr.put(g.getID(), g);
                                }
                            }
                            builder.grupe(gr);
                            builder2.grupe(gr);
                        }
                        System.out.println(pair.getKey() + " " + pair.getValue());
                    }
                    if(type == 1)
                        c.addCourse(builder.build());
                    else
                        c.addCourse(builder2.build());
                }
                ScoreVisitor scvis = new ScoreVisitor();
                ja = (JSONArray) jo.get("examScores");
                itr = ja.iterator();
                while(itr.hasNext()){
                    Teacher t = null;
                    Tuple<Student, String, Double> triplet = Tuple.of(null, "", 0.0);
                    Iterator<Map.Entry> aux = (((Map)itr.next()).entrySet()).iterator();
                    while(aux.hasNext()){
                        Map.Entry pair = aux.next();
                        if(pair.getKey().equals("teacher")){
                            Map names = (Map) pair.getValue();
                            StringBuilder str = new StringBuilder();
                            for(Object ent: names.entrySet()){
                                str.append(((Map.Entry)ent).getValue().toString());
                                str.append(" ");
                            }
                            String[] nm = str.toString().split(" ");
                            t = new Teacher(nm[0], nm[1]);
                        }
                        if(pair.getKey().equals("student")){
                            Map names = (Map) pair.getValue();
                            StringBuilder str = new StringBuilder();
                            for(Object ent: names.entrySet()){
                                str.append(((Map.Entry)ent).getValue().toString());
                                str.append(" ");
                            }
                            String[] nm = str.toString().split(" ");
                            triplet.setVal1(new Student(nm[0], nm[1]));
                        }
                        if(pair.getKey().equals("course")){
                            triplet.setVal2(pair.getValue().toString());
                        }
                        if(pair.getKey().equals("grade")){
                            Double num = Double.parseDouble(pair.getValue().toString());
                            triplet.setVal3(num);
                        }
                        scvis.getExamScore().put(t, triplet);
                        scvis.visit(t);
                    }
                }
                ja = (JSONArray) jo.get("partialScores");
                itr = ja.iterator();
                while(itr.hasNext()){
                    Assistant a = null;
                    Tuple<Student, String, Double> triplet = Tuple.of(null, "", 0.0);
                    Iterator<Map.Entry> aux = (((Map)itr.next()).entrySet()).iterator();
                    while(aux.hasNext()){
                        Map.Entry pair = aux.next();
                        if(pair.getKey().equals("assistant")){
                            Map names = (Map) pair.getValue();
                            StringBuilder str = new StringBuilder();
                            for(Object ent: names.entrySet()){
                                str.append(((Map.Entry)ent).getValue().toString());
                                str.append(" ");
                            }
                            String[] nm = str.toString().split(" ");
                            a = (Assistant)  new UserFactory().getUser("Assistant", nm[0], nm[1]);
                        }
                        if(pair.getKey().equals("student")){
                            Map names = (Map) pair.getValue();
                            StringBuilder str = new StringBuilder();
                            for(Object ent: names.entrySet()){
                                str.append(((Map.Entry)ent).getValue().toString());
                                str.append(" ");
                            }
                            String[] nm = str.toString().split(" ");
                            triplet.setVal1((Student)new UserFactory().getUser("Student", nm[0], nm[1]));
                        }
                        if(pair.getKey().equals("course")){
                            triplet.setVal2(pair.getValue().toString());
                        }
                        if(pair.getKey().equals("grade")){
                            Double num = Double.parseDouble(pair.getValue().toString());
                            triplet.setVal3(num);
                        }
                        scvis.getPartialScore().put(a, triplet);
                        scvis.visit(a);
                    }
                }
                for(Course crs: c.courseList){
                    System.out.println(crs.getCourseName());
                    System.out.println("Profesor " + crs.getTitular());
                    for(Assistant a: crs.getAstTree()){
                        System.out.println("Asistent " + a);
                    }
                    for(Map.Entry<String, Group> ent: crs.getGrupe().entrySet()){
                        System.out.println(ent.getValue().getID() + " " +
                                ent.getValue() + " asistati de " + ent.getValue().getAst());
                    }
                    System.out.println("/////GRADES BELOW/////");
                    crs.makeBackup();
                    crs.undo();
                    for(Grade g : crs.getGradeTree()){
                        System.out.println(g);
                    }
                    System.out.println("*********");
                    System.out.println("BEST STUDENT ------------> " + crs.getBestStudent());
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
    }
}
