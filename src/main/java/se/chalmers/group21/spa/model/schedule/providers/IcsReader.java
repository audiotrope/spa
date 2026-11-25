package se.chalmers.group21.spa.model.schedule.providers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class IcsReader implements AutoCloseable {
    private BufferedReader reader;
    private String bufferedLine = null;
    public IcsReader(Reader r) {
        r = new BufferedReader(r);
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
