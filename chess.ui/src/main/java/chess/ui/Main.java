package chess.ui;

import chess.core.ChessMove;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        var w = new GameWindow();

    }

    private static List<String> getResourceFiles(String path) throws IOException
    {
        List<String> filenames = new ArrayList<>();

        try (
                InputStream in = getResourceAsStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(in)))
        {
            String resource;

            while ((resource = br.readLine()) != null)
            {
                filenames.add(resource);
            }
        }

        return filenames;
    }

    private static InputStream getResourceAsStream(String resource)
    {
        final InputStream in
                = getContextClassLoader().getResourceAsStream(resource);

        return in == null ? Main.class.getResourceAsStream(resource) : in;
    }

    private static ClassLoader getContextClassLoader()
    {
        return Thread.currentThread().getContextClassLoader();
    }


}
