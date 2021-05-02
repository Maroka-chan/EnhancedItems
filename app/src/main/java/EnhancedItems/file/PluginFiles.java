package EnhancedItems.file;

import EnhancedItems.App;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class PluginFiles {
    private PluginFiles(){}

    private static File dataFolder = App.getPlugin().getDataFolder();
    private static ZipInputStream zip;

    public static void init(){
        try{
            zip = new ZipInputStream(PluginFiles.class.getProtectionDomain().getCodeSource().getLocation().openStream());
            createDirs();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File[] getItemFiles(){
        File itemDir = new File(App.getPlugin().getDataFolder() + "/item/");
        File[] items;
        if(!itemDir.isDirectory() || (items = itemDir.listFiles()) == null)
            return null;
        return items;
    }

    private static void createItemFiles() throws IOException {
        ZipEntry entry;
        String entryPath;
        File targetFile;
        OutputStream outputStream;

        while(( entry = zip.getNextEntry() ) != null){
            entryPath = String.format("/%s", entry.getName());
            targetFile = new File(dataFolder + entryPath);
            if(targetFile.isFile()) continue;

            if(entryPath.startsWith("/item/") &&  entryPath.endsWith(".json")) {
                InputStream initialStream = App.class.getResourceAsStream(entryPath);
                if(initialStream == null) continue;
                byte[] buffer = new byte[initialStream.available()];
                if(initialStream.read(buffer) < 1) continue;

                outputStream = new FileOutputStream(targetFile);
                outputStream.write(buffer);
            }
        }
    }

    private static void createDirs() throws IOException {
        Files.createDirectories(Paths.get(dataFolder + "/item/"));
    }
}
