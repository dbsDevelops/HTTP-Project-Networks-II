package http.project.networks.ii;
import java.net.*;
import java.io.*;

public class GreetServer {
    
    APITeachers apiTeachers;
    //List<Socket> connections;
    public GreetServer() {
        this.apiTeachers = new APITeachers();
        this.apiTeachers.teachersMockData();
        //this.connections = new ArrayList<>();
    }

    public void initServer() {

        int port = HTTPUtils.HTTP_PORT;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.err.println("Server is running on port: "+port);
            while(true){
                Socket clientSocket = serverSocket.accept();
                System.err.println("Client connected");
                new Thread(new Runnable(){
                    @Override
                    public void run() {   
                        //System.out.println("Runnable");                 
                        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){
                            String requeststr = handleRequest(in);
                            //SEND RESPONSE
                            OutputStream clientOutput = clientSocket.getOutputStream();
                            Request request = Request.parse(requeststr);
                            response(clientOutput, request);
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }    
                    }
                }).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + port);
            System.exit(-1);
        }

    }

    private String handleRequest(BufferedReader in) throws IOException {
        String s;
        String requeststr = "";
        int requestParts = 0;
            while((s = in.readLine())!=null){
                requeststr += s + "\n";

                //System.out.println(s);

                if(s.isEmpty()){
                    requestParts++;
                }

                if(requestParts >= 2){
                    break;
                }
                    
            }
        return requeststr;
    }

    private void response(OutputStream clientOutput, Request request) throws IOException {

        String response = "";

        String[] urlParts = request.url.getPath().split("/");
                
        if (urlParts.length > 1) {
            if(urlParts[1].equals("teachers")){
                response = apiTeachers.readRequest(request);
            }
            
        }
        else {
            // teapot response
            response = new Response(418, "I'm a teapot", "I'm a teapot").toString();
        }
        
        System.out.println("Response: \n" + response);

        clientOutput.write(response.getBytes());
    
        clientOutput.flush();
        clientOutput.close();
        System.err.println("Client connection closed!");
    }

    //public String getUrlString
}

