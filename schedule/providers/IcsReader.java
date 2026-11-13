package schedule.providers;

import java.io.BufferedReader;

public class IcsReader implements AutoCloseable {
    private BufferedReader reader;
    private String bufferedLine = null;
    public IcsReader() {
        reader = new BufferedReader(reader);
    }

    public String readLogicalLine() throws IOException{
        String logicalLine;
        if (bufferedLine != null) {
            logicalLine = bufferedLine;
        } 
        else {
            logicalLine = reader.readLine();
        }
        bufferedLine = null;

        if (logicalLine == null) return null;

        while (true){
            String next = reader.readLine();
            if (next != null && next.startsWith(" ") || next.startsWith("\t")){
                logicalLine += next.substring(1);
                break;
            }
        }
        return logicalLine;
    }
    @Override 
    public void close() throws IOException {
        reader.close();
    }
}
