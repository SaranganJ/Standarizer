
import java.io.*;
import java.sql.*;
import java.util.ArrayList;



public class Sample {

    public static void main(String... args) throws Exception {

        Sample s1=new Sample();
        s1.execute();
//        System.out.println(System.currentTimeMillis());
//        System.out.println((153932756.7*10000));
//
//        System.out.println(System.currentTimeMillis() - (153932756.7*10000) );

    }

    ArrayList<Long>xValue_standard_array=new ArrayList<Long>();
    ArrayList<Long>xValue_standard_array_s=new ArrayList<Long>();

    ArrayList<Long>tmp_standard_array=new ArrayList<Long>();

    ArrayList<Double>CPU_Usage_time_array=new ArrayList<Double>();
    ArrayList<Double>Network_sent_packets_LBT_array=new ArrayList<Double>();
    ArrayList<Double>Network_sent_packets_LBF_array=new ArrayList<Double>();
    ArrayList<Double>Network_received_packets_LBT_array=new ArrayList<Double>();
    ArrayList<Double>Network_received_packets_LBF_array=new ArrayList<Double>();
    ArrayList<Double>Network_sent_bytes_LBT_array=new ArrayList<Double>();
    ArrayList<Double>Network_sent_bytes_LBF_array=new ArrayList<Double>();
    ArrayList<Double>Network_received_bytes_LBT_array=new ArrayList<Double>();
    ArrayList<Double>Network_received_bytes_LBF_array=new ArrayList<Double>();
    ArrayList<Double>CPU_Utilization_array=new ArrayList<Double>();
    ArrayList<Double>memory_Usage_array=new ArrayList<Double>();
    ArrayList<Double>CPU_Usage_array_Cont=new ArrayList<Double>();
    ArrayList<Double>CPU_Usage_array_Cont_final=new ArrayList<Double>();

    ArrayList<Double>CPU_util_temp_array=new ArrayList<Double>();
    ArrayList<Double>memory_Usage_temp_array=new ArrayList<Double>();
    ArrayList<Double>CPU_Usage_CONT_temp_array=new ArrayList<Double>();


    public void execute() throws  IOException{

        Sample s=new Sample();

        s.sfileRead(new File("/home/sarangan/Applications/Results/EmailProcessor/26-09-2018:Test1(15min)/Node1/Node_Level_Metrics/CPU_Usage_Time.csv"),CPU_Usage_time_array,tmp_standard_array);
        standardize(tmp_standard_array,xValue_standard_array,xValue_standard_array_s);
        s.fileRead(new File("/home/sarangan/Applications/Results/EmailProcessor/26-09-2018:Test1(15min)/Node1/Node_Level_Metrics/Network_Sent_Packets_count_LBT.csv"),Network_sent_packets_LBT_array);
        s.fileRead(new File("/home/sarangan/Applications/Results/EmailProcessor/26-09-2018:Test1(15min)/Node1/Node_Level_Metrics/Network_Sent_Packets_count_LBF.csv"),Network_sent_packets_LBF_array);
        s.fileRead(new File("/home/sarangan/Applications/Results/EmailProcessor/26-09-2018:Test1(15min)/Node1/Node_Level_Metrics/Network_Received_Packets_count_LBT.csv"),Network_received_packets_LBT_array);
        s.fileRead(new File("/home/sarangan/Applications/Results/EmailProcessor/26-09-2018:Test1(15min)/Node1/Node_Level_Metrics/Network_Received_Packets_count_LBF.csv"),Network_received_packets_LBF_array);
        s.fileRead(new File("/home/sarangan/Applications/Results/EmailProcessor/26-09-2018:Test1(15min)/Node1/Node_Level_Metrics/Network_Received_Bytes_count_LBT.csv"),Network_received_bytes_LBT_array);
        s.fileRead(new File("/home/sarangan/Applications/Results/EmailProcessor/26-09-2018:Test1(15min)/Node1/Node_Level_Metrics/Network_Received_Bytes_count_LBF.csv"),Network_received_bytes_LBF_array);
        s.fileRead(new File("/home/sarangan/Applications/Results/EmailProcessor/26-09-2018:Test1(15min)/Node1/Node_Level_Metrics/Network_Sent_Bytes_count_LBT.csv"),Network_sent_bytes_LBT_array);
        s.fileRead(new File("/home/sarangan/Applications/Results/EmailProcessor/26-09-2018:Test1(15min)/Node1/Node_Level_Metrics/Network_Sent_Bytes_count_LBT.csv"),Network_sent_bytes_LBF_array);
        s.fileRead_cpuutil(new File("/home/sarangan/Applications/Results/EmailProcessor/26-09-2018:Test1(15min)/Node1/Node_Level_Metrics/CPU_Utilization.csv"),CPU_Utilization_array,CPU_util_temp_array);
        s.fileRead_memory_usage(new File("/home/sarangan/Applications/Results/EmailProcessor/26-09-2018:Test1(15min)/Node1/Container_Level_Metrics/Memory_Bytes_Used.csv"),memory_Usage_array,memory_Usage_temp_array);
        s.fileRead_cpu_usage_cont(new File("/home/sarangan/Applications/Results/EmailProcessor/26-09-2018:Test1(15min)/Node1/Container_Level_Metrics/CPU_Usage_Time.csv"),CPU_Usage_array_Cont,CPU_Usage_CONT_temp_array,CPU_Usage_array_Cont_final);

        File file_csv_node=new File("/home/sarangan/Applications/Node_level_Metrics.csv");
        File file_csv_container=new File("/home/sarangan/Applications/Container_level_Metrics.csv");
        File file_csv_Siddi=new File("/home/sarangan/Applications/Siddhi_level_Metrics.csv");


        if (!file_csv_node.exists()) {
            file_csv_node.createNewFile();
        }

        filewrite(file_csv_node,"Node");
        filewrite(file_csv_container,"Container");
        filewrite(file_csv_Siddi,"Siddhi");





    }

    public void standardize(ArrayList<Long>array,ArrayList<Long>array2,ArrayList<Long>array3){
        long j=array.get(0);
        for (long l:array){
            long diff=j-l;
            array2.add(diff);
            array3.add(diff/60);
        }
    }






    public void sfileRead(File source_file,ArrayList<Double>array,ArrayList<Long>tmp_standard_array) throws IOException{

        int j=0;

        try (BufferedReader reader = new BufferedReader(new FileReader(source_file))) {
            while (true) {
                String line = reader.readLine();


                try {
                    if (line.contains("Start") || line.contains("gke")) {
                        continue;
                    }
                }
                catch(NullPointerException e){}

                if (line == null) {
                   break; }


                try {
                    long  avgtimestamp=Integer.parseInt(line.substring(25, 35));
                    tmp_standard_array.add(avgtimestamp);
                    Double  value=Double.parseDouble(line.substring(37,line.length()-2));
                    array.add(value);

                }
                catch (StringIndexOutOfBoundsException e){}
                catch(NullPointerException e){}
                catch(NumberFormatException e){}



            }
        }

       int i=0;
    }




    public void fileRead(File source_file,ArrayList<Double>array) throws IOException{

        int j=0;

        try (BufferedReader reader = new BufferedReader(new FileReader(source_file))) {
            while (true) {
                String line = reader.readLine();

                try {
                    if (line.contains("compute.googleapis.com")) {
                        continue;
                    }
                }
                catch(NullPointerException e){}

                if (line == null) {
                    j=j+1; }

                if(j==2){
                    break; }



                try {

                    Double  value=Double.parseDouble(line.substring(37,line.length()-2));
                    array.add(value);
                }



                catch (StringIndexOutOfBoundsException e){}
                catch(NullPointerException e){}
                catch(NumberFormatException e){}



            }






        }catch(FileNotFoundException e){}
    }









    public void fileRead_cpuutil(File source_file,ArrayList<Double>array,ArrayList<Double>array2) throws IOException{

        int j=0;

        try (BufferedReader reader = new BufferedReader(new FileReader(source_file))) {
            while (true) {
                String line = reader.readLine();

                try {
                    if (line.contains("compute.googleapis.com")) {
                        continue;
                    }
                }
                catch(NullPointerException e){}

                if (line == null) {
                    j=j+1; }

                if(j==2){
                    break; }



                try {

                    Double  value=Double.parseDouble(line.substring(37,line.length()-2));
                    array2.add(value);
                    if(array2.size()>1){
                        value=(value+array2.get(0))/2;
                        array.add(value);
                        array2.remove(0);

                    }

                }



                catch (StringIndexOutOfBoundsException e){}
                catch(NullPointerException e){}
                catch(NumberFormatException e){}



            }
        }
    }


    public void fileRead_cpu_usage_cont(File source_file,ArrayList<Double>array,ArrayList<Double>array2,ArrayList<Double>array3) throws IOException{

        int j=0;

        try (BufferedReader reader = new BufferedReader(new FileReader(source_file))) {
            while (true) {
                String line = reader.readLine();

                try {
                    if (line.contains("compute.googleapis.com")) {
                        continue;
                    }
                }
                catch(NullPointerException e){}

                if (line == null) {
                    j=j+1; }

                if(j==2){
                    break; }



                try {

                    Double  value=Double.parseDouble(line.substring(37,line.length()-2));
                    array2.add(value);
                    if(array2.size()>1){
                        value=(value+array2.get(0))/2;
                        array2.add(1,value);
                        double value1=(array2.get(0)+array2.get(1))/2;
                        double value2=(array2.get(1)+array2.get(2))/2;

                        array.add(value1);
                        array.add(value2);
                        array2.remove(0);
                        array2.remove(0);

                    }

                }



                catch (StringIndexOutOfBoundsException e){}
                catch(NullPointerException e){}
                catch(NumberFormatException e){}



            }
        }

        for( int i=0;i<array.size()-1;i++){
            try {
                array3.add(array.get(i) - array.get(i+1));
            }
            catch(IndexOutOfBoundsException e){}
        }


    }




    public void fileRead_memory_usage(File source_file,ArrayList<Double>array,ArrayList<Double>array2) throws IOException{

        int j=0;

        try (BufferedReader reader = new BufferedReader(new FileReader(source_file))) {
            while (true) {
                String line = reader.readLine();

                try {
                    if (line.contains("compute.googleapis.com")) {
                        continue;
                    }
                }
                catch(NullPointerException e){}

                if (line == null) {
                    j=j+1; }

                if(j==2){
                    break; }



                try {

                    Double  value=Double.parseDouble(line.substring(37,line.length()-2));
                    array2.add(value);
                    if(array2.size()>1){
                        value=(value+array2.get(0))/2;
                        array2.add(1,value);
                        double value1=(array2.get(0)+array2.get(1))/2;
                        double value2=(array2.get(1)+array2.get(2))/2;

                        array.add(value1);
                        array.add(value2);
                        array2.remove(0);
                        array2.remove(0);

                    }

                }
                catch (StringIndexOutOfBoundsException e){}
                catch(NullPointerException e){
                   }
                catch(NumberFormatException e){}



            }
        }
    }

    public void filewrite(File file, String type) throws  IOException{

        BufferedWriter bw = null;
        FileWriter fw = null;

        if (!file.exists()) {
            file.createNewFile();
        }

        fw = new FileWriter(file.getAbsoluteFile(), true);
        bw = new BufferedWriter(fw);


        if (type=="Node"){

            bw.write("Average Time stamp, Standardized X value(s), Standardized X value(min), CPU Usage Time, CPU Utilization, Network Received Bytes Count(LBT)," +
                    " Network Received Bytes Count(LBF), Network Sent Bytes Count(LBT), Network Sent Bytes Count(LBF)," +
                    " Network Received Packets Count(LBT),  Network Received Packets Count(LBF), Network Sent Packets Count(LBT)," +
                    " Network Sent Packets Count(LBF)");
            bw.write(String.valueOf("\n"));
            bw.write(String.valueOf("\n"));

            bw.flush();



                for (int i = 0; i < CPU_Usage_time_array.size(); i++) {
                    StringBuilder tempLine = new StringBuilder();


                    try{
                    tempLine.append(tmp_standard_array.get(i));
                    tempLine.append(", ");
                    tempLine.append(xValue_standard_array.get(i));
                    tempLine.append(", ");
                    tempLine.append(xValue_standard_array_s.get(i));
                    tempLine.append(", ");
                    tempLine.append(CPU_Usage_time_array.get(i));
                    tempLine.append(", ");
                    tempLine.append(CPU_Utilization_array.get(i));
                    tempLine.append(", ");
                    }
                    catch(IndexOutOfBoundsException e){}

                    try{
                    tempLine.append(Network_received_bytes_LBT_array.get(i));
                    tempLine.append(", ");
                    }
                    catch(IndexOutOfBoundsException e){}

                    try{
                    tempLine.append(Network_received_bytes_LBF_array.get(i));
                    tempLine.append(", ");
                    }
                    catch(IndexOutOfBoundsException e){}

                    try{
                    tempLine.append(Network_sent_bytes_LBT_array.get(i));
                    tempLine.append(", ");
                    }
                    catch(IndexOutOfBoundsException e){}


                    try{
                    tempLine.append(Network_sent_bytes_LBF_array.get(i));
                        tempLine.append(", ");
                    }
                    catch(IndexOutOfBoundsException e){}


                    try{
                    tempLine.append(Network_received_packets_LBT_array.get(i));
                        tempLine.append(", ");
                    }
                    catch(IndexOutOfBoundsException e){}


                    try{
                    tempLine.append(Network_received_packets_LBF_array.get(i));
                        tempLine.append(", ");
                    }
                    catch(IndexOutOfBoundsException e){}


                    try{
                    tempLine.append(Network_sent_packets_LBT_array.get(i));
                        tempLine.append(", ");
                    }
                    catch(IndexOutOfBoundsException e){}


                    try{
                    tempLine.append(Network_sent_packets_LBF_array.get(i));
                    }
                    catch(IndexOutOfBoundsException e){}

                    bw.write(String.valueOf(tempLine));
                    bw.write(String.valueOf("\n"));
                    bw.flush();
                    System.out.println("Node Metrics data written ");
                }


            System.out.println("Node Metrics data written ");
        }

        if (type=="Container"){

            bw.write("Average Time stamp, Standardized X value(s), Standardized X value(min), CPU Usage time,  Memory usage");
            bw.write(String.valueOf("\n"));
            bw.write(String.valueOf("\n"));

            bw.flush();

            try {

                for (int i = 0; i < CPU_Usage_time_array.size(); i++) {
                    StringBuilder tempLine = new StringBuilder();

                    tempLine.append(tmp_standard_array.get(i));
                    tempLine.append(", ");
                    tempLine.append(xValue_standard_array.get(i));
                    tempLine.append(", ");
                    tempLine.append(xValue_standard_array_s.get(i));
                    tempLine.append(", ");
                    tempLine.append(CPU_Usage_array_Cont_final.get(i));
                    tempLine.append(", ");
                    tempLine.append(memory_Usage_array.get(i));
                    bw.write(String.valueOf(tempLine));
                    bw.write(String.valueOf("\n"));
                    bw.flush();
                    System.out.println("Container Metrics data written ");


                }


            }
            catch(IndexOutOfBoundsException e){}
            System.out.println("Container Metrics data written ");
        }

        if(type == "Siddhi"){

              SiddhiMetrics siddhiMetrics= new SiddhiMetrics();

            siddhiMetrics.siddhiProcessing(tmp_standard_array,
                    xValue_standard_array,xValue_standard_array_s,
                    new File("/home/sarangan/Applications/stats/siddhiLevelMetric1_1.csv"),
                    1,1);
            System.out.println("Siddhi Metrics data written");
            siddhiMetrics.siddhiProcessing(tmp_standard_array,
                    xValue_standard_array,xValue_standard_array_s,
                    new File("/home/sarangan/Applications/stats/siddhiLevelMetric1_2.csv"),
                    1,2);
            System.out.println("Siddhi Metrics data written");
            siddhiMetrics.siddhiProcessing(tmp_standard_array,
                    xValue_standard_array,xValue_standard_array_s,
                    new File("/home/sarangan/Applications/stats/siddhiLevelMetric1_3.csv"),
                    1,3);
            System.out.println("Siddhi Metrics data written");
            siddhiMetrics.siddhiProcessing(tmp_standard_array,
                    xValue_standard_array,xValue_standard_array_s,
                    new File("/home/sarangan/Applications/stats/siddhiLevelMetric1_4.csv"),
                    1,4);
            System.out.println("Siddhi Metrics data written");

            siddhiMetrics.siddhiProcessing(tmp_standard_array,
                    xValue_standard_array,xValue_standard_array_s,
                    new File("/home/sarangan/Applications/stats/siddhiLevelMetric2_1.csv"),
                    2,1);
            System.out.println("Siddhi Metrics data written");
            siddhiMetrics.siddhiProcessing(tmp_standard_array,
                    xValue_standard_array,xValue_standard_array_s,
                    new File("/home/sarangan/Applications/stats/siddhiLevelMetric2_2.csv"),
                    2,2);
            System.out.println("Siddhi Metrics data written");
            siddhiMetrics.siddhiProcessing(tmp_standard_array,
                    xValue_standard_array,xValue_standard_array_s,
                    new File("/home/sarangan/Applications/stats/siddhiLevelMetric2_3.csv"),
                    2,3);
            System.out.println("Siddhi Metrics data written");
            siddhiMetrics.siddhiProcessing(tmp_standard_array,
                    xValue_standard_array,xValue_standard_array_s,
                    new File("/home/sarangan/Applications/stats/siddhiLevelMetric2_4.csv"),
                    2,4);
            System.out.println("Siddhi Metrics data written");



            siddhiMetrics.siddhiProcessing(tmp_standard_array,
                    xValue_standard_array,xValue_standard_array_s,
                    new File("/home/sarangan/Applications/stats/siddhiLevelMetric3_1.csv"),
                    3,1);
            System.out.println("Siddhi Metrics data written");
            siddhiMetrics.siddhiProcessing(tmp_standard_array,
                    xValue_standard_array,xValue_standard_array_s,
                    new File("/home/sarangan/Applications/stats/siddhiLevelMetric3_2.csv"),
                    3,2);
            System.out.println("Siddhi Metrics data written");
            siddhiMetrics.siddhiProcessing(tmp_standard_array,
                    xValue_standard_array,xValue_standard_array_s,
                    new File("/home/sarangan/Applications/stats/siddhiLevelMetric3_3.csv"),
                    3,3);
            System.out.println("Siddhi Metrics data written");
            siddhiMetrics.siddhiProcessing(tmp_standard_array,
                    xValue_standard_array,xValue_standard_array_s,
                    new File("/home/sarangan/Applications/stats/siddhiLevelMetric3_4.csv"),
                    3,4);
            System.out.println("Siddhi Metrics data written");

            siddhiMetrics.siddhiProcessing(tmp_standard_array,
                    xValue_standard_array,xValue_standard_array_s,
                    new File("/home/sarangan/Applications/stats/siddhiLevelMetric4_1.csv"),
                    4,1);
            System.out.println("Siddhi Metrics data written");
        }
    }



}
